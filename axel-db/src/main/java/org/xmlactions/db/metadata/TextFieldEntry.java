package org.xmlactions.db.metadata;

import org.xmlactions.common.xml.XMLObject;

public class TextFieldEntry extends FieldEntry {

	private static final int presentation_width_default=20;

	/**
	 * @param caseDirective	- can be null or {@link MetaDataToXml#FORCE_UPPER_CASE} or {@link MetaDataToXml#FORCE_LOWER_CASE} 
	 * @return the xml for this field entry as an XMLObject
	 */
	@Override
	public XMLObject buildFieldEntryAsXml(String caseDirective) {
		XMLObject xo = new XMLObject("text");
		addCommonAttributes(xo, caseDirective);
		addLengthAttribute(xo);
		xo.addAttribute("presentation_width", presentation_width_default);
		return xo;
	}

}
