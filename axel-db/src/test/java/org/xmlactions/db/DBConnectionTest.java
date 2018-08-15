/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.xmlactions.db;


import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import javax.naming.NamingException;

import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.junit.Before;
import org.junit.Test;
import org.xmlactions.common.io.ResourceUtils;
import org.xmlactions.db.DBConnector;
import org.xmlactions.db.env.EnvironmentAccess;

/**
 * 
 * @author MichaelMurphy
 */
public class DBConnectionTest
{

	private static final Logger log = LoggerFactory.getLogger(DBConnectionTest.class);

	private static DBConnector dbConnector;
	
	@Before
	public void beforeMethod() {
		org.junit.Assume.assumeTrue(EnvironmentAccess.runDatabaseTests());
	}
	

	public static Connection getConnection(String driver, String url, String username, String password) throws SQLException, ClassNotFoundException, NamingException
	{
		DBConnector dbConnection = new DBConnector(driver, url, username, password);
		return dbConnection.getConnection(null);
	}

	public static Connection getConnection() throws SQLException, ClassNotFoundException, NamingException
	{

		DBConnector dbConnection = new DBConnector("com.mysql.jdbc.Driver",
				"jdbc:mysql://localhost:3306/test", "root", "n1e1i1l1");
		return dbConnection.getConnection(null);
	}

	public static Connection getConnection(String propertiesFile)
			throws IOException, SQLException, ClassNotFoundException, NamingException
	{

		if (dbConnector == null) {
			Properties props = new Properties();
			props.load(ResourceUtils.getResourceURL(propertiesFile).openStream());
			String login = props.getProperty("login-name");
			String password = props.getProperty("login-password");
			String driver = props.getProperty("database-driver");
			String url = props.getProperty("database-url");
			dbConnector = new DBConnector(driver, url, login, password);
		}
		return dbConnector.getConnection(null);
	}

	@Test
	public void testGetConnection() throws SQLException, ClassNotFoundException, NamingException
	{

		Connection connection = getConnection();
		DBConnector.closeQuietly(connection);
	}
}