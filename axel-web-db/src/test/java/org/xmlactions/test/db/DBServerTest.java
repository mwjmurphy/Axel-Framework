
package org.xmlactions.test.db;

import static org.junit.Assert.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import junit.framework.TestCase;

import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.dom4j.DocumentException;
import org.junit.Before;
import org.junit.Test;
import org.xmlactions.common.xml.XMLObject;
import org.xmlactions.db.DBConnector;
import org.xmlactions.db.DBSQL;
import org.xmlactions.db.ServerDB;
import org.xmlactions.db.exception.DBSQLException;
import org.xmlactions.pager.env.EnvironmentAccess;


/**
 * Test that we're creating the test database correctly
 * 
 * @author mike
 * 
 */
public class DBServerTest 
{

	private final static Logger log = LoggerFactory.getLogger(DBServerTest.class);

	// private final static String DB_CONFIG = "/db/derby_db.xml";

	private final static String DB_CONFIG = "/db/h2_db.xml";

	private final static String login = "sa";

	private final static String password = "password";

	private final static String url = "jdbc:derby:target/test;create=true";

	// private final static String url = "jdbc:h2:~/target/test";

	private static ServerDB serverDB;
	
	@Before
	public void beforeMethod() {
		org.junit.Assume.assumeTrue(EnvironmentAccess.runDatabaseTests());
	}


	public static Connection getConnection()
	{

		try {
			if (serverDB == null) {
				serverDB = new ServerDB();
				serverDB.startServer(DB_CONFIG);
			}
			return serverDB.getConnection();
		} catch (Exception ex) {
			throw new IllegalArgumentException(ex);
		}
	}

	@Test
	public void testServerDB()
			throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException,
			DocumentException
	{

		Connection connection = getConnection();
		assertNotNull("Unable to get database connection", connection);
		DBConnector.closeQuietly(connection);
		serverDB.stopServer();
	}

	@Test
	public void testMakeQuery1()
			throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException,
			DocumentException, DBSQLException
	{

		String expectedXML = "<root num_rows=\"1\">\n" + " <row index=\"1\">\n"
				+ "  <ID type=\"INTEGER\" value=\"1\"/>\n" + "  <NAME type=\"VARCHAR\" value=\"wilma flinstone\"/>\n"
				+ "  <ADDRESS type=\"VARCHAR\" value=\"bedrock\"/>\n" + " </row>\n" + "</root>";
		Connection connection = getConnection();
		DBSQL dbSQL = new DBSQL();
		String result = dbSQL.queryXML(connection, "Select * from tb_one", "root");
		DBConnector.closeQuietly(connection);
		serverDB.stopServer();
		assertEquals(expectedXML, result);
	}

	@Test
	public void testMakeQuery2()
			throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException,
			DocumentException, DBSQLException
	{

		if (!url.contains("derby")) {
			Connection connection = getConnection();
			DBSQL dbSQL = new DBSQL();
			XMLObject result = dbSQL.query2XMLObject(connection, "Select * from tb_tracker limit 0, 10", "root", null, null, null);
			DBConnector.closeQuietly(connection);
			serverDB.stopServer();
			assertEquals(10, result.getChildCount());
			log.debug("result:" + result.mapXMLObject2XML(result, true));
		}
	}

	@Test
	public void testMakeQuery3()
			throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException,
			DocumentException, DBSQLException
	{

		Connection connection = getConnection();
		DBSQL dbSQL = new DBSQL();
		XMLObject result = dbSQL.query2XMLObject(connection, "Select * from tb_project", "root", null, null, null);
		DBConnector.closeQuietly(connection);
		serverDB.stopServer();
		assertEquals(6, result.getChildCount());
	}

}
