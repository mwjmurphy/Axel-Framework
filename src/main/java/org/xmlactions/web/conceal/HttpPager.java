/*
 * Copyright (C) Mike Murphy 2003-2015 <mike.murphy@xmlactions.org><mwjmurphy@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.xmlactions.web.conceal;


import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import javax.servlet.FilterConfig;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.text.StrSubstitutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.xmlactions.action.Action;
import org.xmlactions.action.ActionConst;
import org.xmlactions.action.NestedActionException;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.common.xml.BadXMLException;
import org.xmlactions.pager.context.SessionExecContext;
import org.xmlactions.web.HttpParam;
import org.xmlactions.web.PagerWebConst;
import org.xmlactions.web.RequestExecContext;
import org.xmlactions.web.http.HttpSessionInfo;

/**
 * Servlet implementation class PagerServlet
 * <p>
 *  This class is used to manage http page requests.
 * </p>
 * <p>
 *  It uses an IExeContext that <b>must</b> be defined in the Spring ApplicationContext with id <b>"pager.execContext"</b>. This IExecContext is then extended with
 *  additional information such as
 *  <ul>
 *  	<li>execContext.put(ActionConst.WEB_REAL_PATH_BEAN_REF, realPath);</li>
 *  	<li>execContext.put(ActionConst.PAGE_NAMESPACE_BEAN_REF, nameSpace);</li>
 *  	<li>execContext.setSession(request.getSession(true));</li>
 *  	<li>execContext.loadFromPersistence();</li>
 *  	<li>execContext.addNamedMap(IExecContext.PERSISTENCE_MAP, execContext.getPersistenceMap());</li>
 *  	<li>execContext.addNamedMap(PagerWebConst.REQUEST, params);</li>
 *  	<li>execContext.put(PagerWebConst.REQUEST_LIST, paramList);</li>
 *  	<li>execContext.put(PagerWebConst.PAGE_NAME, (pageName.length() > 1 ? pageName.substring(1) : pageName));</li>
 *  	<li>execContext.put(PagerWebConst.PAGE_URI, request.getRequestURI());</li>
 *  	<li>execContext.put(PagerWebConst.PAGE_URL, request.getRequestURL()	.toString());</li>
 *  	<li>execContext.put(PagerWebConst.PAGE_SERVER_NAME, request.getServerName());</li>
 *  	<li>execContext.put(PagerWebConst.PAGE_APP_NAME, appName);</li>
 *  	<li>execContext.put(ActionConst.WEB_ROOT_BEAN_REF, "");</li>
 *  	<li>execContext.put(ActionConst.WEB_ROOT_BEAN_REF, "/" + appName);</li>
 *  	<li>execContext.put(PagerWebConst.HTTP_REQUEST, request);</li>
 *  	<li>execContext.put(PagerWebConst.HTTP_RESPONSE, response);</li>
 *  	<li>execContext.put(PagerWebConst.HTTP_SESSION, request.getSession(true));</li>
 *  	<li>execContext.put(PagerWebConst.EXEC_CONTEXT, execContext);</li>
 * 	</ul>
 *  See PagerWebConst and org.xmlactions.action.ActionConst and org.xmlactions.action.config.IExecContext.
 * </p>
 * <p>
 *  The IExecContext is available from the code by calling org.xmlactions.web.RequestExecContext.get().
 * </p>
 */
public class HttpPager {

	private static final Logger log = LoggerFactory.getLogger(HttpPager.class);
	
	
	private static final String access_licence_file = "/config/pager/access.scr";
	private static final int[] p = new int[] { 2213, 1406, 2151, 375, 7445,
			1666, 613, 2272, 3278, 743, 2619, 480, 967, 3853, 1403, 2270 };

	// private static ApplicationContext applicationContext;

	// web site location on disk
	private static String realPath;

	private static String nameSpace;

	private static String wrapperPage;

	private static String prePage;
	private static String postPage;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public HttpPager() {

		super();
	}

	public static ApplicationContext getApplicationContext(
			ServletContext servletContext) {
		ApplicationContext applicationContext;
		applicationContext = WebApplicationContextUtils
				.getRequiredWebApplicationContext(servletContext);
		return applicationContext;
	}

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {

		String value;

		value = config.getServletContext().getRealPath("");
		if (StringUtils.isNotEmpty(value)) {
			realPath = value;
		}

		value = config.getInitParameter("pager.namespace");
		if (StringUtils.isNotEmpty(value)) {
			nameSpace = value;
		}

		value = config.getInitParameter("pager.wrapperPage");
		if (StringUtils.isNotEmpty(value)) {
			wrapperPage = value;
		}

		value = config.getInitParameter("pager.pre.page");
		if (StringUtils.isNotEmpty(value)) {
			prePage = value;
		}

		value = config.getInitParameter("pager.post.page");
		if (StringUtils.isNotEmpty(value)) {
			postPage = value;
		}

		// applicationContext =
		// WebApplicationContextUtils.getRequiredWebApplicationContext(config.getServletContext());
	}

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(FilterConfig filterConfig) {

		String value;
		value = filterConfig.getInitParameter("pager.realPath");
		if (StringUtils.isEmpty(value)) {
			value = filterConfig.getServletContext().getRealPath("");
		}
		if (StringUtils.isNotEmpty(value)) {
			realPath = value;
		}

		value = filterConfig.getInitParameter("pager.namespace");
		if (StringUtils.isNotEmpty(value)) {
			nameSpace = value;
		}

		value = filterConfig.getInitParameter("pager.wrapperPage");
		if (StringUtils.isNotEmpty(value)) {
			wrapperPage = value;
		}

		value = filterConfig.getInitParameter("pager.pre.page");
		if (StringUtils.isNotEmpty(value)) {
			prePage = value;
		}

		value = filterConfig.getInitParameter("pager.post.page");
		if (StringUtils.isNotEmpty(value)) {
			postPage = value;
		}
	}

	public SessionExecContext setupExecContext(HttpServletRequest request,
			HttpServletResponse response) throws IOException,
			FileUploadException {

		ApplicationContext applicationContext = getApplicationContext(request
				.getSession(true).getServletContext());

		SessionExecContext execContext = (SessionExecContext) applicationContext
				.getBean(ActionConst.EXEC_CONTEXT_BEAN_REF);

		new CreateHandyParams(execContext);

		// Make it available for the scope of this request.
		RequestExecContext.set(execContext);

		execContext.setApplicationContext(applicationContext);

		execContext.put(ActionConst.WEB_REAL_PATH_BEAN_REF, realPath);
		execContext.put(ActionConst.PAGE_NAMESPACE_BEAN_REF, nameSpace);

		execContext.setSession(request.getSession(true));
		execContext.loadFromPersistence();
		execContext.addNamedMap(IExecContext.PERSISTENCE_MAP,
				execContext.getPersistenceMap());
		new CreateUserParams(execContext);

		// FIXME - will want to remove the addNamedMap("request")
		Map<String, Object> params = new HtmlRequestMapper(-1)
				.getRequestParamsAsMap(request);
		if (params != null) {
			execContext.addNamedMap(PagerWebConst.REQUEST, params);
		}
		List<HttpParam> paramList = new HtmlRequestMapper(-1)
				.getRequestParamsAsVector(request);
		if (paramList != null) {
			execContext.put(PagerWebConst.REQUEST_LIST, paramList);
		}
		String pageName = request.getServletPath();
		// remove the leading slash/
		execContext.put(PagerWebConst.PAGE_NAME,
				(pageName.length() > 1 ? pageName.substring(1) : pageName));
		execContext.put(PagerWebConst.PAGE_URI, request.getRequestURI());
		execContext.put(PagerWebConst.PAGE_URL, request.getRequestURL()
				.toString());
		execContext
				.put(PagerWebConst.PAGE_SERVER_NAME, request.getServerName());
		String appName = getAppName(request.getRequestURI());
		execContext.put(PagerWebConst.PAGE_APP_NAME, appName);
		if (StringUtils.isEmpty(appName)) {
			execContext.put(ActionConst.WEB_ROOT_BEAN_REF, "");
		} else {
			execContext.put(ActionConst.WEB_ROOT_BEAN_REF, "/" + appName);
		}
		execContext.put(PagerWebConst.HTTP_REQUEST, request);
		execContext.put(PagerWebConst.HTTP_RESPONSE, response);
		execContext.put(PagerWebConst.HTTP_SESSION, request.getSession(true));
		execContext.put(PagerWebConst.EXEC_CONTEXT, execContext);

		// log.debug(((PropertyContainer)
		// webApplicationContext.getBean("readOnlyProperties")).get("user.home"));
		log.debug("nameSpace:" + nameSpace);
		log.debug("Real Path:" + realPath);
		log.debug("bean count:"
				+ execContext.getApplicationContext().getBeanDefinitionCount());
		for (String beanName : applicationContext.getBeanDefinitionNames()) {
			log.debug("bean:" + beanName);
			execContext.put(beanName, applicationContext.getBean(beanName));
		}
		
		execContext.addNamedMap(PagerWebConst.REQUEST_HEADERS, HttpSessionInfo.getRequestHeaders(request));

		log.info("ExecContext size:" + execContext.size());

		return execContext;
	}

	public static SessionExecContext setupExecContext(
			ServletContext servletContext) throws IOException,
			FileUploadException {

		ApplicationContext applicationContext = getApplicationContext(servletContext);

		SessionExecContext execContext = (SessionExecContext) applicationContext
				.getBean(ActionConst.EXEC_CONTEXT_BEAN_REF);

		new CreateHandyParams(execContext);
		new CreateUserParams(execContext);

		// execContext.reset();
		RequestExecContext.set(execContext);

		// Make it available for the scope of this request.

		execContext.setApplicationContext(applicationContext);

		execContext.put(ActionConst.WEB_REAL_PATH_BEAN_REF, realPath);
		execContext.put(ActionConst.PAGE_NAMESPACE_BEAN_REF, nameSpace);

		// remove the leading slash/
		execContext.put(PagerWebConst.EXEC_CONTEXT, execContext);

		log.debug("nameSpace:" + nameSpace);
		log.debug("Real Path:" + realPath);
		log.debug("bean count:"
				+ execContext.getApplicationContext().getBeanDefinitionCount());
		for (String beanName : applicationContext.getBeanDefinitionNames()) {
			log.debug("bean:" + beanName);
			execContext.put(beanName, applicationContext.getBean(beanName));
		}

		log.info("ExecContext size:" + execContext.size());

		return execContext;
	}

	public void processPage(ServletRequest request, ServletResponse response,
			String page) throws ServletException, IOException {
		IExecContext execContext = null;
		if (response instanceof HttpServletResponse
				&& request instanceof HttpServletRequest) {
			try {
				HttpServletRequest httpServletRequest = (HttpServletRequest) request;
				HttpServletResponse httpServletResponse = (HttpServletResponse) response;

				execContext = setupExecContext(httpServletRequest, httpServletResponse);

				String pageName = httpServletRequest.getServletPath();
				
				if (pageName.indexOf("axelconfig") > 0) {
					PrintWriter out = response.getWriter();
					out.print(buildInfo(httpServletRequest, httpServletResponse));
					out.close();
					return;
				}

				if (! pageName.endsWith(".ajax")) {
					
					String alternatePage = processPrePages(execContext, httpServletRequest, httpServletResponse);
					if ("stop".equals(execContext.getString("pre.page.stop"))) {
						execContext.put("pre.page.stop", "");
						response.setContentType("text/html;charset=UTF-8");
	
						PrintWriter out = response.getWriter();
						out.print(alternatePage);
						out.close();
					}
				}

				log.debug("contextPath:" + httpServletRequest.getContextPath());
				log.debug("URI:" + httpServletRequest.getRequestURI());
				log.debug("root:" + realPath + " page:" + pageName
						+ " wrapperPage:" + wrapperPage);
				log.debug(HttpSessionInfo.sysInfo(httpServletRequest));
				Action action;
				if (pageName.endsWith(".ajax")) {
					page = processAjaxCall(
							httpServletRequest,
							httpServletResponse,
							pageName.substring(1,
									pageName.length() - ".ajax".length()),
							execContext);
					page = StrSubstitutor.replace(page, execContext);
				} else if (pageName.endsWith(".bin")
						|| pageName.endsWith(".pdfbin")) {
					String pn = null;
					if (pageName.endsWith(".pdfbin")) {
						pn = pageName.substring(1, pageName.length()
								- ".pdfbin".length());
					} else {
						pn = pageName.substring(1,
								pageName.length() - ".bin".length());
					}
					page = processAjaxCall(httpServletRequest,
							httpServletResponse, pn, execContext);

					if (page.startsWith("EX:")) {
						PrintWriter out = response.getWriter();
						out.print(page);
						out.close();
					} else {
						byte[] image = (byte[]) execContext.get("image");
						if (pageName.endsWith(".pdfbin")) {
							String outputPdfFileName = execContext.getString("outputFileName");
							String ex = serviceJasperPdfRequest(httpServletResponse, image,	outputPdfFileName);
							if (ex != null) {
								PrintWriter out = response.getWriter();
								out.print(page);
								out.close();
							}
							return;
						} else {

							String responseType = execContext.getString("response_type");
							if (StringUtils.isEmpty(responseType)) {
								responseType = "image/png";
							}
							response.setContentType(responseType);

							processPostPages(execContext, httpServletRequest,
									httpServletResponse);

							InputStream in = new ByteArrayInputStream(image);
							OutputStream out = response.getOutputStream();

							// Copy the contents of the file to the output
							// stream
							byte[] buf = new byte[1024];
							int count = 0;
							while ((count = in.read(buf)) >= 0) {
								out.write(buf, 0, count);
							}
							in.close();
							out.close();
						}
						return;
					}
				} else {
					if (pageName.startsWith("/:")) {
						String wrapperPageName = null;
						int secondColon = pageName.indexOf(':', 2);
						if (secondColon >= 0) {
							wrapperPageName = pageName
									.substring(2, secondColon);
							execContext.put("inner_page",
									"/" + pageName.substring(secondColon + 1));
						} else {
							if (StringUtils.isEmpty(wrapperPage)) {
								throw new ServletException(
										"No wrapper page set, why use :");
							}
							wrapperPageName = wrapperPage;
							execContext.put("inner_page",
									"/" + pageName.substring(2));
						}
						// insert the requested page into the wrapper page
						action = new Action(realPath, wrapperPageName,
								nameSpace);
					} else {
						if (StringUtils.isNotEmpty(wrapperPage)) {
							// we have a base wrapper page defined
							execContext.put("inner_page", "/" + pageName);
							action = new Action(realPath, wrapperPage, nameSpace);
						} else {
							if (pageName.indexOf("show_axel_config")>=0) {
								page = AxelConfig.getAxelConfig(execContext);
								log.info(page);
								page="Config Copied to Log";
								action=null;
							} else {
								// if we don't have a wrapper page we show the requested page
								action = new Action(realPath, pageName, nameSpace);
							}
						}
					}
					if (action != null) {
						if (StringUtils.isNotEmpty(page)) {
							page = action.processPage(execContext, page);
						} else {
							page = action.processPage(execContext);
						}
					}
				}
				
				log.debug("URI:" + httpServletRequest.getRequestURI());
				// log.debug("page:" + page);
				Object object = execContext.get(ActionConst.NO_STR_SUBST);
				// log.debug(ActionConst.NO_STR_SUBST + ":[" + object + "]");
				execContext.put(ActionConst.NO_STR_SUBST, null);

				if (pageName.toLowerCase().endsWith("soap")) {
					response.setContentType(IExecContext.CONTENT_TYPE_XML);

				} else if (pageName.toLowerCase().endsWith("json")) {
						response.setContentType(IExecContext.CONTENT_TYPE_JSON);

				} else {
					response.setContentType(IExecContext.CONTENT_TYPE_HTML);
				}

				processPostPages(execContext, httpServletRequest,
						httpServletResponse);

				// = = =
				// check if there is a contentType value stored in the execContext.  This could have been set by one of the actions such as an action that wants to return a JSON data.
				// = = =
				String contentType = execContext.getString(IExecContext.CONTENT_TYPE_KEY);
				if (StringUtils.isNotBlank(contentType)) {
					response.setContentType(contentType);
//					if (contentType.equals(IExecContext.CONTENT_TYPE_JSON) || contentType.equals(IExecContext.CONTENT_TYPE_XML)) {
//						if (response instanceof HttpServletResponse) {
//							((HttpServletResponse)response).setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
//							((HttpServletResponse)response).setHeader("Pragma", "no-cache"); // HTTP 1.0
//							((HttpServletResponse)response).setDateHeader("Expires", 0); // Proxies.
//						}
//					}
				}
				execContext.remove("content_type");
				PrintWriter out = response.getWriter();
				out.print(page);
				out.close();

			} catch (Throwable t) {
				// TODO review this, use a better way for reporting the error.
				log.info(t.getMessage(), t);
				throw new ServletException(t);
			} finally {
				if (execContext != null) {
					execContext.saveToPersistence();
					RequestExecContext.remove();
				}
			}
		} else {
			String msg =
					"Not processing page. Must be HttpServletRequest not ["
					+ request.getClass().getName()
					+ "] and HttpServletResponse not ["
					+ response.getClass().getName() + "]";

			log.info(msg);
			throw new ServletException(msg);
		}
	}

	private String processAjaxCall(HttpServletRequest request,
			HttpServletResponse response, String pageName,
			IExecContext execContext) {

		try {
			if (pageName.indexOf('/') >= 0) {
				pageName = pageName.substring(pageName.lastIndexOf('/') + 1);
			}
			String xml = "<" + nameSpace + ":" + pageName + " />";
			log.debug("processAjaxCall - pageName:" + pageName + " xml:" + xml);
			Action action = new Action(realPath, null, nameSpace);
			return action.processPage(execContext, xml);
		} catch (Exception ex) {
			return ("EX:" + ex.getMessage() + (ex.getCause() != null ? "\n"
					+ ex.getCause() : ""));
		}

	}

	/**
	 * Process a page from a filter chain.
	 */
	public void processPageFromFilter(ServletRequest request,
			ServletResponse response, String page) throws ServletException,
			IOException {
		if (StringUtils.isEmpty(page)) {
			return;
		}
		processPage(request, response, page);
	}

	private String processPrePages(IExecContext execContext,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) throws IOException,
			NestedActionException, ClassNotFoundException,
			InstantiationException, IllegalAccessException,
			InvocationTargetException, NoSuchMethodException, BadXMLException {

		String page = null;
		String pageName = prePage;
		if (StringUtils.isNotEmpty(pageName)) {
			Action action;
			action = new Action(realPath, pageName, nameSpace);
			page = action.processPage(execContext);
		}
		return page;
	}

	private void processPostPages(IExecContext execContext,
			HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse) throws IOException,
			NestedActionException, ClassNotFoundException,
			InstantiationException, IllegalAccessException,
			InvocationTargetException, NoSuchMethodException, BadXMLException {

		String pageName = postPage;
		if (StringUtils.isNotEmpty(pageName)) {
			Action action;
			action = new Action(realPath, pageName, nameSpace);
			String page = action.processPage(execContext);
		}

	}

	private String getAppName(String uri) {
		if (uri.startsWith("/") && uri.indexOf('/', 1) > 0) {
			String[] parts = uri.split("/");
			return parts[1];
		} else if (uri.startsWith("/") == false && uri.indexOf('/') > 0) {
			String[] parts = uri.split("/");
			return parts[0];
		}
		return uri;

	}

	private String serviceJasperPdfRequest(HttpServletResponse response,
			byte[] pdfImage, String outputPdfFileName) {

		InputStream inputStream = null;
		OutputStream responseOutputStream = null;
		try {
			response.setContentType("application/pdf");
			response.addHeader("Content-Disposition", "attachment; filename="
					+ outputPdfFileName);
			response.setContentLength(pdfImage.length);

			inputStream = new ByteArrayInputStream(pdfImage);
			responseOutputStream = response.getOutputStream();
			int bytes;
			while ((bytes = inputStream.read()) != -1) {
				responseOutputStream.write(bytes);
			}
		} catch (Exception ex) {
			return ("EX:" + ex.getMessage());
		} finally {
			IOUtils.closeQuietly(inputStream);
			// IOUtils.closeQuietly(responseOutputStream);
		}
		return null;
	}
	
	private String buildInfo(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		StringBuilder page = new StringBuilder();
		
		page.append("<html>");
		
		File cd = new File("");
		page.append("<li>cd:" + cd.getAbsolutePath() + "</li>");
		page.append("<li>contextPath:" + httpServletRequest.getContextPath() + "</li>");
		page.append("<li>URI:" + httpServletRequest.getRequestURI() + "</li>");
		page.append("<li>root:" + realPath + " wrapperPage:" + wrapperPage + "</li>");
		page.append("<p>" + HttpSessionInfo.sysInfo(httpServletRequest) + "</p>");

		page.append("</html>");
		return page.toString();
	}

}
