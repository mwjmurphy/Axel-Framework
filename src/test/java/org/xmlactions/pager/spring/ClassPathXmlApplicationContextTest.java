package org.xmlactions.pager.spring;


import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import junit.framework.TestCase;

import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.xmlactions.action.ActionConst;
import org.xmlactions.action.config.ExecContext;
import org.xmlactions.action.config.IExecContext;

public class ClassPathXmlApplicationContextTest extends TestCase {

	private static final Logger logger = LoggerFactory.getLogger(ClassPathXmlApplicationContextTest.class);
	
	public void testLoad1() {
		String [] configFiles = {
				ActionConst.SPRING_STARTUP_CONFIG,
				"/config/spring/test-spring-pager-web-startup.xml"
			};
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(configFiles);
		String [] beanNames = applicationContext.getBeanDefinitionNames();
		logger.debug("count:" + applicationContext.getBeanDefinitionCount());
		IExecContext execContext = (ExecContext)applicationContext.getBean(ActionConst.EXEC_CONTEXT_BEAN_REF);
		execContext.setApplicationContext(applicationContext);
		for (String beanName : beanNames) {
			logger.debug("beanName:" + beanName);
		}
		Iterator<Entry<String, Object>> iterator = execContext.entrySet().iterator();
		while(iterator.hasNext()) {
			Object key = iterator.next();
			logger.debug("Object:" + key);
		}
		logger.debug("project.title:"  + execContext.getString("project.title"));
		logger.debug("project.title:" + execContext.get("project.title"));
		logger.debug("project.name:" + execContext.get("project.name"));
		assertEquals("rio web test pager - 1.0.0", execContext.get("project.title"));

	}

}
