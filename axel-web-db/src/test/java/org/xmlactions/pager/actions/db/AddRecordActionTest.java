package org.xmlactions.pager.actions.db;


import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import junit.framework.TestCase;


import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.xmlactions.action.Action;
import org.xmlactions.action.ActionConst;
import org.xmlactions.action.NestedActionException;
import org.xmlactions.action.config.ExecContext;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.common.xml.BadXMLException;
import org.xmlactions.pager.actions.form.Add;
import org.xmlactions.pager.actions.form.AddTest;

public class AddRecordActionTest extends TestCase {


	private static Logger log = LoggerFactory.getLogger(AddRecordActionTest.class);

	private static IExecContext execContext;

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

	public void tearDown()
	{

		execContext = null;
	}

	public void testParse()
			throws IOException, NestedActionException, ClassNotFoundException, InstantiationException,
			IllegalAccessException, InvocationTargetException, NoSuchMethodException, BadXMLException
	{

		String page = "<body>\n"
				+ " <pager:add_record storage_config_ref=\"storageConfig\" table_name=\"tb_hobby\">\n" //
				+ "  <pager:field_list>\n"
				+ "    <pager:field name=\"tb_hobby.id\">1</pager:field>\n" //
				+ "    <pager:field name=\"description\">Any old description</pager:field>\n" //
				+ "  </pager:field_list>\n"
				+ " </pager:add_record>\n" //
				+ "</body>";
		Action action = new Action("/pages", "static page", "pager");
		assertNotNull(execContext.get("storageConfig"));
		try {
			String newPage = action.processPage(execContext, page);
		} catch (Exception ex) {
			// as expected cant write to db
		}
	}
}
