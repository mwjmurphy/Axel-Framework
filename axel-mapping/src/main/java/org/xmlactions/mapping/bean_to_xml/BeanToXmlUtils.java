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
