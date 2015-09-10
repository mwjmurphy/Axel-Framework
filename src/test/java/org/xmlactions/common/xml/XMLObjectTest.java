
package org.xmlactions.common.xml;

import junit.framework.TestCase;

import java.io.IOException;

import org.apache.log4j.lf5.util.ResourceUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmlactions.common.xml.XMLObject;

public class XMLObjectTest extends TestCase
{

	private static final Logger log = LoggerFactory.getLogger(XMLObjectTest.class);

	public void testGetAttributeValueAsString()
	{

		String xml = "<rio_profile num_rows=\"0\" />";
		XMLObject xo = new XMLObject().mapXMLCharToXMLObject(xml);
		// log.debug("num_rows:" + xo.getAttributeValueAsString("num_rows"));
		if (!"0".equals(xo.getAttributeValueAsString("num_rows"))) {
			fail("getAttributeValueAsString for num_rows failed");
		}
	}
	
	public void testGetAttribute() throws Exception {
		String xml = "<root num_rows=\"1\">\n"
				+ "<row index=\"1\" data=\"this is the data\"/>"
				+ "</root>";
		XMLObject xo = new XMLObject().mapXMLCharToXMLObject(xml);
		String value = xo.getAttribute("root/row", "data");
		assertNotNull(value);
		// log.debug("value:" + value);
		
		String path = "root/row[@data]";
	}
	
	/**
	 * Tests that the xml parser can manage a <= character not part of a html.
	 * @throws IOException
	 */
	public void testLessThan() throws IOException {
		String page = org.xmlactions.common.io.ResourceUtils.loadFile("/org/xmlactions/common/xml/lessthan.xml");
		XMLObject xo = new XMLObject().mapXMLCharToXMLObject(page);
		String parsedPage = xo.mapXMLObject2XML(xo, true);
		assertNotNull(parsedPage);
		// log.debug("parsedPage:" + parsedPage);
	}
}
