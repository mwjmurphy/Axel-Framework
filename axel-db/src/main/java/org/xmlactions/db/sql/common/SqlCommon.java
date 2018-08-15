package org.xmlactions.db.sql.common;


import java.util.HashMap;
import java.util.Map;


import org.apache.commons.lang.Validate;
import org.apache.commons.lang.text.StrSubstitutor;
import org.xmlactions.db.actions.Database;
import org.xmlactions.db.actions.Function;
import org.xmlactions.db.actions.Table;

public class SqlCommon {

	/**
	 * Replaces ${p1} with 'table.name'
	 * @param database
	 * @param dbSpecificName
	 * @param function_ref
	 * @param fieldName
	 * @return
	 */
	public static String replaceForSqlFunction(Database database, String dbSpecificName, String function_ref, String fieldName){
		Function function = database.getFunction(dbSpecificName, function_ref);
        Validate.notNull(function, "Function for function_ref [" + function_ref + "] not found in Database [" + database.getName() + "]");
		Map<String, String> map = new HashMap<String,String>();
		map.put("p1", fieldName);
		return StrSubstitutor.replace(function.getSql(), map);
	}
}
