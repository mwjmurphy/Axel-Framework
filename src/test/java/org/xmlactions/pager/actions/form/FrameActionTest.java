
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

public class FrameActionTest extends TestCase
{

	private static Logger log = LoggerFactory.getLogger(FrameActionTest.class);

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

	public void testParam()
			throws ConfigurationException, IOException, NestedActionException, DocumentException,
			ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException,
			NoSuchMethodException, BadXMLException
	{

		// execContext.put("data_source", DataStructureTest.getDatabase());
		// execContext.put("connection", DBServerTest.getConnection());
		execContext.put(ActionConst.WEB_REAL_PATH_BEAN_REF, "src/test/resources");
		Action action = new Action("/pages", "frame.xhtml", "pager");
		String page = action.processPage(execContext);
		log.debug("page:" + page);
		assertTrue("And more bla de bla bla bla bla", page.indexOf("And more bla de bla bla bla bla") > -1);
	}

	public void testListWithSearch()
			throws ConfigurationException, IOException, NestedActionException, DocumentException,
			ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException,
			NoSuchMethodException, BadXMLException
	{

		HashMap<String, Object> map = new HashMap();
		map.put("tb_project.id", " < 5");
		// execContext.put("data_source", DataStructureTest.getDatabase());
		// execContext.put("connection", DBServerTest.getConnection());
		// execContext.addNamedMap(PagerWebConst.REQUEST, map);
		execContext.put(ActionConst.WEB_REAL_PATH_BEAN_REF, "src/test/resources");
		Action action = new Action("/pages", "frame.xhtml", "pager");
		String page = action.processPage(execContext);
		log.debug("page:" + page);
		assertTrue("And more bla de bla bla bla bla", page.indexOf("And more bla de bla bla bla bla") > -1);
	}

	public void testWrapper()
	throws ConfigurationException, IOException, NestedActionException, DocumentException,
	ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException,
	NoSuchMethodException, BadXMLException
	{

		// execContext.put("data_source", DataStructureTest.getDatabase());
		// execContext.put("connection", DBServerTest.getConnection());
		execContext.put(ActionConst.WEB_REAL_PATH_BEAN_REF, "src/test/resources");
		Action action = new Action("/pages", "frame_wrapper.xhtml", "pager");
		String page = action.processPage(execContext);
		log.debug("page:" + page);
		assertTrue(page.indexOf("bla bla bla from echo[Innert Echo]") > -1);
	}


}
