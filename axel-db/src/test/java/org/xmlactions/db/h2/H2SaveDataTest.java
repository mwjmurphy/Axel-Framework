package org.xmlactions.db.h2;

import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.db.DBConnector;
import org.xmlactions.db.DBQuery;
import org.xmlactions.db.actions.Database;
import org.xmlactions.db.actions.Storage;
import org.xmlactions.db.actions.Table;
import org.xmlactions.db.actions.UtilsTestHelper;
import org.xmlactions.db.save.SaveToDB;

public class H2SaveDataTest {
	
	private static final Logger log = LoggerFactory.getLogger(H2SaveDataTest.class);
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
	public void testSaveAndUpdateData() throws Exception {
		
		ATestDatabase h2td = H2TestDatabase.getInstance();

		IExecContext execContext = UtilsTestHelper.getExecContext();
		
		Storage storage = h2td.getStorage();
		Database database = storage.getDatabase("h2_test");
		Table table = database.getTable("tb_one");
		
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("tb_one.name", "Mrs Wilma Flinstone");
		map.put("tb_one.address", "Bedrock");
		SaveToDB saveToDB = new SaveToDB();
		String result = saveToDB.saveData(execContext, h2td.getStorageConfig(), database, table, map);
		int pk = Integer.parseInt(result);
		map = new HashMap<String,Object>();
		map.put("tb_one.name", "Mrs Wilma Flinstone The 2nd");
		map.put("tb_one.address", "Bedrock");
		map.put("tb_one.id", result);
		result = saveToDB.saveData(execContext, h2td.getStorageConfig(), database, table, map);
		log.debug("result:" + result);
		
		DBQuery dbQuery = new DBQuery(connection);
		result = dbQuery.query("select * from tb_one");
		log.debug("result:\n" + result);

	}

}
