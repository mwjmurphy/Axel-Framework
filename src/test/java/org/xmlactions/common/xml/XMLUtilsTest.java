package org.xmlactions.common.xml;


import java.util.List;
import java.util.Map;

import org.xmlactions.common.xml.XMLUtils;


import junit.framework.TestCase;

public class XMLUtilsTest extends TestCase {

	public void testAxelGetNameSpaces() {
		String xml = "xxx xmlns=\"xmlactions.org\" xmlns:nsone xmlns:nstwo=\"xmlactions.org\"\n\t\r\fxmlns:nsthree xmlns:nsfour xmlns:nsfive=\"xmlactions.org\"";
		// wont find nsfour as it doesn't have a whitespace char after it.
		Map<String, Object> map = XMLUtils.findNameSpaces(xml); 
		List<String> list = (List<String>)map.get(XMLUtils.MAP_KEY_URIS);
		assertEquals(3, list.size());
		assertEquals("nstwo", list.get(0));
		assertEquals("nsfive", list.get(1));
		assertEquals("xmlactions.org", map.get(XMLUtils.MAP_KEY_XMLNS));
	}
}
