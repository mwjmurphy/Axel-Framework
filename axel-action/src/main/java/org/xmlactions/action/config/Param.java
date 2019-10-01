package org.xmlactions.action.config;
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
import org.xmlactions.action.utils.Convert;

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
        _String("String", String.class),
        _object("object", Object.class);
        
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
    private boolean resolved = false;


	public String getValue()
	{
		return value;
	}

	public void setValue(String value)
	{

		this.value = value;
	}

	public static Param buildParam(IExecContext execContext, String value) {
		Param param = new Param();
		boolean isString = false;
		if (value.startsWith("'") || value.startsWith("\"")) {
			value = value.substring(1);
			isString = true;
		}
		if (value.endsWith("'") || value.endsWith("\"")) {
			value = value.substring(0, value.length()-1);
			isString = true;
		}
		value = value.trim();
		Object obj = null;
		if (isString == false) {
	        obj = execContext.get(value);
	        if (obj == null) {
	            obj = StrSubstitutor.replace(value, execContext);
	        }
		}
		if (obj == null) {
			obj = value;
		}
		if (obj != null) {
			param.setValue(value);
			// try integer
			Integer i = Convert.toInteger(obj);
			if (i != null) {
				param.setType(Param.TypeOption._int.getType());
				param.setResolved(true);
			} else {
				// try double
				Double d = Convert.toDouble(obj);
				if (d != null) {
					param.setType(Param.TypeOption._double.getType());
					param.setResolved(true);
				} else {
					// default to String
					param.setType(Param.TypeOption._String.getType());
					if(isString == true) {
						param.setResolved(true);
					}
				}
			}
		}
		return param;
	}
	
	public Object getResolvedValue(IExecContext execContext)
	{
        Object obj = null;
        
        if (isResolved() == false) {
	        obj = execContext.get(getValue());
	        if (obj == null) {
	            obj = StrSubstitutor.replace(getValue(), execContext);
	        }
        }
		if (obj == null) {
			obj = getValue();
		}
		if (getType() != null && ! TypeOption._String.type.equals(getType())) {
			// need to convert
			TypeOption typeOption = TypeOption._String.getTypeOption(getType());
			if (typeOption == null) {
				throw new IllegalArgumentException("Invalid type [" + getType() + "] for param [" + getValue() + "]. Refer to schema 'param_converter_types' for a list of options.");
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

	public boolean isResolved() {
		return resolved;
	}

	public void setResolved(boolean resolved) {
		this.resolved = resolved;
	}

}
