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


import java.util.List;


import org.dom4j.Element;
import org.xmlactions.mapping.KeyValue;


public interface PopulateXmlFromClassInterface {

    /**
     * @param keyvalues
     * @param beanToXml
     * @param parent
     *            - the parent element
     * @param object
     *            - the object we extract the xml from
     * @param namespacePrefix - a namespace if required
     * @param elementName
     *            - the name of the element we create in the xml
     * @param beanRef
     *            - the reference to the bean that matches the object we are
     *            extracting
     * @return - the new xml element that was created. this will also have been
     *         inserted to the parent element.
     */
    public Element performElementAction(List<KeyValue> keyvalues, BeanToXml beanToXml, Element parent, Object object,
            String namespacePrefix, String elementName, String beanRef);

    /**
     * @param keyvalues
     * @param beanToXml
     * @param parent
     *            - the parent element
     * @param object
     *            - the object we extract the xml from
     * @param namespacePrefix - a namespace if required
     * @param attributeName
     *            - the name of the attribute we create in the xml
     * @param beanRef
     *            - the reference to the bean that matches the object we are
     *            extracting
     * @return - the new xml element that was created. this will also have been
     *         inserted to the parent element.
     */
    public Element performAttributeAction(List<KeyValue> keyvalues, BeanToXml beanToXml, Element parent, Object object,
    		String namespacePrefix, String attributeName, String beanRef);

}
