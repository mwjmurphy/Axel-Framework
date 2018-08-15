/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.xmlactions.db;


import java.sql.Connection;

import org.junit.Before;
import org.junit.Test;
import org.xmlactions.db.DBConnector;
import org.xmlactions.db.DBQuery;
import org.xmlactions.db.env.EnvironmentAccess;

/**
 *
 * @author MichaelMurphy
 */
public class DBQueryTest {
    
	@Before
	public void beforeMethod() {
		org.junit.Assume.assumeTrue(EnvironmentAccess.runDatabaseTests());
	}
	
	@Test
    public void testCreate() throws Exception
    {
		Connection connection = DBConnectionTest.getConnection();
		DBQuery dbQuery = new DBQuery(connection);
		String query = "select * from tb_user";
		String result = dbQuery.query(query);
		DBConnector.closeQuietly(connection);
    }

}
