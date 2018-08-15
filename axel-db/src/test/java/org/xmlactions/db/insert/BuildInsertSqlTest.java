package org.xmlactions.db.insert;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.junit.Before;
import org.junit.Test;
import org.xmlactions.action.Action;
import org.xmlactions.action.actions.BaseAction;
import org.xmlactions.db.DBConnectionTest;
import org.xmlactions.db.DBConnector;
import org.xmlactions.db.DBSQL;
import org.xmlactions.db.actions.Storage;
import org.xmlactions.db.actions.UtilsTestHelper;
import org.xmlactions.db.env.EnvironmentAccess;
import org.xmlactions.db.sql.BuildSelect;
import org.xmlactions.db.sql.mysql.MySqlSelectQuery;
import org.xmlactions.db.sql.oracle.OracleSelectQuery;
import org.xmlactions.db.sql.select.ISelectInputs;
import org.xmlactions.db.sql.select.ISqlSelectBuildQuery;
import org.xmlactions.db.sql.select.ISqlTable;
import org.xmlactions.db.sql.select.SqlField;

public class BuildInsertSqlTest {
	
	
    private static final Logger log = LoggerFactory.getLogger(BuildInsertSqlTest.class);

	@Before
	public void beforeMethod() {
		org.junit.Assume.assumeTrue(EnvironmentAccess.runDatabaseTests());
	}


	@Test
	public void testCreatePKTableBuildSelectMySql() throws Exception {

        Action action = new Action();
        String page = Action.loadPage("src/test/resources", "db/storage.xml");
        BaseAction[] actions = action.processXML(UtilsTestHelper.getExecContext(), page);
        assertEquals("Invalid number of actions returned from processXML", 1, actions.length);

        assertTrue("Action [" + action.getClass().getName() + "] should be a Storage class",
                actions[0] instanceof Storage);

        Storage storage = (Storage) actions[0];

        List<SqlField> sqlFields = new ArrayList<SqlField>();
        SqlField sqlField;
        sqlField = new SqlField("blob");
        sqlField.setValue("blob ' \" content");
        sqlFields.add(sqlField);
        ISelectInputs sqlSelectInputs = BuildSelect.buildSelect(storage, "test", "tb_all_types", sqlFields, "mysql");
        assertNotNull(sqlSelectInputs);
        ISqlSelectBuildQuery build = new MySqlSelectQuery();
        ISqlTable [] inserts = build.buildInsertSqls(UtilsTestHelper.getExecContext(), sqlSelectInputs);
        log.debug("insertSql:" + inserts);
        assertTrue(inserts.length == 1);
        assertTrue(inserts[0].getInsertSql().contains("insert into tb_all_types"));
        assertTrue(inserts[0].getInsertSql().contains("tb_all_types.blob"));


        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/test";
        String username = "root";
        String password = "n1e1i1l1";


		Connection connection = DBConnectionTest.getConnection(driver, url, username, password);
		DBSQL dbSQL = new DBSQL();
		for (ISqlTable sqlTable : inserts) {
			int pk = dbSQL.insertAndReturnKey(connection, sqlTable.getInsertSql(), sqlTable.getParams());
		}
		DBConnector.closeQuietly(connection);
        
    }

	@Test
    public void testCreatePKTableBuildSelectOracle() throws Exception {

    	Action action = new Action();
        String page = Action.loadPage("src/test/resources", "db/storage.xml");
        BaseAction[] actions = action.processXML(UtilsTestHelper.getExecContext(), page);
        assertEquals("Invalid number of actions returned from processXML", 1, actions.length);

        assertTrue("Action [" + action.getClass().getName() + "] should be a Storage class",
                actions[0] instanceof Storage);

        Storage storage = (Storage) actions[0];

        List<SqlField> sqlFields = new ArrayList<SqlField>();
        SqlField sqlField;
        sqlField = new SqlField("blob");
        sqlField.setValue("blob ' \" content");
        sqlFields.add(sqlField);
        ISelectInputs sqlSelectInputs = BuildSelect.buildSelect(storage, "test", "tb_all_types", sqlFields, "mysql");
        assertNotNull(sqlSelectInputs);
        ISqlSelectBuildQuery build = new OracleSelectQuery();
        ISqlTable [] inserts = build.buildInsertSqls(UtilsTestHelper.getExecContext(), sqlSelectInputs);
        log.debug("insertSql:" + inserts);
        assertTrue(inserts.length == 1);
        assertTrue(inserts[0].getInsertSql().contains("insert into tb_all_types"));
        assertTrue(inserts[0].getInsertSql().contains("tb_all_types.blob"));


        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/test";
        String username = "root";
        String password = "n1e1i1l1";


		Connection connection = DBConnectionTest.getConnection(driver, url, username, password);
		DBSQL dbSQL = new DBSQL();
		for (ISqlTable sqlTable : inserts) {
			int pk = dbSQL.insertAndReturnKey(connection, sqlTable.getInsertSql(), sqlTable.getParams());
		}
		DBConnector.closeQuietly(connection);
        
    }


}
