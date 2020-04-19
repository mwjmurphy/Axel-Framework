
package org.xmlactions.action;


import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import junit.framework.TestCase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.dom4j.DocumentException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.xmlactions.action.Action;
import org.xmlactions.action.ActionConst;
import org.xmlactions.action.NestedActionException;
import org.xmlactions.action.ReplacementMarker;
import org.xmlactions.action.actions.BaseAction;
import org.xmlactions.action.config.ExecContext;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.common.xml.BadXMLException;

public class ActionTest extends TestCase
{

	private static Logger log = LoggerFactory.getLogger(ActionTest.class);

	public static char[][] NAMESPACE = {"pager".toCharArray(), "plite".toCharArray()};

	private static final String [] configFiles = {
		"/config/spring/spring-config.xml"
	};
	private static ApplicationContext applicationContext = new ClassPathXmlApplicationContext(configFiles);

	private static IExecContext execContext;

	public void setUp()
	{

		if (execContext == null) {
			execContext = (ExecContext)applicationContext.getBean(ActionConst.EXEC_CONTEXT_BEAN_REF);
			execContext.setApplicationContext(applicationContext);
			HashMap<String, Object>map = new HashMap<String, Object>();
			map.put("echo", org.xmlactions.action.actions.Echo.class.getName());
			execContext.addActions(map);
		}
	}

	public void testLoadPage() throws IOException
	{

		Action action = new Action();
		String page = action.loadPage("src/test/resources", "pages/echo.xhtml");
		assertTrue("Missing html element", page.indexOf("<html") >= 0);
		log.warn("logger is still working");
	}

	public void testProcessEcho()
			throws IOException, DocumentException, ClassNotFoundException, InstantiationException,
			IllegalAccessException, InvocationTargetException, NoSuchMethodException, NestedActionException,
			BadXMLException
	{

		Action action = new Action();
		String page = action.loadPage("src/test/resources", "pages/echo.xhtml");
		String actionName = execContext.getAction(null, "echo");
		List<ReplacementMarker> list = action.findMarkers(page, NAMESPACE, execContext, null);
		assertEquals(2, list.size());
	}

	public void testProcessXML()
			throws IOException, NestedActionException, ClassNotFoundException, InstantiationException,
			IllegalAccessException, InvocationTargetException, NoSuchMethodException, BadXMLException
	{
		Action action = new Action();
		String page = action.loadPage("src/test/resources", "pages/echo.xml");
		action.setNameSpaces(Action.EMPTY_NAMESPACE);
		BaseAction[] actions = action.processXML(execContext, page);
		assertEquals("No actions returned from processXML", 3, actions.length);
		assertEquals("Missing child", 1, actions[2].getActions().size());
		assertNotNull("No Parent", actions[2].getActions().get(0).getParent());
	}

	public void testProcessXML2()
	throws IOException, NestedActionException, ClassNotFoundException, InstantiationException,
	IllegalAccessException, InvocationTargetException, NoSuchMethodException, BadXMLException
	{
		Action action = new Action();
		// action.setNameSpaces(new char[0][]);
		// action.addEmptyNameSpace();
		String page = action.loadPage("src/test/resources", "pages/echo.xhtml");
		BaseAction[] actions = action.processXML(execContext, page);
		assertEquals("Wrong number of actions returned from processXML", 2, actions.length);
	}


	public void testAddNameSpaces() {
		List<String> list = new ArrayList<String>();
		list.add("n1");
		list.add("n2");
		list.add("n3");
		list.add("n2");
		list.add("n4");
		list.add("n5");
		
		Action action = new Action();
		action.setNameSpaces(new char [0][]);
		action.addNameSpaces(list);
		assertEquals(5,action.getNameSpaces().length);
	}

	
}
