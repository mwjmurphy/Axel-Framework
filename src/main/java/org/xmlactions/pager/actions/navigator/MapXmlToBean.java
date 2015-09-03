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

package org.xmlactions.pager.actions.navigator;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


import org.apache.commons.lang.StringUtils;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.action.config.NoPersistenceExecContext;
import org.xmlactions.common.io.ResourceUtils;
import org.xmlactions.mapping.xml_to_bean.MapXmlToBeanUtils;
import org.xmlactions.mapping.xml_to_bean.PopulateClassFromXml;
import org.xmlactions.mapping.xml_to_bean.XmlToBean;

public class MapXmlToBean {

    private XmlToBean xmlToBean;

    private static IExecContext execContext = null;

    private IExecContext getExecContext() throws IOException {
        Properties props = new Properties();
        props.load(ResourceUtils.getResourceURL("/config/xml_to_bean.properties").openStream());
        List list = new ArrayList();
        list.add(props);
        return new NoPersistenceExecContext(list, null);

    }

    public MapXmlToBean(String mapDefinitionResourceName) {

        if (execContext == null) {
            try {
                execContext = getExecContext();
            } catch (Exception ex) {
                throw new IllegalArgumentException(ex.getMessage(), ex);
            }
        }
        try {
            xmlToBean = MapXmlToBeanUtils.createMappingBean(getExecContext(), mapDefinitionResourceName);
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }

    /**
     * 
     * @param xml
     * @return Root Mapped Class
     */
    public Object map(String xml) {
        try {
            if (StringUtils.isEmpty(xml)) {
                throw new IllegalArgumentException("The xml parameter is empty");
            }
            PopulateClassFromXml pop = new PopulateClassFromXml();
            Object clas = pop.mapXmlToBean(xmlToBean, xml);
            return clas;
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }

    }
}
