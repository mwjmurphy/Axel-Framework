
package org.xmlactions.pager.actions.db;


import java.util.ArrayList;
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
import org.xmlactions.db.StorageContainer;
import org.xmlactions.db.actions.CommonStorageField;
import org.xmlactions.db.actions.Database;
import org.xmlactions.db.actions.PK;
import org.xmlactions.db.actions.Storage;
import org.xmlactions.db.actions.Table;
import org.xmlactions.db.config.StorageConfig;
import org.xmlactions.db.sql.BuildSelect;
import org.xmlactions.db.sql.select.ISqlSelectBuildQuery;
import org.xmlactions.db.sql.select.ISqlTable;
import org.xmlactions.db.sql.select.SqlField;
import org.xmlactions.db.sql.select.SqlSelectInputs;
import org.xmlactions.db.sql.select.SqlTable;
import org.xmlactions.db.update.UpdateToDB;
import org.xmlactions.pager.actions.dates.DateFormatter;
import org.xmlactions.pager.actions.form.ClientParamNames;
import org.xmlactions.pager.actions.form.processor.CodeProcessor;
import org.xmlactions.pager.actions.form.processor.PostCodeProcessor;
import org.xmlactions.pager.actions.form.processor.PreCodeProcessor;
import org.xmlactions.web.HttpParam;
import org.xmlactions.web.PagerWebConst;


/**
 * Inserts a new record into a table.
 * <p>
 * The parameters are contained in the execContext named map 'request'. These
 * are retrieved from the http request.
 * </p>
 * <p>
 * One of the parameters must contain the table name. This is found in the
 * request map using the key 'table.name'
 * </p>
 * <p>
 * There must be at least one parameter to insert.
 * </p>
 */
public class UpdateRecord extends BaseAction
{

	private final static Logger log = LoggerFactory.getLogger(UpdateRecord.class);

    private PreCodeProcessor preCodeProcessor = new PreCodeProcessor();
    private PostCodeProcessor postCodeProcessor = new PostCodeProcessor();

    public String execute(IExecContext execContext) throws Exception
	{

		String result = "OK";
		try {
			result = updateRecord(execContext);
			if (StringUtils.isEmpty(result)) {
				result = "OK:";
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

	public PreCodeProcessor getPreCodeProcessor() {
		return preCodeProcessor;
	}

	public PostCodeProcessor getPostCodeProcessor() {
		return postCodeProcessor;
	}

	private String updateRecord(IExecContext execContext) throws Exception
	{
		
		List<HttpParam> params = (List<HttpParam>)execContext.get(PagerWebConst.REQUEST_LIST);

		Validate.notNull(params, "Missing [" + PagerWebConst.REQUEST_LIST + "] from the execContext");
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> validatedMap = new Hashtable<String, Object>();
		String storage_config_ref = null;
		String tableName = null;
		String pkValue = null;
		for (HttpParam param : params) {
			String key = param.getKey();
			String value = null;
			Object object = param.getValue();
			log.debug("key [" + key  + "] value [" + object + "]");
			if (object instanceof String[]) {
				String [] values = (String[])object;
				if (values.length == 1 ) {
					value = values[0];
				} else if (values.length > 1 ) {
					map.put(key, values);
				} else {
					map.put(key,object);
				}
			} else if (object instanceof String){
				value = (String)object;
			} else {
				value = (String)object;
			}
			if (key.equals(ClientParamNames.STORAGE_CONFIG_REF)) {
				storage_config_ref = value;
			} else if (key.equals(ClientParamNames.TABLE_NAME_MAP_ENTRY)) {
				tableName = value;
			} else if (key.equals(ClientParamNames.PK_VALUE)) {
				pkValue = value;
            } else if (key.startsWith(ClientParamNames.PRE_PROCESSOR)) {
            	preCodeProcessor.addProcessorParam(key, value);
            } else if (key.startsWith(ClientParamNames.POST_PROCESSOR)) {
            	postCodeProcessor.addProcessorParam(key, value);
			} else if (value != null){
				map.put(key, value);
			}
		}
		Validate.notEmpty(map, "No fields have been submitted from the browser request for saving.");
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

		// validate the data before we try to insert into db
		StringBuilder validationErrors = new StringBuilder();
        if (map.keySet().size() == 0) {
            log.warn("No data has been submitted for saving");
            throw new IllegalArgumentException("No data has been submitted for saving.");
        }
		for (String key : map.keySet()) {
			Object value = map.get(key);
			CommonStorageField field;
			try {
				if (Table.isTableAndFieldName(key)) {
					field = database.getStorageField(key);
				} else {
					field = table.getStorageField(key);
				}
			} catch (Exception ex) {
				// just ignore this field.
				continue;
			}
			// BaseStorageField field = table.getField(key);
			HttpParam postFormatParam = HttpParam.getHttpParam(params, key+".post_format");
			if (postFormatParam != null) {
            	value = DateFormatter.formatValue(execContext, (String)value, (String)postFormatParam.getValue());
			}

			if (value instanceof String) {
				String error = null;
                if (StringUtils.isNotEmpty(field.getRegex())) {
                    error = validateRegex(field.getRegex(), (String)value);
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
                } else {
					error = field.validate((String)value);
					if (!StringUtils.isEmpty(error)) {
						if (validationErrors.length() > 0) {
							validationErrors.append("&");
						}
						table.buildTableAndFieldName(field.getName());
						validationErrors.append(table.buildTableAndFieldName(field.getName()) + "=" + error);
					}
                }
			}
			// value = validateField(field, value);
			validatedMap.put(key, value);
		}
		if (validationErrors.length() > 0) {
			// throw new IllegalArgumentException(validationErrors);
			log.debug("validationErrors[" + validationErrors.toString() + "]");
			return validationErrors.toString();
		}

		//String whereClause = "where " + table.getPk().getName() + " = " + pkValue;
		//String sql = DBWriteUtils.buildUpdateTableRow(tableName, validatedMap, whereClause);

        // ===
        // Call pre_processor
        // ===
		for (int i = 1; ;i++) {
			String key = ClientParamNames.PRE_PROCESSOR + "." + i;
			CodeProcessor codeProcessor = preCodeProcessor.getProcessorsMap().get(key);
			if (codeProcessor == null) {
				break;
			} else {
				codeProcessor.callCode(execContext);
			}
		}

		String whereClause = null;
		
//		if (StringUtils.isNotEmpty(table.getAlias())) {
//			whereClause = table.getAlias() + Table.TABLE_FIELD_SEPERATOR  + table.getPk().getName() + " = " + pkValue;
//		} else {
//			whereClause = table.getName() + Table.TABLE_FIELD_SEPERATOR  + table.getPk().getName() + " = " + pkValue;
//		}

		SqlSelectInputs sqlSelectInputs = buildUpdateSqls(storageConfig, database, table,	validatedMap,	execContext, whereClause);
		// add where clauses for each table.
		String errors = addWhereClauses(execContext, sqlSelectInputs, validatedMap);
		Validate.notNull(errors, errors);
		ISqlSelectBuildQuery build = storageConfig.getSqlBuilder();
		ISqlTable[] sqlTables = build.buildUpdateSqls(execContext, sqlSelectInputs);

		UpdateToDB updateToDB = new UpdateToDB();
        Tracer tracer = (Tracer) execContext.get(ConstantsDB.KEY_TRACER_DB_CLASS);
		updateToDB.update(execContext, storageConfig, sqlSelectInputs, sqlTables, tracer);

        // ===
        // Call post_processor
        // ===
		for (int i = 1; ;i++) {
			
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

	/**
	 * When we are updating each table must have a value for it's PK field
	 * @param execContext
	 * @param sqlSelectInputs
	 */
	private String addWhereClauses(IExecContext execContext, SqlSelectInputs sqlSelectInputs, Map<String, Object> requestParameters) {
		ISqlTable [] tables = sqlSelectInputs.getSqlTables();
		StringBuilder sb = new StringBuilder();
		for (ISqlTable iSqlTable : tables) {
			if (iSqlTable instanceof SqlTable) {
				SqlTable sqlTable = (SqlTable)iSqlTable;
				// must find a PK
				boolean foundPk = false;
				for (SqlField sqlField : sqlTable.getFields()) {
					CommonStorageField storageField = sqlField.getCommonStorageField();
					if (storageField instanceof PK) {
						log.debug("PK:" + iSqlTable.getTableName() + "." + storageField.getName());
						String value = getValueForPk(sqlField, requestParameters);
						if (value == null) {
							sb.append("UpdateRecord: Missing Primary Key Value for [" + sqlTable.getTableName() + "." + sqlField.getFieldName() + "]. Cannot update record for this table.");
						} else {
							sqlField.setValue(value);
							log.debug("addWhereClause " + sqlTable.getTableAliasOrName() + "." + sqlField.getAliasOrFieldName() + " = " + value);
						}
					}
				}
				if (foundPk == false) {
					sb.append("UpdateRecord: Missing Primary Key Field for [" + sqlTable.getTableName() + "]. Cannot update record for this table.");
				}
			}
		}
		if (sb.length() > 0) {
			return sb.toString();
		} else {
			return null;
		}
	}
	
	private String getValueForPk(SqlField sqlField, Map<String, Object>map) {
		Object value = map.get(sqlField.getFieldName());
		if (value == null) {
			value = map.get(sqlField.getFieldAlias());
		}
		if (value == null) {
			return null;
		}
		return value.toString();
	}

	private SqlSelectInputs buildUpdateSqls(StorageConfig storageConfig, Database database, Table table, Map<String, Object> map, IExecContext execContext, String whereClause) {

		List<SqlField> sqlFields = new ArrayList<SqlField>();
		for (String key : map.keySet()) {
			//sqlFields.add(new SqlField(map.get(key)));
			SqlField sqlField = new SqlField(key);
			sqlField.setValue(map.get(key));
			sqlFields.add(sqlField);
		}

		SqlSelectInputs sqlSelectInputs = BuildSelect.buildSelect(storageConfig.getStorageContainer().getStorage(), database.getName(), table.getName(), sqlFields, storageConfig.getDbSpecificName());
		sqlSelectInputs.addWhereClause(whereClause);
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

} 
    

