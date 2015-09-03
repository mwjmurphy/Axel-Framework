
package org.xmlactions.common.xml;

import junit.framework.TestCase;




import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmlactions.common.xml.XMLObject;

public class XMLXPathParserTest extends TestCase
{

	private static final Logger logger = LoggerFactory.getLogger(XMLXPathParserTest.class);

	public void testGetXPath() throws Exception {
		String xpath = "root/row[@data]";
		String path = XMLXPathParser.getPath(xpath);
		String key = XMLXPathParser.getAttribute(xpath);
		
		assertEquals("root/row", path);
		assertEquals("data", key);
	}
}
