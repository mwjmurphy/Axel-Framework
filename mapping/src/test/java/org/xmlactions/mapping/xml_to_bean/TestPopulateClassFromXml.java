package org.xmlactions.mapping.xml_to_bean;


import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigInteger;

import junit.framework.TestCase;

import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.xmlactions.action.Action;
import org.xmlactions.action.actions.BaseAction;
import org.xmlactions.mapping.testclasses.A1;
import org.xmlactions.mapping.xml_to_bean.PopulateClassFromXml;
import org.xmlactions.mapping.xml_to_bean.XmlToBean;

public class TestPopulateClassFromXml extends TestCase {

	private static final Logger log = LoggerFactory.getLogger(TestPopulateClassFromXml.class);

	private XmlToBean xmlToBean;

	public void setUp()
	{
		try {
			Action action = new Action();
			String page = action.loadPage(null, "/mapping/A1_xml_to_bean.xml");
			BaseAction[] actions = action.processXML(UtilsTestHelper.getExecContext(), page);
			xmlToBean = (XmlToBean)actions[0];
		} catch (Exception ex) {
			throw new IllegalArgumentException(ex.getMessage(),ex);
		}
	}

    public void testMapping() {
        String xml = "<a1 anInt = \"1\" aDouble=\"1.01\"" + " aString=\"This is a String\" bigInt=\"999999999\""
                + " aLong=\"1002\"" + " timestamp=\"2011-08-15 01:22\"" + " date=\"2011-08-16 01:22\""
                + " sqldate=\"2011-08-17 02:33\"" + ">Content of A1" + "<a2>content of a2</a2>" + "</a1>";
        PopulateClassFromXml pop = new PopulateClassFromXml();
        Object clas = pop.mapXmlToBean("/mapping/A1_xml_to_bean.xml", xml);
        A1 a1 = (A1) clas;
        log.debug("\n" + a1.toString(""));
    }

    public void testMappingContent(){
        String xml = "<a1>Content of A1<a2>Content of A2</a2>1a fo tnetnoC</a1>";
        PopulateClassFromXml pop = new PopulateClassFromXml();
        Object clas = pop.mapXmlToBean("/mapping/A1_xml_to_bean.xml", xml);
        A1 a1 = (A1)clas;
        log.debug("\n" + a1.toString(""));
        assertEquals("Content of A1", a1.getContent());
    }

	public void testMappingCreateA1() throws ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, SecurityException, IllegalArgumentException, NoSuchFieldException {
		String xml =
			"<a1 anInt = \"1\" aDouble=\"1.01\"" +
			" aString=\"This is a String\" bigInt=\"999999999\"" +
			" aLong=\"1002\"" +
			" timestamp=\"2011-08-15 01:22\"" +
			" date=\"2011-08-16 01:22\"" +
			" sqldate=\"2011-08-17 02:33\"" +
			">" +
			"<a2>content of at</a2>" +
			"</a1>";
		PopulateClassFromXml pop = new PopulateClassFromXml();
		Object clas = pop.mapXmlToBean(xmlToBean, xml);
		A1 a1 = (A1)clas;
		log.debug("\n" + a1.toString(""));
	}

	public void testMappingPopulateInt() throws ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, SecurityException, IllegalArgumentException, NoSuchFieldException {
		String xml = "<a1 anInt = \"1\" aDouble=\"1.01\" aString=\"This is a String\" bigInt=\"999999999999\">" +
		"<listOfA1s anInt=\"11\"/>" +
		"<listOfA1s anInt=\"12\"/>" +
		"<listOfA1s anInt=\"13\" bigInt=\"999\"/>" +
		"<a2>content of a2</a2></a1>";
		log.debug("xml:" + xml);
		PopulateClassFromXml pop = new PopulateClassFromXml();
		Object clas = pop.mapXmlToBean(xmlToBean, xml);
		A1 a1 = (A1)clas;
		log.debug("clas:" + clas +
				" a1.anInt:" + a1.getAnInt());
		log.debug(a1.toString(""));
		assertEquals(1, a1.getAnInt());
		assertEquals(BigInteger.valueOf(999999999999l), a1.getBigInt());
		assertEquals(1.01, a1.getaDouble());
		assertEquals("This is a String", a1.getaString());
	}

	public void testMappingPopulateNested() throws ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, SecurityException, IllegalArgumentException, NoSuchFieldException {
		String xml = "<a1 anInt = \"1\" aDouble=\"1.01\" aString=\"This is a String\">" +
		"<listOfA1s anInt=\"11\">" +
		" <listOfA1s anInt=\"111\" aString=\"last nested a1\"/> " +
		"</listOfA1s>" +
		"</a1>";
		log.debug("xml:" + xml);
		PopulateClassFromXml pop = new PopulateClassFromXml();
		Object clas = pop.mapXmlToBean(xmlToBean, xml);
		A1 a1 = (A1)clas;
		log.debug("clas:" + clas +
				" a1.anInt:" + a1.getAnInt());
		log.debug(a1.toString(""));
	}

	public void testMappingMultipleLists() throws ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, SecurityException, IllegalArgumentException, NoSuchFieldException {
		String xml = "<A1 anInt = \"1\" aDouble=\"1.01\" aString=\"This is a String\">" +
		" <listOfA1s aString=\"ListOfA1s\"/> " +
		" <listOfA2s anInt=\"222\" aString=\"last nested a2\"/> " +
		"</A1>";
		log.debug("xml:" + xml);
		PopulateClassFromXml pop = new PopulateClassFromXml();
		Object clas = pop.mapXmlToBean(xmlToBean, xml);
		A1 a1 = (A1)clas;
		assertTrue(a1.getListOfA1s().size() == 1);
		assertTrue(a1.getListOfA2s().size() == 1);
		log.debug("clas:" + clas +
				" a1.anInt:" + a1.getAnInt());
		log.debug(a1.toString(""));
	}

    public void testMappingRenameElements() throws ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, SecurityException, IllegalArgumentException, NoSuchFieldException {
        String xml = "<A1 anInt = \"1\" aDouble=\"1.01\" bString=\"This is a bString\">" +
        " <ListOfA1s anInt=\"11\">" +
        "  <listOfA1s anInt=\"111\" aString=\"last nested a1\"/> " +
        " </ListOfA1s>" +
        " <ListOfA1s aString=\"ListOfA1s\"/> " +
        " <ListOfA2s anInt=\"222\" aString=\"last nested a2\"/> " +
        " <a2 name=\"a2\"/>" +
        "</A1>";
        log.debug("xml:" + xml);
        PopulateClassFromXml pop = new PopulateClassFromXml();
        Object clas = pop.mapXmlToBean(xmlToBean, xml);
        A1 a1 = (A1)clas;
        log.debug("clas:" + clas +
                " a1.anInt:" + a1.getAnInt());
        log.debug(a1.toString(""));
        assertTrue(a1.getListOfA1s().size() == 2);
        assertTrue(a1.getListOfA2s().size() == 1);
        assertNotNull(a1.getA2());
        assertEquals("This is a bString", a1.getaString());
    }
    public void testMappingEmptyBigInt() throws ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, SecurityException, IllegalArgumentException, NoSuchFieldException {
        String xml = "<A1 anInt = \"1\" aDouble=\"1.01\" bString=\"This is a bString\">" +
        "  <listOfA1s bigInt=\"6\"/> " +
        "  <listOfA1s bigInt=\"\"/> " +
        "  <listOfA2s bigInt=\"6\"/> " +
        " <a2 name=\"a2\"/>" +
        "</A1>";
        log.debug("xml:" + xml);
        PopulateClassFromXml pop = new PopulateClassFromXml();
        Object clas = pop.mapXmlToBean(xmlToBean, xml);
        A1 a1 = (A1)clas;
        log.debug("clas:" + clas +
                " a1.anInt:" + a1.getAnInt());
        log.debug(a1.toString(""));
        assertTrue(a1.getListOfA1s().size() == 2);
        assertTrue(a1.getListOfA2s().size() == 1);
        assertNotNull(a1.getA2());
        assertEquals("This is a bString", a1.getaString());
    }

	public void testReflection() throws ClassNotFoundException {
		Class c = Class.forName(A1.class.getName());
		Method m[] = c.getMethods();
		log.debug("c.getMethods");
		for (int i = 0; i < m.length; i++) {
			log.debug(m[i].toString());
		}
		Method dm[] = c.getDeclaredMethods();
		log.debug("c.getDeclaredMethods");
		for (int i = 0; i < dm.length; i++) {
			log.debug(dm[i].toString());
		}
		Field f[] = c.getFields();
		log.debug("c.getFields");
		for (int i = 0; i < f.length; i++) {
			log.debug(f[i].toString());
		}

		Field df[] = c.getDeclaredFields();
		log.debug("c.getDeclaredFields");
		for (int i = 0; i < df.length; i++) {
			log.debug(df[i].toString());
		}
		Class ci[] = c.getInterfaces();
		log.debug("c.getInterfaces");
		for (int i = 0; i < ci.length; i++) {
			log.debug(ci[i].toString());
		}
		Class classes[] = c.getClasses();
		log.debug("c.getClasses");
		for (int i = 0; i < classes.length; i++) {
			log.debug(classes[i].toString());
		}
	}
}
