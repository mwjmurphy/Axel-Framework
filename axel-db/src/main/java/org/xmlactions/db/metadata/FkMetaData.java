package org.xmlactions.db.metadata;

public class FkMetaData {
	
	private String fieldName;	// this is the field name that has a foreign key
	private String fkTableName;	// the foreign table
	private String fkFieldName;	// the field in the foreign table

	public FkMetaData(String fieldName, String fkTableName, String fkFieldName) {
		setFieldName(fieldName);
		setFkTableName(fkTableName);
		setFkFieldName(fkFieldName);
	}
	
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getFkTableName() {
		return fkTableName;
	}
	public void setFkTableName(String fkTableName) {
		this.fkTableName = fkTableName;
	}
	public String getFkFieldName() {
		return fkFieldName;
	}
	public void setFkFieldName(String fkFieldName) {
		this.fkFieldName = fkFieldName;
	}

}
