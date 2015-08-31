
package org.xmlactions.pager.spring;


import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit38.AbstractJUnit38SpringContextTests;
import org.xmlactions.action.ActionConst;
import org.xmlactions.action.config.IExecContext;

@ContextConfiguration(locations = {ActionConst.SPRING_STARTUP_CONFIG,"classpath*:config/spring/test-spring-pager-web-startup.xml"})
public class PagerCoreSpringPropertyTest extends AbstractJUnit38SpringContextTests
{

	private static Logger log = LoggerFactory.getLogger(PagerCoreSpringPropertyTest.class);

	@SuppressWarnings("unchecked")
	public void testGetBean()
	{

		IExecContext execContext = (IExecContext)applicationContext.getBean(ActionConst.EXEC_CONTEXT_BEAN_REF);
		execContext.setApplicationContext(applicationContext);
		
		assertTrue(execContext.getString("application.title") != null);
		log.debug("application.title :" + execContext.getString("application.title"));

		/*
		 * ActionManager actionManager =
		 * (ActionManager)applicationContext.getBean("actionManager");
		 * Map<String, String> actions = actionManager.getActions(); for (String
		 * key : actions.keySet()) { log.debug("key:" + key + " value:" +
		 * actions.get(key)); }
		 */
	}
}
