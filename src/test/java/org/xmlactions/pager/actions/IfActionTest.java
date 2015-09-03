
package org.xmlactions.pager.actions;


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
import org.xmlactions.pager.actions.InsertAction;

public class IfActionTest extends TestCase
{

	private static Logger log = LoggerFactory.getLogger(IfActionTest.class);

	private static IExecContext execContext;

	public void setUp()
	{

		if (execContext == null) {
			ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(
					ActionConst.SPRING_STARTUP_CONFIG);
			execContext = (IExecContext) applicationContext.getBean(ActionConst.EXEC_CONTEXT_BEAN_REF);
		}
	}

	public void testIf1()
			throws NestedActionException, ClassNotFoundException, InstantiationException, IllegalAccessException,
			InvocationTargetException, NoSuchMethodException, BadXMLException
	{

		String page = "<body>\n" + " <pager:if expression=\"1==1\">\n" + "  Echo we're from inside if 1==1\n"
				+ " </pager:if>\n" + "</body>";
		Action action = new Action(null, null, "pager");
		List<ReplacementMarker> markers = action.findMarkers(page, ActionConst.DEFAULT_PAGER_NAMESPACE, execContext, null);

		String newPage = action.replaceMarkers(execContext, page, markers);
		// log.debug("new page:\n" + sb);
		assertEquals("<body>\n \n  Echo we're from inside if 1==1\n \n</body>", newPage);
	}

	public void testIf2()
			throws NestedActionException, ClassNotFoundException, InstantiationException, IllegalAccessException,
			InvocationTargetException, NoSuchMethodException, BadXMLException
	{

		String page = "<body>\n" + " <pager:if expression=\"1==2\">\n" + "  Echo we're from inside if 1==2\n"
				+ " </pager:if>\n" + "</body>";
		Action action = new Action(null, null, "pager");
		List<ReplacementMarker> markers = action.findMarkers(page, ActionConst.DEFAULT_PAGER_NAMESPACE, execContext, null);

		String newPage = action.replaceMarkers(execContext, page, markers);
		// log.debug("new page:\n" + sb);
		assertEquals("<body>\n \n</body>", newPage);
	}

	public void testIf3()
			throws NestedActionException, ClassNotFoundException, InstantiationException, IllegalAccessException,
			InvocationTargetException, NoSuchMethodException, BadXMLException
	{

		String page = "<body>\n"
				+ " <pager:if expression=\"1==1\">[<pager:echo>Echo we're from inside if 1==1</pager:echo>]\n"
				+ " </pager:if>" + "</body>";
		Action action = new Action(null, null, "pager");
		List<ReplacementMarker> markers = action.findMarkers(page, ActionConst.DEFAULT_PAGER_NAMESPACE, execContext, null);

		String newPage = action.replaceMarkers(execContext, page, markers);
		log.debug("new page:\n" + newPage);
		assertEquals("<body>\n [Echo we're from inside if 1==1]\n </body>", newPage);
	}

	public void testIf4()
			throws NestedActionException, ClassNotFoundException, InstantiationException, IllegalAccessException,
			InvocationTargetException, NoSuchMethodException, BadXMLException
	{

		String page = "<body>\n" + " <pager:if expression=\"1==1\">\n"
				+ "  <pager:echo>Echo we're from inside if 1==1</pager:echo>\n" + "  <pager:if expression=\"2==3\">\n"
				+ "   <pager:echo>Echo we're from inside if 2==3</pager:echo>\n" + "  </pager:if>\n" + " </pager:if>\n"
				+ "</body>";
		Action action = new Action(null, null, "pager");
		List<ReplacementMarker> markers = action.findMarkers(page, ActionConst.DEFAULT_PAGER_NAMESPACE, execContext, null);

		String newPage = action.replaceMarkers(execContext, page, markers);
		log.debug("new page:\n" + newPage);
		assertTrue(newPage.indexOf("Echo we're from inside if 1==1") >= 0);
	}

	public void testIfStrSubstutitor()
			throws NestedActionException, ClassNotFoundException, InstantiationException, IllegalAccessException,
			InvocationTargetException, NoSuchMethodException, BadXMLException
	{

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("k1", "value of k1");
		map.put("true", true);
		execContext.addNamedMap("named.map", map);
		log.debug("named.map:true:" + execContext.get("named.map:true"));
		String page = "<body>\n" + "<pager:if expression=\"true==${named.map:true}\">\n"
				+ " We got true [${named.map:true}\n" + " </pager:if>\n\n" + "</body>";
		Action action = new Action(null, null, "pager");
		List<ReplacementMarker> markers = action.findMarkers(page, ActionConst.DEFAULT_PAGER_NAMESPACE, execContext, null);

		String newPage = action.replaceMarkers(execContext, page, markers);
		log.debug("new page:\n" + newPage);
		// assertTrue(newPage.indexOf("Echo we're from inside if 1==1") >= 0);
	}

	public void testImportFromURL() throws Exception
	{

		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				ActionConst.SPRING_STARTUP_CONFIG);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("fred", "my name is fred");
		IExecContext execContext = (IExecContext) applicationContext.getBean(ActionConst.EXEC_CONTEXT_BEAN_REF);
		execContext.setApplicationContext(applicationContext);
		execContext.put("key", 1);
		execContext.addNamedMap("request", map);
		assertEquals("my name is fred", execContext.get("request:fred"));
		assertEquals(1, execContext.get("key"));
		InsertAction insert = new InsertAction();
		// insert.setRealPath(new File("src/test/resource").getAbsolutePath());
		insert.setPath("/pages");
		insert.setPage("if.xhtml");
		insert.setNamespace("pager");
		String page = (String) insert.execute(execContext);
		log.debug("page:" + page);
		assertTrue(page.contains("if 1==1"));
		assertFalse("page contains [if 2==3]\n" + page, page.contains("if 2==3"));
	}

	public void testPagerLoad()
			throws IOException, NestedActionException, DocumentException, ClassNotFoundException,
			InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException,
			BadXMLException
	{

		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				ActionConst.SPRING_STARTUP_CONFIG);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("fred", "my name is fred");
		IExecContext execContext = (IExecContext) applicationContext.getBean(ActionConst.EXEC_CONTEXT_BEAN_REF);
		execContext.put("key", 1);
		execContext.addNamedMap("request", map);

		execContext.put(ActionConst.PAGE_NAMESPACE_BEAN_REF, "pager");
		execContext.put(ActionConst.WEB_REAL_PATH_BEAN_REF, "/pages");
		Action action = new Action("/pages", "if.xhtml", "pager");
		String page = action.processPage(execContext);
		log.debug("page:" + page);
		assertTrue(page.contains("if 1==1"));
		assertFalse(page.contains("if 2==3"));

		assertTrue(page.contains("in if [10==10]"));
		assertFalse(page.contains("elseif 11==11]"));
		assertFalse(page.contains("else ]10==10["));
		assertTrue(page.contains("in elseif [21==21]"));
		assertTrue(page.contains("else after ]30==31["));

	}
}
