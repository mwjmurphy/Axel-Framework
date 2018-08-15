
package org.xmlactions.pager.actions.form;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.apache.bsf.BSFException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.xmlactions.action.Action;
import org.xmlactions.action.ActionConst;
import org.xmlactions.action.NestedActionException;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.common.xml.BadXMLException;
import org.xmlactions.pager.env.EnvironmentAccess;

public class FieldCodeTest
{

	private static Logger log = LoggerFactory.getLogger(FieldCodeTest.class);

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
			execContext = (IExecContext) applicationContext.getBean(ActionConst.EXEC_CONTEXT_BEAN_REF);
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
	public void testField() throws BSFException
	{

		execContext.put("xpos", "10");
		Field field = new Field();
		field.setName("name");
		field.setX("100");
		field.setY("200");
		assertEquals("name", field.getName());
		assertEquals("100", field.getX(execContext));
		assertEquals("200", field.getY(execContext));
		field.setX("100*2");
		assertEquals("200", field.getX(execContext));
		field.setX("100+${xpos}");
		assertEquals("110", field.getX(execContext));
	}

	@Test
	public void testParse()
	throws IOException, NestedActionException, ClassNotFoundException, InstantiationException,
	IllegalAccessException, InvocationTargetException, NoSuchMethodException, BadXMLException
	{

		String page = 
			"<body>\n" +
			"  <pager:field_code name=\"tb_1.id\" x=\"100\" y=\"100\"/>\n" +
			"</body>";
		Action action = new Action("/pages", "static page", "pager");
		String newPage = action.processPage(execContext, page);
		log.debug("newPage:" + newPage);
		assertEquals("<body>\n  \n</body>", newPage);
	}

	@Test
	public void testDraw()
		throws IOException, NestedActionException, ClassNotFoundException, InstantiationException,
		IllegalAccessException, InvocationTargetException, NoSuchMethodException, BadXMLException
	{

		String page = 
			"<body>\n" +
			"  <pager:edit storage_config_ref=\"storageConfig\" table_name=\"tb_all_types\"" +
			"   pk_value=\"1\">\n" +
			"     <pager:field_list>\n" +
			"        <pager:field name=\"id\"/>\n" +
			"        <pager:field name=\"text\"/>\n" +
			"        <pager:field_code header_name=\"code header\" name=\"tb_1.id\" x=\"100\" y=\"100\">\n" +
			"           <pager:code call=\"org.xmlactions.pager.actions.form.FieldCodeTestHandler.getCurrentValue\">\n" +
			"              <pager:param value=\"value for parameter\"/>\n" +
			"           </pager:code>\n" +
			"        </pager:field_code>\n" +
			"     </pager:field_list>\n" +
			"     <pager:link href=\"xxx\" submit=\"true\"/>\n" +
			"  </pager:edit>\n" +
			"</body>";
		Action action = new Action("/pages", "static page", "pager");
		String newPage = action.processPage(execContext, page);
		log.debug("newPage:" + newPage);
		assertTrue(newPage.contains("param[value"));
	}
}
