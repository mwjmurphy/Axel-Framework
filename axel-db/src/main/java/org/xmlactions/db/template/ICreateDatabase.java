
package org.xmlactions.db.template;


import java.sql.Connection;
import java.sql.SQLException;

import org.xmlactions.db.actions.Database;
import org.xmlactions.db.exception.DBSQLException;



public interface ICreateDatabase
{

    public int createDatabase(Connection connection, Database database) throws DBSQLException, SQLException;

    public int createDatabase(Connection connection, String dbName) throws DBSQLException, SQLException;

	public int dropDatabase(Connection connection, String dbName) throws DBSQLException, SQLException;

	public boolean exists(Connection connection, String dbName) throws DBSQLException, SQLException;
}
