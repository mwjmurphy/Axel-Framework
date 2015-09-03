package org.xmlactions.common.reflection;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class TestBeanConstruct {

	@Test
	public void testCreateBean() {
		String beanName = "org.xmlactions.common.reflection.testdata.TbProject";
		
		Object bean = new BeanConstruct().getBean(beanName);
		
		assertNotNull(bean);
	}
}
