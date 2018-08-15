package org.xmlactions.db.save;


import java.sql.Connection;




import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.common.collections.PropertyKeyValue;
import org.xmlactions.common.tracer.Tracer;
import org.xmlactions.db.ConstantsDB;
import org.xmlactions.db.DBConnector;
import org.xmlactions.db.DBSQL;
import org.xmlactions.db.DBUtils;
import org.xmlactions.db.actions.CommonStorageField;
import org.xmlactions.db.actions.Database;
import org.xmlactions.db.actions.FK;
import org.xmlactions.db.actions.PK;
import org.xmlactions.db.actions.Table;
import org.xmlactions.db.config.StorageConfig;
import org.xmlactions.db.exception.DBSQLException;
import org.xmlactions.db.sql.BuildSelect;
import org.xmlactions.db.sql.select.ISqlSelectBuildQuery;
import org.xmlactions.db.sql.select.ISqlTable;
import org.xmlactions.db.sql.select.SqlField;
import org.xmlactions.db.sql.select.SqlSelectInputs;
import org.xmlactions.db.sql.select.SqlTable;
import org.xmlactions.db.sql.select.VersionNumberConcurrency;

public class SaveToDB {

	private static final Logger log = LoggerFactory.getLogger(SaveToDB.class);

	/**
	 * 
	 * @param execContext
	 * @param storageConfig
	 * @param database
	 * @param table
	 * @param map
	 * @return the pk value if this was an insert else the number of rows updated if this was an update.
	 * @throws Exception
	 */
    public String saveData(IExecContext execContext, StorageConfig storageConfig, Database database, Table table,
            Map<String, Object> map) throws Exception {

        SqlSelectInputs sqlSelectInputs = buildSaveSql(storageConfig, database, table, map, execContext);
        ISqlSelectBuildQuery build = storageConfig.getSqlBuilder();
        ISqlTable[] sqlTables = build.buildSaveSqls(execContext, sqlSelectInputs);

        SaveToDB saveToDB = new SaveToDB();
        Tracer tracer = (Tracer) execContext.get(ConstantsDB.KEY_TRACER_DB_CLASS);
        String pkValue = saveToDB.save(execContext, storageConfig, sqlSelectInputs, sqlTables, tracer);
        return pkValue;
    }



    /**
     * 
     * @param execContext
     * @param storageConfig
     * @param sqlSelectInputs
     * @param sqlTables
     * @return the pkValue of the new record
     */
    public String save(IExecContext execContext, StorageConfig storageConfig, SqlSelectInputs sqlSelectInputs, ISqlTable[] sqlTables, Tracer tracer) {
    	
    	List<TableAndPk>pkTables = new ArrayList<TableAndPk>();
    	
    	StringBuilder traceSB = null;
    	if (tracer != null) {
    		traceSB = new StringBuilder();
    	}
		DBConnector dbConnector = storageConfig.getDbConnector();
		Connection connection = null;
        String createdPkValue = null;
		try {
			connection = dbConnector.getConnection(execContext);
			connection.setAutoCommit(false);
			int pk = 0;
			for (int tableIndex = sqlTables.length-1; tableIndex>=0; tableIndex--) {
				ISqlTable sqlTable = sqlTables[tableIndex];
				if (sqlTable.getInsertSql() != null) {
					//log.debug("insert:" + sqlTable.getInsertSql());
					setFk(sqlTable, pkTables);
					pk = insert(execContext, storageConfig, sqlSelectInputs, sqlTable, tracer, traceSB, connection);
					createdPkValue = "" + pk;
					log.debug("insert pk value:" + pk);
					pkTables.add(new TableAndPk(sqlTable.getTable(), createdPkValue));
				} else if (sqlTable.getUpdateSql() != null) {
					//log.debug("update:" + sqlTable.getUpdateSql());
					setFk(sqlTable, pkTables);
					update(execContext, sqlTable, tracer, traceSB, connection);
				}
			}
			if (tracer != null) {
				if (createdPkValue != null) {
					traceSB.append("\ncreatedPkValue [" + createdPkValue + "]");
				}
				tracer.saveTrace(this.getClass().getName(), ConstantsDB.TRACE_INSERT, traceSB.toString());
			}
			connection.commit();
		} catch (Throwable e) {
			try {
				if (connection != null) {
					connection.rollback();
				}
			} catch (Exception ex) {
				// 2nd error. ignore this and only report the first error.
				log.error(ex.getMessage(), ex);
			}
			throw new IllegalArgumentException(e.getMessage(), e);
		} finally {
			
			DBUtils.closeQuietly(connection);
		}
        return createdPkValue;
	}

    // private String insert(IExecContext execContext, ISqlTable sqlTable, SqlSelectInputs sqlSelectInputs) {
    public int insert(IExecContext execContext, StorageConfig storageConfig, SqlSelectInputs sqlSelectInputs, ISqlTable sqlTable, Tracer tracer, StringBuilder traceSB, Connection connection) throws DBSQLException {
        int pk = 0;
		String sql = execContext.replace(sqlTable.getInsertSql());
		String pkSql = null;
		if (sqlTable.getTable().getPk() != null) {
            pkSql = sqlTable.getTable()
                    .getPk()
                    .getPkCreateSql(sqlSelectInputs.getDatabase(), storageConfig.getDbSpecificName());
		}
		
		if (StringUtils.isNotEmpty(pkSql)) {
			if (log.isDebugEnabled()) {
				log.debug("\npkSql:" + pkSql);
			}
		    String pk_value = DBSQL.queryOne(connection, pkSql, null);
		    if (tracer != null) {
		    	traceSB.append("\n");
		    	traceSB.append("pk_value [" + pk_value + "]");
		    }
		    pk = Integer.parseInt(pk_value);
            execContext.put(ConstantsDB.SQL_INSERT_PK_VALUE, pk_value);
        }
        for (SqlField sqlField : sqlTable.getParams()) {
        	if (sqlField.getValue() != null && sqlField.getValue() instanceof String) {
        		String value = execContext.replace((String) sqlField.getValue());
				if (log.isDebugEnabled()) {
					log.debug("setValue (" + sqlField.getFieldName() + ") = [" + value + "]");
				}
			    if (tracer != null) {
					traceSB.append("\nsetValue (" + sqlField.getFieldName() + ") = [" + value + "]");
			    }
        		sqlField.setValue(value);
        	}
        }
		if (log.isDebugEnabled()) {
			log.debug("\ninsert:" + sql);
		}
        if (StringUtils.isNotEmpty(sql)) {
        	pk = DBSQL.insertAndReturnKey(connection, sql, sqlTable.getParams(), sqlTable);
        	// pk = DBSQL.insert(connection, sql, sqlTable.getParams());
        } else {
        	pk = DBSQL.insertAndReturnKey(connection, sql, sqlTable.getParams(), sqlTable);
        }
        
		if (sqlTable.getTable().getPk() != null) {
			PK pK = sqlTable.getTable().getPk();
			String key = sqlTable.getTable().getName() + Table.TABLE_FIELD_SEPERATOR + pK.getName();
			execContext.put(key, pk);
		}
		if (log.isDebugEnabled()) {
			log.debug("pk:" + pk);
		}
    	return pk;
    }

    /**
     * Wil update the data to the database and return the number of rows updated.
     * @param execContext
     * @param sqlTable
     * @param tracer
     * @param traceSB
     * @param connection
     * @return the number of rows updated.
     * @throws DBSQLException
     */
    private int update(IExecContext execContext, ISqlTable sqlTable, Tracer tracer, StringBuilder traceSB, Connection connection) throws DBSQLException {

		// update any foreign key values
		for (SqlField sqlField : sqlTable.getParams()) {
			if (sqlField.getValue() != null && sqlField.getValue() instanceof String) {
				sqlField.setValue(execContext.replace((String)sqlField.getValue()));
				if (tracer != null) {
					traceSB.append("\n  Field(after replace):" + sqlField.getFieldName() + " [" + sqlField.getValue() + "]");
				}
			}
		}
    	
    	String sql = sqlTable.getUpdateSql();
		sql = execContext.replace(sql);
		if (log.isDebugEnabled()) {
			log.debug(sql);
		}
		if (tracer != null) {
			traceSB.append(sql);
			for (SqlField sqlField : sqlTable.getParams()) {
				traceSB.append("\n  Field:" + sqlField.getFieldName() + " [" + sqlField.getValue() + "]");
			}
			tracer.saveTrace(this.getClass().getName(), ConstantsDB.TRACE_UPDATE, traceSB.toString());
		}
		int result = DBSQL.update(connection, sql, sqlTable.getParams());

		Object obj = execContext.get(ConstantsDB.ENFORCE_CONCURRENCY);
		if (obj != null && (Boolean)obj == false) {
			// skip concurrency
		} else {
			PropertyKeyValue versionNumber = VersionNumberConcurrency.getVersionNumber(execContext, sqlTable);
			if (versionNumber != null && result < 1) {
				throw new IllegalArgumentException("Save failed, caused by concurrency update.\n" + sqlToString(sql, sqlTable, versionNumber, result));
			}
		}
		return result;
    }

    private String sqlToString(String sql, ISqlTable sqlTable, PropertyKeyValue versionNumber, int result) {
    	StringBuilder sb = new StringBuilder();
    	sb.append("Concurrency issue version number key:" + versionNumber.getKey() + " version number value:" + versionNumber.getValue() + " rows updated:" + result);
    	sb.append("\nsql:" + sql);
    	if (sqlTable.getParams().size() > 0) {
    		sb.append("\nsql params:");
    	}
    	for (SqlField sqlField : sqlTable.getParams()) {
			sb.append("\n  Field:" + sqlField.getFieldName() + " [" + sqlField.getValue() + "]");
    	}
    	return sb.toString();
    }
    private void  setFk(ISqlTable sqlTable, List<TableAndPk>pkTables) {
    	
    	for (TableAndPk pkTable : pkTables) {
    		SqlField matchingSqlField = pkTable.getMatchingFk(sqlTable);
    		if (matchingSqlField != null && StringUtils.isEmpty("" + matchingSqlField.getValue())) {
    			matchingSqlField.setValue(pkTable.getPkValue());
    		}
    	}
    }

    private class TableAndPk {
    	String pkValue;
    	Table table;
    	
    	private TableAndPk(Table table, String pk) {
    		this.pkValue = pk;
    		this.table = table;
    	}
    	
    	private String getPkValue() {
    		return this.pkValue;
    	}
    	
    	private Table getTable() {
    		return this.table;
    	}
    	
    	private SqlField getMatchingFk(ISqlTable sqlTable) {
    		//
    		// FIXME - get FK from Table not SqlTable
    		// Instead of expecting the FK to be already in the table we should check if there is one in the Table.
    		// This will drop the need for the developer to include the pk field in the add or edit configuration.
    		//
			for (SqlField sqlField : sqlTable.getFields()) {
	    		if (sqlField.getCommonStorageField() != null && sqlField.getCommonStorageField() instanceof FK) {
	    			// check all FKs
	    			FK fk = (FK) sqlField.getCommonStorageField();
	    			if (fk.getForeignTableAliasOrName().equals(getTable().getName()) ||
	    				fk.getForeignTableAliasOrName().equals(getTable().getAlias())) {
	    				// fk references this Table
	    				PK pk = getTable().getPk();
	    				if (pk != null) {
	    					if (fk.getForeign_key().equals(pk.getName()) ||
	    						fk.getForeign_key().equals(pk.getAlias())) {
	    						return sqlField;
	    					}
	    				}
	    			}
	    		}
	    	}
			return null;
    	}
    }

    /**
     * 
     * @param storageConfig
     * @param database
     * @param table
     * @param map
     * @param execContext
     * @return
     */
    private SqlSelectInputs buildSaveSql(StorageConfig storageConfig, Database database, Table table,
            Map<String, Object> map, IExecContext execContext) {

        List<SqlField> sqlFields = new ArrayList<SqlField>();
        for (String key : map.keySet()) {
            // sqlFields.add(new SqlField(map.get(key)));
            SqlField sqlField = new SqlField(key);
            sqlField.setValue(map.get(key));
            sqlFields.add(sqlField);
        }

        SqlSelectInputs sqlSelectInputs = BuildSelect.buildSelect(storageConfig.getStorageContainer().getStorage(),
                database.getName(),
                table.getName(),
                sqlFields,
                storageConfig.getDbSpecificName());
        return sqlSelectInputs;

    }

}
