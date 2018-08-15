package org.xmlactions.db.metadata;

import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.xmlactions.common.xml.XMLObject;

/**
 * Convert the metadata to xml, compliant with the storage schema {@link http://xmlactions.org/schema/storage.xsd}
 * @author mike.murphy
 *
 */
public class MetaDataToXml {
	
	private static final Logger logger = LoggerFactory.getLogger(MetaDataToXml.class);
	
	public static final String FORCE_LOWER_CASE = "FORCE_LOWER_CASE",
							   FORCE_UPPER_CASE = "FORCE_UPPER_CASE";
	
	
	/**
	 * 
	 * @param databaseEntry - The bean representation of the database metadata
	 * @param outputCaseDirective	- if not null can force labels to be upper case or lower case. @See {@link #FORCE_UPPER_CASE} and {@link #FORCE_LOWER_CASE} 
	 * @return an XMLObject containing the definition of the meta data for use with the axel-db framework
	 */
	public XMLObject convertToXml(DatabaseEntry databaseEntry, String outputCaseDirective) {
		
		String root = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
					  "<storage\n" +
					  "   xmlns=\"http://www.xmlactions.org/storage\"\n" +
					  "   xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
					  "   xsi:schemaLocation=\"http://www.xmlactions.org/storage http://www.xmlactions.org/schema/storage.xsd\">\n" +
					  "</storage>";
		
		XMLObject xo = new XMLObject().mapXMLCharToXMLObject(root);
		
		addDatabase(xo, databaseEntry, outputCaseDirective);
		
		return xo;
	}
	
	/**
	 * Convert the label to a case if the caseDirective parameter is set to {@link #FORCE_UPPER_CASE} or {@link #FORCE_LOWER_CASE}.
	 * If the caseDirective parameter is null or does not match the upper or lower case pattern than the label is returned untouched.
	 * @param caseDirective	- can be null or {@link #FORCE_UPPER_CASE} or {@link #FORCE_LOWER_CASE} 
	 * @param label - the string to convert to uppercase or lowercase.
	 * @return the case converted label.
	 */
	public static String caseDirective(String caseDirective, String label) {
		if (caseDirective != null && label != null) {
			if (FORCE_UPPER_CASE.equals(caseDirective)) {
				return label.toUpperCase();
			} else if (FORCE_LOWER_CASE.equals(caseDirective)) {
				return label.toLowerCase();
			}
		}
		return label;
	}
	
	private void addDatabase(XMLObject xo, DatabaseEntry databaseEntry, String caseDirective) {
		XMLObject db = new XMLObject("database");
		db.addAttribute("name", caseDirective(caseDirective, databaseEntry.getDatabaseName()));
		xo.addChild(db);
		addTables(db, databaseEntry, caseDirective);
		
	}

	private void addTables(XMLObject xo, DatabaseEntry databaseEntry, String caseDirective) {
		for (TableEntry tableEntry : databaseEntry.getTables()) {
			XMLObject table = new XMLObject("table");
			table.addAttribute("name", caseDirective(caseDirective,tableEntry.getTableName()));
			addFields(table, tableEntry, caseDirective);
			xo.addChild(table);
		}
	}

	private void addFields(XMLObject xo, TableEntry tableEntry, String caseDirective) {
		for (FieldEntry fieldEntry : tableEntry.getFields()) {
			XMLObject field = fieldEntry.buildFieldEntryAsXml(caseDirective);
			xo.addChild(field);
		}
	}
}
