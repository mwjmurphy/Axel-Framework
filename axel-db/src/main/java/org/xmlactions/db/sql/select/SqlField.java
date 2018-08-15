
package org.xmlactions.db.sql.select;

import org.xmlactions.db.actions.CommonStorageField;
import org.xmlactions.db.actions.Table;

public class SqlField {
    private String fieldName;
    private String fieldAlias;
    private String function_ref;
    private CommonStorageField commonStorageField;
    private Object value;	// the value for insert or update
    private String sql;	// we can put specific sql here.

    public SqlField(String fieldName) {
		setFieldName(fieldName);
		setFieldAlias(fieldName);
    }

    public SqlField(String fieldName, String aliasName) {
		setFieldName(fieldName);
		setFieldAlias(aliasName);
    }

    public SqlField(String fieldName, String aliasName, String function_ref, CommonStorageField dbField, String sql) {
		setFieldName(fieldName);
		setFieldAlias(aliasName);
        setFunction_ref(function_ref);
        setCommonStorageField(dbField);
        setSql(sql);
    }

    public SqlField(String fieldName, String aliasName, String function_ref, CommonStorageField dbField) {
		setFieldName(fieldName);
		setFieldAlias(aliasName);
        setFunction_ref(function_ref);
        setCommonStorageField(dbField);
    }

    public SqlField(String fieldName, String aliasName, String function_ref) {
    	setFieldName(fieldName);
		setFieldAlias(aliasName);
        setFunction_ref(function_ref);
    }
    
    public SqlField(CommonStorageField field) {
		setFieldName(field.getName());
		setFieldAlias(field.getAlias());
        setFunction_ref(field.getFunction_ref());
        setCommonStorageField(field);
    }
    
    public SqlField(CommonStorageField field, boolean applyTableAlias) {
    	if (applyTableAlias) {
    		Table table = (Table)field.getParent();
    		setFieldName(table.buildTableAndFieldName(table.getAlias(), field.getName()));
    		setFieldAlias(table.buildTableAndFieldName(table.getAlias(), field.getAlias()));
    	} else {
    		setFieldName(field.getName());
    		setFieldAlias(field.getAlias());
    	}
    	setFunction_ref(field.getFunction_ref());
    	setCommonStorageField(field);
    }
    
    public String getAliasOrFieldName() {
    	if (getFieldAlias() == null) {
    		return getFieldName();
    	}
    	return getFieldAlias();
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldAlias(String fieldAlias) {
        this.fieldAlias = fieldAlias;
    }

    public String getFieldAlias() {
        return fieldAlias;
    }

    public String getFunction_ref() {
        return function_ref;
    }

    public void setFunction_ref(String function_ref) {
        this.function_ref = function_ref;
    }

	public void setCommonStorageField(CommonStorageField commonStorageField) {
		this.commonStorageField = commonStorageField;
	}

	/**
	 * This is the CommonStorageField associated with this SqlField.
	 * CommonStorageField is the Storage Definition for a Table Field.
	 * @return the associated CommonStorageField
	 */
	public CommonStorageField getCommonStorageField() {
		return commonStorageField;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public Object getValue() {
		return value;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
	    sb.append(fieldName);
	    return sb.toString();
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}
	
}
