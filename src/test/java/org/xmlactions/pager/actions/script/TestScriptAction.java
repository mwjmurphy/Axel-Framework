package org.xmlactions.pager.actions.script;

import java.net.URL;

import junit.framework.TestCase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.xmlactions.action.ActionConst;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.pager.actions.mapping.BeanToXmlAction;
import org.xmlactions.pager.actions.mapping.MapBean;
import org.xmlactions.pager.actions.mapping.TestBeanToXmlAction;
import org.xmlactions.web.RequestExecContext;

public class TestScriptAction extends TestCase 
{
	private static Logger log = LoggerFactory.getLogger(TestScriptAction.class);

	private static IExecContext execContext;

	private static final String[] configFiles = { ActionConst.SPRING_STARTUP_CONFIG,
		"/config/spring/test-spring-pager-web-startup.xml" };

	public void setUp() {

		if (execContext == null) {
			ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(configFiles);

			execContext = (IExecContext) applicationContext.getBean(ActionConst.EXEC_CONTEXT_BEAN_REF);
		}
	}

	public void testScriptAction() throws Exception {

		ScriptAction sa = new ScriptAction();
		sa.setContent("'Project Version = ${project.version}'");
		String result = sa.execute(execContext);;
		assertEquals("Project Version = 1.0.0", result);
		sa.setContent("x = 'This is the script';\n"
				+ "var y = 'this is the other script';\n"
				+ "var p = x + y;\n"
				+ "p;\n"
				);
		// sa.setKey("ScriptResult");
		result = sa.execute(execContext);
		assertEquals("This is the scriptthis is the other script", result);
		sa.setKey("ScriptResult");
		result = sa.execute(execContext);
		assertEquals("This is the scriptthis is the other script", execContext.getString("ScriptResult"));
	}

	public void testScriptActionExecContext() throws Exception {

		RequestExecContext.set(execContext);
		ScriptAction sa = new ScriptAction();
		sa.setContent("x = 'This is the script';\n"
				+ "org.xmlactions.web.RequestExecContext.get().put('key1', 'this is the value for key1');\n"
				+ "var y = 'this is the other script';\n"
				+ "var p = x + y;\n"
				+ "p;\n"
				);
		// sa.setKey("ScriptResult");
		String result = sa.execute(execContext);
		assertEquals("This is the scriptthis is the other script", result);
		assertEquals("this is the value for key1", execContext.get("key1"));
	}

}
