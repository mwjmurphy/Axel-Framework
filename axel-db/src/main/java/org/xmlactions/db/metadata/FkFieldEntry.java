package org.xmlactions.db.metadata;

import org.xmlactions.common.xml.XMLObject;

public class FkFieldEntry extends FieldEntry {

	private String fkTableName, fkFieldName;
	
	/**
	 * @param caseDirective	- can be null or {@link MetaDataToXml#FORCE_UPPER_CASE} or {@link MetaDataToXml#FORCE_LOWER_CASE} 
	 * @return the xml for this field entry as an XMLObject
	 */
	@Override
	public XMLObject buildFieldEntryAsXml(String caseDirective) {
		XMLObject xo = new XMLObject("fk");
		addCommonAttributes(xo, caseDirective);
		xo.addAttribute("foreign_table", MetaDataToXml.caseDirective(caseDirective, getFkTableName()));
		xo.addAttribute("foreign_key", MetaDataToXml.caseDirective(caseDirective, getFkFieldName()));
		return xo;
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
