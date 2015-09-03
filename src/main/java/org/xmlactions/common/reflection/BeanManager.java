/*
 * Copyright (C) Mike Murphy 2003-2015 <mike.murphy@xmlactions.org><mwjmurphy@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.xmlactions.common.reflection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.lang.ClassUtils;
import org.apache.commons.lang.StringUtils;

/**
 * The purpose of this class is to populate a bean from properties.
 * 
 * The principles of this method are based on setting and getting bean property values.
 *  
 * As with all properties the set method must have only one parameter i.e. setName(String name)
 * 
 * The get method does not have any parameters i.e. getName();
 * 
 * @author mike.murphy
 *
 */
public class BeanManager extends BeanMethods {
	
	
	/**
	 * Sets a property on a bean
	 * @param bean
	 * @param propertyName
	 * @param value
	 */
	public void setPropertyValue(Object bean, String propertyName, Object value) {
		Method setMethod = findMatchingSetMethod(bean, propertyName);
		try {
			ConvertUtilsBean cub = new ConvertUtilsBean();
			Object data = cub.convert(value, setMethod.getParameterTypes()[0]);
			setMethod.invoke(bean, data);
		} catch (Exception e) {
			throw new IllegalArgumentException("Unable to set property [" + propertyName + "] on bean [" + bean.getClass().getCanonicalName() + "] with the value [" + value + "]", e);
		}

	}
	
	/**
	 * Gets a property value from a bean
	 * @param bean
	 * @param propertyName
	 */
	public Object getPropertyValue(Object bean, String propertyName) {
		Method getMethod = findMatchingGetMethod(bean, propertyName);
		try {
			Object object = getMethod.invoke(bean);
			return object;
		} catch (IllegalArgumentException e) {
			throw e;
		} catch (Exception e) {
			throw new IllegalArgumentException("Unable to set property [" + propertyName + "] in bean [" + bean.getClass().getCanonicalName() + "]");
		}

	}
	
}
