
package org.xmlactions.pager.actions.db;


import java.sql.Connection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;


import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.xmlactions.action.actions.BaseAction;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.common.tracer.Tracer;
import org.xmlactions.db.ConstantsDB;
import org.xmlactions.db.DBConnector;
import org.xmlactions.db.DBSQL;
import org.xmlactions.db.DBUtils;
import org.xmlactions.db.DBWriteUtils;
import org.xmlactions.db.StorageContainer;
import org.xmlactions.db.actions.Database;
import org.xmlactions.db.actions.Storage;
import org.xmlactions.db.actions.Table;
import org.xmlactions.db.config.StorageConfig;
import org.xmlactions.pager.actions.form.ClientParamNames;
import org.xmlactions.web.HttpParam;
import org.xmlactions.web.PagerWebConst;


/**
 * Deletes a record from a table.
 * <p>
 * The parameters are contained in the execContext named map 'request'. These are retrieved from the http request.
 * </p>
 * <p>
 * One of the parameters must contain the table name. This is found in the request map using the key 'table.name'
 * </p>
 * <p>
 * There must be at one parameter that is the PK used by the where clause for the delete. The where clause will be
 * pk==value
 * </p>
 */
public class DeleteRecord extends BaseAction
{

	private final static Logger log = LoggerFactory.getLogger(DeleteRecord.class);

	public String execute(IExecContext execContext) throws Exception
	{

		String result = "OK";
		try {
			result = deleteRecord(execContext);
			if (StringUtils.isEmpty(result)) {
				result = "OK:";
			} else {
				result = "ER:" + result;
			}
		} catch (Exception ex) {
			log.warn(ex.getMessage(), ex);
			result = "EX:" + ex.getMessage();
		}
		return result;
	}

	private String deleteRecord(IExecContext execContext) throws Exception
	{
		Map<String, String> map = new HashMap<String, String>();
		String storage_config_ref = null;
		String tableName = null;
		String pkValue = null;

		// ===
        // get parameters from incoming http request.
        // ===
        List<HttpParam> paramList = (List<HttpParam>) execContext.get(PagerWebConst.REQUEST_LIST);
        Validate.notNull(paramList, "Missing [" + PagerWebConst.REQUEST_LIST + "] named map from the execContext");

        for (HttpParam param : paramList) {
            Object value = param.getValue();
            String key = param.getKey();
            if (log.isDebugEnabled()) {
                log.debug("key [" + key + "] value [" + value + "]");
            }
			if (key.equals(ClientParamNames.STORAGE_CONFIG_REF)) {
				storage_config_ref = (String) value;
			} else if (key.equals(ClientParamNames.TABLE_NAME_MAP_ENTRY)) {
				tableName = (String) value;
			} else if (key.equals(ClientParamNames.PK_VALUE)) {
				pkValue = (String) value;
			} else {
				map.put(key, (String) value);
			}
		}

		Validate.notEmpty(pkValue, "[" + ClientParamNames.PK_VALUE + "] not found in [" + PagerWebConst.REQUEST
				+ "] named map from the execContext");
		Validate.notEmpty(tableName, "[" + ClientParamNames.TABLE_NAME_MAP_ENTRY + "] not found in ["
				+ PagerWebConst.REQUEST + "] named map from the execContext");
		Validate.notEmpty(storage_config_ref, "[" + ClientParamNames.STORAGE_CONFIG_REF + "] not found in ["
				+ PagerWebConst.REQUEST + "] named map from the execContext");

		StorageConfig storageConfig = (StorageConfig) execContext.get(storage_config_ref);
		Validate.notNull(storageConfig, "No [" + StorageConfig.class.getName() + "] found in ExecContext ["
				+ storage_config_ref + "]");
		StorageContainer storageContainer = storageConfig.getStorageContainer();
		Storage storage = storageContainer.getStorage();

		Database database = storage.getDatabase(storageConfig.getDatabaseName());

		Table table = database.getTable(tableName);

		String whereClause = "where " + table.getPk().getName() + "=" + pkValue;
		String sql = DBWriteUtils.buildDeleteTableRow(database.getName(), tableName, whereClause);

		DBConnector dbConnector = storageConfig.getDbConnector();
		Connection connection = null;
		try {
			connection = dbConnector.getConnection(execContext);
			DBSQL.update(connection, sql);
	        Tracer tracer = (Tracer) execContext.get(ConstantsDB.KEY_TRACER_DB_CLASS);
	        if (tracer != null) {
	        	// trace that we deleted a record
				tracer.saveTrace(this.getClass().getName(), ConstantsDB.TRACE_DELETE, sql);
	        }
			log.debug("DeleteRecord:" + sql);
		} catch (Throwable e) {
			throw new IllegalArgumentException(e.getMessage(), e);
		} finally {
			DBUtils.closeQuietly(connection);
		}

		return "";
	}

}
