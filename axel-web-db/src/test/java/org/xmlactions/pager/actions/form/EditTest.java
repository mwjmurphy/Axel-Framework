
package org.xmlactions.pager.actions.form;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.xmlactions.action.Action;
import org.xmlactions.action.ActionConst;
import org.xmlactions.action.config.ExecContext;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.pager.env.EnvironmentAccess;

public class EditTest
{

	private static Logger log = LoggerFactory.getLogger(EditTest.class);

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
		String s = add.toString();
		assertEquals(s, "add storage_config_ref [pager.storage] table_name [table_name]", add.toString());
	}

	@Test
	public void testParse() throws Exception {

		Action action = new Action("/pages", "edit.uhtml", "pager");
		assertNotNull(execContext.get("storageConfig"));
		String newPage = action.processPage(execContext);
		log.debug("newPage:" + newPage);

		log.debug("newPage:" + newPage);
		assertTrue(newPage.contains("<form "));
	}

	@Test
	public void testPresentation_form()	throws Exception {

		Action action = new Action("/pages", "edit_presentation_form.uhtml", "pager");
		assertNotNull(execContext.get("storageConfig"));
		String newPage = action.processPage(execContext);
		log.debug("newPage:" + newPage);
		assertTrue(newPage.contains("<form "));
	}
}
