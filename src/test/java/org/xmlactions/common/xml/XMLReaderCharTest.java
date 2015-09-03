package org.xmlactions.common.xml;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmlactions.common.xml.XMLParserChar;

import junit.framework.TestCase;

public class XMLReaderCharTest extends TestCase {

	private static final Logger log = LoggerFactory.getLogger(XMLReaderCharTest.class);
	public void testGetInnerContent() {
		XMLParserChar xmlParser = new XMLParserChar();
		String result = xmlParser
				.getInnerContent("<echo>the inner content</echo>");
		assertEquals("the inner content", result);
		result = xmlParser
				.getInnerContent("<echo><b>the</b> inner content</echo>");
		assertEquals("<b>the</b> inner content", result);
		result = xmlParser
				.getInnerContent("<pager:echo><b>the</b> inner content</pager:echo>");
		assertEquals("<b>the</b> inner content", result);
	}
	
	public void testDoubleCharArray()
	{
		String [] names = {"ns_one","ns_two","ns_three"};
		char [][]ns = new char[3][0];
		ns[0] = names[0].toCharArray();
		ns[1] = names[1].toCharArray();
		ns[2] = names[2].toCharArray();
		
		assertEquals('n', ns[2][0]);
		//		for (int i1 = 0 ; i1 < ns.length; i1++) {
		//			for (int i2 = 0 ; i2 < ns[i1].length; i2++) {
		//				log.debug(ns[i1][i2]);
		//			}
		//		}
		
		
	}
	
	public void testMultipleNS() {
		
		String xml = "xxx<ns_one:e1>hello world!!!</ns_one:e1>" +
					 "xxx<ns_two:e2>hello world!!!</ns_two:e2>" +
					 "xxx<ns_one:e11>hello world!!!</ns_one:e11>" +
					 "xxx<ns_three:e3>hello world!!!</ns_three:e3>";
		String [] names = {"ns_two","ns_one","ns_three"};
		char [][]ns = new char[3][0];
		ns[0] = names[0].toCharArray();
		ns[1] = names[1].toCharArray();
		ns[2] = names[2].toCharArray();
		XMLParserChar xmlParser = new XMLParserChar(xml.toCharArray(), false);
		String elementAsString;
		int count = 0;
		while ((elementAsString = xmlParser.getNextNodeWithNSAsString(ns)) != null) {
			//log.debug("elementAsString:" + elementAsString);
			count++;
		}
		assertEquals(4,count);
		
	}

}
