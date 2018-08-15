package org.xmlactions.pager.actions.form;

import junit.framework.TestCase;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.xmlactions.action.ActionConst;
import org.xmlactions.action.config.ExecContext;
import org.xmlactions.action.config.IExecContext;

public abstract class SpringTestCaseSetup extends TestCase {

	private static IExecContext execContext;

	public void setUp()
	{
		if (execContext == null) {
			ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(new String[] {
					ActionConst.SPRING_STARTUP_CONFIG, "classpath*:/config/spring/test-spring-pager-web-startup.xml" });
			execContext = (ExecContext)applicationContext.getBean(ActionConst.EXEC_CONTEXT_BEAN_REF);
			execContext.setApplicationContext(applicationContext);
		}
		localSetup();
	}

	public static IExecContext getExecContext() {
		return execContext;
	}

	public static void setExecContext(IExecContext execContext) {
		SpringTestCaseSetup.execContext = execContext;
	}

	protected abstract void localSetup();

}
