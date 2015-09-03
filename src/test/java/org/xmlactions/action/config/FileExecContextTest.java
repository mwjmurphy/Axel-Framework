
package org.xmlactions.action.config;


import java.io.IOException;

import junit.framework.TestCase;


import org.apache.commons.configuration.ConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.xmlactions.action.ActionConst;
import org.xmlactions.action.config.ExecContext;
import org.xmlactions.action.config.IExecContext;


public class FileExecContextTest extends TestCase
{

	private static Logger log = LoggerFactory.getLogger(FileExecContextTest.class);
	private static final String [] configFiles = {
		"/config/spring/spring-config.xml"
	};
	private static ApplicationContext applicationContext = new ClassPathXmlApplicationContext(configFiles);

	public void testSaveLoad() throws ConfigurationException, IOException
	{
		String fileName = "build/persistent_sl.properties";
		IExecContext execContext = (ExecContext)applicationContext.getBean(ActionConst.EXEC_CONTEXT_BEAN_REF);
		execContext.setApplicationContext(applicationContext);
		execContext.persist("newKey1", "New Key Value 1");
		execContext.persist("newKey2", "replace ${persistence:newKey1}");
		assertEquals("New Key Value 1", execContext.getString("persistence:newKey1"));
		assertEquals("replace New Key Value 1", execContext.getString("persistence:newKey2"));
		log.debug("newKey2:" + execContext.get("persistence:newKey2"));
		execContext.saveToPersistence();
		execContext = (ExecContext)applicationContext.getBean(ActionConst.EXEC_CONTEXT_BEAN_REF);
		execContext.setApplicationContext(applicationContext);
		execContext.loadFromPersistence();
		assertEquals("New Key Value 1", execContext.getString("persistence:newKey1"));
		assertEquals("replace New Key Value 1", execContext.getString("persistence:newKey2"));
		log.debug("newKey2:" + execContext.get("persistence:newKey2"));

	}

	public void testLoadSave() throws ConfigurationException, IOException
	{
		String fileName = "build/persistent_ls.properties";
		IExecContext execContext = (ExecContext)applicationContext.getBean(ActionConst.EXEC_CONTEXT_BEAN_REF);
		execContext.setApplicationContext(applicationContext);
		execContext.loadFromPersistence();
		execContext.persist("newKey1", "New Key Value 1");
		execContext.persist("newKey2", "replace ${persistence:newKey1}");
		execContext.saveToPersistence();
		execContext.loadFromPersistence();
		assertEquals("New Key Value 1", execContext.getString("persistence:newKey1"));
		assertEquals("replace New Key Value 1", execContext.getString("persistence:newKey2"));
		log.debug("newKey2:" + execContext.get("persistence:newKey2"));

	}
}
