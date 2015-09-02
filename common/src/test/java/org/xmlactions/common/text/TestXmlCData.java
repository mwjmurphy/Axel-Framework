package org.xmlactions.common.text;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestXmlCData {
	
	private static final Logger logger = LoggerFactory.getLogger(TestXmlCData.class);
	
	@Test
	public void testRemoveCData() {
		String content = "stuff here is removed <![CDATA[   This is where the sql will be ]]> and remove this ";
		String result = XmlCData.removeCData(content);
		assertEquals("stuff here is removed <![CDATA[   This is where the sql will be ]]> and remove this ", result);
		content = "\r\n\t<![CDATA[stuff here is removed <![CDATA[   This is where the sql will be ]]> and remove this ]]>\r\n   \r\n\t";
		result = XmlCData.removeCData(content);
		assertEquals("stuff here is removed <![CDATA[   This is where the sql will be ]]> and remove this ", result);
		content = "<![CDATA[stuff here is removed <![CDATA[   This is where the sql will be ]]> and remove this ]>";
		result = XmlCData.removeCData(content);
		assertEquals("<![CDATA[stuff here is removed <![CDATA[   This is where the sql will be ]]> and remove this ]>", result);
	}

}
