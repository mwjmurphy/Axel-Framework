package org.xmlactions.db.metadata;

import org.xmlactions.common.xml.XMLObject;

public abstract class FieldEntry {

	private String	databaseName = null,
					tableName = null,
					fieldName = null;
	
	private int fieldSize;
	
	private int fieldType = 0;
	
	/**
	 * 
	 * @param caseDirective - can be used to force a label to upper or lower case. @see {@link MetaDataToXml#caseDirective(String, String)}  
	 * @return and xml representation of the metadata compliant with the storage schema. @see {@link http://xmlactions.org/schema/storage.xsd} 
	 */
	public abstract XMLObject buildFieldEntryAsXml(String caseDirective);
	
	public String getDatabaseName() {
		return databaseName;
	}


	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}


	public String getTableName() {
		return tableName;
	}


	public void setTableName(String tableName) {
		this.tableName = tableName;
	}


	public String getFieldName() {
		return fieldName;
	}


	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}


	public int getFieldType() {
		return fieldType;
	}


	public void setFieldType(int fieldType) {
		this.fieldType = fieldType;
	}
	
	public int getFieldSize() {
		return fieldSize;
	}

	public void setFieldSize(int size) {
		this.fieldSize = size;
	}

	/**
	 * @param xo - add common attributes to the XMLObject
	 * @param caseDirective	- can be null or {@link #FORCE_UPPER_CASE} or {@link #FORCE_LOWER_CASE} 
	 */
	public void addCommonAttributes(XMLObject xo, String caseDirective) {
		xo.addAttribute("name", MetaDataToXml.caseDirective(caseDirective, getFieldName()));
		xo.addAttribute("presentation_name",  MetaDataToXml.caseDirective(caseDirective, getFieldName()));
	}

	/**
	 * @param xo - add common attributes to the XMLObject
	 * @param caseDirective	- can be null or {@link #FORCE_UPPER_CASE} or {@link #FORCE_LOWER_CASE} 
	 */
	public void addLengthAttribute(XMLObject xo) {
		if (getFieldSize() > 0) {
			xo.addAttribute("length",  getFieldSize());
		}
	}	

}
