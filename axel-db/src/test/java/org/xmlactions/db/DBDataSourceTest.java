package org.xmlactions.db;


import java.sql.SQLException;

import javax.naming.NamingException;

import org.xmlactions.db.DBDataSource;


import junit.framework.TestCase;

public class DBDataSourceTest extends TestCase {
	
	public void testConstruct()
	{
		DBDataSource dbDataSource = new DBDataSource();
		dbDataSource.setInitContextLookup("java:/comp/env");
		dbDataSource.setDataSourceReferenceName("jdbc/DerbyDB");
		assertEquals("java:/comp/env", dbDataSource.getInitContextLookup());
		assertEquals("jdbc/DerbyDB", dbDataSource.getDataSourceReferenceName());
	}
	
	
	public void testCreate() throws NamingException, SQLException
	{
		DBDataSource dbDataSource = new DBDataSource();
		dbDataSource.setInitContextLookup("java:/comp/env");
		dbDataSource.setDataSourceReferenceName("jdbc/DerbyDB");
		// TODO need to build a fake environment so we can make the create call
		//dbDataSource.create();
	}
	
	

}
