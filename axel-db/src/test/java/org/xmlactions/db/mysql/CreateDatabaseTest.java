
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
import org.xmlactions.db.exception.DBSQLException;
import org.xmlactions.db.mysql.CreateDatabase;


public class CreateDatabaseTest extends TestCase
{

	private final static Logger log = LoggerFactory.getLogger(CreateDatabaseTest.class);

	public void testAll()
			throws IOException, SQLException, ClassNotFoundException, NamingException, NestedActionException,
			InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException,
			BadXMLException, DBSQLException
	{

		createDatabase();
		existsDatabase();
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
			log.debug("result:" + result);
			assertEquals("Failed to create table [" + database.getName() + "]", 1, result);

			DBConnector.closeQuietly(connection);
		}
	}

	public void existsDatabase()
			throws IOException, SQLException, ClassNotFoundException, NamingException, NestedActionException,
			InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException,
			BadXMLException
	{

		if ("mike-laptop".equalsIgnoreCase(JS.getEnv("USERDOMAIN"))) {
			Connection connection = CreateStorageTest.getConnection();

			Storage storage = (Storage) CreateStorageTest.getActions()[0];
			Database database = storage.getDatabases().get(0);

			CreateDatabase createDatabase = new CreateDatabase();
			boolean result = createDatabase.exists(connection, database.getName());
			log.debug("result:" + result);
			assertEquals("Failed to drop database [" + database.getName() + "]", true, result);

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
			assertEquals("Failed to drop table [" + database.getName() + "]", 0, result);

			DBConnector.closeQuietly(connection);
		}
	}

}
