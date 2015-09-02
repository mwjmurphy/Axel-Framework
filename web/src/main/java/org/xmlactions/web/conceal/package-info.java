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
