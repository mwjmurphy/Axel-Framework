
package org.xmlactions.pager.actions.form;

import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

import org.apache.commons.configuration.ConfigurationException;
import org.dom4j.DocumentException;
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
import org.xmlactions.web.PagerWebConst;


public class ListCPTest
{

	private static Logger log = LoggerFactory.getLogger(ListCPTest.class);
	
	@Before
	public void beforeMethod() {
		org.junit.Assume.assumeTrue(EnvironmentAccess.runDatabaseTests());
		setUp();
	}
	
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

	@After
	public void tearDown()
	{
		execContext = null;
	}

	@Test
	public void testParam()
			throws ConfigurationException, IOException, NestedActionException, DocumentException,
			ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException,
			NoSuchMethodException, BadXMLException
	{

		Action action = new Action("/pages", "listcp.xhtml", "pager");
		String page = action.processPage(execContext);
		log.debug("page:" + page);
		assertTrue("page should contain [This is inside list]", page.indexOf("testing List from list.xhtml") > -1);
	}

	@Test
	public void testListWithSearch()
			throws ConfigurationException, IOException, NestedActionException, DocumentException,
			ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException,
			NoSuchMethodException, BadXMLException
	{

		HashMap<String, Object> map = new HashMap();
		map.put("tb_project.id", " < 5");
		execContext.addNamedMap(PagerWebConst.REQUEST, map);
		Action action = new Action("/pages", "listcp.xhtml", "pager");
		String page = action.processPage(execContext);
		log.debug("page:" + page);
		assertTrue("page should contain [This is inside list]", page.indexOf("testing List from list.xhtml") > -1);
	}
}
