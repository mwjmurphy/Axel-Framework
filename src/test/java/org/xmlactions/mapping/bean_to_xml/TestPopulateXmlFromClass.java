package org.xmlactions.mapping.bean_to_xml;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.xmlactions.mapping.bean_to_xml.PopulateXmlFromClass;
import org.xmlactions.mapping.testclasses.A1;
import org.xmlactions.mapping.testclasses.A2;

public class TestPopulateXmlFromClass extends TestCase {

	private static Logger log = LoggerFactory.getLogger(TestPopulateXmlFromClass.class);

	public static char[][] NAMESPACE = {"".toCharArray(), "pager".toCharArray()};

	public void setUp() {
		PopulateXmlFromClass.reset();
	}

    public void testOne() throws IOException {
		A1 a1 = new TestMapFromClass().buildTestClasses();
        String xml = PopulateXmlFromClass.mapBeanToXml(a1, "/mapping/A1_bean_to_xml.xml");
		log.debug("xml:" + xml);
		
	}
	public void testTwo() throws IOException {
		
		A1 a1 = new TestMapFromClass().buildTestClasses();
		a1.setListOfA1s(null);

        String xml = PopulateXmlFromClass.mapBeanToXml(a1, "/mapping/A1_bean_to_xml.xml");
		log.debug("xml:" + xml);
		
	}

	public void testThree() throws IOException {

		// create bean A1
		A1 a1 = new A1();

		a1.setAnInt(1);		// populate some data

		// create an array of A2 beans
		List<A2> a2s = new ArrayList<A2>();

		A2 a2 = new A2();	// create bean A2
		a2.setAnInt(1);		// populate some data in A2
		a2s.add(a2);		// add bean to list
		a2 = new A2();		// create bean A2
		a2.setAnInt(2);		// populate some data in A2
		a2s.add(a2);		// add bean to list
		a2 = new A2();		// create bean A2
		a2.setAnInt(3);		// populate some data in A2
		a2s.add(a2);		// add bean to list

		a1.setErs(a2s);		// add list of A2 beans to A1

		String xml = PopulateXmlFromClass.mapBeanToXml(a1, "/mapping/A1_bean_to_xml.xml");
		log.debug("xml:" + xml);

	}
}
