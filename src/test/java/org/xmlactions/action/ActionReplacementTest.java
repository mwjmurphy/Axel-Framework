
package org.xmlactions.action;


import java.lang.reflect.InvocationTargetException;
import java.util.List;

import junit.framework.TestCase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.xmlactions.action.Action;
import org.xmlactions.action.ActionConst;
import org.xmlactions.action.NestedActionException;
import org.xmlactions.action.ReplacementMarker;
import org.xmlactions.action.config.ExecContext;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.common.xml.BadXMLException;

public class ActionReplacementTest extends TestCase
{

	private static Logger log = LoggerFactory.getLogger(ActionReplacementTest.class);

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
		}
	}

	public void testReplacement() throws Exception
	{

		String page = "<body>\n"
				+ "<pager:echo>This is the 1st echo content</pager:echo>\n"
				+ "<pager:echo>This is the 2nd echo <pager:echo>This <b>is</b> child of 2nd echo</pager:echo> content</pager:echo>\n"
				+ "</body>";
		Action action = new Action();
		List<ReplacementMarker> markers = action.findMarkers(page, ActionTest.NAMESPACE, execContext, null);

		assertEquals(2, markers.size());
		assertEquals(0, markers.get(0).getNestedMarkers().size());
		assertEquals(1, markers.get(1).getNestedMarkers().size());

		String newPage = action.replaceMarkers(execContext, page, markers);

		log.debug("new page:\n" + newPage);
		assertEquals(
				"<body>\nThis is the 1st echo content\nThis is the 2nd echo This <b>is</b> child of 2nd echo content\n</body>",
				newPage);

	}

	public void testNestedReplacement()
			throws NestedActionException, ClassNotFoundException, InstantiationException, IllegalAccessException,
			InvocationTargetException, NoSuchMethodException, BadXMLException
	{

		String page = "<body>\n"
				+ "<pager:echo>in</pager:echo>\n"
				+ "<pager:echo>[<pager:echo>in</pager:echo>]</pager:echo>\n"
				+ "<pager:echo>[<pager:echo>in<pager:echo>si</pager:echo>d<pager:echo>e</pager:echo></pager:echo>]</pager:echo>\n"
				+ "<pager:echo>Hello World!!!</pager:echo>\n" + "</body>";

		Action action = new Action();
		List<ReplacementMarker> markers = action.findMarkers(page, ActionTest.NAMESPACE, execContext, null);
		assertEquals(4, markers.size());

		String newPage = action.replaceMarkers(execContext, page, markers);

		assertEquals("<body>\nin\n[in]\n[inside]\nHello World!!!\n</body>", newPage);

		log.debug("sb:" + newPage);

	}

}
