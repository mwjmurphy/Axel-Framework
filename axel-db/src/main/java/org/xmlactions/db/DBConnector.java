/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.xmlactions.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.xmlactions.action.config.IExecContext;


/**
 * 
 * @author MichaelMurphy
 */
public class DBConnector {
	private static final Logger log = LoggerFactory.getLogger(DBConnector.class);

	/** Used for a JNDI Lookup for a DataSource */
	private String dataSourceReferenceName;

	/** Used for a JNDI Lookup for a DataSource */
	private String initContextLookup;

	/** Used when creating the DataSource */
	private String driver = null;

	/** Used when creating the DataSource */
	private String url = null;

	/** Used when creating the DataSource */
	private String username = null;

	/** Used when creating the DataSource */
	private String password = null;

	private PostConnectionConfig postConnectionConfig;
	
	/**
	 * if dataSource is not null then this is used to create or reset a
	 * connection.
	 */
	private transient DataSource dataSource = null;

	public static final String DEFAULT_DATE_FORMAT = "yyyyMMdd";

	public DBConnector()
	{

	};

	public DBConnector(String initContextLookup, String dataSourceReferenceName)
	{
		this.initContextLookup = initContextLookup;
		this.dataSourceReferenceName = dataSourceReferenceName;
	}

	public DBConnector(String driver, String url, String username, String password)
	{

		this.driver = driver;
		this.url = url;
		this.username = username;
		this.password = password;
	}

	/**
	 * Creates a DB Connection
	 * 
	 * @return Connection A DB Connection Object
	 * @throws Exception
	 */
	public Connection getConnection(IExecContext execContext) throws SQLException, ClassNotFoundException, NamingException
	{
		Connection connection;
		if (this.dataSource != null) {
			connection = createFromDataSource(this.dataSource);
		} else if (StringUtils.isNotEmpty(getDataSourceReferenceName())) {
			this.dataSource = this.createFromJndi();
			connection = createFromDataSource(this.dataSource);
		} else {
			// Create the Connection
			// Load the driver class name.
			// Log.getInstance().debug("driver:" + this.driver);
			Class.forName(this.driver);
			if (this.username == null && this.password == null) {
				connection = DriverManager.getConnection(this.url);
			} else {
				connection = DriverManager.getConnection(this.url,
						this.username, this.password);
			}
		}
		if (postConnectionConfig != null) {
			postConnectionConfig.configConnection(execContext, connection);
		}

		return (connection);
	}
	
	/**
	 * Returns a datasource from a JNDI Datasource or create one from a Spring DriverManagerDataSource. 
	 * @return a DataSource
	 * @throws SQLException 
	 * @throws NamingException 
	 */
	public DataSource getDatasourceFromDBConnector() throws NamingException, SQLException {
		DataSource ds = null;
		if (getDataSource() != null) {
			// if we already have one use it
			ds = getDataSource();
		} else if (StringUtils.isNotEmpty(getDataSourceReferenceName())) {
			// try and create from jndi
			ds = this.createFromJndi();
		} else {
			// try and create using the DBConnector JDBC connector fields.
			ds = new DriverManagerDataSource(getUrl(), getUsername(), getPassword());
			// ds = new DriverManagerDataSource(getDriver(), getUrl(), getUsername(), getPassword());
		}
		return ds;
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

	/**
	 * Creates a DataSource. Used for getting connections to DB's etc.
	 * 
	 * @return created DataSource
	 * @throws NamingException
	 * @throws SQLException
	 */
	private DataSource createFromJndi() throws NamingException, SQLException
	{

		// Obtain our environment naming context
		Context initCtx = new InitialContext();

        if (StringUtils.isNotEmpty(initContextLookup)) {
            Context envCtx = (Context) initCtx.lookup(initContextLookup);
            // Look up our data source by the name we gave it when we created
            // it.
            // In this case that's "jdbc/EmployeeDB".
            DataSource ds = (DataSource) envCtx.lookup(dataSourceReferenceName);
            return ds;
        } else {
            DataSource ds = (DataSource) initCtx.lookup(dataSourceReferenceName);
            return ds;

        }

	}

	/**
	 * Use this to get a db connection from a data source. A data source is a web dataSourceName something like
	 * 'jdbc/i26907_riostl' This is usually setp in the jboss-ds.xml or context.xml.
	 */
	private Connection createFromDataSource(DataSource dataSource) throws SQLException
	{

		this.dataSource = dataSource;
		return this.dataSource.getConnection();
	}

	public void setDataSourceReferenceName(String dataSourceReferenceName)
	{

		this.dataSourceReferenceName = dataSourceReferenceName;
	}

	public String getDataSourceReferenceName()
	{

		return dataSourceReferenceName;
	}

	public void setInitContextLookup(String initContextLookup)
	{

		this.initContextLookup = initContextLookup;
	}

	public String getInitContextLookup()
	{

		return initContextLookup;
	}

	public String getDriver()
	{

		return driver;
	}

	public void setDriver(String driver)
	{

		this.driver = driver;
	}

	public String getUrl()
	{

		return url;
	}

	public void setUrl(String url)
	{

		this.url = url;
	}

	public String getUsername()
	{

		return username;
	}

	public void setUsername(String username)
	{

		this.username = username;
	}

	public String getPassword()
	{

		return password;
	}

	public void setPassword(String password)
	{

		this.password = password;
	}

	public DataSource getDataSource()
	{

		return dataSource;
	}

	public void setDataSource(DataSource dataSource)
	{

		this.dataSource = dataSource;
	}

	/**
	 * @return the postConnectionConfig
	 */
	public PostConnectionConfig getPostConnectionConfig() {
		return postConnectionConfig;
	}

	/**
	 * @param postConnectionConfig the postConnectionConfig to set
	 */
	public void setPostConnectionConfig(PostConnectionConfig postConnectionConfig) {
		this.postConnectionConfig = postConnectionConfig;
	}

}
