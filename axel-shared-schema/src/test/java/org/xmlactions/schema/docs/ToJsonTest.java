package org.xmlactions.schema.docs;

import java.io.IOException;

import org.json.JSONObject;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmlactions.common.io.ResourceUtils;
import org.xmlactions.common.xml.XMLObject;

public class ToJsonTest {
	
	private static final Logger logger = LoggerFactory.getLogger(ToJsonTest.class);
	
	@Test
	public void testLoadSchemaAll() throws IOException {
		String data = LoadSchema.loadSchema("/schema/axel_core.xsd");
		XMLObject head = SchemaToXmlObject.convertSchemaToXmlObject(data);

		XMLObject child;
		
//		data = LoadSchema.loadSchema("/schema/axel_attributes.xsd");
//		child = SchemaToXmlObject.convertSchemaToXmlObject(data);
//		SchemaToXmlObject.mergeXmlObjects(head, child);

		data = LoadSchema.loadSchema("/schema/axel_types.xsd");
		child = SchemaToXmlObject.convertSchemaToXmlObject(data);
		SchemaToXmlObject.mergeXmlObjects(head, child);
		
		ToJson toJson = new ToJson();
		JSONObject result = toJson.toJson(head);
		
		ResourceUtils.saveFile("../axel-tutorial/schema/axel_core.json", result.toString(4));

		logger.warn(result.toString(4));
	}


}
