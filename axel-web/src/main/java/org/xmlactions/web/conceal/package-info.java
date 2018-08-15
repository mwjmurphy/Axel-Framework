package org.xmlactions.web.conceal;

/**
 
 \page org_xmlactons_web_conceal_httppager Http Pager Servlet
 
 \tableofcontents

 \section httppager_servicing Servicing Http Requests
 
 The HttpPager class services all axel framwork pages.
 
 For an html page to apply axel xml actions it will usually have an html declaration that would
 look something like:
 <code>
	 <html xmlns="http://www.w3.org/1999/html"
		xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	    xmlns:axel="http://www.xmlactions.org/pager_actions"
		xmlns:db="http://www.xmlactions.org/pager_db_actions"
		xsi:schemaLocation="http://www.xmlactions.org/pager_actions http://www.xmlactions.org/schema/pager_actions.xsd
	                        http://www.xmlactions.org/pager_db_actions http://www.xmlactions.org/schema/pager_db_actions.xsd">
		YOUR PAGE CONTENT GOES HERE
	  </html>
 </code>
 
 Working with JSON and XML you can use json or xml declarations in place of html. These instruct the axel
 framework to remove the declarations before returning the page to the browser.
 
 */
