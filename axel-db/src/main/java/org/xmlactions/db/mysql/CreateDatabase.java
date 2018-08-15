
package org.xmlactions.db.mysql;


import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.xmlactions.db.DBSQL;
import org.xmlactions.db.actions.Database;
import org.xmlactions.db.actions.Table;
import org.xmlactions.db.exception.DBSQLException;
import org.xmlactions.db.template.ICreateDatabase;



public class CreateDatabase implements ICreateDatabase
{

	public boolean exists(Connection connection, String dbName) throws SQLException
	{

		boolean exists = false;
		DatabaseMetaData dbm = connection.getMetaData();
		ResultSet rs = dbm.getCatalogs();
		while (rs.next()) {
			String name = rs.getString(1);
			if (name.equalsIgnoreCase(dbName)) {
				exists = true;
				break;
			}
		}
		return exists;
	}

    public int createDatabase(Connection connection, Database database) throws DBSQLException, SQLException
	{

		String sql = connection.nativeSQL("create database " + database.getName() + "");
		int result = DBSQL.insert(connection, sql);

		CreateTable createTable = new CreateTable();
		for (Table table : database.getTables()) {
			createTable.createTable(connection, database.getName(), table);
		}
		return result;
	}

    public int createDatabase(Connection connection, String dbName) throws DBSQLException, SQLException
	{

		String sql = connection.nativeSQL("create database " + dbName + "");
		int result = DBSQL.insert(connection, sql);
		return result;
	}

    public int dropDatabase(Connection connection, String dbName) throws SQLException, DBSQLException
	{

		String sql = connection.nativeSQL("drop database " + dbName + "");
		int result = DBSQL.insert(connection, sql);
		return result;
	}

}
