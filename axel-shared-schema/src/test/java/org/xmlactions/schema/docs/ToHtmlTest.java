package org.xmlactions.schema.docs;

import java.io.IOException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmlactions.common.io.ResourceUtils;
import org.xmlactions.common.xml.XMLObject;

public class ToHtmlTest {
	
	private static final Logger logger = LoggerFactory.getLogger(ToHtmlTest.class);
	
	@Test
	public void testLoadSchema() throws IOException {
		String schemaName = "/schema/storage.xsd";
		String data = LoadSchema.loadSchema(schemaName);
		XMLObject xo = SchemaToXmlObject.convertSchemaToXmlObject(data);
		ToHtml toHtml = new ToHtml();
		String result = toHtml.getRootDocs(xo, "storage.xsd");
		ResourceUtils.saveFile("../axel-war/src/main/webapp/org/xmlactions/schema/preview/storage_doc.html", result);
		result = toHtml.toHtml(xo, "storage.xsd");
		ResourceUtils.saveFile("../axel-war/src/main/webapp/org/xmlactions/schema/preview/storage.html", result);
		result = toHtml.buildIndex(xo);
		ResourceUtils.saveFile("../axel-war/src/main/webapp/org/xmlactions/schema/preview/storage_index.html", result);
		// logger.debug(result);
	}

	@Test
	public void testCoreAxelMultipleSchemas() throws IOException {
		String data = LoadSchema.loadSchema("/schema/axel_core.xsd");
		XMLObject head = SchemaToXmlObject.convertSchemaToXmlObject(data);

		XMLObject child;
		
		data = LoadSchema.loadSchema("/schema/axel_attributes.xsd");
		child = SchemaToXmlObject.convertSchemaToXmlObject(data);
		SchemaToXmlObject.mergeXmlObjects(head, child);

		data = LoadSchema.loadSchema("/schema/axel_types.xsd");
		child = SchemaToXmlObject.convertSchemaToXmlObject(data);
		SchemaToXmlObject.mergeXmlObjects(head, child);

		ToHtml toHtml = new ToHtml();
		String result = toHtml.getRootDocs(head, "axel_core.xsd");
		ResourceUtils.saveFile("../axel-war/src/main/webapp/org/xmlactions/schema/preview/axel_core_actions_doc.html", result);
		result = toHtml.toHtml(head, "axel_core.xsd");
		ResourceUtils.saveFile("../axel-war/src/main/webapp/org/xmlactions/schema/preview/axel_core_actions.html", result);
		result = toHtml.buildIndex(head);
		ResourceUtils.saveFile("../axel-war/src/main/webapp/org/xmlactions/schema/preview/axel_core_actions_index.html", result);
		// logger.debug(result);
	}

	@Test
	public void testAxelMultipleSchemas() throws IOException {
		String data = LoadSchema.loadSchema("/schema/pager_actions.xsd");
		XMLObject head = SchemaToXmlObject.convertSchemaToXmlObject(data);

		XMLObject child;
		
		data = LoadSchema.loadSchema("/schema/pager_attributes.xsd");
		child = SchemaToXmlObject.convertSchemaToXmlObject(data);
		SchemaToXmlObject.mergeXmlObjects(head, child);

		data = LoadSchema.loadSchema("/schema/pager_types.xsd");
		child = SchemaToXmlObject.convertSchemaToXmlObject(data);
		SchemaToXmlObject.mergeXmlObjects(head, child);
		ToHtml toHtml = new ToHtml();
		String result = toHtml.getRootDocs(head, "pager_actions.xsd");
		ResourceUtils.saveFile("../axel-war/src/main/webapp/org/xmlactions/schema/preview/axel_actions_doc.html", result);
		result = toHtml.toHtml(head, "pager_actions.xsd");
		ResourceUtils.saveFile("../axel-war/src/main/webapp/org/xmlactions/schema/preview/axel_actions.html", result);
		result = toHtml.buildIndex(head);
		ResourceUtils.saveFile("../axel-war/src/main/webapp/org/xmlactions/schema/preview/axel_actions_index.html", result);
		// logger.debug(result);
	}

	@Test
	public void testAxelDbMultipleSchemas() throws IOException {
		String data = LoadSchema.loadSchema("/schema/pager_db_actions.xsd");
		XMLObject head = SchemaToXmlObject.convertSchemaToXmlObject(data);

		XMLObject child;
		
		data = LoadSchema.loadSchema("/schema/pager_attributes.xsd");
		child = SchemaToXmlObject.convertSchemaToXmlObject(data);
		SchemaToXmlObject.mergeXmlObjects(head, child);

		data = LoadSchema.loadSchema("/schema/pager_types.xsd");
		child = SchemaToXmlObject.convertSchemaToXmlObject(data);
		SchemaToXmlObject.mergeXmlObjects(head, child);
		
		ToHtml toHtml = new ToHtml();
		String result = toHtml.getRootDocs(head, "pager_db_actions.xsd");
		ResourceUtils.saveFile("../axel-war/src/main/webapp/org/xmlactions/schema/preview/axel_db_actions_doc.html", result);
		result = toHtml.toHtml(head, "pager_db_actions.xsd");
		ResourceUtils.saveFile("../axel-war/src/main/webapp/org/xmlactions/schema/preview/axel_db_actions.html", result);
		result = toHtml.buildIndex(head);
		ResourceUtils.saveFile("../axel-war/src/main/webapp/org/xmlactions/schema/preview/axel_db_actions_index.html", result);
		// logger.debug(result);
	}


	@Test
	public void testMappingBeanToXmlSchema() throws IOException {
		String schemaName = "/schema/bean_to_xml.xsd";
		String data = LoadSchema.loadSchema(schemaName);
		XMLObject xo = SchemaToXmlObject.convertSchemaToXmlObject(data);
		ToHtml toHtml = new ToHtml();
		String result = toHtml.getRootDocs(xo, "bean_to_xml.xsd");
		ResourceUtils.saveFile("../axel-war/src/main/webapp/org/xmlactions/schema/preview/bean_to_xml_doc.html", result);
		result = toHtml.toHtml(xo, "bean_to_xml.xsd");
		ResourceUtils.saveFile("../axel-war/src/main/webapp/org/xmlactions/schema/preview/bean_to_xml.html", result);
		result = toHtml.buildIndex(xo);
		ResourceUtils.saveFile("../axel-war/src/main/webapp/org/xmlactions/schema/preview/bean_to_xml_index.html", result);
		// logger.debug(result);
	}

	@Test
	public void testMappingXmlToBeanSchema() throws IOException {
		String schemaName = "/schema/xml_to_bean.xsd";
		String data = LoadSchema.loadSchema(schemaName);
		XMLObject xo = SchemaToXmlObject.convertSchemaToXmlObject(data);
		ToHtml toHtml = new ToHtml();
		String result = toHtml.getRootDocs(xo, "xml_to_bean.xsd");
		ResourceUtils.saveFile("../axel-war/src/main/webapp/org/xmlactions/schema/preview/xml_to_bean_doc.html", result);
		result = toHtml.toHtml(xo, "xml_to_bean.xsd");
		ResourceUtils.saveFile("../axel-war/src/main/webapp/org/xmlactions/schema/preview/xml_to_bean.html", result);
		result = toHtml.buildIndex(xo);
		ResourceUtils.saveFile("../axel-war/src/main/webapp/org/xmlactions/schema/preview/xml_to_bean_index.html", result);
		// logger.debug(result);
	}

}
