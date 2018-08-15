package org.xmlactions.db.metadata;

import java.util.List;

public class DatabaseEntry {

	private String databaseName;
	private List<TableEntry> tables;
	
	DatabaseEntry(String databaseName) {
		this.setDatabaseName(databaseName);
	}

	DatabaseEntry(String databaseName, List<TableEntry> tables) {
		setDatabaseName(databaseName);
		setTables(tables);
	}

	public String getDatabaseName() {
		return databaseName;
	}

	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	public List<TableEntry> getTables() {
		return tables;
	}

	public void setTables(List<TableEntry> tables) {
		this.tables = tables;
	}
}
