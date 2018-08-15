package org.xmlactions.db.metadata;

import java.util.List;

public class CreateFieldEntry {


	public FieldEntry createFieldEntry(String databaseName, String tableName, String fieldName, int fieldType, List<PkMetaData>pks) {
		
		FieldEntry fieldEntry = null;
		for (PkMetaData pk : pks) {
			if (pk.getFieldName().equals(fieldName)) {
				fieldEntry = new PkFieldEntry();
			}
		}
		if (fieldEntry == null) {
			fieldEntry = createFieldEntry(fieldType);
		}
		if(fieldEntry == null) {
			throw new IllegalArgumentException("Unable to create a FieldEntry for field type [" + fieldType + ":" + getFieldTypeAsString(fieldType) + "] for [" + databaseName + "." + tableName + "." + fieldName + "]" );
		}
		setFieldEntryCommonValues(fieldEntry, databaseName, tableName, fieldName, fieldType);
		return fieldEntry;
		
	}

	public FieldEntry createFieldEntry(String databaseName, String tableName, FkMetaData fkMetaData) {
		
		FkFieldEntry fieldEntry = new FkFieldEntry();
		fieldEntry.setFkTableName(fkMetaData.getFkTableName());
		fieldEntry.setFkFieldName(fkMetaData.getFkFieldName());
		setFieldEntryCommonValues(fieldEntry, databaseName, tableName, fkMetaData.getFieldName(), 0);
		return fieldEntry;
	}
	
	private void setFieldEntryCommonValues(FieldEntry fieldEntry, String databaseName, String tableName, String fieldName, int fieldType) {
		fieldEntry.setDatabaseName(databaseName);
		fieldEntry.setTableName(tableName);
		fieldEntry.setFieldName(fieldName);
		fieldEntry.setFieldType(fieldType);
	}

	private FieldEntry createFieldEntry(int fieldType) {
		switch (fieldType) {
		case java.sql.Types.ARRAY:
			return null;
		case java.sql.Types.BIGINT:
		case java.sql.Types.INTEGER:
		case java.sql.Types.SMALLINT:
		case java.sql.Types.TINYINT:
			return new IntFieldEntry();
		case java.sql.Types.BIT:
		case java.sql.Types.BOOLEAN:
			return new IntFieldEntry();
		case java.sql.Types.BINARY:
		case java.sql.Types.LONGVARBINARY:
		case java.sql.Types.VARBINARY:
			return new BooleanFieldEntry();
		case java.sql.Types.DATALINK:
			return null;
		case java.sql.Types.DATE:
			return new DateFieldEntry();
		case java.sql.Types.DISTINCT:
			return null;
		case java.sql.Types.DECIMAL:
		case java.sql.Types.DOUBLE:
		case java.sql.Types.FLOAT:
		case java.sql.Types.NUMERIC:
		case java.sql.Types.REAL:
			return new IntFieldEntry();
		case java.sql.Types.JAVA_OBJECT:
		case java.sql.Types.BLOB:
		case java.sql.Types.CHAR:
		case java.sql.Types.CLOB:
		case java.sql.Types.VARCHAR:
		case java.sql.Types.OTHER:
			return new TextFieldEntry();
		case java.sql.Types.NULL:
		case java.sql.Types.REF:
		case java.sql.Types.STRUCT:
			return null;
		case java.sql.Types.TIME:
			return new TimeFieldEntry();
		case java.sql.Types.TIMESTAMP:
			return new TimestampFieldEntry();
		}
		return null;
	}
	
	private String getFieldTypeAsString(int type) {
		switch (type) {
		case java.sql.Types.ARRAY:
			return "ARRAY";
		case java.sql.Types.BIGINT:
			return "BIGINT";
		case java.sql.Types.BINARY:
			return "BINARY";
		case java.sql.Types.BIT:
			return "BIT";
		case java.sql.Types.BLOB:
			return "BLOB";
		case java.sql.Types.BOOLEAN:
			return "BOOLEAN";
		case java.sql.Types.CHAR:
			return "CHAR";
		case java.sql.Types.CLOB:
			return "CLOB";
		case java.sql.Types.DATALINK:
			return "DATALINK";
		case java.sql.Types.DATE:
			return "DATE";
		case java.sql.Types.DECIMAL:
			return "DECIMAL";
		case java.sql.Types.DISTINCT:
			return "DISTINCT";
		case java.sql.Types.DOUBLE:
			return "DOUBLE";
		case java.sql.Types.FLOAT:
			return "FLOAT";
		case java.sql.Types.INTEGER:
			return "INTEGER";
		case java.sql.Types.JAVA_OBJECT:
			return "JAVA_OBJECT";
		case java.sql.Types.LONGVARBINARY:
			return "LONGVARBINARY";
		case java.sql.Types.LONGVARCHAR:
			return "LONGVARCHAR";
		case java.sql.Types.NULL:
			return "NULL";
		case java.sql.Types.NUMERIC:
			return "NUMERIC";
		case java.sql.Types.OTHER:
			return "OTHER";
		case java.sql.Types.REAL:
			return "REAL";
		case java.sql.Types.REF:
			return "REF";
		case java.sql.Types.SMALLINT:
			return "SMALLINT";
		case java.sql.Types.STRUCT:
			return "STRUCT";
		case java.sql.Types.TIME:
			return "TIME";
		case java.sql.Types.TIMESTAMP:
			return "TIMESTAMP";
		case java.sql.Types.TINYINT:
			return "TINYINT";
		case java.sql.Types.VARBINARY:
			return "VARBINARY";
		case java.sql.Types.VARCHAR:
			return "VARCHAR";
		}
		return ("UNKNOWN");
	}
	
}
