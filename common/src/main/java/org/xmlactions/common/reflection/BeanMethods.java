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

import java.lang.reflect.Method;

/**
 * The purpose of this class is to find getter and setter methods on a bean
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

public class BeanMethods extends BeanConstruct {
	
	/**
	 * Find a matching set method for a property
	 * @param bean - this is the class which is used to get the beans methods and also for exception purposes. Need to know which bean doesn't have the method
	 * @param propertyName - find a setter method for this property
	 * @return
	 */
	public Method findMatchingSetMethod(Object bean, String propertyName) {
		for (Method method : bean.getClass().getMethods()) {
			// find all the set methods
			if (method.getName().startsWith("set") && method.getName().length() > "set".length()) {
				String methodName = method.getName().substring("set".length());
				methodName = methodName.replace(methodName.substring(0,1), methodName.substring(0,1).toLowerCase());
				if (methodNameMatches(methodName, propertyName)) {
					Class<?>[] paramTypes = method.getParameterTypes();
					if (paramTypes.length != 1) {
						throw new IllegalArgumentException("Unable to find matching setter method for [" + propertyName + "] in bean [" + bean.getClass().getCanonicalName() + "]. Found [" + method.getName() + "] but this expects [" + paramTypes.length + "] parameters when it shoud be expecting [1] parameter");
					}
					return method;
				}
			}
		}
		throw new IllegalArgumentException("Unable to find matching setter method for [" + propertyName + "] in bean [" + bean.getClass().getCanonicalName() + "]");
	}
	
	/**
	 * Find a matching get method for a property
	 * @param bean - this is the class which is used to get the beans methods and also for exception purposes. Need to know which bean doesn't have the method
	 * @param propertyName - find a getter method for this property
	 * @return
	 */
	public Method findMatchingGetMethod(Object bean, String propertyName) {
		for (Method method : bean.getClass().getMethods()) {
			// find all the set methods
			if (method.getName().startsWith("get") && method.getName().length() > "get".length()) {
				String methodName = method.getName().substring("get".length());
				methodName = methodName.replace(methodName.substring(0,1), methodName.substring(0,1).toLowerCase());
				if (methodNameMatches(methodName, propertyName)) {
					if (method.getReturnType() == null) {
						throw new IllegalArgumentException("Unable to find matching getter method for [" + propertyName + "] in bean [" + bean.getClass().getCanonicalName() + "]. Found [" + method.getName() + "] but this does not have a return type.");
					}
					return method;
				}
			}
		}
		throw new IllegalArgumentException("Unable to find matching getter method for [" + propertyName + "] in bean [" + bean.getClass().getCanonicalName() + "]");
	}
	
	/**
	 * Check if the method name (- the set or get) equalsIgnoreCase the property name
	 * 
	 * @param methodName - the name of the method minus the set or get
	 * @param propertyName - the property name
	 * 
	 * return true if they match else false
	 * 
	 */
	public boolean methodNameMatches(String methodName, String propertyName) {
		if (methodName.equalsIgnoreCase(propertyName)) {
			return true;
		}
		return false;
	}

}
