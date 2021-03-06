
package org.xmlactions.web;



import java.io.IOException;

import javax.servlet.FilterConfig;
import javax.servlet.Servlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileUploadException;
import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.pager.context.PersistenceExecContext;
import org.xmlactions.web.conceal.HttpPager;


/**
 * Servlet implementation class PagerServlet
 */
public class PagerServlet extends HttpServlet
{

    private static final long serialVersionUID = 1L;

    private static final Logger log = LoggerFactory.getLogger(PagerServlet.class);

    private static HttpPager httpPager = new HttpPager();

    /**
     * @see HttpServlet#HttpServlet()
     */
    public PagerServlet() {

        super();
    }

    /**
     * @see HttpServlet#HttpServlet()
     */
    public PagerServlet(HttpPager _httpPager) {

        super();
        httpPager = _httpPager;
    }

    /**
     * @deprecated Use RequestExecContext.get();
     */
    public static ApplicationContext getApplicationContext() {
    	throw new IllegalArgumentException("@deprecated. Use RequestExecContext.get();");
    	//return httpPager.getApplicationContext();
    }

    /**
     * @see Servlet#init(ServletConfig)
     */
    public void init(ServletConfig config) throws ServletException {

    	httpPager.init(config);
    }

    /**
     * @see Servlet#init(ServletConfig)
     */
    public void init(FilterConfig filterConfig) {
    	httpPager.init(filterConfig);
    }


    
    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        httpPager.processPage(request, response, null);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {

        doGet(request, response);
    }

    public IExecContext setupExecContext(HttpServletRequest request, HttpServletResponse response)
    throws IOException, FileUploadException {
    	return httpPager.setupExecContext(request, response);
    }
    
    public void processPageFromFilter(ServletRequest request, ServletResponse response, String page)
    throws ServletException, IOException {
    	httpPager.processPageFromFilter(request, response, page);
    }

    /**
     * @deprecated Use RequestExecContext.get();
     */
    public static PersistenceExecContext setupExecContext() throws IOException, FileUploadException {
    	throw new IllegalArgumentException("@deprecated. Use RequestExecContext.get();");
    	//return httpPager.setupExecContext();
    }

}

