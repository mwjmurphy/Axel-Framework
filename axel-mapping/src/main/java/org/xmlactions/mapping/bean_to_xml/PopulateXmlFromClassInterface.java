package org.xmlactions.mapping.bean_to_xml;


import java.util.List;


import org.dom4j.Element;
import org.xmlactions.mapping.KeyValue;


public interface PopulateXmlFromClassInterface {

    /**
     * @param keyvalues
     * @param beanToXml
     * @param parent
     *            - the parent element
     * @param object
     *            - the object we extract the xml from
     * @param namespacePrefix - a namespace if required
     * @param elementName
     *            - the name of the element we create in the xml
     * @param beanRef
     *            - the reference to the bean that matches the object we are
     *            extracting
     * @return - the new xml element that was created. this will also have been
     *         inserted to the parent element.
     */
    public Element performElementAction(List<KeyValue> keyvalues, BeanToXml beanToXml, Element parent, Object object,
            String namespacePrefix, String elementName, String beanRef);

    /**
     * @param keyvalues
     * @param beanToXml
     * @param parent
     *            - the parent element
     * @param object
     *            - the object we extract the xml from
     * @param namespacePrefix - a namespace if required
     * @param attributeName
     *            - the name of the attribute we create in the xml
     * @param beanRef
     *            - the reference to the bean that matches the object we are
     *            extracting
     * @return - the new xml element that was created. this will also have been
     *         inserted to the parent element.
     */
    public Element performAttributeAction(List<KeyValue> keyvalues, BeanToXml beanToXml, Element parent, Object object,
    		String namespacePrefix, String attributeName, String beanRef);

}
