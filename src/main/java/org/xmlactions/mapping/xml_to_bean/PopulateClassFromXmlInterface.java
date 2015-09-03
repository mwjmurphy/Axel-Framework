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


import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.xmlactions.common.xml.XMLObject;
import org.xmlactions.mapping.KeyValue;



public interface PopulateClassFromXmlInterface {

    /**
     * 
     * @param keyvalues
     *            - a list of KeyValue that can be set from populator
     * @param object
     *            - The object that will have a property set
     * @param propertyName
     *            - the name of the property to set on the object
     * @param value
     *            - the value to set the property to, for arrays etc the value
     *            will be added.
     * @param xo
     *            - XMLObject
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws SecurityException
     * @throws IllegalArgumentException
     * @throws NoSuchFieldException
     */
    public void performAction(List<KeyValue> keyvalues, Object object, String propertyName, Object value, XMLObject xo)
            throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, SecurityException,
            IllegalArgumentException, NoSuchFieldException;
}
