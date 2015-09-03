
package org.xmlactions.pager.config;


import java.io.InvalidObjectException;
import java.net.MalformedURLException;

import junit.framework.TestCase;

import org.apache.commons.configuration.ConfigurationException;
import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.xmlactions.action.ActionConst;
import org.xmlactions.action.config.ExecContext;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.pager.context.ShowExecContext;

public class ExecContextTest extends TestCase
{

	private static Logger log = LoggerFactory.getLogger(ExecContextTest.class);

	public void testCreate() throws InvalidObjectException, ConfigurationException, MalformedURLException
	{

		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("/config/spring/spring-config.xml");
		ExecContext execContext = (ExecContext)applicationContext.getBean(ActionConst.EXEC_CONTEXT_BEAN_REF);
		execContext.setApplicationContext(applicationContext);
		execContext.put("newKey1", "New Key Value 1");
		execContext.put("newKey2", "replace ${newKey1}");
		assertEquals("New Key Value 1", execContext.getString("newKey1"));
		assertEquals("replace New Key Value 1", execContext.getString("newKey2"));
		log.debug("newKey2:" + execContext.get("newKey2"));
		log.debug("echo:" + execContext.get("echo"));
	}

	public void testMapNameAndKey() throws InvalidObjectException, ConfigurationException, MalformedURLException
	{

		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				ActionConst.SPRING_STARTUP_CONFIG);
		ExecContext execContext = (ExecContext)applicationContext.getBean(ActionConst.EXEC_CONTEXT_BEAN_REF);
		execContext.setApplicationContext(applicationContext);
		execContext.put("newKey1", "New Key Value 1");
		execContext.put("newKey2", "replace ${newKey1}");
		execContext.put("session:key1", "this is session:key1 value");
		execContext.put("session:key2", "session:key2 = [${newKey2}]");
		assertEquals("New Key Value 1", execContext.getString("newKey1"));
		assertEquals("replace New Key Value 1", execContext.getString("newKey2"));
		log.debug("newKey2:" + execContext.get("newKey2"));
		log.debug("echo:" + execContext.get("echo"));
		assertEquals("this is session:key1 value", execContext.get("session:key1"));
		assertEquals("session:key2 = [replace New Key Value 1]", execContext.get("session:key2"));
	}
	
	public void testXmlKey() throws InvalidObjectException, ConfigurationException, MalformedURLException
	{

		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				ActionConst.SPRING_STARTUP_CONFIG);
		Object obj = applicationContext.getBean("application.xml");
		IExecContext execContext = (IExecContext) applicationContext.getBean(ActionConst.EXEC_CONTEXT_BEAN_REF);
		log.debug(execContext.show());
		log.debug("application.title:" + execContext.get("application.title"));
		log.debug("application.name:" + execContext.get("application.name"));
		assertTrue(execContext.get("application.title") != null);
		assertTrue(execContext.get("application.name") != null);
	}
}
