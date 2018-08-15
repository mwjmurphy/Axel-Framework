
package org.xmlactions.db.actions;


import java.util.ArrayList;
import java.util.List;





import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.xmlactions.action.actions.BaseAction;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.common.locale.LocaleUtils;
import org.xmlactions.db.ConstantsDB;
import org.xmlactions.db.DBConfigException;


public class Database extends BaseAction
{

	private final static Logger log = LoggerFactory.getLogger(Database.class);

	public final static String SQL_QUOTE = "\"";

    private List<DBInsert> dbInserts = new ArrayList<DBInsert>();
    
    private List<Table> tables = new ArrayList<Table>();

    private List<PkCreate> pkCreates = new ArrayList<PkCreate>();

    private List<Sql> sqls = new ArrayList<Sql>();
    
    private List<Function> functions = new ArrayList<Function>();

    private List<DbSpecific> dbSpecifics = new ArrayList<DbSpecific>();

	private String name;
	
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
	
	
	private String date_format = "yyyy-MM-dd";
    private String time_format = "HH:mm:ss.SSS";
    private String datetime_format = "yyyy-MM-dd HH:mm:ss.SSS";

	public String execute(IExecContext execContext) throws Exception
	{

		// TODO Auto-generated method stub
		return null;
	}

    public void setInsert(DBInsert insert) {

        dbInserts.add(insert);
    }

    public DBInsert getInsert() {

        return dbInserts.get(dbInserts.size() - 1);
    }

    public List<DBInsert> getInserts() {

        return dbInserts;
    }

    public void setTable(Table table) {

        tables.add(table);
    }

    /** @return the last table added. */
    public Table getTable() {

        return tables.get(tables.size() - 1);
    }

    public void setPk_create(PkCreate pkCreate) {
        pkCreates.add(pkCreate);
    }

    /** @return the last pkCreate added. */
    public PkCreate getPk_create() {

        return pkCreates.get(pkCreates.size() - 1);
    }

    public void setSql(Sql sql) {
        sqls.add(sql);
    }

    /** @return the last XmlCData added. */
    public Sql getSql() {

        return sqls.get(sqls.size() - 1);
    }

    public void setFunction(Function function) {
        functions.add(function);
    }

    /** @return the last Function added. */
    public Function getFunction() {

        return functions.get(functions.size() - 1);
    }

    public void setDb_specific(DbSpecific dbSpecific) {
        dbSpecifics.add(dbSpecific);
    }

    /** @return the last DbSpecific added. */
    public DbSpecific getdbSpecific() {

        return dbSpecifics.get(dbSpecifics.size() - 1);
    }

    /**
     * Get the named table from the table list
     * 
     * @return the named table.
     * @throws IllegalArgumentException
     *             of not found
     */
    public Table getTable(String name) throws IllegalArgumentException {

        Table table = getTableQuietly(name);
        Validate.notNull(table, "Table [" + name + "] not found in Database [" + getName() + "]");
        return table;
    }

    /**
     * Get the named table from the table list
     * 
     * @return the named table.
     * @throws IllegalArgumentException
     *             of not found
     */
    public Table getTableQuietly(String name) throws IllegalArgumentException {

        Table table = null;
        for (Table t : getTables()) {
            if (t.getName().equals(name)) {
                table = t;
                break;
            } else if (t.getAlias() != null && t.getAlias().equals(name)) {
                table = t;
                break;
            }
        }
        // try and find table through FK fields
        if (table == null) {
            for (Table t : getTables()) {
                for (CommonStorageField field : t.getFields()) {
                    if (field instanceof FK) {
                        FK fk = (FK) field;
                        if (name.equals(fk.getForeign_table_alias())) {
                            for (Table t2 : getTables()) {
                                if (t2.getName().equals(fk.getForeign_table())) {
                                    return t2;
                                }
                            }
                        }
                    }
                }
            }
        }
        return table;
    }

    /**
     * Get the named table from the table list
     * 
     * @return the named table.
     * @throws IllegalArgumentException
     *             of not found
     */
    private TableWithRefFK getTableWithRefFK(String name) throws IllegalArgumentException {

        Table table = null;
        for (Table t : getTables()) {
            if (t.getName().equals(name)) {
                table = t;
                break;
            } else if (t.getAlias() != null && t.getAlias().equals(name)) {
                table = t;
                break;
            }
        }
        // try and find table through FK fields
        if (table == null) {
            for (Table t : getTables()) {
                for (CommonStorageField field : t.getFields()) {
                    if (field instanceof FK) {
                        FK fk = (FK) field;
                        if (name.equals(fk.getForeign_table_alias())) {
                            for (Table t2 : getTables()) {
                                if (t2.getName().equals(fk.getForeign_table())) {
                                    TableWithRefFK tableWithRefFK = new TableWithRefFK();
                                    tableWithRefFK.setTable(t2);
                                    tableWithRefFK.setFk(fk);
                                    return tableWithRefFK;
                                }
                            }
                        }
                    }
                }
            }
        }
        Validate.notNull(table, "Table [" + name + "] not found on Database [" + getName() + "]");

        TableWithRefFK tableWithRefFK = new TableWithRefFK();
        tableWithRefFK.setTable(table);
        return tableWithRefFK;
    }

    private class TableWithRefFK {
        private Table table;
        private FK fk;

        private void setTable(Table table) {
            this.table = table;
        }

        private Table getTable() {
            return table;
        }

        private void setFk(FK fk) {
            this.fk = fk;
        }

        private FK getFk() {
            return fk;
        }
    }

    /**
     * Get the named pkCreate from the pkCreates list
     * 
     * @return the named pkCreate.
     * @throws IllegalArgumentException
     *             if not found
     */
    public PkCreate getPkCreate(String name, String dbSpecificName) throws IllegalArgumentException {
        DbSpecific dbSpecific = getDbSpecific(dbSpecificName);
        Validate.notNull(dbSpecific, "DbSpecific [" + dbSpecificName + "] not found in Database [" + getName() + "]");
        PkCreate pkCreate = dbSpecific.getPkCreate(name);
        Validate.notNull(pkCreate, "PkCreate [" + name + "] not found in Database [" + getName() + "] in DBSpecific ["
                + dbSpecificName + "]");
        return pkCreate;
    }

    /**
     * Get the named pkCreate from the pkCreates list
     * 
     * @return the named pkCreate.
     * @throws IllegalArgumentException
     *             if not found
     */
    public PkCreate getPkCreate(String name) throws IllegalArgumentException {
    	PkCreate pkCreate = getPk_create();
        Validate.notNull(pkCreate, "PkCreate [" + name + "] not found in Database [" + getName() + "]");
        return pkCreate;
    }

    /**
     * Get the named pkCreate from the pkCreates list
     * 
     * @return the named pkCreate.
     * @throws IllegalArgumentException
     *             if not found
     */
    public PkCreate getPkCreateQuietly(String name) throws IllegalArgumentException {
    	for (PkCreate pkCreate : getPkCreates()) {
    		if (pkCreate.getName().equals(name)) {
    			return pkCreate;
    		}
    	}
        return null;
    }

    /**
     * Get the named sql from the sqls list
     * 
     * @param name
     *            - the name of the sql that we want
     * 
     * @return the named sql.
     * @throws IllegalArgumentException
     *             if not found
     */
    public Sql getSql(String name) throws IllegalArgumentException {

        Sql sql = getSqlQuietly(name);
        Validate.notNull(sql, "Sql [" + name + "] not found in Database [" + getName() + "]");
        return sql;
    }

    /**
     * Get the named sql from the sqls list
     * 
     * @param name
     *            - the name of the sql that we want
     * 
     * @return the named sql.
     */
    public Sql getSqlQuietly(String name) throws IllegalArgumentException {

        for (Sql sql : getSqls()) {
            if (name.equals(sql.getName())) {
                return sql;
            }
        }
        return null;
    }

    /**
     * Get the named sql from the sqls list
     * 
     * @param dbSpecificName
     *            - if we want a database specific sql
     * @param name
     *            - the name of the sql that we want
     * 
     * @return the named sql.
     * @throws IllegalArgumentException
     *             if not found
     */
    public Sql getSql(String dbSpecificName, String name) throws IllegalArgumentException {
        DbSpecific dbSpecific = getDbSpecific(dbSpecificName);
        Sql sql = dbSpecific.getSql(name);
        return sql;
    }


    /**
     * Get the named function from the functions list
     * 
     * @param name
     *            - the name of the function that we want
     * 
     * @return the named function.
     * @throws IllegalArgumentException
     *             if not found
     */
    public Function getFunction(String name) throws IllegalArgumentException {

        Function function = getFunctionQuietly(name);
        Validate.notNull(function, "Function [" + name + "] not found in Database [" + getName() + "]");
        return function;
    }

    /**
     * Get the named function from the functions list
     * 
     * @param name
     *            - the name of the function that we want
     * 
     * @return the named function.
     */
    public Function getFunctionQuietly(String name) throws IllegalArgumentException {

        for (Function function : getFunctions()) {
            if (name.equals(function.getName())) {
                return function;
            }
        }
        return null;
    }

    /**
     * Get the named function from the functions list
     * 
     * @param dbSpecificName
     *            - if we want a database specific sql
     * @param name
     *            - the name of the sql that we want
     * 
     * @return the named function.
     * @throws IllegalArgumentException
     *             if not found
     */
    public Function  getFunction(String dbSpecificName, String name) throws IllegalArgumentException {
        DbSpecific dbSpecific = getDbSpecific(dbSpecificName);
        Function function = dbSpecific.getFunction(name);
        return function;
    }

    /**
     * Get the named DbSpecific from the dbSpecifics list
     * 
     * @return the named DbSpecific.
     * @throws IllegalArgumentException
     *             if not found
     */
    public DbSpecific getDbSpecific(String name) throws IllegalArgumentException {

    	DbSpecific dbSpecific = getDbSpecificQuietly(name);
        Validate.notNull(dbSpecific, "DbSpecific [" + name + "] not found in Database [" + getName() + "]");
        return dbSpecific;
    }

    /**
     * Get the named DbSpecific from the dbSpecifics list
     * 
     * @return the named DbSpecific or null
     */
    public DbSpecific getDbSpecificQuietly(String name) throws IllegalArgumentException {

        for (DbSpecific dbSpecific : getDbSpecifics()) {
            if (name.equals(dbSpecific.getName())) {
                return dbSpecific;
            }
        }
        return null;
    }

    public List<Table> getTables() {

        return tables;
    }

    public List<PkCreate> getPkCreates() {

        return pkCreates;
    }

    public List<Sql> getSqls() {
        return sqls;
    }

    public List<Function> getFunctions() {
        return functions;
    }

    public List<DbSpecific> getDbSpecifics() {
        return dbSpecifics;
    }

	public void setName(String name)
	{

		this.name = name;
	}

	public String getName()
	{

		return name;
	}

	public String toString(int indent)
	{

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < indent; i++) {
			sb.append(' ');
		}
		sb.append("DATABASE:" + getName());
		return sb.toString();
	}

	/**
	 * Build both the sql count to retrieve the total number of rows and the sql query to retrieve all or a subset of
	 * these rows
	 * 
	 * @param tableName
	 * @param fields
	 *            we want to include in the query or null if we want them all
	 * @param leftJoin
	 * 
	 * @param whereClause
	 *            is the where clause if not null.
	 * @param orderBy
	 *            the sort order or null if no sort required
	 * @param rowcount
	 *            the number of rows we want returned, -1 for all rows
	 * @param offset
	 *            the starting row, -1 to ignore
	 * @return the SQL Count Query and the Row Query
	 */
	public String[] buildQuery(String tableName, String[] fields, String leftJoin, String whereClause, String orderBy,
			int rowCount, int offset) throws DBConfigException
	{

		return buildQuery(getName(), tableName, fields, leftJoin, whereClause, orderBy, rowCount, offset);
	}

	/**
	 * Build both the sql count to retrieve the total number of rows and the sql query to retrieve all or a subset of
	 * these rows
	 * 
	 * @param databaseName
	 * @param tableName
	 * @param fields
	 *            we want to include in the query or null if we want them all
	 * @param leftJoin
	 * @param whereClause
	 *            is the where clause if not null.
	 * @param orderBy
	 *            the sort order or null if no sort required
	 * @param rowcount
	 *            the number of rows we want returned, -1 for all rows
	 * @param offset
	 *            the starting row, -1 to ignore
	 * @return the SQL Count Query and the Row Query
	 */
	public String[] buildQuery(String databaseName, String tableName, String[] fields, String leftJoin,
			String whereClause, String orderBy, int rowCount, int offset) throws DBConfigException
	{

		List<CommonStorageField> commonStorageFields = this.buildTableAndFieldNames(tableName);

		if (StringUtils.isEmpty(databaseName)) {
			databaseName = this.name;
		}
		StringBuffer query = new StringBuffer();
		String innerQuery = null;
		// ===
		// build the limit part of the query
		// FIXME this is failing in tests, does limit work with derby? derby
		// doesn't support limit
		// ===
		String limit = "";
		if (rowCount > -1) {
			limit = " limit " + rowCount;
			if (offset > -1) {
				limit += " offset " + offset;
			}
		}
		boolean onlyInnerRequired = true;
		if (fields != null && fields.length > 0) {
			onlyInnerRequired = false;
		}

		Table table = getTable(tableName);
		
		if (table == null) {
			throw new DBConfigException("Table [" + tableName + "] not found in Database [" + getName() + "]");
		}
		innerQuery = table.buildQuery(databaseName, commonStorageFields, leftJoin, whereClause, orderBy,
				SQL_QUOTE);

		String countQuery = "select count(*) from (" + innerQuery + ") tb";
		// ===
		// build the outer query part
		// ===
		if (onlyInnerRequired == false) {
			query.append("select ");
			// for (int iLoop = 0; iLoop < tableAndFieldNames.size(); iLoop++) {
			for (int iLoop = 0; iLoop < fields.length; iLoop++) {
				if (iLoop > 0) {
					query.append(',');
				}
				// String f = tableAndFieldNames.get(iLoop);
				String f = fields[iLoop];
				if (f.indexOf('.') < 0) {
					// use default table name
					f = tableName + "." + f;
				}
				query.append(f.replace('.', '_'));
				query.append(" as " + SQL_QUOTE);
				query.append(f);
				query.append(SQL_QUOTE);
			}
			query.append(" from ( ");
			query.append(innerQuery);
			query.append(limit);
			query.append(" ) tb");
		} else {
			query.append(innerQuery);
			query.append(limit);
		}

		// Log.getInstance().debug("innerQuery:" + innerQuery);
		log.debug("query:" + query.toString());
		return new String[] { countQuery, query.toString() };
	}

	public List<CommonStorageField> buildTableAndFieldNames(String tableName)
	{

		Table table = getTable(tableName);

		List<CommonStorageField> fields = new ArrayList<CommonStorageField>();

		for (CommonStorageField field : table.getFields()) {
			// fieldNames.add(table.getName() + "." + field.getName());
			fields.add(field);
			if (field instanceof FK) {
				FK fk = (FK) field;
				// log.debug("FK(" + fk.getForeign_table() + "." + fk.getForeign_key() + ")");
				fields.addAll(buildTableAndFieldNames(fk.getForeign_table()));
			}
		}
		return fields;
	}

	/**
	 * Get a StorageField from the database . table that matches tableAndFieldName.
	 * 
	 * TODO add unit test
	 * 
	 * @param tableName
	 * @param fieldName
	 * @return
	 * @throws IllegalArgumentException
	 */
	public CommonStorageField getStorageField(String tableAndFieldName)
	{

		String tableName = Table.getTableName(tableAndFieldName);
		Validate.notEmpty(tableName, String.format(LocaleUtils.getLocalizedString(ConstantsDB.localeResourceName,
				ConstantsDB.KEY_database_missing_tablename), tableAndFieldName));
		String fieldName = Table.getFieldName(tableAndFieldName);
		Validate.notEmpty(fieldName, String.format(LocaleUtils.getLocalizedString(ConstantsDB.localeResourceName,
				ConstantsDB.KEY_database_missing_fieldname), tableAndFieldName));
		CommonStorageField field = getStorageField(tableName, fieldName);
		return field;
	}

	/**
	 * Get a StorageField from the database . table that matches tableName.
	 * 
	 * TODO add unit test
	 * 
	 * @param tableName
	 * @param fieldName
	 * @return
	 * @throws IllegalArgumentException
	 */
	public CommonStorageField getStorageField(String tableName, String fieldName)
	{
        // Table table = getTable(tableName);
        TableWithRefFK tableWithRefFK = getTableWithRefFK(tableName);
        CommonStorageField commonStorageField = tableWithRefFK.getTable().getField(fieldName);
        commonStorageField.setRefFk(tableWithRefFK.getFk());
        return commonStorageField;

	}
	
	public Table findTablePath(String tableName1, String tableName2) {
		for (Table table : getTables()) {
			for (TablePath tablePath : table.getTablePaths()) {
				if (tablePath.getTable_a().equalsIgnoreCase(tableName1)){
					if (tablePath.getTable_b().equalsIgnoreCase(tableName2)){
						return table;
					}
				} else if (tablePath.getTable_a().equalsIgnoreCase(tableName2)){
					if (tablePath.getTable_b().equalsIgnoreCase(tableName1)){
						return table;
					}
				}
			}
		}
		return null;
	}
	
	/**
	 * A database may contain DBInserts that are used to insert databases from other files. This
	 * method calls the insertion process.
	 * @param execContext
	 * @exception Exception 
	 */
	protected void processInserts(IExecContext execContext) throws Exception {
		for (DBInsert insert : dbInserts) {
			insert.execute(execContext);
		}
	}

	/**
	 * @return the date_format
	 */
	public String getDate_format() {
		return date_format;
	}

	/**
	 * @param date_format the date_format to set
	 */
	public void setDate_format(String date_format) {
		this.date_format = date_format;
	}

	/**
	 * @return the time_format
	 */
	public String getTime_format() {
		return time_format;
	}

	/**
	 * @param time_format the time_format to set
	 */
	public void setTime_format(String time_format) {
		this.time_format = time_format;
	}

	/**
	 * @return the datetime_format
	 */
	public String getDatetime_format() {
		return datetime_format;
	}

	/**
	 * @param datetime_format the datetime_format to set
	 */
	public void setDatetime_format(String datetime_format) {
		this.datetime_format = datetime_format;
	}

	public String getUpdate_field_version_num() {
		return update_field_version_num;
	}

	public void setUpdate_field_version_num(String update_field_version_num) {
		this.update_field_version_num = update_field_version_num;
	}
}
