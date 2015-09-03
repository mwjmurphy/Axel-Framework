
package org.xmlactions.pager.actions;


import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import junit.framework.TestCase;

import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.dom4j.DocumentException;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.xmlactions.action.Action;
import org.xmlactions.action.ActionConst;
import org.xmlactions.action.NestedActionException;
import org.xmlactions.action.config.ExecContext;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.common.text.Html;
import org.xmlactions.common.xml.BadXMLException;
import org.xmlactions.pager.actions.InsertAction;

public class InsertActionTest extends TestCase
{

	private static Logger log = LoggerFactory.getLogger(InsertActionTest.class);

	public void testImportFromFile() throws Exception
	{
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(new String[] {
				ActionConst.SPRING_STARTUP_CONFIG, "classpath*:config/spring/test-spring-pager-web-startup.xml" });
		IExecContext execContext = (ExecContext)applicationContext.getBean(ActionConst.EXEC_CONTEXT_BEAN_REF);
		execContext.setApplicationContext(applicationContext);

		InsertAction insert = new InsertAction();
		insert.setPath(new File("src/test/resources/pages").getAbsolutePath());
		insert.setPage("insert_test.xhtml");
		insert.setNamespace("pager");
		String page = (String) insert.execute(execContext);
		log.debug("page:" + page);
		assertTrue(page.contains("arg1(String):the value of param1 arg2(String):code class ="));
		assertTrue(page.contains("toString:org.xmlactions.test.CodeCall"));
		assertTrue(page.contains("This is the echo content"));

	}

	public void testImportFromURL() throws Exception
	{

		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				ActionConst.SPRING_STARTUP_CONFIG);

		IExecContext execContext = (IExecContext) applicationContext.getBean(ActionConst.EXEC_CONTEXT_BEAN_REF);

		InsertAction insert = new InsertAction();
		// insert.setRealPath(new File("src/test/resource").getAbsolutePath());
		insert.setPath("/pages");
		insert.setPage("insert_test.xhtml");
		insert.setNamespace("pager");
		String page = (String) insert.execute(execContext);

		log.debug("page:" + page);
		assertTrue(page.contains("arg1(String):the value of param1 arg2(String):code class ="));
		assertTrue(page.contains("toString:org.xmlactions.test.CodeCall"));
		assertTrue(page.contains("This is the echo content"));
	}

	public void testPagerLoad()
			throws IOException, NestedActionException, DocumentException, ClassNotFoundException,
			InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException,
			BadXMLException
	{

		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				ActionConst.SPRING_STARTUP_CONFIG);

		IExecContext execContext = (IExecContext) applicationContext.getBean(ActionConst.EXEC_CONTEXT_BEAN_REF);

		execContext.put(ActionConst.PAGE_NAMESPACE_BEAN_REF, "pager");
		execContext.put(ActionConst.WEB_REAL_PATH_BEAN_REF, "/pages");
		Action action = new Action("/pages", "insert.xhtml", "pager");
		String page = action.processPage(execContext);
		log.debug("page:" + page);
		assertTrue(page.contains("insert from insert3.htmlcontent of insert3.html"));

	}

	public void testRemoveHtml()
			throws IOException, NestedActionException, DocumentException, ClassNotFoundException,
			InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException,
			BadXMLException
	{
		
		String html = 
				"<html xmlns=\"http://www.w3.org/1999/xhtml\"\n" + 
			    "	xmlns:pager=\"http://www.riostl.com/schema/pager\"\n" +  
			    "	xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" + 
			    "	xsi:schemaLocation=\"http://www.riostl.com/schema/pager http://www.riostl.com/schema/pager.xsd\">\n" + 
			    "	<head>\n" + 
			    "	</head>\n" + 
			    "	<body>\n" + 
			    "	<pager:insert page=\"insert2.xhtml\" remove_html=\"true\"/>\n" + 
			    "	</body>\n" + 
			    "</html>\n";
		
		String page = Html.removeOuterHtml(html);
		log.debug(page);

	}

}
