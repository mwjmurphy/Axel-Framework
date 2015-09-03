package org.xmlactions.test.xmlobject;

import junit.framework.TestCase;

import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.xmlactions.common.xml.XMLObject;

public class XMLObjectTest extends TestCase {
	
	private static final Logger log = LoggerFactory.getLogger(XMLObjectTest.class);
	
	public void testCreate()
	{
		String xml = "<root><pager:node>this is the node <pager:node>a node inside pager:node</pager:node> and this is another node <pager:node>another node inside pager:node</pager:node></pager:node></root>";
		XMLObject xo = new XMLObject().mapXMLCharToXMLObject(xml);
		log.debug("xo" + xo.mapXMLObject2XML(xo));
		
	}
}
