package org.xmlactions.common.text;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestHtml {
	
	private static final Logger logger = LoggerFactory.getLogger(TestHtml.class);
	
	@Test
	public void testJson() {
		Html html = new Html();
		String page = "<json b=\"z\">This is the inner content of the json</json>";
		String result = html.removeOuterJsonOrXml(page);
		assertEquals("This is the inner content of the json", result);
		result = html.removeOuterJsonOrXml(page+"   \n    \t");
		assertEquals("This is the inner content of the json", result);
		result = html.removeOuterJsonOrXml(page+"x");
		assertEquals("<json b=\"z\">This is the inner content of the json</json>x", result);
		result = html.removeOuterJsonOrXml(page+"x   ");
		assertEquals("<json b=\"z\">This is the inner content of the json</json>x   ", result);
	}

	@Test
	public void testXml() {
		Html html = new Html();
		String page = "<xml b=\"z\">This is the inner content of the xml</xml>";
		String result = html.removeOuterJsonOrXml(page);
		assertEquals(Html.CONTENT_TYPE_XML, html.getContentType());
		assertEquals("This is the inner content of the xml", result);
		html.setContentType(null);
		result = html.removeOuterJsonOrXml(page+"   \n    \t");
		assertEquals("This is the inner content of the xml", result);
		assertEquals(Html.CONTENT_TYPE_XML, html.getContentType());
		html.setContentType(null);
		result = html.removeOuterJsonOrXml(page+"x");
		assertEquals("<xml b=\"z\">This is the inner content of the xml</xml>x", result);
		assertNull(html.getContentType());
		html.setContentType(null);
		result = html.removeOuterJsonOrXml(page+"x   ");
		assertEquals("<xml b=\"z\">This is the inner content of the xml</xml>x   ", result);
		assertNull(html.getContentType());
		html.setContentType(null);
	}

}
