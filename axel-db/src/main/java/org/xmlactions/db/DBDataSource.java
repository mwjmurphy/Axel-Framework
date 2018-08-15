
package org.xmlactions.db;

import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * Used to create a DataSource.
 * <p>
 * Create the bean, set the properties and then call create() to get back a DataSource that provides a getConnection()
 * method. Dont forget to close the connection after you.
 * </p>
 * 
 * @deprecated - use DBConnector instead
 */
public class DBDataSource
{

	/** Used for a JNDI Lookup for a DataSource */
	private String dataSourceReferenceName;

	/** Used for a JNDI Lookup for a DataSource */
	private String initContextLookup;

	/**
	 * Creates a DataSource. Used for getting connections to DB's etc.
	 * 
	 * @return created DataSource
	 * @throws NamingException
	 * @throws SQLException
	 */
	public DataSource create() throws NamingException, SQLException
	{
		// Obtain our environment naming context
		Context initCtx = new InitialContext();
		Context envCtx = (Context) initCtx.lookup(initContextLookup);

		// Look up our data source by the name we gave it when we created it.
		// In this case that's "jdbc/EmployeeDB".
		DataSource ds = (DataSource) envCtx.lookup(dataSourceReferenceName);
		return ds;
	}

	public String getDataSourceReferenceName()
	{

		return dataSourceReferenceName;
	}

	public void setDataSourceReferenceName(String dataSourceReferenceName)
	{

		this.dataSourceReferenceName = dataSourceReferenceName;
	}

	public String getInitContextLookup()
	{

		return initContextLookup;
	}

	public void setInitContextLookup(String initContextLookup)
	{

		this.initContextLookup = initContextLookup;
	}

}
