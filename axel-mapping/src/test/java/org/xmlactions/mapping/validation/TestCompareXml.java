package org.xmlactions.mapping.validation;

import junit.framework.TestCase;


import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.xmlactions.mapping.validation.CompareXml;

public class TestCompareXml extends TestCase {
    
    private String xml1 = "<xml at=\"1\" ta=\"2\"><xmla>content</xmla><xmlb>content of xmlb</xmlb></xml>";
    private String xml2 = "<xml ta=\"1\" at=\"2\"><xmla>content</xmla><xmlb>content of xmlb</xmlb></xml>";
    private String xml3 = "<xml ta=\"1\" at=\"2\"><xmlb>content of xmlb</xmlb><xmla>Content</xmla></xml>";
    private String xml4 = "<xml at=\"1\" ta=\"2\"><xmla>content</xmla><xmlb>content of xmlb</xmlb>\n  </xml>";

    public void testElements() throws DocumentException {
        CompareXml compareXml = new CompareXml();
        Element element1 = DocumentHelper.parseText(xml1).getRootElement();
        Element element2 = DocumentHelper.parseText(xml1).getRootElement();
        boolean result = compareXml.compare(element1, element2);
        assertTrue(result);

        compareXml = new CompareXml();
        element2 = DocumentHelper.parseText(xml2).getRootElement();
        result = compareXml.compare(element1, element2);
        assertFalse(compareXml.getErrorsAsString(), result);
        compareXml.setCompareAttributeContent(false);
        result = compareXml.compare(element1, element2);
        assertTrue(compareXml.getErrorsAsString(), result);

        compareXml = new CompareXml();
        element2 = DocumentHelper.parseText(xml3).getRootElement();
        result = compareXml.compare(element1, element2);
        assertFalse(compareXml.getErrorsAsString(), result);
        compareXml = new CompareXml();
        compareXml.setCompareElementContent(false);
        compareXml.setCompareAttributeContent(false);
        result = compareXml.compare(element1, element2);
        assertTrue(compareXml.getErrorsAsString(), result);

        compareXml = new CompareXml();
        element2 = DocumentHelper.parseText(xml4).getRootElement();
        result = compareXml.compare(element1, element2);
        assertFalse(compareXml.getErrorsAsString(), result);
        compareXml = new CompareXml();
        compareXml.setIgnoreWhitespace(true);
        result = compareXml.compare(element1, element2);
        assertTrue(compareXml.getErrorsAsString(), result);

    }

}
