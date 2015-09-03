
package org.xmlactions.pager;


import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import junit.framework.TestCase;

import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.dom4j.DocumentException;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.xmlactions.action.Action;
import org.xmlactions.action.ActionConst;
import org.xmlactions.action.ActionMarkers;
import org.xmlactions.action.NestedActionException;
import org.xmlactions.action.ReplacementMarker;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.common.xml.BadXMLException;

public class PagerReplacementTest extends TestCase
{

	private static Logger log = LoggerFactory.getLogger(PagerReplacementTest.class);

	private static IExecContext execContext;

	public void setUp()
	{

		if (execContext == null) {
			ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(
					ActionConst.SPRING_STARTUP_CONFIG);
			execContext = (IExecContext) applicationContext.getBean(ActionConst.EXEC_CONTEXT_BEAN_REF);
		}
	}

	public void testLoadPage() throws IOException
	{

		Action action = new Action();
		String page = action.loadPage("src/test/resources", "pages/actions_with_child_action.xhtml");
		assertTrue("Missing html element", page.indexOf("<html") >= 0);
		// log.debug("loaded page:" + page);
	}

	public void testPageMarkers()
			throws IOException, DocumentException, ClassNotFoundException, InstantiationException,
			IllegalAccessException, InvocationTargetException, NoSuchMethodException, BadXMLException
	{

		Action action = new Action();
		String page = action.loadPage("src/test/resources", "pages/actions_with_child_action.xhtml");
		assertTrue("Missing html element", page.indexOf("<html") >= 0);
		ActionMarkers markers = new ActionMarkers();
		List<ReplacementMarker> list = markers.getReplacementList(page, PagerTest.NAMESPACE, execContext, null);
		assertEquals(2, list.size());
		assertEquals(0, list.get(0).getNestedMarkers().size());
		assertEquals(1, list.get(1).getNestedMarkers().size());
	}

	public void testReplacement() throws Exception
	{

		String page = "<body>\n"
				+ "<pager:echo>This is the 1st echo content</pager:echo>\n"
				+ "<pager:echo>This is the 2nd echo <pager:echo>This <b>is</b> child of 2nd echo</pager:echo> content</pager:echo>\n"
				+ "</body>";
		Action action = new Action();
		List<ReplacementMarker> markers = action.findMarkers(page, PagerTest.NAMESPACE, execContext, null);

		// assertEquals(2, markers.size());
		// assertEquals(0, markers.get(0).getNestedMarkers().size());
		// assertEquals(1, markers.get(1).getNestedMarkers().size());

		String newPage = action.replaceMarkers(execContext, page, markers);

		log.debug("new page:\n" + newPage);
		assertEquals(
				"<body>\nThis is the 1st echo content\nThis is the 2nd echo This <b>is</b> child of 2nd echo content\n</body>",
				newPage);

	}

	public void testNestedReplacement()
			throws NestedActionException, ClassNotFoundException, InstantiationException, IllegalAccessException,
			InvocationTargetException, NoSuchMethodException, BadXMLException, NestedActionException
	{

		String page = "<body>\n"
				+ "<pager:echo>in</pager:echo>\n"
				+ "<pager:echo>[<pager:echo>in</pager:echo>]</pager:echo>\n"
				+ "<pager:echo>[<pager:echo>in<pager:echo>si</pager:echo>d<pager:echo>e</pager:echo></pager:echo>]</pager:echo>\n"
				+ "<pager:echo>Hello World!!!</pager:echo>\n" + "</body>";

		Action action = new Action();
		List<ReplacementMarker> markers = action.findMarkers(page, PagerTest.NAMESPACE, execContext, null);
		assertEquals(4, markers.size());

		String newPage = action.replaceMarkers(execContext, page, markers);

		assertEquals("<body>\nin\n[in]\n[inside]\nHello World!!!\n</body>", newPage);

		log.debug("sb:" + newPage);

	}

}
