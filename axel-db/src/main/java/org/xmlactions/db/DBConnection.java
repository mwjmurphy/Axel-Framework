/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.xmlactions.db;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.NamingException;
import javax.sql.DataSource;


import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.xmlactions.db.DBConnection;


/**
 * @deprecated - use DBConnector instead
 * @author MichaelMurphy
 */
public class DBConnection {
	private static final Logger log = LoggerFactory.getLogger(DBConnection.class);

	/** Used when creating the DataSource */
	private String driver = null;

	/** Used when creating the DataSource */
	private String url = null;

	/** Used when creating the DataSource */
	private String userName = null;

	/** Used when creating the DataSource */
	private String password = null;

	/**
	 * if dataSource is not null then this is used to create or reset a
	 * connection.
	 */
	private transient DataSource dataSource = null;

	public static final String DEFAULT_DATE_FORMAT = "yyyyMMdd";

	/**
	 * Use this to get a db connection from a data source. A data source is a
	 * web dataSourceName something like 'jdbc/i26907_riostl' This is usually
	 * setp in the jboss-ds.xml or context.xml.
	 */
	private Connection createFromDataSource(DataSource dataSource)
			throws SQLException {
		this.dataSource = dataSource;
		return this.dataSource.getConnection();
	}

	/**
	 * Creates a DB Connection
	 * 
	 * @return Connection A DB Connection Object
	 * @throws Exception
	 */
	public Connection createConnection() throws SQLException,
			ClassNotFoundException, NamingException {
		Connection connection;
		if (this.dataSource != null) {
			connection = createFromDataSource(this.dataSource);
		} else {
			// Create the Connection
			// Load the driver class name.
			// Log.getInstance().debug("driver:" + this.driver);
			Class.forName(this.driver);
			if (this.userName == null && this.password == null) {
				connection = DriverManager.getConnection(this.url);
			} else {
				connection = DriverManager.getConnection(this.url,
						this.userName, this.password);
			}
		}
		return (connection);
	}

	public static void closeQuietly(Connection connection) {
		try {
			if (connection != null) {
				connection.close();
			}
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
		}
	}

	public static void closeQuietly(Statement statement) {
		try {
			if (statement != null) {
				statement.close();
			}
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
		}
	}

	public static void closeQuietly(ResultSet resultSet) {
		try {
			if (resultSet != null) {
				resultSet.close();
			}
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
		}
	}

}
