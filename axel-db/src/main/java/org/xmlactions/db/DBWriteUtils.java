
package org.xmlactions.db;


import java.util.Hashtable;
import java.util.Map;

import org.xmlactions.db.actions.Database;
import org.xmlactions.db.actions.Table;



/**
 * Utility for build write to database sql.
 * 
 * @author mike
 * 
 */
public class DBWriteUtils
{

	/**
	 * Select a table row from the database
	 * 
	 * @param tableName
	 *            is the name of the table that we want to update.
	 * @param keyPairs
	 *            is the values we want to update in the table
	 * @param whereClause
	 *            will select the row (maybe rows) to be updated.
	 */
	public static String buildSelectTableRow(String tableName, String whereClause)
	{

		StringBuilder sb = new StringBuilder();
		sb.append("select count(*) from " + tableName);
		sb.append(" " + whereClause);
		return (sb.toString());
	}

	/**
	 * @deprecated - may not be suitable for all databases.  Use the StorageConfig.getDBSqlManager
	 * Update a table row in the database
	 * 
	 * @param tableName
	 *            is the name of the table that we want to update.
	 * @param keyPairs
	 *            is the values we want to update in the table
	 * @param whereClause
	 *            will select the row (maybe rows) to be updated.
	 */
	public static String buildUpdateTableRow(String tableName, Map<String, String> params, String whereClause)
	{

		StringBuilder sb = new StringBuilder();
		sb.append("update " + tableName + " set ");
		boolean addComma = false;

		for (String key : params.keySet()) {
			String value = params.get(key);
			if (addComma == true) {
				sb.append(',');
			}
			sb.append(key);
			sb.append("='" + DBSQL.escCharacters(value) + "'");
			addComma = true;

		}
		sb.append(" " + whereClause);
		return (sb.toString());
	}

	/**
	 * Delete a table row in the database
	 * 
	 * @param tableName
	 *            is the name of the table that we want to update.
	 * @param whereClause
	 *            will select the row (maybe rows) to be updated.
	 */
	public static String buildDeleteTableRow(String databaseName, String tableName, String whereClause)
	{

		StringBuilder sb = new StringBuilder();
		sb.append("delete from " + databaseName + "." + tableName + " " + whereClause);
		return (sb.toString());
	}

    /**
     * @deprecated replaced with buildAddTableRow(Database database, Table
     *             table, Map<String, String> params) Insert a table row in the
     *             database
     * 
     * @param databaseName
     * @param tableName
     *            is the name of the table that we want to update.
     * @param keyPairs
     *            is the values we want to update in the table
     * @param whereClause
     *            will select the row (maybe rows) to be updated.
     */
	public static String buildAddTableRow(String databaseName, String tableName, Map<String, String> params)
	{

		StringBuilder sb = new StringBuilder();
		sb.append("insert into " + databaseName + "." + tableName + " (");
		boolean addComma = false;
		for (String key : params.keySet()) {
			if (addComma == true) {
				sb.append(',');
			}
			sb.append(key);
			addComma = true;
		}
		sb.append(") values (");
		addComma = false;
		for (String key : params.keySet()) {
			String value = (String) params.get(key);
			if (addComma == true) {
				sb.append(',');
			}
			sb.append("'" + DBSQL.escCharacters(value) + "'");
			addComma = true;
		}
		sb.append(")");
		return (sb.toString());
	}

    /**
     * 
     * @param database
     * @param table
     *            is the table that we want to update.
     * @param keyPairs
     *            is the values we want to update in the table
     * @param whereClause
     *            will select the row (maybe rows) to be updated.
     */
    public static String buildAddTableRow(Database database, Table table, Map<String, String> params) {

        StringBuilder sb = new StringBuilder();
        //sb.append("insert into " + database.getName() + "." + table.getName() + " " + table.getAlias() + " (");
        sb.append("insert into " + database.getName() + "." + table.getName() + " (");
        boolean addComma = false;
        for (String key : params.keySet()) {
            if (addComma == true) {
                sb.append(',');
            }
            sb.append(key);
            addComma = true;
        }
        sb.append(") values (");
        addComma = false;
        for (String key : params.keySet()) {
            String value = (String) params.get(key);
            if (addComma == true) {
                sb.append(',');
            }
            sb.append("'" + DBSQL.escCharacters(value) + "'");
            addComma = true;
        }
        sb.append(")");
        return (sb.toString());
    }
	/**
	 * Login by querying a db with a map of key-pairs and returing the pk
	 * 
	 * @param tableName
	 *            is the name of the table that we want to query.
	 * @param keyPairs
	 *            is the values we want to query in the table
	 * @param pk
	 *            is the field (most likely primary key) we want returned for the query.
	 */
	public static String buildLogin(String tableName, Hashtable<String, String> params, String pk)
	{

		StringBuilder sb = new StringBuilder();
		sb.append("select " + pk + " from " + tableName);
		boolean addComma = false;

		for (String key : params.keySet()) {
			String value = params.get(key);
			if (addComma == true) {
				sb.append(" and ");
			} else {
				sb.append(" where ");
			}
			sb.append(key);
			sb.append("='" + DBSQL.escCharacters(value) + "'");
			addComma = true;

		}
		return (sb.toString());
	}
}
