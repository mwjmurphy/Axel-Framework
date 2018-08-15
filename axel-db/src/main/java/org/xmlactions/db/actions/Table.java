
package org.xmlactions.db.actions;


import java.util.ArrayList;
import java.util.List;



import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.db.DBConfigException;
import org.xmlactions.db.sql.select.SqlField;


public class Table extends Fields
{

	/**
	 * An sql query will include the table name with the field name. This is used to distinguish all fields in a query.
	 * The separator character between the table name and the field name is this value. Most likely it's a '.'
	 */
	public static final String TABLE_FIELD_SEPERATOR = ".";

	/**
	 * An sql query will return the table name with the field name. This is used to distinguish all fields returned from
	 * a query. The separator character between the table name and the field name is this value. Most likely it's a '_'
	 */
	public static final String TABLE_FIELD_AS_SEPERATOR = "_";

	/**
	 * These are used to builds ManyToMany entries between tables using a lookup
	 * table containing references between the tables. 
	 */
	private List<TablePath> tablePaths;
	
	
	/*
	 * This attribute is used to tell the system which field in the table is used as the version_num.
	 * 
	 * It is only applied to SQLs that update records, and not inserts or deletes.
	 * 
	 * 	The version_num field is expected to increment each time the row is modified.  Usually the increment
	 * is performed by a trigger in the database.
	 * 
	 *  By remembering the value stored in the version_num when the row was last read you can eliminate
	 *  writing to the row if the data was modified by someone else after your last read.
	 *  
	 *  To guarantee that you don't overwrite unknown changes in the row you should use a where
	 *  clause when doing an update. The where clause should use a pk and also the version_num value.
	 *  If the update returns 0 rows updated then you know the data was modified between your read
	 *  and write.
	 *  
	 *  If this attribute is used then by default the system will apply the synchronized update option. 
	 */
	private String update_field_version_num = null;
	
	/**
	 * This optional attribute is used to tell the system which java bean is related to this table
	 */
	private String bean = null;
	
	private static final Logger log = LoggerFactory.getLogger(Table.class);
	

	//private String name;


	public String execute(IExecContext execContext) throws Exception
	{

		return null;
	}

	//public void setName(String name)
	//{
	//	this.name = name;
	//}

	//public String getName()
	//{
	//	return name;
	//}

	/**
	 * Get a field that matches the field name.
	 * 
	 * @param fieldName
	 *            is the field we want to find
	 * @return the found field or null
	 */
	public CommonStorageField getField(String fieldName)
	{

		for (CommonStorageField field : getFields()) {
			if (field.getName().equalsIgnoreCase(fieldName)) {
				return (field);
			} else if (field.getAlias() != null && field.getAlias().equals(fieldName)) {
				return (field);
	        }
			// String name = dbFields[iLoop].getFieldName(); // remove this line
			if (field.getName().equalsIgnoreCase(fieldName)) {
				return (field);
			}
		}
		throw new IllegalArgumentException("Field [" + fieldName + "] not found in Table [" + getName() + "]");
	}

	/**
	 * Get a field that matches the table name and field name.
	 * 
	 * @param tableAndFieldName
	 *            is the field we want to find which will have the table name at the beginning. i.e.
	 *            tb_tablename.fieldname
	 */
	public CommonStorageField getFieldFromTableAndFieldName(String tableAndFieldName) throws DBConfigException
	{

		String fieldName = getFieldName(tableAndFieldName);
		return getField(fieldName);
	}

	/**
	 * This will get the field name from a table and field name string. i.e. 'tb_table.field' will return 'field'
	 * 
	 * @param tableAndFieldName
	 * @return the field name or null if none found
	 */
	public static String getFieldName(String tableAndFieldName)
	{

		if (tableAndFieldName != null) {
			int index = tableAndFieldName.indexOf('.');
			if (index >= 0 && index < tableAndFieldName.length() - 1) {
				return (tableAndFieldName.substring(index + 1));
			}
		}
		return (null);
	}

	/**
	 * This will get the table name from a table and field name string. i.e. 'tb_table.field' will return 'tb_table'
	 * 
	 * @param tableAndFieldName
	 * @return the table name or null if none found
	 */
	public static String getTableName(String tableAndFieldName)
	{

		if (tableAndFieldName != null) {
			int index = tableAndFieldName.indexOf('.');
			if (index >= 0 && index < tableAndFieldName.length() - 1) {
				return (tableAndFieldName.substring(0, index));
			}
		}
		return (null);
	}

    /**
     * Builds a table and field name by combining the tableName.fieldName
     * 
     * @param tableName
     * @param fieldName
     * @return tableName.fieldName
     */
    public String buildTableAndFieldName(String fieldName)
    {
        if (fieldName.indexOf('.') >= 0) {
            return fieldName;
        } else if (getAlias() == null) {
            return getName() + "." + fieldName;
        } else if (getAlias().length() == 0) {
            return fieldName;
        } else {
            return getAlias() + "." + fieldName;
        }
    }

    /**
     * Builds a table and field name by combining the tableName.fieldName
     * 
     * @param tableName
     * @param fieldName
     * @return tableName.fieldName
     */
    public static String buildTableAndFieldName(String tableName, String fieldName) {

        if (tableName == null) {
            return fieldName;
        }
        return tableName + "." + fieldName;
    }

	/**
	 * Check if a table has a field
	 * 
	 * @param fieldName
	 *            is the field we want to find
	 * @return true if table has the field
	 */
	public boolean hasField(String fieldName)
	{

		for (CommonStorageField field : getFields()) {
			if (field.getName().equalsIgnoreCase(fieldName)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Build a query to return all rows for this table including any foreign tables
	 * 
	 * @param databaseName
	 * @param tableFieldSeparator
	 * @param leftJoin
	 * @param whereClause
	 * @param orderBy
	 * @param quote
	 *            this character quote's the field names we are returning. May be null or a ' or a
	 *            ". i.e. tb_xxx.id as "tb_xxx.id"
	 */
	public String buildQuery(String databaseName, List<CommonStorageField> commonStorageFields, String leftJoin,
			String whereClause, String orderBy, String quote)
	{

		StringBuffer sb = new StringBuffer();

		sb.append("select ");

		String s = addFields(commonStorageFields, quote);
		// Log.getInstance().debug("s:" + s);

		sb.append(s);

		// Log.getInstance().debug("sb:" + sb.toString());

		sb.append("\n from ");

		// TODO Add the join here
		List<Table> tables = new ArrayList<Table>();
		addTables(tables, commonStorageFields, this);
		tables = removeDuplicateTables(tables);
		int i = 0;
		for (Table table : tables) {
			if (i > 0) {
				sb.append(',');
			}
			i++;
			if (StringUtils.isNotEmpty(databaseName)) {
				sb.append("\n " + databaseName + ".");
			}
			if (StringUtils.isNotEmpty(table.getAlias())) { 
				sb.append(table.getName() + " " + table.getAlias());
			} else {
				sb.append(table.getName());
			}
		}
		sb.append('\n');

		if (StringUtils.isEmpty(leftJoin) == false) {
			sb.append(leftJoin);
			sb.append('\n');
		}

		sb.append(" where 1 = 1 ");
		if (whereClause != null) {
			sb.append(" " + whereClause + " ");
		}

		sb.append(addWhereClause(commonStorageFields));

		if (orderBy != null) {
			sb.append("\n " + orderBy);
		}

		// Log.getInstance().debug("inner.sb:" + sb.toString());
		return (sb.toString());

	}

	private List<Table> removeDuplicateTables(List<Table> tables)
	{

		// for (int i = 0; i < tables.size(); i++) {
		for (int i = tables.size()-1 ; i > 0 ; i--) {
			removeDuplicateTable(tables, i -1, tables.get(i).getName());
		}
		return (tables);
	}

	private void removeDuplicateTable(List<Table> tables, int from, String name)
	{

		for (int i = from ; i >= 0; i--) {
			if (name.equalsIgnoreCase(tables.get(i).getName())) {
				tables.remove(from+1);
			}
		}
	}

	private void addTables(List<Table> tables, List<CommonStorageField> fields, Table table)
	{

		// log.debug("addTable(" + tableName + ")");
		tables.add(table);
		for (CommonStorageField field : fields) {
			if (field instanceof FK) {
				FK fk = (FK)field;
				Database database = (Database)getParent();
				Table fkTable =database.getTable(((FK) field).getForeign_table());
				addTables(tables, fk.getFields(), fkTable);
			}
		}
	}

	// TODO add code to test addFields
	private String addFields(List<CommonStorageField> commonStorageFields, String quote)
	{

		StringBuffer sb = new StringBuffer();

		boolean once = true;
		for (CommonStorageField field : commonStorageFields) {
			if (!once) {
				sb.append(',');
			}
			once = false;
			
			Table table = (Table)field.getParent();
			String tableAndFieldName;
			if (StringUtils.isNotEmpty(table.getAlias())) {
				tableAndFieldName = table.getAlias() + "." + field.getName();
			} else {
				tableAndFieldName = table.getName() + "." + field.getName();
			}
			// log.debug("adding(" + tableAndFieldName + ")");
			sb.append(addField(tableAndFieldName, quote));
		}
		return (sb.toString());
	}

	// TODO add code to test addForeignFields
	private String addForeignFields(List<CommonStorageField> fields, char tableFieldSeparator, String foreignTableName,
			String quote)
	{

		StringBuffer sb = new StringBuffer();

		boolean once = true;
		for (CommonStorageField field : fields) {
			if (!once) {
				sb.append(',');
			}
			once = false;
			sb.append(addForeignField(field, tableFieldSeparator, foreignTableName, quote));
			if (field instanceof FK) {
				sb.append(',');
				sb.append(addForeignFields(((FK) field).getFields(), tableFieldSeparator, ((FK) field)
						.getForeign_table(), quote));
			}
		}
		// Log.getInstance().debug("sb:" + sb.toString());
		return (sb.toString());
	}

	/**
	 * adds a field formatted as table.field as 'table_field'
	 * 
	 * @param field
	 * @param quote
	 * @return
	 */
	private String addField(String field, String quote)
	{

		return (field + " as " + quote + field.replace('.', '_') + quote);
	}

	private String addField(CommonStorageField field, char tableFieldSeparator, String quote)
	{

		return (getName() + '.' + field.getName() + " as " + quote + getName() + tableFieldSeparator + field.getName() + quote);
	}

	private String addForeignField(CommonStorageField field, char tableFieldSeparator, String foreignTableName,
			String quote)
	{

		return (foreignTableName + '.' + field.getName() + " as " + quote + foreignTableName + tableFieldSeparator
				+ field.getName() + quote);
	}

	private String addWhereClause(List<CommonStorageField> fields)
	{

		StringBuffer sb = new StringBuffer();

		for (CommonStorageField field : fields) {
			if (field instanceof FK) {

				// get the parent table name and the referencing key
				// =
				// the foreign table name and the foreign table key
				sb.append("\n and " + ((Table) field.getParent()).getName() + "." + field.getName() + " = "
						+ ((FK) field).getForeign_table() + "." + ((FK) field).getForeign_key());

				sb.append(addWhereClause(((FK) field).getFields()));
			}
		}
		return (sb.toString());
	}

	public String toString(int offset)
	{

		StringBuffer sb = new StringBuffer();
		sb.append("table name:" + getName() + "\n");
		for (CommonStorageField field : getFields()) {
			for (int i = 0; i < offset; i++) {
				sb.append(' ');
			}
			sb.append(field.toString(offset + 2));
		}
		return (sb.toString());
	}

	/**
	 * Gets a list of storage fields that match the field names. If the fieldNames is empty we return the complete list
	 * of storage fields
	 * 
	 * @param fieldNames
	 *            the list of field names
	 * @return the list of storage fields, used to build a query
	 */
    public List<CommonStorageField> buildStorageFieldsList(List<SqlField> sqlFields)
	{

		List<CommonStorageField> list = new ArrayList<CommonStorageField>();

        if (sqlFields != null) {
            for (SqlField sqlField : sqlFields) {
            	if (StringUtils.isBlank(sqlField.getSql())) {
	                // we're only going to get storage fields that match the fields
            		CommonStorageField storageField = getStorageField(sqlField.getFieldName());
            		if (storageField != null) {
            			sqlField.setCommonStorageField(storageField);
            			// Set the true field name in the sqlField replacing the alias if this was set by the action
            			String name = sqlField.getFieldName();
            			int index = name.indexOf(".");
            			if (index >= 0) {
            				sqlField.setFieldName(name.substring(0,index+1) + storageField.getName());
            			} else {
            				sqlField.setFieldName(storageField.getName());
            			}
            		}
	            	list.add(storageField);
            	} else {
            		Int intField = new Int();
            		intField.setName(sqlField.getFieldName());
            		intField.setAlias(sqlField.getAliasOrFieldName());
            		intField.setPresentation_name(sqlField.getAliasOrFieldName());
	            	list.add(intField);
            	}
				// TODO add FKs if we have more than one table.
			}
		} else {
			return getFields();
		}
		return list;
	}

	public CommonStorageField getStorageField(String name)
	{

		if (name.indexOf('.') >= 0) {
			return ((Database) getParent()).getStorageField(name);
		} else {
			return getField(name);
		}
	}

	/**
	 * Checks if the name is a table and field name, by searching for a . in the name
	 * 
	 * @param name
	 * @return true if name is a table and field name
	 */
	public static boolean isTableAndFieldName(String name)
	{

		if (name.indexOf('.') >= 0) {
			return true;
		}
		return false;
	}

	/**
	 * Search through list of fields in table looking for the Primary Key.
	 * 
	 * @return the PK or null if none found.
	 */
	public PK getPk()
	{

		for (CommonStorageField field : getFields()) {
			if (field instanceof PK) {
				return (PK) field;
			}
		}
		return (null);
	}

	public void setTablePaths(List<TablePath> tablePaths) {
		this.tablePaths = tablePaths;
	}

	/**
	 * These are used to builds ManyToMany entries between tables using a lookup
	 * table containing references between the tables. 
	 */
	public List<TablePath> getTablePaths() {
		if (tablePaths == null) {
			setTablePaths(new ArrayList<TablePath>());
		}
		return tablePaths;
	}
	
	public void setTable_path(TablePath tablePath) {
		getTablePaths().add(tablePath);
	}
	
	public TablePath getTable_path() {
		if (getTablePaths().size() > 0) {
			return getTablePaths().get(getTablePaths().size()-1);
		}
		return null;
	}

    /**
     * Builds a table and field name into the output for an sql, considering
     * that the table may have an alias.
     * 
     * @param tableFieldName
     *            - this can be either the table.fieldname or the fieldname
     * @return the table.alias or table.name + "." + fieldname
     */
    public String buildSqlFieldName(CommonStorageField field) {
        return buildSqlFieldName(field.getName());
    }

    /**
     * Builds a table and field name into the output for an sql, considering
     * that the table may have an alias.
     * 
     * @param tableFieldName
     *            - this can be either the table.fieldname or the fieldname
     * @return the table.alias or table.name + "." + fieldname
     */
    public String buildSqlFieldName(String tableFieldName) {
        String fieldName;
        if (isTableAndFieldName(tableFieldName)) {
            fieldName = getFieldName(tableFieldName);
        } else {
            fieldName = tableFieldName;
        }
        String sqlFieldName;
        if (StringUtils.isNotEmpty(getAlias())) {
            sqlFieldName = getAlias() + Table.TABLE_FIELD_SEPERATOR + fieldName;
        } else {
            sqlFieldName = getName() + Table.TABLE_FIELD_SEPERATOR + fieldName;
        }
        return sqlFieldName;

    }
    
    public String toString() {
    	return getName();
    }

	public String getUpdate_field_version_num() {
		return update_field_version_num;
	}

	public void setUpdate_field_version_num(String update_field_version_num) {
		this.update_field_version_num = update_field_version_num;
	}

	public String getBean() {
		return bean;
	}

	public void setBean(String bean) {
		this.bean = bean;
	}

}
