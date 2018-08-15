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
