package org.xmlactions.mapping.validation;


import java.util.List;

import junit.framework.TestCase;


import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.xmlactions.mapping.validation.TraverseXml;

public class TestTraverseXml extends TestCase {

    private String xml1 = 
            "<ers:xml" +
            "  xmlns:ers=\"http://ec.europa.eu/fisheries/schema/ers/v1\"\n" +
            "  xmlns=\"http://ec.europa.eu/fisheries/schema/ers/v1\"\n" +
            "    at=\"1\" ta=\"2\">\n" +
            "  <ers:xmla>" +
            "    <ers:xmlaa>content</ers:xmlaa>\n" +
            "  </ers:xmla>\n" +
            "  <ers:xmla>" +
            "    <ers:xmlaa>content</ers:xmlaa>\n" +
            "    <xmlaa>content</xmlaa>\n" +
            "  </ers:xmla>\n" +
    		"  <ers:xmlb>content of xmlb</ers:xmlb>\n" +
    		"</ers:xml>\n";

    public void testGetSnippet() throws DocumentException {
        TraverseXml traverseXml = new TraverseXml();
        Element element1 = DocumentHelper.parseText(xml1).getRootElement();
        Element element = traverseXml.getSnippet(element1, "xml/xmlb");
        assertNotNull(element);
        element = traverseXml.getSnippet(element1, "xml/xml.");
        assertNotNull(element);
        element = traverseXml.getSnippet(element1, "xml/[xml.]/xmlaa");
        assertNotNull(element);
        element = traverseXml.getSnippet(element1, "xml/xmla/xmlaa");
        assertNotNull(element);
    }

    public void testGetSnippets() throws DocumentException {
        TraverseXml traverseXml = new TraverseXml();
        Element element1 = DocumentHelper.parseText(xml1).getRootElement();
        List<Element> list = traverseXml.getSnippets(element1, "xml/xmla");
        assertEquals(2, list.size());
        list = traverseXml.getSnippets(element1, "xml/xmlb");
        assertEquals(1, list.size());
        list = traverseXml.getSnippets(element1, "xml/xml.");
        assertEquals(3, list.size());
        list = traverseXml.getSnippets(element1, "xml/[xmla]|[xmlb]");
        assertEquals(3, list.size());
        list = traverseXml.getSnippets(element1, "xml/xmla/xmlaa");
        assertEquals(3, list.size());
    }


}
