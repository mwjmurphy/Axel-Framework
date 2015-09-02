
package org.xmlactions.pager.actions;


import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.dom4j.DocumentException;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.xmlactions.action.Action;
import org.xmlactions.action.ActionConst;
import org.xmlactions.action.NestedActionException;
import org.xmlactions.action.ReplacementMarker;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.common.xml.BadXMLException;
import org.xmlactions.pager.PagerTest;


public class EchoActionTest extends TestCase
{

	private static Logger log = LoggerFactory.getLogger(EchoActionTest.class);

	
	private static IExecContext execContext;

	public void setUp()
	{

		if (execContext == null) {
			ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(
					ActionConst.SPRING_STARTUP_CONFIG);
			execContext = (IExecContext) applicationContext.getBean(ActionConst.EXEC_CONTEXT_BEAN_REF);
		}
	}

	public void testMarkersEcho()
			throws IOException, DocumentException, ClassNotFoundException, InstantiationException,
			IllegalAccessException, InvocationTargetException, NoSuchMethodException, BadXMLException,
			NestedActionException
	{

		Action action = new Action();
		String page = action.loadPage("src/test/resources", "pages/echo.xhtml");
		List<ReplacementMarker> list = action.findMarkers(page, PagerTest.NAMESPACE, execContext, null);
		assertEquals(2, list.size());
	}

	public void testReplacement1()
			throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException,
			InvocationTargetException, NoSuchMethodException, BadXMLException, NestedActionException
	{

		String page = "<pager:echo>content of echo</pager:echo>";
		Action action = new Action(null, null, "pager");
		List<ReplacementMarker> markers = action.findMarkers(page, PagerTest.NAMESPACE, execContext, null);

		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				ActionConst.SPRING_STARTUP_CONFIG);
		Map<String, Object> execContext = (Map<String, Object>) applicationContext
				.getBean(ActionConst.EXEC_CONTEXT_BEAN_REF);

		String newPage = action.replaceMarkers((IExecContext) execContext, page, markers);
		log.debug("new page:\n" + newPage);
		assertEquals("content of echo", newPage);

	}

	public void testReplacement2()
			throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException,
			InvocationTargetException, NoSuchMethodException, BadXMLException, NestedActionException
	{

		String page = "<pager:echo><b>content</b> of echo</pager:echo>";
		Action action = new Action(null, null, "pager");
		List<ReplacementMarker> markers = action.findMarkers(page, PagerTest.NAMESPACE, execContext, null);

		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				ActionConst.SPRING_STARTUP_CONFIG);
		Map<String, Object> execContext = (Map<String, Object>) applicationContext
				.getBean(ActionConst.EXEC_CONTEXT_BEAN_REF);

		String newPage = action.replaceMarkers((IExecContext) execContext, page, markers);
		log.debug("new page:\n" + newPage);
		assertEquals("<b>content</b> of echo", newPage);

	}

	public void testProcessPage() throws Exception
	{

		Action action = new Action("src/test/resources", "pages/echo.xhtml", PagerTest.NAMESPACE);

		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				ActionConst.SPRING_STARTUP_CONFIG);
		IExecContext execContext = (IExecContext) applicationContext.getBean(ActionConst.EXEC_CONTEXT_BEAN_REF);

		String page = action.processPage(execContext);
		// log.debug("new page:\n" + page);
		assertTrue(page.indexOf("This is the 1st echo content") >= 0);
		assertTrue(page.indexOf("This is the 2nd echo content") >= 0);
	}

}
