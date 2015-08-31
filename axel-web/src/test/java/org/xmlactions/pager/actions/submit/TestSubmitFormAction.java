package org.xmlactions.pager.actions.submit;


import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;


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
import org.xmlactions.pager.actions.form.FrameActionTest;

import junit.framework.TestCase;

public class TestSubmitFormAction extends TestCase {

	private static Logger log = LoggerFactory.getLogger(TestSubmitFormAction.class);

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

	public void testForm1()
			throws ConfigurationException, IOException, NestedActionException, DocumentException,
			ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException,
			NoSuchMethodException, BadXMLException
	{
		execContext.put(ActionConst.WEB_REAL_PATH_BEAN_REF, "src/test/resource");
		Action action = new Action("/pages", "submit_form.xhtml", "pager");
		String page = action.processPage(execContext);
		log.debug("page:" + page);
		assertTrue(page.indexOf("<form ") > -1);
		assertTrue(page.indexOf("id=\"uniqueid\"") > -1);
		assertTrue(page.indexOf("onsubmit=\"return showValidationErrors(processCodeCall(captureInputsFromElement(") > -1);
	}


}
