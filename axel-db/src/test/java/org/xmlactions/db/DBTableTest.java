
package org.xmlactions.db;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.action.config.NoPersistenceExecContext;
import org.xmlactions.common.collections.MapUtils;
import org.xmlactions.db.DBConnector;
import org.xmlactions.db.DBQuery;
import org.xmlactions.db.StorageContainer;
import org.xmlactions.db.actions.Database;
import org.xmlactions.db.actions.Storage;
import org.xmlactions.db.actions.Table;
import org.xmlactions.db.env.EnvironmentAccess;


public class DBTableTest
{

	private static final Logger log = LoggerFactory.getLogger(DBTableTest.class);

	@Before
	public void beforeMethod() {
		org.junit.Assume.assumeTrue(EnvironmentAccess.runDatabaseTests());
	}
	

	@Test
	public void testFKQueryFromTbRights() throws Exception
	{
		Map<String, Object> map = MapUtils.loadMapFromProperties("/config/db/actions_db.properties");
		List <Object> list = new ArrayList<Object>();
		list.add(map);
		IExecContext execContext = new NoPersistenceExecContext(list, null);
		StorageContainer storageContainer = new StorageContainer("/db/storage.xml", execContext);
		assertTrue(storageContainer.getStorage() instanceof Storage);

		Storage storage = storageContainer.getStorage();
		Database database = storage.getDatabase("oneloginforall");
		Table table = database.getTable("tb_rights");
		String query = table.buildQuery(database.getName(), table.getFields(), null, null, null, "'");
		log.debug("query:" + query);

		Connection connection = DBConnectionTest.getConnection();
		DBQuery dbQuery = new DBQuery(connection);
		String result = dbQuery.query(query);
		DBConnector.closeQuietly(connection);

	}

	@Test
	public void testFKQueryFromTbuser() throws Exception
	{

		Map<String, Object> map = MapUtils.loadMapFromProperties("/config/db/actions_db.properties");
		List <Object> list = new ArrayList<Object>();
		list.add(map);
		IExecContext execContext = new NoPersistenceExecContext(list, null);
		StorageContainer storageContainer = new StorageContainer("/db/storage.xml", execContext);
		assertTrue(storageContainer.getStorage() instanceof Storage);

		Storage storage = storageContainer.getStorage();
		Database database = storage.getDatabase("talkskool");
		Table table = database.getTable("tb_user_schools");
		String query = table.buildQuery(database.getName(), table.getFields(), null, null, null, "'");
		log.debug("query:" + query);

		Connection connection = DBConnectionTest.getConnection();
		DBQuery dbQuery = new DBQuery(connection);
		String result = dbQuery.query(query);
		DBConnector.closeQuietly(connection);

	}
}
