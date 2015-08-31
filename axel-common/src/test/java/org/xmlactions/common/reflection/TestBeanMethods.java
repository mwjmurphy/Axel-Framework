package org.xmlactions.common.reflection;

import static org.junit.Assert.assertNotNull;

import java.lang.reflect.Method;

import org.junit.Test;

public class TestBeanMethods {
	
	@Test
	public void testSetter() {
		String beanName = "org.xmlactions.common.reflection.testdata.TbProject";

		BeanMethods beanMethods = new BeanMethods();
		Object bean = beanMethods.getBean(beanName);
		
		Method setMethod = beanMethods.findMatchingSetMethod(bean, "id");
		
		assertNotNull(setMethod);
		
	}

	public void testGetter() {
		String beanName = "org.xmlactions.common.reflection.testdata.TbProject";

		BeanMethods beanMethods = new BeanMethods();
		Object bean = beanMethods.getBean(beanName);
		
		Method setMethod = beanMethods.findMatchingGetMethod(bean, "id");
		
		assertNotNull(setMethod);
		
	}

}
