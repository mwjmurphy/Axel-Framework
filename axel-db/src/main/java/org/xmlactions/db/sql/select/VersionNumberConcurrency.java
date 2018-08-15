package org.xmlactions.db.sql.select;

import org.xmlactions.action.config.IExecContext;
import org.xmlactions.common.collections.PropertyKeyValue;
import org.xmlactions.db.actions.Database;
import org.xmlactions.db.actions.Table;

/**
 * This class manages concurrency clashes when two or more edits are performed on the same record.
 * 
 * If 2 users read the record, 1 makes a change and submits then the 2nd user cannot submit any
 * changes until they update their record to reflect the changes made by the first user.
 * 
 * @author mike.murphy
 *
 */
public class VersionNumberConcurrency {
	
	
	public static final String VERSION_NUM_KEY_NAME="ver_num";
	

    public static PropertyKeyValue getVersionNumber(IExecContext execContext, ISqlTable sqlTable) {
		PropertyKeyValue propertyKeyValue = null;

    	if (sqlTable.getTable() != null && sqlTable.getTable().getParent() != null) {
    		Table table = sqlTable.getTable();
    		if (table.getUpdate_field_version_num() != null) {
    			String key = table.getAlias() + "." + table.getUpdate_field_version_num();
//    			String versionNumberKey = buildVersionNumberKey(key);
//    			String value = execContext.getString(versionNumberKey);
    			String value = execContext.getString(key);
    			propertyKeyValue = new PropertyKeyValue(key,  value);
    		} else {
    			Database database = (Database)table.getParent();
        		if (database.getUpdate_field_version_num() != null) {
        			String key = table.getAlias() + "." + database.getUpdate_field_version_num();
//        			String versionNumberKey = buildVersionNumberKey(key);
//        			String value = execContext.getString(versionNumberKey);
        			String value = execContext.getString(key);
        			propertyKeyValue = new PropertyKeyValue(key,  value);
        		}    			
    		}
    	}
    	return propertyKeyValue;
    }

	/**
	 * This static method is used to build a unique identifier for the version number key in the session persistence execContext.
	 * @param key the table name/alias + . + field name/alias.
	 * @return PERSISTENCE_MAP + ":ver_num." + key
	 */
//	public static String buildVersionNumberKey(String key) {
//		return VERSION_NUM_KEY_NAME + "." + key;
//	}

	/**
	 * This static method is used to build a unique identifier for the version number key in the session persistence execContext.
	 * @param key the table name/alias + . + field name/alias.
	 * @param pkValue a reference to a PK value for this versionNumber.
	 * @return "ver_num." + key (+ "." + pkValue)
	 */
	public static String buildVersionNumberKey(String key) {
		return VERSION_NUM_KEY_NAME + "." + key;
	}

	/**
	 * This static method is used to build a unique identifier for the version number key in the session persistence execContext.
	 * @param key the table name/alias + . + field name/alias.
	 * @param pkValue a reference to a PK value for this versionNumber.
	 * @return "ver_num." + key + "." + pkValue
	 */
	public static String buildVersionNumberKeyWithPkValue(String key, String pkValue) {
		return VERSION_NUM_KEY_NAME + "." + key + (pkValue != null ? "." + pkValue : "");
	}


}
