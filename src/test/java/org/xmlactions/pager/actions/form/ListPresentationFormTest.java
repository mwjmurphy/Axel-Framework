
package org.xmlactions.pager.actions.form;


import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import junit.framework.TestCase;

import org.apache.commons.configuration.ConfigurationException;
import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.dom4j.DocumentException;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.xmlactions.action.Action;
import org.xmlactions.action.ActionConst;
import org.xmlactions.action.NestedActionException;
import org.xmlactions.action.config.ExecContext;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.common.xml.BadXMLException;

public class ListPresentationFormTest extends TestCase
{

	private static Logger log = LoggerFactory.getLogger(ListPresentationFormTest.class);

	// private static Map<String, Object> execContext;
	IExecContext execContext;

	public void setUp()
	{

		if (execContext == null) {
			ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(new String[] {
					ActionConst.SPRING_STARTUP_CONFIG, "classpath*:/config/spring/test-spring-pager-web-startup.xml" });
			execContext = (ExecContext)applicationContext.getBean(ActionConst.EXEC_CONTEXT_BEAN_REF);
			execContext.setApplicationContext(applicationContext);
		}
	}

	public void tearDown()
	{

		execContext = null;
	}
	
	public void testNothing() {}

	public void _testParam()
			throws ConfigurationException, IOException, NestedActionException, DocumentException,
			ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException,
			NoSuchMethodException, BadXMLException
	{

		// execContext.put("data_source", DataStructureTest.getDatabase());
		// execContext.put("connection", DBServerTest.getConnection());
		Action action = new Action("/pages", "list_with_presentation_form.xhtml", "pager");
		String page = action.processPage(execContext);
		log.debug("page:" + page);
		assertTrue("page should contain [This is inside list]", page.indexOf("testing List from list.xhtml") > -1);
	}

}
