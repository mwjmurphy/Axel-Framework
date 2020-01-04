
package org.xmlactions.pager.actions;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.dom4j.DocumentException;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.xmlactions.action.Action;
import org.xmlactions.action.ActionConst;
import org.xmlactions.action.NestedActionException;
import org.xmlactions.action.ReplacementMarker;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.common.xml.BadXMLException;
import org.xmlactions.pager.PagerTest;


public class HttpActionTest
{

	private static Logger log = LoggerFactory.getLogger(HttpActionTest.class);

	
	private static IExecContext execContext;

	@Before
	public void setUp()
	{

		if (execContext == null) {
			ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(
					ActionConst.SPRING_STARTUP_CONFIG);
			execContext = (IExecContext) applicationContext.getBean(ActionConst.EXEC_CONTEXT_BEAN_REF);
		}
	}

	@Test
	public void testHttpGet() throws Exception {
		
		HttpAction httpAction = new HttpAction();
		httpAction.setHref("https://google.com");
		httpAction.setMethod("get");
		String response = httpAction.execute(execContext);
		assertNotNull(response);
	}

	@Test
	public void testHttpGetWithParams() throws Exception {
		
		HttpAction httpAction = new HttpAction();
		httpAction.setHref("https://google.com");
		httpAction.setMethod("get");
		Param p = new Param();
		p.setValue("p=spain");
		httpAction.getParams().add(p);
		String response = httpAction.execute(execContext);
		assertNotNull(response);
	}

	@Test
	public void testHttpGetToKey() throws Exception {
		
		HttpAction httpAction = new HttpAction();
		httpAction.setHref("https://google.com");
		httpAction.setMethod("get");
		httpAction.setKey("gkey");
		String response = httpAction.execute(execContext);
		assertEquals("", response);
		assertTrue(execContext.getString("gkey") != null);
	}

	// @Test
	public void testHttpPost() throws Exception {
		
		HttpAction httpAction = new HttpAction();
		httpAction.setHref("https://google.com");
		httpAction.setMethod("post");
		Param p = new Param();
		p.setValue("p=spain");
		httpAction.getParams().add(p);
		String response = httpAction.execute(execContext);
		assertNotNull(response);
	}
	
	@Test
	public void testProcessPage() throws Exception
	{

		Action action = new Action("src/test/resources", "pages/http.xhtml", PagerTest.NAMESPACE);

		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(ActionConst.SPRING_STARTUP_CONFIG);
		IExecContext execContext = (IExecContext) applicationContext.getBean(ActionConst.EXEC_CONTEXT_BEAN_REF);

		String page = action.processPage(execContext);
		assertTrue(page.contains("<title>"));
		assertTrue(execContext.getString("gkey").contains("<title>"));
		
	}


}
