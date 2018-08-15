package org.xmlactions.mapping.bean_to_xml;


import java.util.List;


import org.dom4j.Element;
import org.xmlactions.mapping.KeyValue;

public class PopulatorFromList extends AbstractPopulateXmlFromClass {

    public Element performElementAction(List<KeyValue> keyvalues, BeanToXml beanToXml, Element parent, Object object,
            String namespacePrefix, String elementName, String beanRef) {
        if (object instanceof List<?>) {
            List<?> list = (List<?>) object;
            //Element element = parent.addElement(elementName);
            for (Object item : list) {
                Bean bean = beanToXml.findBeanByName(beanRef);
                bean.processBean(beanToXml, parent, item);
            }
            //return element;
            return parent;
        } else {
            throw new IllegalArgumentException("The object parameter must be a List<?>");
        }

    }

    public Element performAttributeAction(List<KeyValue> keyvalues, BeanToXml beanToXml, Element parent, Object object,
    		String namespacePrefix, String attributeName, String beanRef) {
        return null;
    }

}
