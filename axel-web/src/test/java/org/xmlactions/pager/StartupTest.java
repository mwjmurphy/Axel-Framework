
package org.xmlactions.pager;


import org.apache.commons.configuration.ConfigurationException;
import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit38.AbstractJUnit38SpringContextTests;
import org.xmlactions.action.ActionConst;
import org.xmlactions.action.config.IExecContext;

@ContextConfiguration(locations = ActionConst.SPRING_STARTUP_CONFIG)
public class StartupTest extends AbstractJUnit38SpringContextTests
{

	private static Logger log = LoggerFactory.getLogger(StartupTest.class);

	public void testLoad() throws BeansException, ConfigurationException
	{

		IExecContext execContext = ((IExecContext) applicationContext.getBean(ActionConst.EXEC_CONTEXT_BEAN_REF));
		log.debug(execContext.getString("user.home"));
		assertTrue(execContext.getString("application.name") != null);
	}
}
