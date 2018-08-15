
package org.xmlactions.db.template;


import java.sql.Connection;
import java.sql.SQLException;

import org.xmlactions.db.actions.Table;
import org.xmlactions.db.exception.DBSQLException;



public interface ICreateTable
{

    /**
     * 
     * @param connection
     *            to the database
     * @param dbName
     *            the name of the database we want to create the table in
     * @param table
     *            the table we want to create
     * @return 1 if table created, not 1 for error (never happens as
     *         SQLException is thrown)
     * @throws DBSQLException
     * @throws SQLException
     */
    public int createTable(Connection connection, String dbName, Table table) throws DBSQLException, SQLException;

    /**
     * 
     * @param connection
     *            to the database
     * @param dbName
     *            the name of the database we want to create the table in
     * @param tbName
     *            the name of the table we want to check exists
     * @return 0 if table dropped
     * @throws DBSQLException
     * @throws SQLException
     */
    public int dropTable(Connection connection, String dbName, String tbName) throws DBSQLException, SQLException;

	/**
	 * 
	 * @param connection
	 *            to the database
	 * @param dbName
	 *            the name of the database we want to create the table in
	 * @param tbName
	 *            the name of the table we want to check exists
	 * @return true if table exists
	 * @throws SQLException
	 */
	public boolean exists(Connection connection, String dbName, String tbName) throws SQLException;
}
