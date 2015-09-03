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


import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.xmlactions.mapping.KeyValueUtils;


public abstract class AbstractPopulateClassFromXml extends KeyValueUtils implements PopulateClassFromXmlInterface {


    public Object getValue(Object object, String propertyName) throws IllegalAccessException,
            InvocationTargetException, NoSuchMethodException, SecurityException, NoSuchFieldException {
        Object objectValue = null;
        Method method = null;
        try {
            // try getting value using method getXXX
            PropertyDescriptor pd = PopulateClassFromXml.findPropertyDescriptor(object, propertyName);
            method = pd.getReadMethod();
            objectValue = method.invoke(object);
        } catch (Exception ex) {
            // try getting value using direct access
            Class<?> c = object.getClass();
            Field field = c.getDeclaredField(propertyName);
            try {
                objectValue = field.get(object);
            } catch (IllegalAccessException ex2) {
                throw new IllegalArgumentException(ex.getMessage() + "\nMethod [" + method.getName() + "] property ["
                        + propertyName + "]", ex);
            }
        }

        return objectValue;
    }
    



}
