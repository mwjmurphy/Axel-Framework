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
