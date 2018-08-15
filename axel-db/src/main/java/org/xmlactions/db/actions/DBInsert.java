package org.xmlactions.db.actions;


import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmlactions.action.actions.BaseAction;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.db.StorageContainer;

public class DBInsert extends BaseAction {
	
	private static final Logger logger = LoggerFactory.getLogger(DBInsert.class);

    private String file;
    private String database;
    private String table;

    public String execute(IExecContext execContext) throws Exception {
    	logger.debug("file:" + file + " database:" + database);
    	if (StringUtils.isNotBlank(file)) {
    		processFileInsert(execContext, file, database);
    	} else {
    		processInsert(execContext, database);
    	}
        return null;
    }
    
    private void processInsert(IExecContext execContext, String databaseName) throws Exception {
    	if (getParent() instanceof Database) {
			Storage storage = (Storage)getParent().getParent();
			logger.debug("storage:" + storage.getName());
			Database fromDB = storage.getDatabase(databaseName);
			mergeDatabases(execContext, fromDB, (Database)getParent());
    	} else {
    		throw new IllegalArgumentException("Parent of insert must be a database.");
    	}
    }
    private void processFileInsert(IExecContext execContext, String filename, String databaseName) throws Exception {
    	if (getParent() instanceof Database) {
    		StorageContainer storageContainer = new StorageContainer(filename, execContext);
    		logger.debug("storage:" + storageContainer.getStorage().getName());
			Database fromDB = storageContainer.getStorage().getDatabase(databaseName);
			mergeDatabases(execContext, fromDB, (Database)getParent());
    	} else {
    		throw new IllegalArgumentException("Parent of insert must be a database.");
    	}
    }
    
    private void mergeDatabases(IExecContext execContext, Database fromDB, Database toDB) {
    	
    	if (StringUtils.isNotBlank(fromDB.getDate_format())) {
    		toDB.setDate_format(fromDB.getDate_format());
    	}
    	if (StringUtils.isNotBlank(fromDB.getTime_format())) {
    		toDB.setTime_format(fromDB.getTime_format());
    	}
    	if (StringUtils.isNotBlank(fromDB.getDatetime_format())) {
    		toDB.setDatetime_format(fromDB.getDatetime_format());
    	}
    	
    	for (DbSpecific fromDbSpecific : fromDB.getDbSpecifics()) {
    		DbSpecific toDbSpecific = toDB.getDbSpecificQuietly(fromDbSpecific.getName());
    		if (toDbSpecific == null) {
    			// This named fromDbSpecific does not exist in the destination database.
    			// so copy everything
    			toDB.setDb_specific(fromDbSpecific);
    		} else {
    			// we already have the same named dbSpecific in the destination database.
    			// So we copy the contents of the fromDbSpecific.
    			if (toDbSpecific.getTotal_record_count_field() == null) {
    				toDbSpecific.setTotal_record_count_field(fromDbSpecific.getTotal_record_count_field());
    			}
    			if (toDbSpecific.getTotal_record_count_sql() == null) {
    				toDbSpecific.setTotal_record_count_sql(fromDbSpecific.getTotal_record_count_sql());
    			}
    			for (Function function : fromDbSpecific.getFunctions()) {
    				if (toDbSpecific.getFunctionQuietly(function.getName()) != null) {
    					throw new IllegalArgumentException("Error Merging Databases [" + fromDB.getName() + " to " + toDB.getName() + "] Duplicate function [" + function.getName() + "]");
    				}
    				toDbSpecific.setFunction(function);
    			}
    			for (PkCreate pkCreate : fromDbSpecific.getPkCreates()){
    				if (toDbSpecific.getPkCreateQuietly(pkCreate.getName()) != null) {
    					throw new IllegalArgumentException("Error Merging Databases [" + fromDB.getName() + " to " + toDB.getName() + "] Duplicate pk_create [" + pkCreate.getName() + "]");
    				}
    				toDbSpecific.setPk_create(pkCreate);
    			}
    			for (Sql sql : fromDbSpecific.getSqls()) {
    				if (toDbSpecific.getSqlQuietly(sql.getName()) != null) {
    					throw new IllegalArgumentException("Error Merging Databases [" + fromDB.getName() + " to " + toDB.getName() + "] Duplicate sql [" + sql.getName() + "]");
    				}
    				toDbSpecific.setSql(sql);
    			}
    		}
    	}
    	for (Function function : fromDB.getFunctions()) {
			if (toDB.getFunctionQuietly(function.getName()) != null) {
				throw new IllegalArgumentException("Error Merging Databases [" + fromDB.getName() + " to " + toDB.getName() + "] Duplicate function [" + function.getName() + "]");
			}
    		toDB.setFunction(function);
    	}
    	for (DBInsert insert : fromDB.getInserts()) {
    		toDB.setInsert(insert);
    	}
    	for (PkCreate pkCreate: fromDB.getPkCreates()) {
			if (toDB.getPkCreateQuietly(pkCreate.getName()) != null) {
				throw new IllegalArgumentException("Error Merging Databases [" + fromDB.getName() + " to " + toDB.getName() + "] Duplicate pk_create [" + pkCreate.getName() + "]");
			}
    		toDB.setPk_create(pkCreate);
    	}
    	for (Sql sql: fromDB.getSqls()) {
			if (toDB.getSqlQuietly(sql.getName()) != null) {
				throw new IllegalArgumentException("Error Merging Databases [" + fromDB.getName() + " to " + toDB.getName() + "] Duplicate sql [" + sql.getName() + "]");
			}
    		toDB.setSql(sql);
    	}
    	for (Table table: fromDB.getTables()) {
    		Table toTable = toDB.getTableQuietly(table.getName());
    		if (toTable != null) {
    			if (toTable.getAlias() == null && table.getAlias() == null) {
    				throw new IllegalArgumentException("Error Merging Databases [" + fromDB.getName() + " to " + toDB.getName() + "] Duplicate table [" + table.getName() + "]");
    			}
    			if (toTable.getAlias() != null) {
    				if (toTable.getAlias().equals(table.getAlias())) {
        				throw new IllegalArgumentException("Error Merging Databases [" + fromDB.getName() + " to " + toDB.getName() + "] Duplicate table [" + table.getName() + "]");
    				}
    			}
    		}
    		toDB.setTable(table);
    	}
    }

	/**
	 * @return the file
	 */
	public String getFile() {
		return file;
	}

	/**
	 * @param file the file to set
	 */
	public void setFile(String file) {
		this.file = file;
	}

	/**
	 * @return the database
	 */
	public String getDatabase() {
		return database;
	}

	/**
	 * @param database the database to set
	 */
	public void setDatabase(String database) {
		this.database = database;
	}

	/**
	 * @return the table
	 */
	public String getTable() {
		return table;
	}

	/**
	 * @param table the table to set
	 */
	public void setTable(String table) {
		this.table = table;
	}
    

}
