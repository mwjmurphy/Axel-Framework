package org.xmlactions.pager.actions.navigator;

import org.xmlactions.mapping.bean_to_xml.PopulateXmlFromClass;


public class MapBeanToXml {

    public static String map(Object object, String mapDefinitionResourceName) {
        
        return PopulateXmlFromClass.mapBeanToXml(object, mapDefinitionResourceName);

    }
}
