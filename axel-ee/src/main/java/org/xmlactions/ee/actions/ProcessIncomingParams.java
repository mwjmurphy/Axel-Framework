package org.xmlactions.ee.actions;

import java.util.List;

import org.xmlactions.action.config.IExecContext;
import org.xmlactions.db.actions.Database;
import org.xmlactions.db.actions.Storage;
import org.xmlactions.web.HttpParam;

/**
 * This class will map incoming params into a table entity model.
 * 
 * Examples of incoming data
 * 
 * table name = primary table
 * tablename1.fieldname = value
 * tablename1.fieldname = value
 * tablename2.fieldname = value
 * tablename3.fieldname = value
 * 
 * This uses the storage xsd to define the table names and the beans that match the tables.
 * 
 * When mapping the incoming params to the ORM a storage configuration is required.  The
 * storage configuration knows which bean maps to which table.
 * 
 * @author mike.murphy
 *
 */
public class ProcessIncomingParams {
	
	private static final String TABLE_NAME = "table_name";

	/**
	 * Must have table_name in the paramList for the primary table.  All others stem from here.  table_name is
	 * not case sensitive it could be Table_name.
	 * 
	 * @param storage
	 * @param paramList
	 * @param execContext
	 */
	public void processRequest(Database database, List<HttpParam> paramList, IExecContext execContext) {
		String tableName = findTableNameQuietly(paramList);
		if (tableName == null) {
			throw new IllegalArgumentException("The key [" + TABLE_NAME + "] is not found in paramList");
		}
		
	}
	
	
	/**
	 * Finds the table name in the paramList
	 * @param paramList
	 * @return the table name or null if not found
	 */
	private String findTableNameQuietly(List<HttpParam> paramList){
		
		for (HttpParam param : paramList) {
			if (param.getKey().toLowerCase().equals(TABLE_NAME)) {
				return (String) param.getValue();
			}
		}
		return null; 
		
	}
}
