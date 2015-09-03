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

import org.apache.commons.lang.StringUtils;
import org.dom4j.Element;

public class BeanToXmlUtils {

	/**
	 * Build an xml element or attribute name. 
	 * @param namespacePrefix - optional namespace prefix
	 * @param name - element or attribute name
	 * @return the name with prepended prefix and ':' seperator.
	 */
	public static String buildName(String namespacePrefix, String name) {
        String out = null;

        if (StringUtils.isNotEmpty(namespacePrefix)) {
            out = namespacePrefix;
        }

        if (StringUtils.isNotEmpty(name)) {
            if (out == null) {
                out = name;
            } else {
                out += ":" + name;
            }
        } else {
        	throw new IllegalArgumentException("Missing name value for element or attribute. namespacePrefix[" + namespacePrefix +"]");
        }
        return out;
	}
	
	public static Element addElement(Element element, String namespacePrefix, String name) {
		return element.addElement(buildName(namespacePrefix, name));
	}

	public static Element addAttribute(Element element, String namespacePrefix, String name, String value) {
		return element.addAttribute(buildName(namespacePrefix, name), value);
	}
}
