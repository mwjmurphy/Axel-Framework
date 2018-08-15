
package org.xmlactions.pager.actions.form;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.xmlactions.action.Action;
import org.xmlactions.action.ActionConst;
import org.xmlactions.action.NestedActionException;
import org.xmlactions.action.config.ExecContext;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.common.xml.BadXMLException;
import org.xmlactions.pager.env.EnvironmentAccess;

public class AddTest
{

	private static Logger log = LoggerFactory.getLogger(AddTest.class);

	private static IExecContext execContext;

	@Before
	public void beforeMethod() {
		org.junit.Assume.assumeTrue(EnvironmentAccess.runDatabaseTests());
		setUp();
	}
	
	public void setUp()
	{
		if (execContext == null) {
			ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(new String[] {
					ActionConst.SPRING_STARTUP_CONFIG, "classpath*:/config/spring/test-spring-pager-web-startup.xml" });
			execContext = (ExecContext)applicationContext.getBean(ActionConst.EXEC_CONTEXT_BEAN_REF);
			execContext.setApplicationContext(applicationContext);
			File file = new File("src/test/resource");
	        execContext.put(ActionConst.WEB_REAL_PATH_BEAN_REF, file.getAbsolutePath());
		}
	}

	@After
	public void tearDown()
	{

		execContext = null;
	}

	@Test
    public void testClass()
	{

		Add add = new Add();
		add.setExecContext(execContext);
		add.setStorage_config_ref("pager.storage");
		add.setTable_name("table_name");
		assertEquals(add.toString(), "add storage_config_ref [pager.storage] table_name [table_name]", add.toString());
	}

	@Test
	public void testParse()
			throws IOException, NestedActionException, ClassNotFoundException, InstantiationException,
			IllegalAccessException, InvocationTargetException, NoSuchMethodException, BadXMLException
	{

		String page = "<body>\n"
				+ " <pager:add storage_config_ref=\"storageConfig\" table_name=\"tb_hobby\" theme_name=\"riostl\">\n" //
				+ "  <pager:field_list>\n"
				+ "    <pager:field name=\"id\"/>\n" //
				+ "    <pager:field name=\"description\"/>\n" //
				+ "  </pager:field_list>\n"
				+ "  <pager:link name=\"submit\" uri=\"rio_insert.xhtml\" submit=\"true\"/>\n" + " </pager:add>\n" //
				+ "</body>";
		Action action = new Action("/pages", "static page", "pager");
		assertNotNull(execContext.get("storageConfig"));
		String newPage = action.processPage(execContext, page);
		log.debug("newPage:" + newPage);

		//String expected = "<body>\n"
		//		+ " <table id=\"null\" class=\"table\" ><input name=\"storage.config.ref\" value=\"storageConfig\" type=\"hidden\" /><input name=\"table.name\" value=\"tb_hobby\" type=\"hidden\" /><tr class=\"tr\" ><td class=\"td\" ><table class=\"table\" ><tr class=\"tr\" ><th align=\"left\" class=\"th\" >Description</th><td class=\"td\" ><input maxlength=\"45\" name=\"tb_hobby.description\" value=\"\" class=\"editbox\" type=\"text\" size=\"45\" /></td></tr></table></td></tr><tr style=\"tr\" ><td style=\"td\" align=\"right\" ><table style=\"table\" ><tr style=\"tr\" /></table></td><td style=\"td\" ><a onclick=\"return(showValidationErrors(insertRecord('null')));\" class=\"none\" href=\"rio_insert.xhtml\" ><span class=\"link_menu\" >submit</span></a></td></tr></table>\n"
		//		+ "</body>";
		log.debug("newPage:" + newPage);
		assertTrue(newPage.contains("<form "));
	}

	@Test
	public void testPresentation_form() throws IOException, NestedActionException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, BadXMLException {
		
		Action action = new Action("/pages", "add_presentation_form.uhtml", "pager");
		assertNotNull(execContext.get("storageConfig"));
		String newPage = action.processPage(execContext);
		log.debug("newPage:" + newPage);
		assertTrue(newPage.contains("<form "));
		assertTrue(newPage.contains("add form"));
		assertTrue(newPage.contains("showValidationErrors"));
		
	}

}
