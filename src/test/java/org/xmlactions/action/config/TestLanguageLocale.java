package org.xmlactions.action.config;

import junit.framework.TestCase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.xmlactions.action.ActionConst;

public class TestLanguageLocale extends TestCase {

	private static Logger log = LoggerFactory.getLogger(TestLanguageLocale.class);

	private static final String [] configFiles = {
		"/config/spring/spring-config.xml"
	};
	private static ApplicationContext applicationContext = new ClassPathXmlApplicationContext(configFiles);
	
	public void testLanguageLocale() {
		LanguageLocale languageLocale = new LanguageLocale();
		ExecContext execContext = (ExecContext)applicationContext.getBean(ActionConst.EXEC_CONTEXT_BEAN_REF);

		String val = (String) languageLocale.getLang(execContext, "lang:newline:config/lang/test:ES");
		log.debug("val:" + val);
		
		val = execContext.getString("lang:newline:config/lang/test:EN");
		log.debug("val:" + val);
		
		val = execContext.getString("lang:newline:config/lang/test:ES");
		log.debug("val:" + val);
		
	}

}
