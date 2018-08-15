package org.xmlactions.db.metadata;

import java.util.List;

public class TableEntry {

	private String tableName;
	private List<FieldEntry> fields;
	
	TableEntry(String tableName) {
		this.setTableName(tableName);
	}

	TableEntry(String tableName, List<FieldEntry> fields) {
		setTableName(tableName);
		setFields(fields);
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public List<FieldEntry> getFields() {
		return fields;
	}

	public void setFields(List<FieldEntry> fields) {
		this.fields = fields;
	}
}
