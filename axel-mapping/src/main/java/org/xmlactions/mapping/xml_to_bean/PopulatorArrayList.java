package org.xmlactions.mapping.xml_to_bean;


import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;


import org.apache.commons.beanutils.BeanUtils;
import org.xmlactions.common.xml.XMLObject;
import org.xmlactions.mapping.KeyValue;

public class PopulatorArrayList extends AbstractPopulateClassFromXml {

    public void performAction(List<KeyValue> keyvalues, Object object, String propertyName, Object value, XMLObject xo)
            throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, SecurityException,
            IllegalArgumentException, NoSuchFieldException {
		
		Object objectValue = getValue(object, propertyName);
		if (objectValue == null) {
			List list = new ArrayList();
			list.add(value);
			BeanUtils.setProperty(object, propertyName, list);
		} else {
			List list = (List) objectValue;
			list.add(value);
		}
	}
	
}
