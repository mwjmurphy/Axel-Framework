
package org.xmlactions.pager.actions;


import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;

import junit.framework.TestCase;

import org.apache.commons.lang.Validate;
import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.dom4j.DocumentException;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.ResourceUtils;
import org.xmlactions.action.Action;
import org.xmlactions.action.ActionConst;
import org.xmlactions.action.NestedActionException;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.common.io.ResourceCommon;
import org.xmlactions.common.xml.BadXMLException;
import org.xmlactions.pager.actions.InsertAction;

public class EvalActionTest extends TestCase
{

	private static Logger log = LoggerFactory.getLogger(EvalActionTest.class);

	private static IExecContext execContext;

	public void setUp()
	{

		if (execContext == null) {
			ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(
					ActionConst.SPRING_STARTUP_CONFIG);

			execContext = (IExecContext) applicationContext.getBean(ActionConst.EXEC_CONTEXT_BEAN_REF);
		}
	}

	public void testEval()
	throws IOException, NestedActionException, ClassNotFoundException, InstantiationException,
	IllegalAccessException, InvocationTargetException, NoSuchMethodException, BadXMLException
	{

		String page = "<pager:eval key=\"eval-content\">1+1*100</pager:eval>"
			+ "<pager:echo>eval-content:${eval-content}</pager:echo>";

		Action action = new Action("", "", ActionConst.DEFAULT_PAGER_NAMESPACE);
		String newPage = action.processPage(execContext, page);
		log.debug("newPage:" + newPage);
		assertEquals("eval-content:101", newPage);
	}

	public void testPut2()
	throws IOException, NestedActionException, ClassNotFoundException, InstantiationException,
	IllegalAccessException, InvocationTargetException, NoSuchMethodException, BadXMLException
	{

		String page =
			"<pager:put key=\"put-content\">This is the 1st put content" +
			"[<pager:echo>inner echo content</pager:echo>]</pager:put>"	+
			"<pager:echo>from execContext:${put-content}</pager:echo>";

		Action action = new Action("", "", ActionConst.DEFAULT_PAGER_NAMESPACE);
		String newPage = action.processPage(execContext, page);
		log.debug("newPage:" + newPage);
		assertEquals("from execContext:This is the 1st put content[inner echo content]", newPage);
		//assertTrue(newPage.startsWith("put-content:This is the 1st put content"));
	}

	public void testPutInsert()
	throws IOException, NestedActionException, ClassNotFoundException, InstantiationException,
	IllegalAccessException, InvocationTargetException, NoSuchMethodException, BadXMLException
	{
        URL url = ResourceUtils.class.getResource("/pages");
        Validate.notNull(url, "Resource [" + "/pages" + "] not found");

		log.warn("url.path:" + url.getPath());
		log.warn("url.file:" + url.getFile());
		execContext.put(ActionConst.WEB_REAL_PATH_BEAN_REF, "/pages");
		String page = "<pager:put key=\"put-content\"><pager:insert page=\"insert3.xhtml\"/></pager:put>"
			+ "<pager:echo>put-content:${put-content}</pager:echo>";

		Action action = new Action("", "", ActionConst.DEFAULT_PAGER_NAMESPACE);
		String newPage = action.processPage(execContext, page);
		String s = execContext.getString("put-content");
		assertEquals("content of insert3.html", s);
		assertEquals("put-content:content of insert3.html", newPage);
	}

	public void testPutInsert2()
	throws IOException, NestedActionException, ClassNotFoundException, InstantiationException,
	IllegalAccessException, InvocationTargetException, NoSuchMethodException, BadXMLException
	{

		execContext.put(ActionConst.WEB_REAL_PATH_BEAN_REF, "/pages");
		String page = "<pager:put key=\"put-content\"><pager:insert page=\"insert3.xhtml\"/></pager:put>"
			+ "<pager:echo>put-content:[${put-content}]</pager:echo>";

		Action action = new Action("/pages", "", ActionConst.DEFAULT_PAGER_NAMESPACE);
		String newPage = action.processPage(execContext, page);
		String s = execContext.getString("put-content");
		assertEquals("content of insert3.html", s);
		assertEquals("put-content:[content of insert3.html]", newPage);
	}

	public void testImportFromFile() throws Exception
	{

		InsertAction insert = new InsertAction();
		insert.setPath(new File("src/test/resources/pages").getAbsolutePath());
		insert.setPage("put.xhtml");
		insert.setNamespace("pager");
		String page = (String) insert.execute(execContext);
	}

	public void testImportFromURL() throws Exception
	{

		InsertAction insert = new InsertAction();
		// insert.setRealPath(new File("src/test/resource").getAbsolutePath());
		insert.setPath("/pages");
		insert.setPage("put.xhtml");
		insert.setNamespace("pager");
		String page = (String) insert.execute(execContext);
	}

	public void testPagerLoad()
	throws IOException, NestedActionException, DocumentException, ClassNotFoundException,
	InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException,
	BadXMLException
	{

		execContext.put(ActionConst.PAGE_NAMESPACE_BEAN_REF, "pager");
		execContext.put(ActionConst.WEB_REAL_PATH_BEAN_REF, "/pages");
		Action action = new Action("/pages", "put.xhtml", "pager");
		String page = action.processPage(execContext);
		log.debug("page:" + page);
		assertTrue("Should have found [1st put content] on the page", page.indexOf("1st put content") > -1);

	}
}
