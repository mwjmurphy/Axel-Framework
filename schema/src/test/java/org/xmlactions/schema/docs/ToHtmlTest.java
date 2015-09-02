package org.xmlactions.schema.docs;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmlactions.common.xml.XMLObject;

public class ToHtmlTest {
	
	private static final Logger logger = LoggerFactory.getLogger(ToHtmlTest.class);
	
	private static final String targetFolder = "target/bin/";	// "../axel-war/src/main/webapp/org/xmlactions/schema/preview/"
	
	@Test
	public void testLoadSchema() throws IOException {
		String schemaName = "/schema/storage.xsd";
		String data = LoadSchema.loadSchema(LoadSchemaTest.LANG_FILE_NAME, schemaName);
		XMLObject xo = SchemaToXmlObject.convertSchemaToXmlObject(data);
		ToHtml toHtml = new ToHtml();
		
		String result = toHtml.getRootDocs(xo, "storage.xsd");
		saveFile(targetFolder + "storage_doc.html", result);
		
		result = toHtml.toHtml(xo, "storage.xsd");
		saveFile(targetFolder + "storage.html", result);

		result = toHtml.buildIndex(xo);
		saveFile(targetFolder + "storage_index.html", result);
	}

	@Test
	public void testAxelMultipleSchemas() throws IOException {
		String data = LoadSchema.loadSchema(LoadSchemaTest.LANG_FILE_NAME, "/schema/pager_actions.xsd");
		XMLObject head = SchemaToXmlObject.convertSchemaToXmlObject(data);

		XMLObject child;
		
		data = LoadSchema.loadSchema(LoadSchemaTest.LANG_FILE_NAME, "/schema/pager_attributes.xsd");
		child = SchemaToXmlObject.convertSchemaToXmlObject(data);
		SchemaToXmlObject.mergeXmlObjects(head, child);

		data = LoadSchema.loadSchema(LoadSchemaTest.LANG_FILE_NAME, "/schema/pager_types.xsd");
		child = SchemaToXmlObject.convertSchemaToXmlObject(data);
		SchemaToXmlObject.mergeXmlObjects(head, child);
		ToHtml toHtml = new ToHtml();
		String result = toHtml.getRootDocs(head, "pager_actions.xsd");
		saveFile(targetFolder + "axel_actions_doc.html", result);

		result = toHtml.toHtml(head, "pager_actions.xsd");
		saveFile(targetFolder + "axel_actions.html", result);
		result = toHtml.buildIndex(head);
		saveFile(targetFolder + "axel_actions_index.html", result);
		// logger.debug(result);
	}

	@Test
	public void testAxelDbMultipleSchemas() throws IOException {
		String data = LoadSchema.loadSchema(LoadSchemaTest.LANG_FILE_NAME, "/schema/pager_db_actions.xsd");
		XMLObject head = SchemaToXmlObject.convertSchemaToXmlObject(data);

		XMLObject child;
		
		data = LoadSchema.loadSchema(LoadSchemaTest.LANG_FILE_NAME, "/schema/pager_attributes.xsd");
		child = SchemaToXmlObject.convertSchemaToXmlObject(data);
		SchemaToXmlObject.mergeXmlObjects(head, child);

		data = LoadSchema.loadSchema(LoadSchemaTest.LANG_FILE_NAME, "/schema/pager_types.xsd");
		child = SchemaToXmlObject.convertSchemaToXmlObject(data);
		SchemaToXmlObject.mergeXmlObjects(head, child);
		
		ToHtml toHtml = new ToHtml();
		String result = toHtml.getRootDocs(head, "pager_db_actions.xsd");
		saveFile(targetFolder + "axel_db_actions_doc.html", result);

		result = toHtml.toHtml(head, "pager_db_actions.xsd");
		saveFile(targetFolder + "axel_db_actions.html", result);
		
		result = toHtml.buildIndex(head);
		saveFile(targetFolder + "axel_db_actions_index.html", result);
		// logger.debug(result);
	}


	@Test
	public void testMappingBeanToXmlSchema() throws IOException {
		String schemaName = "/schema/bean_to_xml.xsd";
		String data = LoadSchema.loadSchema(LoadSchemaTest.LANG_FILE_NAME, schemaName);
		XMLObject xo = SchemaToXmlObject.convertSchemaToXmlObject(data);
		ToHtml toHtml = new ToHtml();
		
		String result = toHtml.getRootDocs(xo, "bean_to_xml.xsd");
		saveFile(targetFolder + "bean_to_xml_doc.html", result);
		
		result = toHtml.toHtml(xo, "bean_to_xml.xsd");
		saveFile(targetFolder + "bean_to_xml.html", result);

		result = toHtml.buildIndex(xo);
		saveFile(targetFolder + "bean_to_xml_index.html", result);
		// logger.debug(result);
	}

	@Test
	public void testMappingXmlToBeanSchema() throws IOException {
		String schemaName = "/schema/xml_to_bean.xsd";
		String data = LoadSchema.loadSchema(LoadSchemaTest.LANG_FILE_NAME, schemaName);
		XMLObject xo = SchemaToXmlObject.convertSchemaToXmlObject(data);
		ToHtml toHtml = new ToHtml();

		String result = toHtml.getRootDocs(xo, "xml_to_bean.xsd");
		saveFile(targetFolder + "xml_to_bean_doc.html", result);
		
		result = toHtml.toHtml(xo, "xml_to_bean.xsd");
		saveFile(targetFolder + "xml_to_bean.html", result);
		
		result = toHtml.buildIndex(xo);
		saveFile(targetFolder + "xml_to_bean_index.html", result);
		// logger.debug(result);
	}
	
	private void saveFile(String fileName, String data) throws IOException {
		File file = new File(fileName);
		FileUtils.writeStringToFile(file, data);
		// ResourceUtils.saveFile("../axel-war/src/main/webapp/org/xmlactions/schema/preview/storage_doc.html", result);
	}

}
