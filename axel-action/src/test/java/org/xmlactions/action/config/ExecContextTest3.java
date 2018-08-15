
package org.xmlactions.action.config;


import java.io.InvalidObjectException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.apache.commons.configuration.ConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.xmlactions.action.ActionConst;
import org.xmlactions.action.ActionConsts;
import org.xmlactions.action.config.ExecContext;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.action.config.NoPersistenceExecContext;
import org.xmlactions.common.locale.LocaleUtils;
import org.xmlactions.common.theme.Theme;


public class ExecContextTest3 extends TestCase
{

	private static Logger log = LoggerFactory.getLogger(ExecContextTest3.class);
	
	private static final String [] configFiles = {
			"/config/spring/spring-config.xml"
		};
	private static ApplicationContext applicationContext = new ClassPathXmlApplicationContext(configFiles);
	

	public void testSplit() throws InvalidObjectException, ConfigurationException, MalformedURLException
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("splitTestKey", "fred");
		ExecContext execContext = (ExecContext)applicationContext.getBean(ActionConst.EXEC_CONTEXT_BEAN_REF);
		execContext.put("splitTestMap", map);
		Object obj = execContext.get("splitTestMap");
		assertNotNull(obj);
		obj = execContext.get("splitTestMap/splitTestKey");
		assertEquals(obj, "fred");
	}
    
}
