package org.xmlactions.mapping.xml_to_bean.tutorial;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmlactions.mapping.xml_to_bean.PopulateClassFromXml;

public class TestTutorial {

	private static final Logger log = LoggerFactory.getLogger(TestTutorial.class);

	@Test
    public void testSimpleMapping1() {
        String xml = "<first id=\"1\" name=\"First Bean\"/>";
        PopulateClassFromXml pop = new PopulateClassFromXml();
        Object clas = pop.mapXmlToBean("/org/xmlactions/mapping/xml_to_bean/tutorial/tutorial_xml_to_beans.xml", xml);
        First first = (First) clas;
        assertEquals("First Bean", first.getName());
        assertEquals(1, first.id);
    }

	@Test
    public void testSimpleMapping2() {
        String xml =
        	"<first id=\"1\" name=\"First Bean\">\n" +
        	"   <second name=\"Second Bean\"/>\n" +
        	"</first>";
        PopulateClassFromXml pop = new PopulateClassFromXml();
        Object clas = pop.mapXmlToBean(
        	"/org/xmlactions/mapping/xml_to_bean/tutorial/tutorial_xml_to_beans.xml", xml);
        First first = (First) clas;
        assertEquals("First Bean", first.getName());
        assertEquals(1, first.id);
        assertNotNull(first.getSecond());
        assertEquals("Second Bean", first.getSecond().getName());
    }

}
