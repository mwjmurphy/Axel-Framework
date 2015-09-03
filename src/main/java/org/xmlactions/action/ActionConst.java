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

package org.xmlactions.action;

public class ActionConst
{

	public final static String CONFIGS_LOCATION = "/config/pager/";

	public final static String STARTUP_CONFIG = "/config/spring/spring-pager-startup.xml";

	public final static String SPRING_STARTUP_CONFIG = "classpath:" + STARTUP_CONFIG;
	
	public final static String SPRING_WEB_XML_CONTEXT_PARAM = "contextConfigLocation";
	
	/** Name of bean containing the application property settings */
	//public final static String PROPERTY_CONTAINER_BEAN_REF = "pager.propertyContainer";

	/** Name of execContext created from spring config for each page process */
	public final static String EXEC_CONTEXT_BEAN_REF = "pager.execContext";

	/** Name of bean containing the web path (where web pages are stored) */
	public final static String WEB_REAL_PATH_BEAN_REF = "pager.realPath";

	/** Name of bean containing the web root path (the root of the web site) */
	public final static String WEB_ROOT_BEAN_REF = "pager.root";

    /**
     * To stop the processPage from performing a StrSubstution set the
     * NO_STR_SUBST to any value
     */
    public final static String NO_STR_SUBST = "pager.NoStrSubst";

	/**
	 * Name of bean containing the xml page namespace for pager actions e.g
	 * &lt;pager:action...&gt;
	 */
	public final static String PAGE_NAMESPACE_BEAN_REF = "pager.namespace";

	/** The default pager namespace. */
	public final static char[][] DEFAULT_PAGER_NAMESPACE = {"pager".toCharArray(), "axel".toCharArray()};
	

	/**
	 * Used by the pager:list action, each row returned from an xml query is stored into the execContext as a map by
	 * this name.
	 * <p>
	 * The pager:list action uses this to make the row data available to other action scripts such as add_record_link.
	 * </p>
	 */	
	public final static String ROW_MAP_NAME = "row";

	/**
	 * Used by presentation actions such as JSONToPresentationAction and XMLToPresentionAction to store the total row count into the execContext by this name
	 * <p>
	 * The actions uses this to make the row total codata available to other action scripts such as add_record_link.
	 * </p>
	 */	
	public final static String ROW_TOTAL_COUNT = "row_total_count";
}
