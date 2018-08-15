package org.xmlactions.db.metadata;

/**
 * @author mike.murphy
 *
 */
public class PkMetaData {
	
	private String fieldName;	// this is the primary key field name	
	private String pkName;		// this is the primary key name
	
	PkMetaData(String fieldName, String pkName) {
		setFieldName(fieldName);
		setPkName(pkName);
	}
	
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getPkName() {
		return pkName;
	}
	public void setPkName(String pkName) {
		this.pkName = pkName;
	}

}
