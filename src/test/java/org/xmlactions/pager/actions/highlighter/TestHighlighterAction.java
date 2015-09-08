package org.xmlactions.pager.actions.highlighter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.xmlactions.action.ActionConst;
import org.xmlactions.action.config.ExecContext;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.pager.actions.EchoActionTest;

import junit.framework.TestCase;

public class TestHighlighterAction extends TestCase {

	private static Logger log = LoggerFactory.getLogger(EchoActionTest.class);

	 	
	private static IExecContext execContext;

	public void setUp() {

		if (execContext == null) {
			ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(new String[] {
					ActionConst.SPRING_STARTUP_CONFIG, "classpath*:/config/spring/test-spring-pager-web-startup.xml" });
			execContext = (IExecContext) applicationContext.getBean(ActionConst.EXEC_CONTEXT_BEAN_REF);
 		}
 	}
	
	public void testLoad() throws Exception {
		HighlighterAction highlighterAction = new HighlighterAction();
		highlighterAction.setFile_name("/org/xmlactions/pager/actions/highlighter/highlighter.xml");
		highlighterAction.execute(execContext);
	}
}
