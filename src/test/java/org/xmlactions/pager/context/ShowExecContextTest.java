package org.xmlactions.pager.context;

import junit.framework.TestCase;

import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.xmlactions.action.ActionConst;
import org.xmlactions.action.config.ExecContext;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.pager.context.ShowExecContext;

public class ShowExecContextTest extends TestCase {
	
	private static final Logger log = LoggerFactory.getLogger(ShowExecContextTest.class);
	
	private static IExecContext execContext;

	public void setUp()
	{

		if (execContext == null) {
			ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(
					ActionConst.SPRING_STARTUP_CONFIG);
			execContext = (ExecContext)applicationContext.getBean(ActionConst.EXEC_CONTEXT_BEAN_REF);
			execContext.setApplicationContext(applicationContext);
		}
	}
	
	public void testShow() {
		execContext.put("key1", "value for key1");
		log.debug(ShowExecContext.show(execContext));
	}


}
