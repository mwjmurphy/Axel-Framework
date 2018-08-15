package org.xmlactions.db.h2;


import static org.junit.Assert.assertTrue;

import java.sql.Connection;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmlactions.db.DBConnector;
import org.xmlactions.db.DBQuery;
import org.xmlactions.db.DBSQL;
import org.xmlactions.db.actions.Table;


public class H2DBTest {

	private static final Logger log = LoggerFactory.getLogger(H2DBTest.class);

	private Connection connection;
	@Before
	public void getConnection() throws Exception {
		connection = H2TestDatabase.getInstance().getConnection();
	}
	
	
	@After
	public void closeConnection() {
		DBConnector.closeQuietly(connection);
	}
	
	@Test
	public void testQueryTbOne() throws Exception
	{
		H2TestDatabase h2td = H2TestDatabase.getInstance();

		Table table = h2td.getTable("h2_test", "tb_one");
		String query = table.buildQuery(null, table.getFields(), null, null, null, "\"");
		log.debug("query:" + query);

		DBQuery dbQuery = new DBQuery(connection);
		String result = dbQuery.query(query);
		log.debug("result:\n" + result);
		assertTrue(result.contains("bedrock"));
	}

	@Test
	public void testInsertTbOne() throws Exception
	{
		int pk = DBSQL.insertAndReturnKey(connection, "insert into tb_one (name, address) values ('fred flinstone', 'bedrock') ");
		pk = DBSQL.insertAndReturnKey(connection, "insert into tb_one (name, address) values ('bambam flinstone', 'bedrock') ");
		assertTrue(pk > 2);
		log.debug("pk:" + pk);
	}
	
}
