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
 
 \page org_xmlactons_web_server_configuration Web Server Configuration
 
 \tableofcontents

 \section axel_web_server_configuration AXEL Web Server Configuration
 
 <p>
 AXEL is designed to work in a Web Server.  To do this it services specific file types such as <i>.axel or .bin</i>.  To implement AXEL
 in a Web Server you need to configure the <i>web.xml</i> and also setup <i>static data</i> such as themes, language files, database connectors etc.
 
 See<br/>
 \ref axel_web_server_xml_configuration to configure the <i>web.xml</i><br/>
 \ref axel_web_server_spring_configuration to configure the <i>static data</i>.
 </p>
 
 <p>
 To test if the configuration for the web.xml and the static data is working you need to
 1. Start the web server
 2. Enter "host:port://project/show_axel_config.axel" into your browser. Where
 	<ul>
 		<li>host - the host name or ip address if running locally you might use localhost or 127.0.0.1</li>
 		<li>port - the http port of your web server, usually this would be 8080 or 80.</li>
 		<li>project - the ContextRoot of you web application, usually your project name.</li>
 		<li>show_axel_config.axel - the service called when the page is processed by AXEL. This dumps some simple configuration items into the Web Server log file.</li>
 	</ul>
 	If the server is working correctly you will see <b>"Config Copied to Log"</b> on your browser as the response from the server.
 </p> 
  
*/
