package org.xmlactions.db.metadata;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger; import org.slf4j.LoggerFactory;

public class QueryMetaData {

	private static final Logger logger = LoggerFactory.getLogger(QueryMetaData.class);

	/**
	 * No use for this at the moment
	 */
	public void getCatalogs(DatabaseMetaData dbmd) throws SQLException {
        ResultSet rs = dbmd.getCatalogs();
        int i = 1;
        while (rs.next()) {
        	Object o = rs.getObject(i);
        	logger.debug("catalog(" + i + "):" + o);
        }
	}
	
	
	/**
	 * Gets the database, tables and fields into a DatabaseEntry 
	 * @param ds
	 * @param catalog
	 * @param databaseName
	 * @return
	 * @throws SQLException
	 */
	public DatabaseEntry getDatabase(DataSource ds, String catalog, String databaseName, String [] theseTablesOnly) throws SQLException {
        Connection connection = ds.getConnection();
        DatabaseMetaData dbmd = connection.getMetaData();
        List<TableEntry> tables = getTables(dbmd, catalog, databaseName, theseTablesOnly);
        logger.debug("tables.size:" + tables.size());
        DatabaseEntry databaseEntry = new DatabaseEntry(databaseName, tables);
        return databaseEntry;
	}
	
	private List<TableEntry> getTables(DatabaseMetaData dbmd, String catalog, String schemaPattern, String [] theseTablesOnly) throws SQLException {
		String   tableNamePattern = null;
		String[] types            = new String[] {"TABLE"};	// only pickup tables, ignores VIEWs
		
		List<TableEntry> tables = new ArrayList<TableEntry>();

		ResultSet result = dbmd.getTables(catalog, schemaPattern, tableNamePattern, types);

		while(result.next()) {
		    String tableName = result.getString(3);
		    if (acceptTableName(theseTablesOnly, tableName)) {
		    	logger.debug("accept table:" + tableName);
		    	TableEntry tableEntry = addTableFields(dbmd, tableName);
		    	tables.add(tableEntry);
		    } else {
		    	logger.debug("ignore table:" + tableName);
		    }
		}

		return tables;
		
	}
	
	private boolean acceptTableName(String [] tableNames, String tableName) {
		if (tableNames != null && tableNames.length > 0) {
			for (String tn : tableNames) {
				if (tableName.equalsIgnoreCase(tn)) {
					return true;
				}
			}
		} else {
			return true;	// accept all table names
		}
		return false;	// tableName not in tableNames
	}
	
	private TableEntry addTableFields(DatabaseMetaData dbmd, String tableName) throws SQLException {
		
		TableEntry tableEntry = new TableEntry(tableName);
		
		List<FieldEntry> fields = new ArrayList<FieldEntry>();
		String   catalog           = null;
		String   schemaPattern     = null;
		String   tableNamePattern  = tableName;
		String   columnNamePattern = null;


		List<PkMetaData> pks = new ArrayList<PkMetaData>();
		ResultSet result = dbmd.getPrimaryKeys(catalog, schemaPattern,  tableNamePattern);
		while(result.next()){
			Object r1 = result.getString(1);
			Object r2 = result.getString(2);
			Object r3 = result.getString(3);
			String pkFieldName = result.getString(4);
			Object r5 = result.getString(5);
			String pkName = result.getString(6);
			PkMetaData pkMetaData = new PkMetaData(pkFieldName, pkName);
			pks.add(pkMetaData);
		}
		List<FkMetaData> fks = new ArrayList<FkMetaData>();
		result = dbmd.getExportedKeys(catalog, schemaPattern,  tableNamePattern);
		while(result.next()){
			//Object r1 = result.getString(1);
			//Object r2 = result.getString(2);
			//Object r3 = result.getString(3);
			String fieldName = result.getString(4);
			//Object r5 = result.getString(5);
			//Object r6 = result.getString(6);
			String fkTableName = result.getString(7);
			String fkFieldName = result.getString(8);
			//Object r9 = result.getString(9);
			//Object r10 = result.getString(10);
			//Object r11 = result.getString(11);
			//Object r12 = result.getString(12);
			FkMetaData fkMetaData = new FkMetaData(fieldName, fkTableName, fkFieldName);
			fks.add(fkMetaData);
		}
		result = dbmd.getColumns(
		    catalog, schemaPattern,  tableNamePattern, columnNamePattern);
		

		while(result.next()){
			FieldEntry fieldEntry = createField(result, pks);
			fields.add(fieldEntry);
		}
		
		for(FkMetaData fkMetaData : fks) {
			FieldEntry fieldEntry = createField(schemaPattern, tableName, fkMetaData);
			fields.add(fieldEntry);
		}
		
		tableEntry.setFields(fields);
		return tableEntry;
	}
	
	private FieldEntry createField(ResultSet result, List<PkMetaData>pks) throws SQLException {
		Object r1 = result.getString(1);
		String databaseName = (String)result.getString(2);
		String tableName = (String)result.getString(3);
	    String fieldName = (String)result.getString(4);
	    int    fieldType = (int)result.getInt(5);
	    //Object r6 = result.getString(6);
	    int size = result.getInt(7);
	    //Object r8 = result.getString(8);
	    //Object r9 = result.getString(9);
	    //Object r10 = result.getString(10);
	    //Object r11 = result.getString(11);
	    //Object r12 = result.getString(12);
	    
    	// logger.debug("r1:" + r1 + " databaseName:" + databaseName + " tableName:" + tableName + " fieldName:" + fieldName + " fieldType:" + fieldType);
    	FieldEntry fieldEntry = new CreateFieldEntry().createFieldEntry(databaseName, tableName, fieldName, fieldType, pks);
    	fieldEntry.setFieldSize(size);
    	return fieldEntry;

	}
	
	private FieldEntry createField(String databaseName, String tableName, FkMetaData fkMetaData) throws SQLException {
    	FieldEntry fieldEntry = new CreateFieldEntry().createFieldEntry(databaseName, tableName, fkMetaData);
    	fieldEntry.setFieldSize(0);
    	return fieldEntry;

	}
	
	
}
