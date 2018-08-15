package org.xmlactions.schema.docs;

import java.io.IOException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmlactions.common.xml.XMLObject;

public class ToTextTest {
	
	private static final Logger logger = LoggerFactory.getLogger(ToTextTest.class);
	
	@Test
	public void testLoadSchema() throws IOException {
		String schemaName = "/schema/storage.xsd";
		String data = LoadSchema.loadSchema(schemaName);
		XMLObject xo = SchemaToXmlObject.convertSchemaToXmlObject(data);
		ToText toText = new ToText();
		String result = toText.toText(xo);
		// logger.debug(result);
	}

}
