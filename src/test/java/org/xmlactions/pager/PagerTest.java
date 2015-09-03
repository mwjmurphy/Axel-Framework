
package org.xmlactions.pager;


import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
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

public class PagerTest extends TestCase
{

	private static Logger log = LoggerFactory.getLogger(PagerTest.class);

	public static char[][] NAMESPACE = {"pager".toCharArray()};

	private static IExecContext execContext;

	public void setUp()
	{

		if (execContext == null) {
			ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(
					ActionConst.SPRING_STARTUP_CONFIG);
			execContext = (IExecContext) applicationContext.getBean(ActionConst.EXEC_CONTEXT_BEAN_REF);
			execContext.setApplicationContext(applicationContext);
		}
	}

	public void testLoadPage() throws IOException
	{

		Action action = new Action();
		String page = action.loadPage("src/test/resources", "pages/echo.xhtml");
		assertTrue("Missing html element", page.indexOf("<html") >= 0);
	}

	public void testMarkersEcho()
			throws IOException, DocumentException, ClassNotFoundException, InstantiationException,
			IllegalAccessException, InvocationTargetException, NoSuchMethodException, BadXMLException,
			NestedActionException
	{

		Action action = new Action();
		String page = action.loadPage("src/test/resources", "pages/echo.xhtml");
		List<ReplacementMarker> list = action.findMarkers(page, NAMESPACE, execContext, null);
		assertEquals(2, list.size());
	}

	public void testMarkersComplex()
			throws IOException, DocumentException, ClassNotFoundException, InstantiationException,
			IllegalAccessException, InvocationTargetException, NoSuchMethodException, BadXMLException,
			NestedActionException
	{

		Action action = new Action();
		String page = action.loadPage("src/test/resources", "pages/complex.xhtml");
		List<ReplacementMarker> list = action.findMarkers(page, NAMESPACE, execContext, null);
		assertEquals(3, list.size());
		assertEquals(1, list.get(1).getXMLObject().getAttributeCount());
		assertEquals(new String(NAMESPACE[0]), list.get(0).getXMLObject().getNameSpace());
		assertEquals("echo", list.get(2).getXMLObject().getElementName());
		assertEquals("code", list.get(1).getXMLObject().getElementName());
		assertEquals("param", list.get(0).getXMLObject().getChild(0).getElementName());
	}

	public void testParams()
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException,
			NoSuchMethodException, BadXMLException, NestedActionException
	{

		String page = "<pager:code call=\"org.xmlactions.test.CodeCall.call\">zssd<pager:param value=\"value p1\"/><pager:param value=\"value p2\"/></pager:code>";
		Action action = new Action("test", "test", "pager");
		List<ReplacementMarker> markers = action.findMarkers(page, NAMESPACE, execContext, null);
		assertEquals(1, markers.size());
		assertEquals(2, markers.get(0).getNestedMarkers().size());

		String newPage = action.replaceMarkers(execContext, page, markers);

		assertEquals("arg1(String):value p1 arg2(String):value p2", newPage);
		log.debug("result:" + newPage.toString());
	}

	public void testReplacement()
			throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException,
			InvocationTargetException, NoSuchMethodException, BadXMLException, NestedActionException
	{

		Action action = new Action("src/test/resource", "pages/complex.xhtml", "pager");
		String page = action.loadPage("src/test/resources", "pages/complex.xhtml");
		List<ReplacementMarker> markers = action.findMarkers(page, NAMESPACE, execContext, null);

		String newPage = action.replaceMarkers(execContext, page, markers);
		log.debug("new page:\n" + newPage);
		assertTrue(newPage.indexOf("toString:org.xmlactions.test.CodeCall") >= 0);

	}

	public void testProcessPage() throws Exception
	{

		Action action = new Action("src/test/resources", "pages/complex.xhtml", NAMESPACE);

		String page = action.processPage(execContext);
		log.debug("new page:\n" + page);
		assertTrue(page.indexOf("toString:org.xmlactions.test.CodeCall") >= 0);
	}

	public void testProcessPageWithReplacement() throws Exception
	{

		Action action = new Action("src/test/resources", "pages/insert_replace.xhtml", NAMESPACE);
		action.addNameSpace("pager".toCharArray());
		String newPage = action.processPage(execContext);
		log.debug("new page:\n" + newPage);
		log.debug("testkey:\n" + execContext.get("testkey"));
		assertTrue("page should contain [value for test key]\n" + newPage, newPage.indexOf("[value for test key]") >= 0);
	}

	public void testProcessTextPage() throws Exception
	{

		Action action = new Action("src/test/resources", "pages/replace.txt", NAMESPACE);

		Map<String, Object> namedMap = new HashMap<String, Object>();
		namedMap.put("marker", "value for marker");
		execContext.addNamedMap("session", namedMap);
		execContext.put("a", "a == b");
		String page = action.processPage(execContext);
		log.debug("new page:\n" + page);
		assertTrue(page.indexOf("value for marker") >= 0);
		int index = page.indexOf("a == b");
		assertTrue(index >= 0);
		index = page.indexOf("a == b", index + 1);
		assertTrue(index >= 0);

	}

}
