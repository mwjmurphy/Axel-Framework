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

package org.xmlactions.web;

/**
 
 \page org_xmlactons_web_xml_configuration Web Server XML Configuration
 
 \tableofcontents

 \section axel_web_server_xml_configuration AXEL Web Server web.xml Configuration
 
 See \ref org_xmlactons_web_server_spring_configuration to configure spring for loading static data.

 \subsection axel_web_xml_configuration AXEL Web XML Configuration
 
 These instructions show how to configure the <b>web.xml</b> and have the web server process axel requests.
  
 \ref setting_up_servlet_in_web_xml - how to setup the axel HttpPager servlet to service axel requests. 
 
 \ref setting_up_filter_in_web_xml - how to setup a filter to process jsp and axel requests.
 
 \ref setting_up_spring_framework_in_web_xml - how to setup the main spring configuration.
 
 \subsubsection setting_up_servlet_in_web_xml Setting up the servlet
 
 \code{.xml}
 
		<servlet>
			<description>AXEL Servlet</description>
			<servlet-name>axel</servlet-name>
			<servlet-class>org.xmlactions.web.PagerServlet</servlet-class>
			<init-param>
				<param-name>pager.namespace</param-name>
				<param-value>pager</param-value>
			</init-param>
			<load-on-startup>1</load-on-startup>
		</servlet>
		<servlet-mapping>
			<servlet-name>axel</servlet-name>
			<url-pattern>*.axel</url-pattern>
		</servlet-mapping>
		<servlet-mapping>
			<servlet-name>axel</servlet-name>
			<url-pattern>*.soap</url-pattern>
		</servlet-mapping>
		<servlet-mapping>
			<servlet-name>axel</servlet-name>
			<url-pattern>*.ajax</url-pattern>
		</servlet-mapping>
		<servlet-mapping>
			<servlet-name>axel</servlet-name>
			<url-pattern>*.bin</url-pattern>
		</servlet-mapping>

 \endcode
	
	Explanation of <b>Setting up the servlet</b>:
 	<table border="0">
 		<tr>
 			<td><b>xml element</b></td>
 			<td style="padding-left:10px;border-left:1px solid #a4bcea;"><b>element description</b></td>
 		</tr>
 		<tr>
 			<td colspan="3"><hr/></td>
 		</tr>
 		<tr>
 			<td>servlet</td>
 			<td style="padding-left:10px;border-left:1px solid #a4bcea;">The java class that will process the HttpRequest's.  The associated servlet-mapping's define the page types that will be processed by the servlet</td>
 		</tr>
 		<tr>
 			<td colspan="3" height="20px"></td>
 		</tr>
 		<tr>
 			<td width="120px">servlet-mapping</td>
 			<td style="padding-left:10px; border-left:1px solid #a4bcea;">These are the page types that will be serviced as HttpRequests.  The page type refers to the file extension such as <b>.axel</b> etc.<br/><br/>
 				The type of files that are processed in this configuration are<br/>
 				<ul>
 					<li>.axel - web page extensions similar to html pages.</li>
 					<li>.soap - soap requests, these are processed the same as axel pages but the response type is "text/xml;charset=UTF-8"</li>
 					<li>.ajax - ajax requests, these are processed the same as axel pages but can return errors in the response.  The response can have
	 					<ul>
	 						<li>OK: - request processed successfully</li>
	 						<li>ER: - request processed with an error. The error content follows the ER: in the response.  Generally an alert is shown on the requesting browser with the error details.</li>
	 						<li>EX: - request processed with an exception. The exception content follows the EX: in the response.  Generally an alert is shown on the requesting browser with the exception details.</li>
	 					</ul>
 					</li>
 					<li>.bin - binary requests, these are processed the same as axel pages but the response is returned with an appropriate contentType.  The default content type is "image/png" but this can get
 						set differently by the servicing code.
 					</li>
 				</ul>
 			</td>
 		</tr>
 	</table>
 	
 \subsubsection setting_up_filter_in_web_xml  Setting up a servlet filter
 
 A servlet filter will firstly service it's associated file types (such as .jsp's) then service the file through the axel servlet, combining JSPs with AXEL.  
 
 \code{.xml}
 
   <filter>
      <filter-name>PagerFilter</filter-name>
      <filter-class>org.xmlactions.web.PagerFilter</filter-class>
      <init-param>
         <param-name>pager.realPath</param-name>
         <param-value></param-value>
      </init-param>
      <init-param>
         <param-name>pager.namespace</param-name>
         <param-value>pager</param-value>
      </init-param>
   </filter>
   <filter-mapping>
      <filter-name>PagerFilter</filter-name>
      <url-pattern>*.jsp</url-pattern>
   </filter-mapping>
 	
 \endcode
 
	Explanation of <b>Setting up a servlet filter</b>:
 	<table border="0">
 		<tr>
 			<td><b>xml element</b></td>
 			<td style="padding-left:10px;border-left:1px solid #a4bcea;"><b>element description</b></td>
 		</tr>
 		<tr>
 			<td colspan="3"><hr/></td>
 		</tr>
 		<tr>
 			<td>filter</td>
 			<td style="padding-left:10px;border-left:1px solid #a4bcea;">The java class that will process the filter HttpRequest's.  The associated filter-mapping's define the page types that will be processed by the servlet filter</td>
 		</tr>
 		<tr>
 			<td colspan="3" height="20px"></td>
 		</tr>
 		<tr>
 			<td width="120px">filter-mapping</td>
 			<td style="padding-left:10px; border-left:1px solid #a4bcea;">These are the page types that will be serviced as filter HttpRequests.  The page type refers to the file extension such as <b>.jsp</b> etc.<br/><br/>
 				The type of files that are processed in this configuration are<br/>
 				<ul>
 					<li>.jsp - Java Server Pages</li>
 				</ul>
 			</td>
 		</tr>
 	</table>
 
 \subsubsection setting_up_spring_framework_in_web_xml Setting up the Spring Framework Configuration
 
 The Spring Framework is used by AXEL to configure the system.  The configuration includes the Application Context, Themes, Database Connections, Static Data,
  Language files generally any property or xml configurations that are required by the system being developed.  
 
 \code{.xml}
 
    <listener>
        <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
    </listener>
    <listener>
        <listener-class>org.springframework.web.context.request.RequestContextListener</listener-class>
    </listener>

	<context-param>
		<param-name>contextConfigLocation</param-name>
		<param-value>
			classpath:/config/spring/axel-spring-pager-web-startup.xml
		</param-value>
	</context-param>
 	
 \endcode
 
	Explanation of <b>Setting up the Spring Framework Configuration</b>:
 	<table border="0">
 		<tr>
 			<td colspan="3"><hr/></td>
 		</tr>
 		<tr>
 			<td>listener-class</td>
 			<td style="padding-left:10px;border-left:1px solid #a4bcea;">org.springframework.web.context.ContextLoaderListener - Bootstrap listener to start up and shut down Spring's root WebApplicationContext.</td>
 		</tr>
 		<tr>
 			<td colspan="3" height="20px"></td>
 		</tr>
 		<tr>
 			<td>listener-class</td>
 			<td style="padding-left:10px;border-left:1px solid #a4bcea;">org.springframework.web.context.request.RequestContextListener - Enables bean scope of request, session and application.</td>
 		</tr>
 		<tr>
 			<td colspan="3" height="20px"></td>
 		</tr>
 		<tr>
 			<td>context-param</td>
 			<td style="padding-left:10px;border-left:1px solid #a4bcea;">classpath:/config/spring/spring-pager-web-startup.xml - location of the main spring configuration.</td>
 		</tr>
 		<tr>
 			<td colspan="3" height="20px"></td>
 		</tr>
 	</table>
 
*/
