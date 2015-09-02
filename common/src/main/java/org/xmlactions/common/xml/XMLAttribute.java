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

/*
 * XMLAttribute.java
 *
 * Created on August 30, 2005, 3:27 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package org.xmlactions.common.xml;

/**
 *
 * @author Mike
 */
public class XMLAttribute
{
	String key;
	Object value;
	
	XMLAttribute(String key, Object value)
	{
		this.key = key;
		this.value = value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public Object getValue()
	{
		return(value);
	}

	public String getValueAsString()
	{
		return((String)value);
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getKey()
	{
		return(key);
	}

	public String toString() {
		return (key + " = " + value);
	}

}
