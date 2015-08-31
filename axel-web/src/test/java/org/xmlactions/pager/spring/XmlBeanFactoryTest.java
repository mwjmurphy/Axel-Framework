package org.xmlactions.pager.spring;

import junit.framework.TestCase;

import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

public class XmlBeanFactoryTest extends TestCase {

	private static final Logger logger = LoggerFactory.getLogger(XmlBeanFactoryTest.class);
	public void testLoad1() {
		ClassPathResource res = new ClassPathResource("/config/spring/test-spring-pager-web-startup.xml");
		XmlBeanFactory factory = new XmlBeanFactory(res);
		String [] beanNames = factory.getBeanDefinitionNames();
		logger.debug("count:" + factory.getBeanDefinitionCount());
		for (String beanName : beanNames) {
			logger.debug("beanName:" + beanName);
		}
	}
}
