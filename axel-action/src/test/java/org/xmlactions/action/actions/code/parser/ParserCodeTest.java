package org.xmlactions.action.actions.code.parser;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.xmlactions.action.ActionConst;
import org.xmlactions.action.config.ExecContext;
import org.xmlactions.action.config.IExecContext;

public class ParserCodeTest {

	
	String code = " x = get(\"app.name\")\n"
			+ "\n"
			+ " y = org.xmlactions.action.actions.code.tests.GetStuff.get(x)"
			+ " z = org.xmlactions.action.actions.code.tests.GetStuff.get(y)";
	
	private static final String [] configFiles = {
			"/config/spring/spring-config.xml"
		};
	private static ApplicationContext applicationContext = new ClassPathXmlApplicationContext(configFiles);

	IExecContext execContext = null;
	
	@Before
	public void setUp() {
		if (execContext == null) {
			execContext = (ExecContext)applicationContext.getBean(ActionConst.EXEC_CONTEXT_BEAN_REF);
			execContext.setApplicationContext(applicationContext);
		}
	}


	
	@Test
	public void parseCode1() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("get", "org.xmlactions.action.actions.code.tests.GetStuff.get");
		execContext.addNamedActions("actions", map);
		ParserCode pc = new ParserCode(execContext, code);
	}
}
