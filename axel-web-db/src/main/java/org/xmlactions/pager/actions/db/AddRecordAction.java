package org.xmlactions.pager.actions.db;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmlactions.action.actions.BaseAction;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.common.tracer.Tracer;
import org.xmlactions.db.ConstantsDB;
import org.xmlactions.db.DBConfigException;
import org.xmlactions.db.StorageContainer;
import org.xmlactions.db.actions.CommonStorageField;
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
import org.xmlactions.pager.actions.form.CommonFormFields;
import org.xmlactions.pager.actions.form.Field;
import org.xmlactions.pager.actions.form.FieldHide;
import org.xmlactions.pager.actions.form.FieldList;
import org.xmlactions.web.HttpParam;

/**
 * Adds a new record/s to the database.
 * <p>
 * Will return the pk value of the new record.
 * </p>
 * 
 * @author mike.murphy
 * 
 */
public class AddRecordAction extends CommonFormFields
{

    private final static Logger log = LoggerFactory.getLogger(AddRecordAction.class);

	private FieldList field_list;

    private List<HttpParam> validatedParams = new ArrayList<HttpParam>();

	private StorageConfig storageConfig;

	private Database database;

	private Table table;
	
	/**
	 * If this is set then the pk value will be stored into the execContext
	 * using this key.
	 */
	private String key;

    public String execute(IExecContext execContext) throws Exception {

        validate(execContext, "pager:add_record");

        String result = null;
    	try {
            result = addRecord(execContext);
	    } catch (IllegalArgumentException ex) {
	        throw ex;
	    } catch (DBConfigException ex) {
	        throw new IllegalArgumentException(ex.getMessage(), ex);
	    } catch (Exception ex) {
	        throw new IllegalArgumentException(ex.getMessage(), ex);
	    }

	    if (StringUtils.isNotEmpty(result) && StringUtils.isNotEmpty(getKey())) {
	    	execContext.put(key, result);
	    }
        return "";
    }
    
    private class StorageFieldWithValue {
    	CommonStorageField storageField;
    	Object value;
    	private StorageFieldWithValue(CommonStorageField storageField, Object value) {
    		this.storageField = storageField;
    		this.value = value;
    	}
    	private String getKey() {
    		Table table = (Table) storageField.getParent();
    		return table.getAlias() + Table.TABLE_FIELD_SEPERATOR + storageField.getName();
    	}
    }

    private String addRecord(IExecContext execContext) throws DBConfigException, Exception {
    	
        storageConfig = (StorageConfig) execContext.get(getStorage_config_ref(execContext));
		Validate.notNull(storageConfig, "No [" + StorageConfig.class.getName() + "] found in ExecContext ["
                + getStorage_config_ref(execContext) + "]");
		StorageContainer storageContainer = storageConfig.getStorageContainer();
		Storage storage = storageContainer.getStorage();
	
		database = storage.getDatabase(storageConfig.getDatabaseName());
	
		table = database.getTable(getTable_name());
	
		List<StorageFieldWithValue>list = buildFieldList(execContext, storageConfig, database, table);
		
		String result =  validateData(list, database, table);
		if (StringUtils.isNotEmpty(result)) {
			throw new IllegalArgumentException(result);
		}
		
		Map<String,Object>map = new HashMap<String, Object>();
		for (StorageFieldWithValue param : list) {
			map.put(param.getKey(), param.value);
		}
        String pkValue = saveData(execContext, storageConfig, database, table, map);
		
		return pkValue;
    }
    
    private List<StorageFieldWithValue> buildFieldList(IExecContext execContext, StorageConfig storageConfig, Database database, Table table) throws DBConfigException {
    	List<StorageFieldWithValue>list = new ArrayList<StorageFieldWithValue>();
    	if (getField_list() != null) {
            for (BaseAction baseAction : getField_list().getFields()) {
            	CommonStorageField storageField = null;
                if (baseAction instanceof Field) {

                	Field field = (Field) baseAction;
                    if (Table.isTableAndFieldName(field.getName())) {
                        storageField = database.getStorageField(field.getName());
                    } else {
                        storageField = table.getFieldFromTableAndFieldName(Table.buildTableAndFieldName(getTable_name(), field.getName()));
                    }
                    
                } else if (baseAction instanceof FieldHide) {
               	
                    Field field = (Field) baseAction;
                    if (Table.isTableAndFieldName(field.getName())) {
                        storageField = database.getStorageField(field.getName());
                    } else {
                        storageField = table.getFieldFromTableAndFieldName(Table.buildTableAndFieldName(getTable_name(), field.getName()));
                    }
                }
                if (storageField != null) {
                	Table parentTable = (Table) storageField.getParent();
                	Object value = execContext.replace(baseAction.getContent());
                	list.add(new StorageFieldWithValue(storageField, value));
                }
            }
    	}    	
    	return list;
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
    private String validateData(List<StorageFieldWithValue> list, Database database, Table table) {

        StringBuilder validationErrors = new StringBuilder();

        if (list.size() == 0) {
            log.warn("No data has been submitted for saving");
            throw new IllegalArgumentException("No data has been submitted for saving.");
        }

        for (StorageFieldWithValue param : list) {
        	CommonStorageField field = param.storageField;
            Object value = param.value;
            String key = param.getKey();
            if (log.isDebugEnabled()) {
                log.debug("find field for key:" + key + " value:" + value);
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
        if (validationErrors.length() > 0) {
            // throw new IllegalArgumentException(validationErrors);
            if (log.isDebugEnabled()) {
                log.debug("validationErrors[" + validationErrors.toString() + "]");
            }
            return validationErrors.toString();
        }

        return null;// all okay
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

	public void setField_list(FieldList field_list) {
		this.field_list = field_list;
	}

	public FieldList getField_list() {
		return field_list;
	}

    public void validate(IExecContext execContext, String errMsg)
	{
        Validate.notEmpty(getStorage_config_ref(execContext), "storage_config_ref has not been set - " + errMsg);
		Validate.notEmpty(getTable_name(), "table_name has not been set - " + errMsg);
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}

}
