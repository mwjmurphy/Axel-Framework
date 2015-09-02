package org.xmlactions.mapping.validation;

import junit.framework.TestCase;


import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.xmlactions.mapping.validation.CompareSnippets;

public class TestCompareSnippets extends TestCase {

    private static final Logger LOG = LoggerFactory.getLogger(TestCompareSnippets.class);

    private String xml1 = 
        "<ers:xml" +
        "  xmlns:ers=\"http://ec.europa.eu/fisheries/schema/ers/v1\"\n" +
        "  xmlns=\"http://ec.europa.eu/fisheries/schema/ers/v1\"\n" +
        "    at=\"1\" ta=\"2\">\n" +
        "  <ers:xmla>\n" +
        "    <ers:xmlaa>content</ers:xmlaa>\n" +
        "  </ers:xmla>\n" +
        "  <ers:xmla>\n" +
        "    <ers:xmlaa>content</ers:xmlaa>\n" +
        "    <xmlaa>content</xmlaa>\n" +
        "  </ers:xmla>\n" +
        "  <ers:xmlb>content of xmlb</ers:xmlb>\n" +
        "</ers:xml>\n";

    private String xml2 = 
        "<ers:xml" +
        "  xmlns:ers=\"http://ec.europa.eu/fisheries/schema/ers/v1\"\n" +
        "  xmlns=\"http://ec.europa.eu/fisheries/schema/ers/v1\"\n" +
        "    at=\"1\" ta=\"2\">\n" +
        "  <ers:xmla>\n" +
        "    <ers:xmlab>content</ers:xmlab>\n" +
        "  </ers:xmla>\n" +
        "  <ers:xmla>\n" +
        "    <ers:xmlaa>content</ers:xmlaa>\n" +
        "    <xmlaa>content</xmlaa>\n" +
        "  </ers:xmla>\n" +
        "  <ers:xmlb>content of xmlb</ers:xmlb>\n" +
        "</ers:xml>\n";
    
    private String xml3 = 
        "<ers:xml" +
        "  xmlns:ers=\"http://ec.europa.eu/fisheries/schema/ers/v1\"\n" +
        "  xmlns=\"http://ec.europa.eu/fisheries/schema/ers/v1\"\n" +
        "    at=\"1\" ta=\"2\">\n" +
        "  <ers:xmla>\n" +
        "    <ers:xmlaa>contentx</ers:xmlaa>\n" +
        "  </ers:xmla>\n" +
        "  <ers:xmla>\n" +
        "    <ers:xmlaa>content</ers:xmlaa>\n" +
        "    <xmlaa>content</xmlaa>\n" +
        "  </ers:xmla>\n" +
        "  <ers:xmlb>content of xmlb</ers:xmlb>\n" +
        "</ers:xml>\n";

    public void testCompareSnippetSame() throws DocumentException {
        CompareSnippets compareSnippets = new CompareSnippets();
        Element element1 = DocumentHelper.parseText(xml1).getRootElement();
        Element element2 = DocumentHelper.parseText(xml1).getRootElement();

        String errors = compareSnippets.compareSnippet(element1, element2, "xml/xmla");

        assertTrue(StringUtils.isEmpty(errors));
    }

    public void testCompareSnippetMissingElement() throws DocumentException {
        CompareSnippets compareSnippets = new CompareSnippets();
        Element element1 = DocumentHelper.parseText(xml1).getRootElement();
        Element element2 = DocumentHelper.parseText(xml2).getRootElement();
        LOG.debug("compare:\n" + element1.asXML() + "\nto:\n" + element2.asXML());

        String errors = compareSnippets.compareSnippet(element1, element2, "xml/xmla");

        LOG.debug("errors:" + errors);
        assertTrue(errors.contains("No matching element found for ["));
    }

    public void testCompareSnippetChangedContent() throws DocumentException {
        CompareSnippets compareSnippets = new CompareSnippets();
        Element element1 = DocumentHelper.parseText(xml1).getRootElement();
        Element element2 = DocumentHelper.parseText(xml3).getRootElement();
        LOG.debug("compare:\n" + element1.asXML() + "\nto:\n" + element2.asXML());

        String errors = compareSnippets.compareSnippet(element1, element2, "xml/xmla");

        LOG.debug("errors:[" + errors + "]");
        assertTrue(errors.contains("does not match"));
    }
}
