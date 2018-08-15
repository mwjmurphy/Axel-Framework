package org.xmlactions.db.metadata;

import org.xmlactions.common.xml.XMLObject;

public class PkFieldEntry extends FieldEntry {

	/**
	 * @param caseDirective	- can be null or {@link MetaDataToXml#FORCE_UPPER_CASE} or {@link MetaDataToXml#FORCE_LOWER_CASE} 
	 * @return the xml for this field entry as an XMLObject
	 */
	@Override
	public XMLObject buildFieldEntryAsXml(String caseDirective) {
		XMLObject xo = new XMLObject("pk");
		addCommonAttributes(xo, caseDirective);
		return xo;
	}


}
