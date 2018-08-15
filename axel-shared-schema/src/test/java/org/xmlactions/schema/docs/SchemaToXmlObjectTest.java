package org.xmlactions.schema.docs;

import java.io.IOException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmlactions.common.xml.XMLObject;

public class SchemaToXmlObjectTest {
	
	private static final Logger logger = LoggerFactory.getLogger(SchemaToXmlObjectTest.class);
	
	@Test
	public void testConvertToXmlObject() throws IOException {
		String schemaName = "/schema/storage.xsd";
		String data = LoadSchema.loadSchema(schemaName);
		XMLObject xo = SchemaToXmlObject.convertSchemaToXmlObject(data);
		// logger.debug(xo.mapXMLObject2XML(xo, true));
	}

}
