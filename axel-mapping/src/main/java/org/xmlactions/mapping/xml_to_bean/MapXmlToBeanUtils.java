package org.xmlactions.mapping.xml_to_bean;


import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.xmlactions.action.Action;
import org.xmlactions.action.NestedActionException;
import org.xmlactions.action.actions.BaseAction;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.common.xml.BadXMLException;


public class MapXmlToBeanUtils {

    public static XmlToBean createMappingBean(IExecContext execContext, String mapDefinitionResourceName)
            throws IOException,
            NestedActionException, ClassNotFoundException, InstantiationException, IllegalAccessException,
            InvocationTargetException, NoSuchMethodException, BadXMLException {
        String page = Action.loadPage(null, mapDefinitionResourceName);
        Action action = new Action();
        BaseAction[] actions = action.processXML(execContext, page);
        return (XmlToBean) actions[0];
    }

}
