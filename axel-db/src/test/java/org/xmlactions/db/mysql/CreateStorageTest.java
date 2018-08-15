
package org.xmlactions.db.mysql;


import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.naming.NamingException;


import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.xmlactions.action.Action;
import org.xmlactions.action.actions.BaseAction;
import org.xmlactions.common.io.ResourceUtils;
import org.xmlactions.common.system.JS;
import org.xmlactions.db.DBConnector;
import org.xmlactions.db.actions.UtilsTestHelper;


public class CreateStorageTest // extends TestCase
{

	private final static Logger log = LoggerFactory.getLogger(CreateStorageTest.class);

	private final static String storageFile = "/db/storage.xml";

	private final static String propertiesFile = "/db/mysql.properties";

	private static BaseAction[] actions;

	private static DBConnector dbConnector;

	public static Connection getConnection() throws IOException, SQLException, ClassNotFoundException, NamingException
	{

		if (dbConnector == null) {
			Properties props = new Properties();
			props.load(ResourceUtils.getResourceURL(propertiesFile).openStream());
			String login = props.getProperty("login-name");
			String password = props.getProperty("login-password");
			String driver = props.getProperty("database-driver");
			String url = props.getProperty("database-url");
			dbConnector = new DBConnector();
			dbConnector.setDriver(driver);
			dbConnector.setUrl(url);
			dbConnector.setUsername(login);
			dbConnector.setPassword(password);
		}
		return dbConnector.getConnection(null);
	}

	public static BaseAction[] getActions()
	{

		if (actions == null) {

			try {
				Action action = new Action();
				String page = action.loadPage("src/test/resources", storageFile);
				actions = action.processXML(UtilsTestHelper.getExecContext(), page);
			} catch (Throwable e) {
				throw new RuntimeException("Failed to load Storage [" + storageFile + "]:" + e.getMessage());
			}
		}
		return actions;
	}

	/*
	 * public void testRun() throws IOException, SQLException, ClassNotFoundException, NamingException,
	 * NestedActionException, InstantiationException, IllegalAccessException, InvocationTargetException,
	 * NoSuchMethodException, BadXMLException {
	 * 
	 * loadStorage(); createConnection(); createStorage(); dropStorage(); }
	 */
	/*
	 * public void loadStorage() throws IOException, NestedActionException, ClassNotFoundException,
	 * InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, BadXMLException
	 * {
	 * 
	 * Storage storage = (Storage) getActions()[0]; assertEquals("Invalid number of actions returned from processXML",
	 * 1, getActions().length); List<Database> databases = storage.getDatabases();
	 * assertEquals("Missing Database from Storage:" + storage.getName(), 3, databases.size());
	 * 
	 * }
	 */

	public void createConnection() throws IOException, SQLException, ClassNotFoundException, NamingException
	{

		if ("mike-laptop".equalsIgnoreCase(JS.getEnv("USERDOMAIN"))) {

			Connection connection = getConnection();
			DBConnector.closeQuietly(connection);
		}
	}

	/*
	 * public void createStorage() throws IOException, SQLException, ClassNotFoundException, NamingException,
	 * NestedActionException, InstantiationException, IllegalAccessException, InvocationTargetException,
	 * NoSuchMethodException, BadXMLException {
	 * 
	 * if ("mike-laptop".equalsIgnoreCase(JS.getEnv("USERDOMAIN"))) {
	 * 
	 * Connection connection = getConnection(); CreateStorage createStorage = new CreateStorage();
	 * createStorage.createStorage(storageFile, connection, UtilsTestHelper.getExecContext());
	 * DBConnection.closeQuietly(connection); } }
	 */

	/*
	 * public void dropStorage() throws IOException, SQLException, ClassNotFoundException, NamingException,
	 * NestedActionException, InstantiationException, IllegalAccessException, InvocationTargetException,
	 * NoSuchMethodException, BadXMLException {
	 * 
	 * if ("mike-laptop".equalsIgnoreCase(JS.getEnv("USERDOMAIN"))) {
	 * 
	 * Connection connection = getConnection(); CreateStorage createStorage = new CreateStorage();
	 * createStorage.dropStorage(storageFile, connection, UtilsTestHelper.getExecContext());
	 * DBConnection.closeQuietly(connection); } }
	 */

}
