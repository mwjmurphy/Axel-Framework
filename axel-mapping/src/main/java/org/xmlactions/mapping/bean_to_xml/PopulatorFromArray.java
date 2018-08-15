package org.xmlactions.mapping.bean_to_xml;


import java.util.List;


import org.apache.commons.lang.StringUtils;
import org.dom4j.Element;
import org.xmlactions.mapping.KeyValue;

public class PopulatorFromArray extends AbstractPopulateXmlFromClass {

    public Element performElementAction(List<KeyValue> keyvalues, BeanToXml beanToXml, Element parent, Object object,
    		String namespacePrefix, String elementName, String beanRef) {
        Element element;
        if (object instanceof Object[]) {
            Object[] array = (Object[]) object;
            if (StringUtils.isEmpty(beanRef)) {
                for (Object item : array) {
                    element = BeanToXmlUtils.addElement(parent, namespacePrefix, elementName);
                    element.setText("" + item);
                }
            } else {
                for (Object item : array) {
                    element = BeanToXmlUtils.addElement(parent, namespacePrefix, elementName);
                    Bean bean = beanToXml.findBeanByName(beanRef);
                    bean.processBean(beanToXml, element, item);
                }
            }
            return parent;
        } else {
            throw new IllegalArgumentException("The object parameter must be an Object[] array");
        }
    }

    public Element performAttributeAction(List<KeyValue> keyvalues, BeanToXml beanToXml, Element parent, Object object,
            String namespacePrefix, String attributeName, String beanRef) {
        return null;
    }

}
