package org.xmlactions.db.insert;


import java.sql.Connection;


import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.common.tracer.Tracer;
import org.xmlactions.db.ConstantsDB;
import org.xmlactions.db.DBConnector;
import org.xmlactions.db.DBSQL;
import org.xmlactions.db.DBUtils;
import org.xmlactions.db.actions.PK;
import org.xmlactions.db.actions.Table;
import org.xmlactions.db.config.StorageConfig;
import org.xmlactions.db.sql.select.ISqlTable;
import org.xmlactions.db.sql.select.SqlField;
import org.xmlactions.db.sql.select.SqlSelectInputs;

public class InsertToDB {

	private static final Logger log = LoggerFactory.getLogger(InsertToDB.class);

    /**
     * 
     * @param execContext
     * @param storageConfig
     * @param sqlSelectInputs
     * @param sqlTables
     * @return the pkValue of the new record
     */
    public String insert(IExecContext execContext, StorageConfig storageConfig, SqlSelectInputs sqlSelectInputs,
            ISqlTable[] sqlTables, Tracer tracer) {
    	
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
				if (log.isDebugEnabled()) {
					log.debug(sqlTable.getInsertSql());
				}
				if (tracer != null) {
					traceSB.append(sqlTable.getInsertSql());
				}
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
				    //Map<String, String> strSubstutionMap = new HashMap<String, String>();
	                //strSubstutionMap.put(ConstantsDB.SQL_INSERT_PK_VALUE, pk_value);
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
	            if (StringUtils.isNotEmpty(pkSql)) {
	            	DBSQL.insert(connection, sql, sqlTable.getParams());
	            } else {
	            	pk = DBSQL.insertAndReturnKey(connection, sql, sqlTable.getParams());
	            }
	            
                createdPkValue = "" + pk;
				if (sqlTable.getTable().getPk() != null) {
					PK pK = sqlTable.getTable().getPk();
					String key = sqlTable.getTable().getName() + Table.TABLE_FIELD_SEPERATOR + pK.getName();
					execContext.put(key, pk);
				}
				if (log.isDebugEnabled()) {
					log.debug("pk:" + pk);
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


}
