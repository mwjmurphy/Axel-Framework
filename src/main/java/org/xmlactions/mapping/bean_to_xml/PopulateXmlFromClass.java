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

package org.xmlactions.mapping.bean_to_xml;


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.xmlactions.action.Action;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.action.config.NoPersistenceExecContext;
import org.xmlactions.common.io.ResourceUtils;


public class PopulateXmlFromClass {
	
	private static char[][] NAMESPACE = {"".toCharArray()};
	
	private static String actionPropertiesFilename = "/config/bean_to_xml.properties";

    private static IExecContext execContext = null;

    /**
     *  This will clear the execContext.
     */
    public static void reset() {
    	execContext = null;
    }
    public static String mapBeanToXml(Object root, String mapDefinitionResourceName) {
		try {
		    if (execContext == null) {
		        execContext = new NoPersistenceExecContext(null, null);
		        execContext.put(org.xmlactions.mapping.bean_to_xml.BeanToXml.MAP_OBJECT_REF, root);
		        execContext.addActions(getActionProperties());
            }

	        Action action = new Action();
            String mappingConfig = action.loadPage(null, mapDefinitionResourceName);

            String page = action.processPage(execContext, mappingConfig);

			return page;
		} catch (Exception e) {
			throw new IllegalArgumentException(e.getMessage(), e);
		}
	}

    private static Properties getActionProperties() {
		try {
			InputStream is = ResourceUtils.getResourceURL(actionPropertiesFilename).openStream();
			Properties actionProperties = new Properties();
			actionProperties.load(is);
			is.close();
			return actionProperties;
		} catch (IOException e) {
			throw new IllegalArgumentException(e.getMessage(), e);
		}
	}

}
