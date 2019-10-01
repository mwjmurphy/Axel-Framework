package org.xmlactions.action.config;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.xmlactions.action.ActionConst;

public class TestCodeParser {

	private static Logger LOGGER = LoggerFactory.getLogger(TestCodeParser.class);
	
	private static final String [] configFiles = {
			"/config/spring/spring-config.xml"
		};
	private static ApplicationContext applicationContext = new ClassPathXmlApplicationContext(configFiles);
	

	@Test
	public void testCodeCode() throws Exception {
	
		IExecContext execContext = (IExecContext)applicationContext.getBean(ActionConst.EXEC_CONTEXT_BEAN_REF);

		CodeParser codeParser = new CodeParser();
		
		String name = (String) codeParser.parseCode(execContext, "org.xmlactions.action.config.TestCodeParser.buildName('fred','Flinstone')");
		assertTrue(name.startsWith("f"));
		LOGGER.debug("name:{}", name);

		name = (String) codeParser.parseCode(execContext, "org.xmlactions.action.config.TestCodeParser.buildName('fred','Flinstone', 40)");
		assertTrue(name.startsWith("'Integer':"));
		LOGGER.debug("name:{}", name);

		name = (String) codeParser.parseCode(execContext, "org.xmlactions.action.config.TestCodeParser.buildName('fred', 'Flinstone', 41.0)");
		assertTrue(name.startsWith("'Double':"));
		LOGGER.debug("name:{}", name);

	}
	
	@Test
	public void testCodeCodeWithReplace() throws Exception {
	
		IExecContext execContext = (IExecContext)applicationContext.getBean(ActionConst.EXEC_CONTEXT_BEAN_REF);
		
		CodeParser codeParser = new CodeParser();
		
		String name = (String) codeParser.parseCode(execContext, "org.xmlactions.action.config.TestCodeParser.buildName('k1',k1, 40)");
		assertTrue(name.startsWith("'Integer':"));
		LOGGER.debug("name:{}", name);

	}
	
	@Test
	public void testCodeReplaceFromExecContext() throws Exception {
	
		IExecContext execContext = (IExecContext)applicationContext.getBean(ActionConst.EXEC_CONTEXT_BEAN_REF);
		
		String name = "" + execContext.get("code:org.xmlactions.action.config.TestCodeParser.buildName('fred',k1, 40)");
		
		LOGGER.debug("name:{}", name);
		assertTrue(name.contains("value of k1"));

	}
	
	public String buildName(String first, String second) {
		
		return first + " . " + second;
		
	}

	public String buildName(String first, String second, Integer age) {
		
		return "'Integer':" + first + " . " + second + " age:" + age;
		
	}

	public String buildName(String first, String second, Double age) {
		
		return "'Double':" + first + " . " + second + " age:" + age;
		
	}
}
