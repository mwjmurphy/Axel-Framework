
package org.xmlactions.action.actions;


import java.lang.reflect.InvocationTargetException;
import java.util.List;

import junit.framework.TestCase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.xmlactions.action.Action;
import org.xmlactions.action.ActionConst;
import org.xmlactions.action.ActionMarkers;
import org.xmlactions.action.ActionTest;
import org.xmlactions.action.NestedActionException;
import org.xmlactions.action.ReplacementMarker;
import org.xmlactions.action.actions.BaseAction;
import org.xmlactions.action.actions.SetupBeanFromXML;
import org.xmlactions.action.config.ExecContext;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.common.xml.BadXMLException;

public class SetupBeanFromXMLTest extends TestCase
{

	private static final Logger log = LoggerFactory.getLogger(SetupBeanFromXML.class);

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
			execContext.put("echo", org.xmlactions.action.actions.Echo.class);
			execContext.put("code", org.xmlactions.action.actions.Code.class);
			execContext.put("param", org.xmlactions.action.actions.Param.class);
		}
	}

	public void _testCreateEcho()
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException,
			NoSuchMethodException, BadXMLException
	{

		String page = "<pager:echo>This is the echo content</pager:echo>";
		List<ReplacementMarker> markers = new ActionMarkers().getReplacementList(page, ActionTest.NAMESPACE, execContext, null);
		BaseAction action = markers.get(0).getAction();
		assertEquals(Echo.class, action.getClass());
	}

	public void _testCreateCode()
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException,
			NoSuchMethodException, BadXMLException
	{

		String page = "<pager:code call=\"org.xmlactions.test.CodeCall.call\">"
				+ "<pager:param value=\"the value of param1\"/>" + "<pager:param value=\"code class = ${code}\"/>"
				+ "</pager:code>";

		List<ReplacementMarker> markers = new ActionMarkers().getReplacementList(page, ActionTest.NAMESPACE, execContext, null);
		Object action = markers.get(0).getAction();
		assertEquals(Code.class, action.getClass());
	}

	public void _testSetProperty()
	{

		try {
			SetupBeanFromXML setupBeanFromXML = new SetupBeanFromXML();
			Code code = new Code();
			Param param1 = new Param();
			setupBeanFromXML.setProperty(param1, "value", (Object) "value 1");
			Param param2 = new Param();
			setupBeanFromXML.setProperty(param2, "value", (Object) "value 2");
			setupBeanFromXML.setProperty(code, "param", param1);
			setupBeanFromXML.setProperty(code, "param", param2);
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			fail(ex.getMessage());
		}
	}

	public void testNestedActions()
			throws IllegalAccessException, InvocationTargetException, ClassNotFoundException, InstantiationException,
			NoSuchMethodException, NestedActionException, BadXMLException
	{

		String page = "<body>\n"
				+ "<pager:echo>in</pager:echo>\n"
				+ "<pager:echo>[<pager:echo>in</pager:echo>]</pager:echo>\n"
				+ "<pager:echo>[<pager:echo>in<pager:echo>si</pager:echo>d<pager:echo>e</pager:echo></pager:echo>]</pager:echo>\n"
				+ "<pager:echo>Hello World!!!</pager:echo>\n" + "</body>";

		Action action = new Action();
		List<ReplacementMarker> markers = action.findMarkers(page, ActionTest.NAMESPACE, execContext, null);
		assertEquals(4, markers.size());

		assertEquals("in", markers.get(0).getAction().getContent());

		assertEquals("[<pager:echo>in</pager:echo>]", markers.get(1).getAction().getContent());

	}
}
