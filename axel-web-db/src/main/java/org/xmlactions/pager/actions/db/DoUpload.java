
package org.xmlactions.pager.actions.db;


import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.xmlactions.action.ActionConst;
import org.xmlactions.action.actions.BaseAction;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.common.tracer.Tracer;
import org.xmlactions.db.ConstantsDB;
import org.xmlactions.db.StorageContainer;
import org.xmlactions.db.actions.Database;
import org.xmlactions.db.actions.Storage;
import org.xmlactions.db.actions.Table;
import org.xmlactions.db.config.StorageConfig;
import org.xmlactions.db.insert.InsertToDB;
import org.xmlactions.db.sql.BuildSelect;
import org.xmlactions.db.sql.select.ISqlSelectBuildQuery;
import org.xmlactions.db.sql.select.ISqlTable;
import org.xmlactions.db.sql.select.SqlField;
import org.xmlactions.db.sql.select.SqlSelectInputs;
import org.xmlactions.web.PagerWebConst;
import org.xmlactions.web.conceal.HtmlRequestMapper;


/**
 * Performs a reset.
 */
public class DoUpload extends BaseAction
{

	private final static Logger log = LoggerFactory.getLogger(DoUpload.class);

	public String execute(IExecContext execContext) throws Exception
	{

		Map<String, Object> httpRequestMap = execContext.getNamedMap(PagerWebConst.REQUEST);

		Map<String, Object> additionalParameters = new HashMap<String,Object>();
		String storageConfigRef = null;
		String tableName = null;
		String fieldName = null;
		String fileName = null;
		String path = null;
		String realPath = null;

		for (String key : httpRequestMap.keySet()) {
			Object obj = httpRequestMap.get(key);
			if (obj instanceof String) {
				String value = (String) obj;
				if (key.equals(HtmlRequestMapper.UPLOAD_STORAGE_CONFIG_REF)) {
					storageConfigRef = value;
				} else if (key.equals(HtmlRequestMapper.UPLOAD_TABLE_NAME)) {
					tableName = value;
				} else if (key.equals(HtmlRequestMapper.UPLOAD_FIELD_NAME)) {
					fieldName = value;
				} else if (key.equals(HtmlRequestMapper.UPLOAD_FILE_NAME)) {
					fileName = value;
				} else if (key.equals(HtmlRequestMapper.UPLOAD_FILE_PATH)) {
					path = value;
				} else if (key.equals(ActionConst.WEB_REAL_PATH_BEAN_REF)) {
					realPath = value;
				} else if (key.startsWith("field:")){
					additionalParameters.put(key.substring("field:".length()), value);
				}
			}
		}
		
		if (StringUtils.isNotEmpty(tableName) && StringUtils.isNotEmpty(fieldName)) {
			try {
				return saveDataToDb(execContext, httpRequestMap, storageConfigRef, fileName, tableName, fieldName, additionalParameters);
			} catch (Exception ex) {
				log.error(ex.getMessage(),ex);
				return "EX:" + ex.getMessage();
			}
		} else {
			try {
				return saveDataToFile(execContext, httpRequestMap, fileName, path, realPath);
			} catch (Exception ex) {
				log.error(ex.getMessage(),ex);
				return "EX:" + ex.getMessage();
			}
		}
	}
	
	private String saveDataToFile(IExecContext execContext, Map<String, Object> httpRequestMap, String fileName, String path, String realPath) throws Exception {
		
		long fileSize = (Long) httpRequestMap.get(HtmlRequestMapper.UPLOAD_FILE_SIZE);
		byte[] fileData = (byte[]) httpRequestMap.get(HtmlRequestMapper.UPLOAD_FILE_DATA);

		fileName = new File(fileName).getName();
		File file = new File(realPath, path);
		if (file.exists() == false || file.isFile()) {
			return "EX:" + "Folder [" + path + "] does not exist.";
			// throw new IllegalArgumentException("Folder [" + path + "] does not exist.");
		}
		file = new File(file, fileName);
		fileName = file.getAbsolutePath();
		log.debug("fileName:" + fileName);
		log.debug("fileSize:" + fileSize);
		log.debug("fileData:" + fileData);
		log.debug("path:" + path);
		log.debug("realPath:" + realPath);
		OutputStream outputStream = new FileOutputStream(file.getAbsolutePath());
		IOUtils.write(fileData, outputStream);

		return "OK:";
		
	}
	
	private String saveDataToDb(IExecContext execContext, Map<String, Object> httpRequestMap, String storageConfigRef, String fileName, String tableName, String fieldName, Map<String, Object> additionalParameters) throws Exception {
		
        StorageConfig storageConfig = (StorageConfig) execContext.get(storageConfigRef);
        if (storageConfig==null) {
			throw new IllegalArgumentException("No StorageConfig found in ExecContext for [" + storageConfigRef+ "]");
        }
        StorageContainer storageContainer = storageConfig.getStorageContainer();
        Storage storage = storageContainer.getStorage();

        Database database = storage.getDatabase(storageConfig.getDatabaseName());

        Table table = database.getTable(tableName);
        
        Map<String, Object>map = new HashMap<String, Object>();
        map.put(fieldName, httpRequestMap.get(HtmlRequestMapper.UPLOAD_FILE_DATA));
        
        for (String key : additionalParameters.keySet()) {
        	map.put(key, additionalParameters.get(key));
        }

        String fieldFileName = (String) httpRequestMap.get(HtmlRequestMapper.UPLOAD_FIELD_FILE_NAME);
        if (StringUtils.isNotEmpty(fieldFileName)) {
        	map.put(fieldFileName, fileName);
        }
        
        String fieldFkRef = (String) httpRequestMap.get(HtmlRequestMapper.UPLOAD_FIELD_FK_REF);
        String fieldFkRefValue = (String) httpRequestMap.get(HtmlRequestMapper.UPLOAD_FIELD_FK_REF_VALUE);
        if (StringUtils.isNotEmpty(fieldFkRef) && StringUtils.isNotEmpty(fieldFkRefValue)) {
        	map.put(fieldFkRef, fieldFkRefValue);
        }
        
        String pkValue = saveData(execContext, storageConfig, database, table, map);

		return "OK:pk=" + pkValue;
	}
	
    private String saveData(IExecContext execContext, StorageConfig storageConfig, Database database, Table table,
            Map<String, Object> map) throws Exception {

        SqlSelectInputs sqlSelectInputs = buildInsertSql(storageConfig, database, table, map, execContext);
        ISqlSelectBuildQuery build = storageConfig.getSqlBuilder();
        ISqlTable[] sqlTables = build.buildInsertSqls(execContext, sqlSelectInputs);
        if (log.isDebugEnabled()) {
            log.debug("\ninsertSqls:" + sqlTables);
        }

        InsertToDB insertToDB = new InsertToDB();
        Tracer tracer = (Tracer) execContext.get(ConstantsDB.KEY_TRACER_DB_CLASS);
        String pkValue = insertToDB.insert(execContext, storageConfig, sqlSelectInputs, sqlTables, tracer);
        return pkValue;
    }
    
    private SqlSelectInputs buildInsertSql(StorageConfig storageConfig, Database database, Table table,
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
