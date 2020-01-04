package org.xmlactions.action.actions.code.execute;

import java.util.List;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.lang.text.StrSubstitutor;
import org.xmlactions.action.config.IExecContext;

public class CodeParam {
	
	protected enum TypeOption {
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
    private TypeOption typeOption = TypeOption._String;


	public String getValue()
	{
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setValueAsString(String value) {
		this.value = value;
		this.typeOption = TypeOption._String;
	}

	public void setValueAsInt(String value) {
		this.value = value;
		this.typeOption = TypeOption._int;

	}

	public void setValueAsLong(String value) {
		this.value = value;
		this.typeOption = TypeOption._long;

	}

	public void setValueAsDecimal(String value) {
		this.value = value;
		this.typeOption = TypeOption._double;
	}

	public void setValue(TypeOption typeOption, String value)
	{
		this.setTypeOption(typeOption);
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
		if (! TypeOption._String.equals(getTypeOption())) {
			// need to convert
			if (getTypeOption() == null) {
				throw new IllegalArgumentException("Invalid type [" + getTypeOption() + "] for param.   Refer to schema 'param_converter_types' for a list of options.");
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

	public TypeOption getTypeOption() {
		return typeOption;
	}

	public void setTypeOption(TypeOption typeOption) {
		this.typeOption = typeOption;
	}




}
