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

package org.xmlactions.pager.actions;
/**
\page action_param Params

 Params provides parameters for some of the actions such as \ref action_code_action or \ref action_transform 

 An example of how actions are used
 \code
	<axel:code call="org.xmlactions.utils.Class.methodName">
   		<axel:param value="1" type="int"/>
   		<axel:param value="Zoo" type="String"/>
	</axel:code>
 \endcode

 Params have 3 attributes
 <ul>
 	<li>value</li> - this the value to use for the parameter. Can be used to get parameters from the execContext by using the string replacement pattern i.e. ${request:user_name}  
 	<li>type</li> - an optional type that can be used to force a type for the parameter such as int, bool etc. See the full list below \ref action_param_list_types
 	<li>key</li> - required only if mapping the params to a map, will be used as the key in the map.
 </ul>
 
 \section action_param_list_types param types
 
 List of supported param types
 <ul>
 	<li>boolean</li>
 	<li>byte</li>
 	<li>short</li>
 	<li>int</li>
 	<li>long</li>
 	<li>float</li>
 	<li>double</li>
 	<li>char</li>
 	<li>String</li>
 </ul>

*/


import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang.text.StrSubstitutor;
import org.xmlactions.action.actions.BaseAction;
import org.xmlactions.action.config.IExecContext;

public class Param extends BaseAction
{
	private enum TypeOption {
        _boolean("boolean", Boolean.class),
        _byte("byte", Byte.class),
        _short("short", Short.class),
        _int("int", Integer.class),
        _long("long", Long.class),
        _float("float", Float.class),
        _double("double", Double.class),
        _char("char", Character.class),
        _String("String", String.class);
        
        String type;
        Class<?> clazz;
        TypeOption(String type, Class<?> clazz) {
        	this.type = type;
        	this.clazz = clazz;
        }
        private String getType() {
        	return this.type;
        }
        private Class<?> getClazz() {
        	return this.clazz;
        }
        private TypeOption getTypeOption(String type) {
        	for (TypeOption typeOption : values() ) {
        		if (typeOption.getType().equals(type)) {
        			return typeOption;
        		}
        	}
        	return null;
        }
	}
	
    private String key;
    private String value;
    private String type = TypeOption._String.type;


	public String getValue()
	{
		return value;
	}

	public void setValue(String value)
	{

		this.value = value;
	}

	public Object getResolvedValue(IExecContext execContext)
	{
        Object obj;

        obj = execContext.get(getValue());
        if (obj == null) {
            obj = StrSubstitutor.replace(getValue(), execContext);
        }
		if (obj == null) {
			obj = getValue();
		}
		if (getType() != null && ! TypeOption._String.type.equals(getType())) {
			// need to convert
			TypeOption typeOption = TypeOption._String.getTypeOption(getType());
			if (typeOption == null) {
				throw new IllegalArgumentException("Invalid type [" + getType() + "] for param.   Refer to schema 'param_converter_types' for a list of options.");
			}
			// now double check that the obj class is not the same as the converter class
			if (obj.getClass() != typeOption.getClazz()) {
				// must convert
				obj = ConvertUtils.convert(obj, typeOption.getClazz());
			}
		}
		return obj;
	}

	public String execute(IExecContext execContext) throws Exception
	{

		return null;
	}

	public String toString()
	{

		return value;
	}

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

}