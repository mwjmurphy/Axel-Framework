package org.xmlactions.test.dom4j;

import java.io.IOException;

import junit.framework.TestCase;


import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.xmlactions.pager.actions.MixedHtmlTest;


public class Dom4JTest extends TestCase {

	private final static Logger log = LoggerFactory.getLogger(MixedHtmlTest.class);

	String xmlVersion = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
	String xml = "<root><node>this is content <node> and this is inside</node> on the <node> 2nd inside content</node> outside</node></root>";

	public void testParser() throws IOException, DocumentException {
		Document doc = DocumentHelper.parseText(xmlVersion + "\n" + xml);
		assertEquals(xmlVersion + "\n" + xml, doc.asXML());
		Element root = doc.getRootElement();
		assertEquals(xml, root.asXML());
		assertEquals(1, root.elements().size());
		Element node = (Element) root.elements().get(0);
		assertEquals(2, node.elements().size());
		assertEquals("this is content  on the  outside", node.getText());
	}

	String xmlVersionNS = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
	String xmlNS = "<root xmlns:ns=\"http://www.riostl.com/schema/pager\"><ns:node>this is content <node> and this is inside</node> on the <node> 2nd inside content</node> outside</ns:node></root>";

	public void testParserWithNS() throws IOException, DocumentException {
		Document doc = DocumentHelper.parseText(xmlVersion + "\n" + xmlNS);
		assertEquals(xmlVersion + "\n" + xmlNS, doc.asXML());
		Element root = doc.getRootElement();
		assertEquals(xmlNS, root.asXML());
		assertEquals(1, root.elements().size());
		Element node = (Element) root.elements().get(0);
		assertEquals(2, node.elements().size());
		assertEquals("this is content  on the  outside", node.getText());
	}

}
