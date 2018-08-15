
package org.xmlactions.db.mysql;


import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.NamingException;

import junit.framework.TestCase;

import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.xmlactions.action.NestedActionException;
import org.xmlactions.common.system.JS;
import org.xmlactions.common.xml.BadXMLException;
import org.xmlactions.db.DBConnector;
import org.xmlactions.db.actions.Database;
import org.xmlactions.db.actions.Storage;
import org.xmlactions.db.actions.Table;
import org.xmlactions.db.exception.DBSQLException;
import org.xmlactions.db.mysql.CreateDatabase;
import org.xmlactions.db.mysql.CreateTable;


public class CreateTableTest extends TestCase
{

	private final static Logger log = LoggerFactory.getLogger(CreateTableTest.class);

	public void testRun()
			throws IOException, SQLException, ClassNotFoundException, NamingException, NestedActionException,
			InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException,
			BadXMLException, DBSQLException
	{

		createDatabase();
		createTables();
		existsTable();
		dropTable();
		tableDropped();
		dropDatabase();
	}

	public void createDatabase()
			throws IOException, SQLException, ClassNotFoundException, NamingException, NestedActionException,
			InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException,
			BadXMLException, DBSQLException
	{

		if ("mike-laptop".equalsIgnoreCase(JS.getEnv("USERDOMAIN"))) {

			Connection connection = CreateStorageTest.getConnection();

			Storage storage = (Storage) CreateStorageTest.getActions()[0];
			Database database = storage.getDatabases().get(0);

			CreateDatabase createDatabase = new CreateDatabase();
			int result = createDatabase.createDatabase(connection, database.getName());
			assertTrue("Failed to create table [" + database.getName() + "]", result >= 0);

			DBConnector.closeQuietly(connection);
		}
	}

	public void createTables()
			throws IOException, SQLException, ClassNotFoundException, NamingException, NestedActionException,
			InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException,
			BadXMLException, DBSQLException
	{

		if ("mike-laptop".equalsIgnoreCase(JS.getEnv("USERDOMAIN"))) {

			Connection connection = CreateStorageTest.getConnection();

			Storage storage = (Storage) CreateStorageTest.getActions()[0];
			Database database = storage.getDatabases().get(0);

			for (Table table : database.getTables()) {

				CreateTable createTable = new CreateTable();
				int result = createTable.createTable(connection, database.getName(), table);
				assertEquals("Failed to create table [" + table.getName() + "]", 0, result);
			}

			DBConnector.closeQuietly(connection);
		}
	}

	public void existsTable()
			throws IOException, SQLException, ClassNotFoundException, NamingException, NestedActionException,
			InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException,
			BadXMLException
	{

		if ("mike-laptop".equalsIgnoreCase(JS.getEnv("USERDOMAIN"))) {
			Connection connection = CreateStorageTest.getConnection();

			Storage storage = (Storage) CreateStorageTest.getActions()[0];
			Database database = storage.getDatabases().get(0);

			for (Table table : database.getTables()) {

				CreateTable createTable = new CreateTable();
				boolean result = createTable.exists(connection, database.getName(), table.getName());
				assertEquals("Table should exist [" + database.getName() + "." + table.getName() + "]", true, result);
			}

			DBConnector.closeQuietly(connection);
		}
	}

	public void dropTable()
			throws IOException, SQLException, ClassNotFoundException, NamingException, NestedActionException,
			InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException,
			BadXMLException, DBSQLException
	{

		if ("mike-laptop".equalsIgnoreCase(JS.getEnv("USERDOMAIN"))) {

			Connection connection = CreateStorageTest.getConnection();

			Storage storage = (Storage) CreateStorageTest.getActions()[0];
			Database database = storage.getDatabases().get(0);

			for (Table table : database.getTables()) {

				CreateTable createTable = new CreateTable();
				int result = createTable.dropTable(connection, database.getName(), table.getName());
				log.debug(table.getName() + " exists:" + result);
				assertEquals("Table should exist [" + database.getName() + "." + table.getName() + "]", 0, result);
			}

			DBConnector.closeQuietly(connection);
		}
	}

	public void tableDropped()
			throws IOException, SQLException, ClassNotFoundException, NamingException, NestedActionException,
			InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException,
			BadXMLException
	{

		if ("mike-laptop".equalsIgnoreCase(JS.getEnv("USERDOMAIN"))) {
			Connection connection = CreateStorageTest.getConnection();

			Storage storage = (Storage) CreateStorageTest.getActions()[0];
			Database database = storage.getDatabases().get(0);

			for (Table table : database.getTables()) {

				CreateTable createTable = new CreateTable();
				boolean result = createTable.exists(connection, database.getName(), table.getName());
				log.debug("result:" + result);
				assertEquals("Failed table still exists [" + database.getName() + "." + table.getName() + "]", false,
						result);
			}

			DBConnector.closeQuietly(connection);
		}
	}

	public void dropDatabase()
			throws IOException, SQLException, ClassNotFoundException, NamingException, NestedActionException,
			InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException,
			BadXMLException, DBSQLException
	{

		if ("mike-laptop".equalsIgnoreCase(JS.getEnv("USERDOMAIN"))) {
			Storage storage = (Storage) CreateStorageTest.getActions()[0];
			Database database = storage.getDatabases().get(0);

			Connection connection = CreateStorageTest.getConnection();

			CreateDatabase createDatabase = new CreateDatabase();
			int result = createDatabase.dropDatabase(connection, database.getName());
			log.debug("result:" + result);
			assertTrue("Failed to drop database[" + database.getName() + "]", result == 0);

			DBConnector.closeQuietly(connection);
		}
	}
}
