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
