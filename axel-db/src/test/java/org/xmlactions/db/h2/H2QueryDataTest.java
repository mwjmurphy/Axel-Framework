package org.xmlactions.db.h2;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.common.xml.XMLObject;
import org.xmlactions.db.DBConnector;
import org.xmlactions.db.DBSQL;
import org.xmlactions.db.ServerDB;
import org.xmlactions.db.StorageContainer;
import org.xmlactions.db.actions.CommonStorageField;
import org.xmlactions.db.actions.Database;
import org.xmlactions.db.actions.Storage;
import org.xmlactions.db.actions.Table;
import org.xmlactions.db.actions.UtilsTestHelper;
import org.xmlactions.db.config.StorageConfig;
import org.xmlactions.db.sql.BuildSelect;
import org.xmlactions.db.sql.h2.H2SelectQuery;
import org.xmlactions.db.sql.select.ISqlSelectBuildQuery;
import org.xmlactions.db.sql.select.SqlField;
import org.xmlactions.db.sql.select.SqlSelectInputs;

public class H2QueryDataTest {
	
	private static final Logger log = LoggerFactory.getLogger(H2QueryDataTest.class);
	private Connection connection;
	private IExecContext execContext;

	@Before
	public void getConnection() throws Exception {
		connection = H2TestDatabase.getInstance().getConnection();
	}
	
	@Before
	public void getExecContext() throws Exception {
		execContext = H2TestDatabase.getInstance().getExecContext();
	}
	
	@After
	public void closeConnection() {
		DBConnector.closeQuietly(connection);
	}
	
	@Test
	public void testQueryData() throws Exception {


		StorageContainer storageContainer = H2TestDatabase.getInstance().getStorageContainer();
		StorageConfig storageConfig = H2TestDatabase.getInstance().getStorageConfig();
		Storage storage = storageContainer.getStorage();
		Database database = storage.getDatabase("h2_test");
		Table table = database.getTable("tb_one");
		
		List<SqlField> sqlFields = new ArrayList<SqlField>();
		
		CommonStorageField field = table.getField("name");
		SqlField sqlField = new SqlField(field);
		sqlField.setFieldName("tb_1.name");
		sqlFields.add(sqlField);
		field = table.getField("address");
		sqlField = new SqlField(field);
		sqlField.setFieldName("tb_1.address");
		sqlFields.add(sqlField);
		
		SqlSelectInputs sqlSelectInputs = BuildSelect.buildSelect(storageContainer.getStorage(), database.getName(), "tb_one", sqlFields, storageConfig.getDbSpecificName());
		
		
        ISqlSelectBuildQuery builder = storageConfig.getSqlBuilder();

        List<SqlField> sqlParams = new ArrayList<SqlField>();
		String sqlCount = "select count(*) from (" + builder.buildSelectQuery(execContext, sqlSelectInputs, sqlParams) + ") tb";

		
		int totalRows = Integer.parseInt(DBSQL.queryOne(connection, sqlCount, sqlParams));
		log.debug("totalRows:" + totalRows);
		
		sqlSelectInputs.setLimitFrom("1");
		sqlSelectInputs.setLimitTo("10");
		
		String sqlQuery = builder.buildSelectQuery(execContext, sqlSelectInputs, sqlParams);
		log.debug("sqlQuery:" + sqlQuery);
		DBSQL dbSQL = new DBSQL();

		XMLObject xo = dbSQL.query2XMLObject(connection, sqlQuery, "root", null, null, sqlParams);
		log.debug("sql result:" + xo.mapXMLObject2XML(xo, true));


//		Connection connection = serverDB.getConnection();
//		DBQuery dbQuery = new DBQuery(connection);
//		result = dbQuery.query("select * from tb_one");
//		log.debug("result:\n" + result);

		

	}

}
