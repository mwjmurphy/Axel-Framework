package org.xmlactions.db.update;


import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;


import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.text.StrSubstitutor;
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

public class UpdateToDB {

	private static final Logger log = LoggerFactory.getLogger(UpdateToDB.class);
	
	public void update(IExecContext execContext, StorageConfig storageConfig, SqlSelectInputs sqlSelectInputs, ISqlTable [] sqlTables, Tracer tracer) {
    	StringBuilder traceSB = null;
    	if (tracer != null) {
    		traceSB = new StringBuilder();
    	}
		DBConnector dbConnector = storageConfig.getDbConnector();
		Connection connection = null;
		try {
			connection = dbConnector.getConnection(execContext);
			connection.setAutoCommit(false);
			int pk = 0;
			for (int tableIndex = sqlTables.length-1; tableIndex>=0; tableIndex--) {
				ISqlTable sqlTable = sqlTables[tableIndex];
				if (sqlTable.getUpdateSql() != null) {
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
					DBSQL.update(connection, sql, sqlTable.getParams());
				}
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
	}


}
