/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.xmlactions.db;

import static org.junit.Assert.*;

import java.sql.Connection;

import org.junit.Before;
import org.junit.Test;
import org.xmlactions.db.DBConnector;
import org.xmlactions.db.DBSQL;
import org.xmlactions.db.DBSQL.JavaToSqlType;
import org.xmlactions.db.env.EnvironmentAccess;


import junit.framework.TestCase;

/**
 * 
 * @author MichaelMurphy
 */
public class DBSQLTest
{

	@Before
	public void beforeMethod() throws Exception {
		org.junit.Assume.assumeTrue(EnvironmentAccess.runDatabaseTests());
	}



	@Test
	public void testCreate() throws Exception
	{

		Connection connection = DBConnectionTest.getConnection();
		DBSQL dbSQL = new DBSQL();
		String query = "select * from tb_all_types";
		String result = dbSQL.query(connection, query);
		String xml = dbSQL.queryXML(connection, query, "root");
		DBConnector.closeQuietly(connection);
	}

	@Test
	public void testEscCharacters()
	{

		assertEquals("\\\"this is the test", DBSQL.escCharacters("\"this is the test"));
		assertEquals("\\\"this \\\"is the test", DBSQL.escCharacters("\"this \"is the test"));
		assertEquals("\\\'this \\\'is the test", DBSQL.escCharacters("\'this \'is the test"));
		assertEquals("\\\'this\\\' \\\"is\\\" the test", DBSQL.escCharacters("\'this\' \"is\" the test"));
	}
	
	@Test
	public void testSqlType() {
		long aLong = 1000l;
		short aShort = 101;
		int aInt = 101;
		int sqlType;
		double aDouble = 101.101;
		float aFloat = 101.101f;
		sqlType = JavaToSqlType.getSqlType(new Float("1.9"));
		assertEquals (java.sql.Types.FLOAT, sqlType);
		sqlType = JavaToSqlType.getSqlType(new Double("1.9"));
		assertEquals (java.sql.Types.DOUBLE, sqlType);
		sqlType = JavaToSqlType.getSqlType(new Long("100"));
		assertEquals (java.sql.Types.BIGINT, sqlType);
		sqlType = JavaToSqlType.getSqlType(aLong);
		assertEquals (java.sql.Types.BIGINT, sqlType);
		sqlType = JavaToSqlType.getSqlType(aShort);
		assertEquals (java.sql.Types.SMALLINT, sqlType);
		sqlType = JavaToSqlType.getSqlType(aInt);
		assertEquals (java.sql.Types.INTEGER, sqlType);
		sqlType = JavaToSqlType.getSqlType(aDouble);
		assertEquals (java.sql.Types.DOUBLE, sqlType);
		sqlType = JavaToSqlType.getSqlType(aFloat);
		assertEquals (java.sql.Types.FLOAT, sqlType);
	}
}
