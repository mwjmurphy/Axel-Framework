
package org.xmlactions.pager.actions.db;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;









import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.xmlactions.action.actions.BaseAction;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.common.collections.PropertyKeyValue;
import org.xmlactions.common.tracer.Tracer;
import org.xmlactions.db.ConstantsDB;
import org.xmlactions.db.StorageContainer;
import org.xmlactions.db.actions.CommonStorageField;
import org.xmlactions.db.actions.Database;
import org.xmlactions.db.actions.PK;
import org.xmlactions.db.actions.Storage;
import org.xmlactions.db.actions.Table;
import org.xmlactions.db.config.StorageConfig;
import org.xmlactions.db.insert.InsertToDB;
import org.xmlactions.db.save.SaveToDB;
import org.xmlactions.db.sql.BuildSelect;
import org.xmlactions.db.sql.select.ISqlSelectBuildQuery;
import org.xmlactions.db.sql.select.ISqlTable;
import org.xmlactions.db.sql.select.SqlField;
import org.xmlactions.db.sql.select.SqlSelectInputs;
import org.xmlactions.db.sql.select.VersionNumberConcurrency;
import org.xmlactions.pager.actions.dates.DateFormatter;
import org.xmlactions.pager.actions.form.ClientParamNames;
import org.xmlactions.pager.actions.form.Edit;
import org.xmlactions.pager.actions.form.processor.CodeProcessor;
import org.xmlactions.pager.actions.form.processor.PostCodeProcessor;
import org.xmlactions.pager.actions.form.processor.PreCodeProcessor;
import org.xmlactions.web.HttpParam;
import org.xmlactions.web.PagerWebConst;


/**
 * Inserts and / or updates data into one or more tables.
 * <p>
 * The parameters are contained in the execContext named map 'request'. These are retrieved from the http request.
 * </p>
 * <p>
 * One of the parameters must contain the table name. This is found in the request map using the key 'table.name'
 * </p>
 * <p>
 * There must be at least one parameter to insert.
 * </p>
 */
public class SaveRecordAction extends BaseAction
{

    private final static Logger log = LoggerFactory.getLogger(SaveRecordAction.class);

    private PreCodeProcessor preCodeProcessor = new PreCodeProcessor();
    private PostCodeProcessor postCodeProcessor = new PostCodeProcessor();

    private String id = null;
    private String storage_config_ref = null;
    private String tableName = null;
    private List<HttpParam> validatedParams = new ArrayList<HttpParam>();
    
    private String newPk = null;

    private Map<String, String>rememberMap = null;

    public String execute(IExecContext execContext) throws Exception {

        String result = null;
        try {
            result = saveRecord(execContext);
            if (StringUtils.isEmpty(result)) {
                result = "OK:" + newPk;
            } else {
                result = "ER:" + result;
            }
        } catch (Exception ex) {
            Throwable cause;
            cause = ex;
            while (cause.getCause() != null) {
                cause = cause.getCause();
            }
            result = "EX:" + cause.getMessage();
            log.error(ex.getMessage(), ex);
        }
        return result;
    }

    private String saveRecord(IExecContext execContext) throws Exception {

        // ===
        // get parameters from incoming http request.
        // ===
        @SuppressWarnings("unchecked")
		List<HttpParam> paramList = (List<HttpParam>) execContext.get(PagerWebConst.REQUEST_LIST);

        // ===
        // extract the required parameters and validate that the required
        // parameters have been entered from the incoming http request.
        // ===
        List<HttpParam> databaseEntries = captureDataFromInputs(execContext, paramList);
        
        


        // ===
        // Get our database and main table
        // ===
        StorageConfig storageConfig = (StorageConfig) execContext.get(storage_config_ref);
        Validate.notNull(storageConfig, "No [" + StorageConfig.class.getName() + "] found in ExecContext ["
                + storage_config_ref + "]");
        StorageContainer storageContainer = storageConfig.getStorageContainer();
        Storage storage = storageContainer.getStorage();

        Database database = storage.getDatabase(storageConfig.getDatabaseName());

        Table table = database.getTable(tableName);

        if (log.isDebugEnabled()) {
            log.debug("table:" + table.getName());
        }
        
        if (rememberMap != null) {
            getPrePostProcesses(rememberMap);
            getFields(rememberMap, execContext, databaseEntries, database);
        }

        // ===
        // Validate the data matches the database table and field names, regex,
        // mandatory etc.
        // ===
        String errors = validateData(execContext, databaseEntries, database, table);
        if (errors != null) {
            return errors;
        }

        // ===
        // We now have a validated list of parameters that are ready for storage
        // to database.
        // ===

        // ===
        // Sort duplicates if we have any.
        // ===
        List<HttpParam> duplicateParams = findDuplicates(validatedParams);

        // ===
        // prepare the data for saving to database excluding duplicates.
        // ===
        Map<String, Object> map = new HashMap<String, Object>();
        for (HttpParam param : validatedParams) {
            if (param.isActive()) {
                map.put(param.getKey(), param.getValue());
            }
        }
        
        // ===
        // Call pre_processor
        // ===
        for (int i = 1;; i++) {
            String key = ClientParamNames.PRE_PROCESSOR + "." + i;
            CodeProcessor codeProcessor = preCodeProcessor.getProcessorsMap().get(key);
            if (codeProcessor == null) {
                break;
            } else {
                codeProcessor.callCode(execContext);
            }
        }

        SaveToDB saveToDB = new SaveToDB();
        if (duplicateParams.isEmpty() == false) {
            for (HttpParam duplicateParam : duplicateParams) {
                map.put(duplicateParam.getKey(), duplicateParam.getValue());
                if (log.isDebugEnabled()) {
                    log.debug("duplicate param:" + duplicateParam.getKey() + "=" + duplicateParam.getValue());
                }
                String pkValue = saveToDB.saveData(execContext, storageConfig, database, table, map);
                newPk = pkValue;
            }
        } else {
            String pkValue = saveToDB.saveData(execContext, storageConfig, database, table, map);
            newPk = pkValue;
        }

        // ===
        // Call post_processor
        // ===
        for (int i = 1;; i++) {
            String key = ClientParamNames.POST_PROCESSOR + "." + i;
            CodeProcessor codeProcessor = postCodeProcessor.getProcessorsMap().get(key);
            if (codeProcessor == null) {
                break;
            } else {
                codeProcessor.callCode(execContext);
            }
        }
        return "";
    }

//    public String saveData(IExecContext execContext, StorageConfig storageConfig, Database database, Table table,
//            Map<String, Object> map) throws Exception {
//
//        SqlSelectInputs sqlSelectInputs = buildSaveSql(storageConfig, database, table, map, execContext);
//        ISqlSelectBuildQuery build = storageConfig.getSqlBuilder();
//        ISqlTable[] sqlTables = build.buildSaveSqls(execContext, sqlSelectInputs);
//
//        SaveToDB saveToDB = new SaveToDB();
//        Tracer tracer = (Tracer) execContext.get(ConstantsDB.KEY_TRACER_DB_CLASS);
//        String pkValue = saveToDB.save(execContext, storageConfig, sqlSelectInputs, sqlTables, tracer);
//        return pkValue;
//    }
    

    private List<HttpParam> findDuplicates(List<HttpParam> params) {
        
        List<HttpParam> duplicates = new ArrayList<HttpParam>();
        for (int index = 0; index < params.size(); index++) {
            HttpParam param = params.get(index);
            if (param.isActive() == true) {
            
                int found = index + 1;
                
                if (found < params.size()) {
                    boolean processing = true;
                    while (processing) {
                        found = findMatchingHttpParam(params, param, found);
                        if (found > -1) {
                            if (param.isActive()) {
                                duplicates.add(param);
                                param.setActive(false);
                            }
                            HttpParam matchingHttpParam = params.get(found);
                            duplicates.add(matchingHttpParam);
                            matchingHttpParam.setActive(false);
                            if (log.isDebugEnabled()) {
                                log.debug("duplicate" +
                                        " key:" + param.getKey() + " value:" + param.getValue() +
                                        " matches " +
                                        " key:" + matchingHttpParam.getKey() + " value:" + matchingHttpParam.getValue());
                            }
                            found++;
                        } else {
                            processing = false;
                        }
                    }
                }
            }
        }
        return duplicates;
    }

    private int findMatchingHttpParam(List<HttpParam> params, HttpParam param, int startingAt) {
        for (int index = startingAt; index < params.size(); index++) {
            HttpParam searchParam = params.get(index);

            if (searchParam.isActive() && searchParam.getKey().equals(param.getKey())) {
                return index;
            }
        }
        return -1; // not found
    }

    /**
     * Validate the parameters before we try to insert into db. The validated
     * parameters are stored into the validatedMap (declared at top of this
     * source).
     * 
     * @param databaseEntries
     *            these are the request input parameters that may be saved to
     *            database,
     * 
     * @return a string if there are errors, else null
     */
    private String validateData(IExecContext execContext, List<HttpParam> databaseEntries, Database database, Table table) {

    	List<HttpParam> removeThese = new ArrayList<HttpParam>();
    	
        StringBuilder validationErrors = new StringBuilder();

        if (databaseEntries.size() == 0) {
            log.warn("No data has been submitted for saving");
            throw new IllegalArgumentException("No data has been submitted for saving.");
        }

        for (HttpParam param : databaseEntries) {
            Object value = param.getValue();
            String key = param.getKey();
            if (log.isDebugEnabled()) {
                log.debug("find field for key:" + key + " value:" + value);
            }
            CommonStorageField field;
            if (key.indexOf('.') > 0) {
    			try {
    				field = database.getStorageField(key);
    				HttpParam postFormatParam = HttpParam.getHttpParam(databaseEntries, key+".post_format");
    				if (postFormatParam != null) {
                    	value = DateFormatter.formatValue(execContext, (String)value, (String)postFormatParam.getValue());
    				}
    			} catch (Exception ex) {
    				// just ignore this field.
    				continue;
    			}
    		} else {
                if (table.hasField(key)) {
                    field = database.getStorageField(tableName, key);
    				HttpParam postFormatParam = HttpParam.getHttpParam(databaseEntries, key+".post_format");
    				if (postFormatParam != null) {
                    	value = DateFormatter.formatValue(execContext, (String)value, (String)postFormatParam.getValue());
    				}
                    if (field instanceof PK) {
                        log.error("field [" + key + "] in table [" + tableName + "] is PK");
                        throw new IllegalArgumentException("field [" + key + "] in table [" + tableName + "] is PK");
                        // continue;
                    }
                } else {
                    log.error("no field found for key [" + key + "] in table [" + tableName + "].  Item has been removed from List.");
                    // throw new IllegalArgumentException("no field found for key [" + key + "] in table [" + tableName + "]");
                    removeThese.add(param);
                    continue;
                }
            }
            if (log.isDebugEnabled()) {
                log.debug("add field:" + field.getName());
            }
            // BaseStorageField field = table.getField(key);
            if (value instanceof String) {
                String error = null;
                if (error == null && StringUtils.isNotEmpty(field.getRegex())) {
                    error = validateRegex(field.getRegex(), (String) value);
                }
                if (error == null && field.isMandatory() && StringUtils.isEmpty((String) value)) {
                    error = "Is mandatory and must contain a value";
                }
                if (error == null) {
                    error = field.validate((String) value);
                }
                if (StringUtils.isNotEmpty(error)) {
                    if (validationErrors.length() > 0) {
                        validationErrors.append("&");
                    }
                    table.buildTableAndFieldName(field.getName());
                    validationErrors.append(table.buildTableAndFieldName(field.getName()) + "=" + error);
                }
            }
            validatedParams.add(new HttpParam(key, value));
        }
        
        for (HttpParam param : removeThese) {
        	databaseEntries.remove(param);
        }
        if (databaseEntries.size() == 0) {
            log.warn("No data has been submitted for saving");
            throw new IllegalArgumentException("No data has been submitted for saving.");
        }
        if (validationErrors.length() > 0) {
            // throw new IllegalArgumentException(validationErrors);
            if (log.isDebugEnabled()) {
                log.debug("validationErrors[" + validationErrors.toString() + "]");
            }
            return validationErrors.toString();
        }
        return null;// all okay
    }

    /**
     * Capture the relevant data from the incoming parameters.
     * 
     * @param requestVector
     * @return the parameters to be stored into the database
     */
    private List<HttpParam> captureDataFromInputs(IExecContext execContext, List<HttpParam> httpRequestParams) {
    	String pkValue = "";
        List<HttpParam> databaseEntries = new ArrayList<HttpParam>();
        Validate.notNull(httpRequestParams, "Missing [" + PagerWebConst.REQUEST + "] named map from the execContext");
        for (HttpParam param : httpRequestParams) {
            Object value = param.getValue();
            String key = param.getKey();
            if (log.isDebugEnabled()) {
                log.debug("key [" + key + "] value [" + value + "]");
            }
            if (key.equals(ClientParamNames.UNIQUE_ID)) {
            	id = (String) value;
            } else if (key.equals(ClientParamNames.STORAGE_CONFIG_REF)) {
    			storage_config_ref = "" + value;
    		} else if (key.equals(ClientParamNames.TABLE_NAME_MAP_ENTRY)) {
    			tableName = "" + value;
    		} else if (key.equals(ClientParamNames.PK_VALUE)) {
    			pkValue = "" + value;
            } else {
                databaseEntries.add(param);
                execContext.put(key, value);	// need this to inject pk to fk using ${...}
            }
        }
//        Validate.notEmpty(id, "[" + ClientParamNames.UNIQUE_ID + "] not found in ["
//                + PagerWebConst.REQUEST + "] named map from the execContext");
        if (StringUtils.isNotEmpty(id)) {
        	rememberMap = (Map<String, String>)execContext.get(IExecContext.PERSISTENCE_MAP + ":" + id);
        	Validate.notEmpty(rememberMap, "[" + (IExecContext.PERSISTENCE_MAP + ":" + id) + "] not found in session storage");
        	tableName = rememberMap.get(ClientParamNames.TABLE_NAME_MAP_ENTRY);
        	storage_config_ref = rememberMap.get(ClientParamNames.STORAGE_CONFIG_REF);
        }        
        Validate.notEmpty(tableName, "[" + ClientParamNames.TABLE_NAME_MAP_ENTRY + "] not found in ["
                + PagerWebConst.REQUEST + "] named map from the execContext");
        Validate.notEmpty(storage_config_ref, "[" + ClientParamNames.STORAGE_CONFIG_REF + "] not found in ["
                + PagerWebConst.REQUEST + "] named map from the execContext");
        
        
        return databaseEntries;
    }
    
    private void getPrePostProcesses(Map<String, String> map) {
        Set<Entry<String,String>> set = map.entrySet();
        Iterator<Entry<String,String>> iterator = set.iterator();
        while (iterator.hasNext()) {
        	Entry<String,String> entry = iterator.next();
        	String key = entry.getKey();
        	if (key.startsWith(ClientParamNames.PRE_PROCESSOR)) {
        		preCodeProcessor.addProcessorParam(key, entry.getValue());
        	} else if (key.startsWith(ClientParamNames.POST_PROCESSOR)) {
        		postCodeProcessor.addProcessorParam(key, entry.getValue());
        	}
        }
    }

    private void getFields(Map<String, String> map, IExecContext execContext, List<HttpParam> databaseEntries, Database database) {
    	// this includes PK, Concurrency and post_formats
        Set<Entry<String,String>> set = map.entrySet();
        Iterator<Entry<String,String>> iterator = set.iterator();
        while (iterator.hasNext()) {
        	Entry<String,String> entry = iterator.next();
        	String key = entry.getKey();
        	if (key.startsWith(ClientParamNames.TABLE_FIELD_NAME + ".")) {
        		String tfName = key.substring((ClientParamNames.TABLE_FIELD_NAME + ".").length());
        		if (! isVersionNo(database, tfName)) {	// dont add version no.s
        			databaseEntries.add(new HttpParam(tfName, entry.getValue()));
        		}
                execContext.put(tfName, entry.getValue());	// need this to inject pk to fk using ${...}
        	} else if (key.equals(ClientParamNames.ENFORCE_CONCURRENCY)){
                execContext.put(ClientParamNames.ENFORCE_CONCURRENCY, Boolean.parseBoolean(entry.getValue()));	// need this to inject pk to fk using ${...}
        	}
        }
    }
    
    private boolean isVersionNo(Database database, String tfName) {
    	String tableName = Table.getTableName(tfName);
    	String fieldName = Table.getFieldName(tfName);
    	Table table = database.getTable(tableName);
    	if (table.getUpdate_field_version_num() != null && table.getUpdate_field_version_num().equals(fieldName)) {
    		return true;
    	}
    	return false;
    }


//    private SqlSelectInputs buildSaveSql(StorageConfig storageConfig, Database database, Table table,
//            Map<String, Object> map, IExecContext execContext) {
//
//        List<SqlField> sqlFields = new ArrayList<SqlField>();
//        for (String key : map.keySet()) {
//            // sqlFields.add(new SqlField(map.get(key)));
//            SqlField sqlField = new SqlField(key);
//            sqlField.setValue(map.get(key));
//            sqlFields.add(sqlField);
//        }
//
//        SqlSelectInputs sqlSelectInputs = BuildSelect.buildSelect(storageConfig.getStorageContainer().getStorage(),
//                database.getName(),
//                table.getName(),
//                sqlFields,
//                storageConfig.getDbSpecificName());
//        return sqlSelectInputs;
//
//    }

    private String validateRegex(String regex, String value) {

        try {
            boolean match = value.matches(regex);
            if (match == true) {
                return null;
            }
        } catch (Exception ex) {
            return "Regex failure [" + regex + "] is an invalid regular expression.  Matching against[" + value + "]";
        }
        return "The value [" + value + "] fails Regex match of [" + regex + "]";
    }
}
