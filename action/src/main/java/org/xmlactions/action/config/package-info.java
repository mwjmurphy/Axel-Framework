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

package org.xmlactions.action.config;
/**
 * \page org_xmlactons_action_config Actions Config
 * 
 * \tableofcontents
 * 
 * \section exec_context Execution Context
 * 
 * <p>
 * An Execution Context is used by the system to manage and maintain a set of static data and also session and request data.
 * See org.xmlactions.action.config.IExecContext and org.xmlactions.action.config.ExecContext
 * </p>
 * <p>
 *  The Execution Context is defined in the spring configuration file.  It has a fixed id of <b>pager.execContext</b>. The id cannot be changed as it is
 *  used to retrieve the Execution Context from the spring configuration.<br/>
 * </p>
 * <p>
 *  This is an example of the spring configuration taken from the axel-web project file <b>test-spring-pager-web-startup.xml</b>
 *  \code
 *    <bean id="pager.execContext" class="org.xmlactions.pager.context.PersistenceExecContext" scope="prototype">
 *       <constructor-arg>&lt;!-- "actionMaps" --&gt;
 *          &lt;list&gt;
 *             <util:properties location="classpath:config/pager/actions.properties" />
 *          &lt;/list&gt;
 *      </constructor-arg>
 *      <constructor-arg>&lt;!-- "localMaps" --&gt;
 *          &lt;list&gt;
 *             <ref bean="application.xml" />
 *             <util:properties location="classpath:config/project/web.properties" />
 *             <util:properties location="classpath:themes/snippets/html_snippets.properties" />
 *             <util:map>
 *                <entry key="email_client_config" value-ref="email_client_config"/>
 *             </util:map>
 *          &lt;/list&gt;
 *      </constructor-arg>
 *      <constructor-arg>&lt;!-- "themes" --&gt;
 *          &lt;util:list&gt;
 *             <ref bean="blueTheme" />
 *             <ref bean="blackTheme" />
 *             <ref bean="iceTheme" />
 *             <ref bean="grayTheme" />
 *          &lt;/util:list&gt;
 *      </constructor-arg>
 *   </bean>
 *  \endcode
 * </p>
 * <p>
 *  An ExecContext is created by the HttpPager class for each request and stored into the org.xmlactions.web.RequestExecContext filter. Making it available by calling 
 *  org.xmlactions.web.RequestExecContext.get().
 * </p>
 * 
 * 
 * \section language_locale Language Locale
 *
 * <p>
 * Language Locale manages access to the locale files.  When you want to provide language specific information on a web page
 * you use replacement markers such as ${lang:key} that will retrieve the value for "key" from the default language file.
 * </p>
 * 
 * <p>
 * Additional parameters allow for specifying the language file to use, the language,  the country and the country variant.
 * </p> 
 * <p>
 * 	The replacement pattern is <b>"lang:key:resouce:language:country:variant"</b>
 *  <br/> where:
 *  <ul>
 *  <li>"lang" - states that we want a locale language replacement.  This is a required field.</li>
 * 	<li>"key" - is the reference to the property key that we want the replacement value for. This is a required field.</li>
 * 	<li>"resource" - is the name of the resource file to use. This is an optional field and if not set the default language file will be used. Note that the file name extension ".properties" is not used.</li>
 * 	<li>"language" - is the language to use. Example: "en" (English), "ja" (Japanese), "kok" (Konkani). This is an optional field and if not set the default locale language is used.</li>
 * 	<li>"country" - is the country to use. Example: "US" (United States), "FR" (France), "029" (Caribbean). This is an optional field and if not set the default locale language is used.</li>
 * 	<li>"variant" - is the language variant we want to use. Example: "polyton" (Polytonic Greek), "POSIX". This is an optional field and if not set the default locale language is used.</li>
 *  </ul>
 * </p>
 * <p>
 *  Example:
 *  <br/> ${lang:application.title} - Will use the default language properties file and get the value for <b>application.title</b>
 *  <br/> ${lang:application.title:jms_services} - Will use the language properties file <b>jms_services</b> and get the value for <b>application.title</b>
 * </p>
 * <p>
 *  <b>Default Language</b><br/>
 *  The default language can be set using a property with the key <b>default_locale_file</b>.  Usually this is configured in the web.properties file loaded by
 *  the spring configuration at startup<br/>
 *  An example of this setting might look like<br/>
 *   <pre>
 *   default_locale_file=config/lang/default_lang
 *   </pre>
 *   Where <b>config/lang/default_lang</b> is a properties file named <b>default_lang.properties</b><br/>
 *   Do not include the extension ".properties" when setting this in the properties file.
 * </p>
 * 
 * 
 */
