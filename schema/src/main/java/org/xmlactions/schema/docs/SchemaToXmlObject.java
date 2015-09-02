package org.xmlactions.schema.docs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmlactions.common.xml.XMLObject;

public class SchemaToXmlObject {
	
	private static final Logger logger = LoggerFactory.getLogger(SchemaToXmlObject.class);
	
	protected static XMLObject convertSchemaToXmlObject(String schema) {
		try {
			XMLObject xo = new XMLObject().mapXMLCharToXMLObject(schema);
			return xo;
		} catch (Exception ex) {
			throw new IllegalArgumentException(ex.getMessage(), ex);
		}
	}

	protected static void mergeXmlObjects(XMLObject to, XMLObject from) {
		for (XMLObject child : from.getChildren()) {
			to.addChild(child);
		}
	}

}
