package org.xmlactions.mapping.bean_to_xml;


import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.xmlactions.action.Action;
import org.xmlactions.action.NestedActionException;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.action.config.NoPersistenceExecContext;
import org.xmlactions.common.xml.BadXMLException;
import org.xmlactions.mapping.KeyValue;
import org.xmlactions.mapping.Populator;
import org.xmlactions.mapping.bean_to_xml.Bean;
import org.xmlactions.mapping.bean_to_xml.BeanToXml;
import org.xmlactions.mapping.bean_to_xml.MapperAttribute;
import org.xmlactions.mapping.bean_to_xml.MapperElement;
import org.xmlactions.mapping.bean_to_xml.Namespace;
import org.xmlactions.mapping.testclasses.A1;
import org.xmlactions.mapping.testclasses.A2;

public class TestMapFromClass extends TestCase {

	private static Logger log = LoggerFactory.getLogger(TestMapFromClass.class);

	public static char[][] NAMESPACE = {"".toCharArray(), "pager".toCharArray()};

	private static IExecContext execContext;

	public void setUp()
	{
		if (execContext == null) {
			execContext = new NoPersistenceExecContext(null, null);
			Map<String, String> map = new HashMap<String, String>();
			map.put("bean_to_xml", BeanToXml.class.getName());
			map.put("bean", Bean.class.getName());
			map.put("element", MapperElement.class.getName());
			map.put("attribute", MapperAttribute.class.getName());
			map.put("populator", Populator.class.getName());
			map.put("keyvalue", KeyValue.class.getName());
			map.put("namespace", Namespace.class.getName());
			execContext.addActions(map);
		}
	}
	
	public void testA1() throws IOException, NestedActionException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, BadXMLException {

		A1 a1 = buildTestClasses();

		execContext.put("map_classes", a1);

		Action action = new Action("src/test/resources", "mapping/A1_bean_to_xml.xml", NAMESPACE);
 		
		String page = action.processPage(execContext);
		log.debug("page:" + page);
	}


	private List<A1> buildListOfA1s(String id) {
		List<A1> list = new ArrayList<A1>();
		A1 a1 = buildA1(id+".1.");
		list.add(a1);
		a1 = buildA1(id+".2.");
		list.add(a1);
		a1 = buildA1(id+".3.");
		list.add(a1);
		return list;
	}
	
	private A1 buildA1(String id) {
		A1 a1 = new A1();
		a1.setAnInt(1);
		a1.setaDouble(Double.valueOf("1.01"));
		a1.setaString(id + " This is a string");
		return a1;
	}
	
	public A1 buildTestClasses() {
		A1 a1 = new A1();
		a1.setAnInt(1);
		a1.setaDouble(Double.valueOf("1.01"));
		a1.setaString("This is a string");
		a1.setTimestamp(new Timestamp(new Date().getTime()));

		A2 a2 = new A2();
		a2.setaString("This is an A2 string");
		a2.setAnInt(1);
		a2.setName("name of a2");
		a1.setA2(a2);
		
		a1.setListOfA1s(buildListOfA1s(".1."));

		String [] users = new String [] {"name1","name2","name3"};
		a1.setUsers(users);
		return a1;
	}

}
