package org.xmlactions.common.reflection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Method;

import org.junit.Test;

public class TestBeanManager {

	@Test
	public void testSetGetProperty() {
		String beanName = "org.xmlactions.common.reflection.testdata.TbProject";

		BeanManager beanManager = new BeanManager();
		
		Object bean = beanManager.getBean(beanName); 
		
		beanManager.setPropertyValue(bean, "id", 100);
		
		Object value = beanManager.getPropertyValue(bean, "id");
		
		assertTrue(value != null);
		
		long l = Long.parseLong("" + value);
		
		assertEquals(l, 100);
		
	}


}
