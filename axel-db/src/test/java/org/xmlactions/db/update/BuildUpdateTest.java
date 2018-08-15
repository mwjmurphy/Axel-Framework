
package org.xmlactions.db.update;

import static org.junit.Assert.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.math.BigInteger;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.junit.Before;
import org.junit.Test;
import org.xmlactions.action.Action;
import org.xmlactions.action.NestedActionException;
import org.xmlactions.action.actions.BaseAction;
import org.xmlactions.common.xml.BadXMLException;
import org.xmlactions.db.DBConnectionTest;
import org.xmlactions.db.DBConnector;
import org.xmlactions.db.DBSQL;
import org.xmlactions.db.actions.Storage;
import org.xmlactions.db.actions.UtilsTestHelper;
import org.xmlactions.db.env.EnvironmentAccess;
import org.xmlactions.db.sql.BuildSelect;
import org.xmlactions.db.sql.mysql.MySqlSelectQuery;
import org.xmlactions.db.sql.select.ISqlSelectBuildQuery;
import org.xmlactions.db.sql.select.ISqlTable;
import org.xmlactions.db.sql.select.SqlField;
import org.xmlactions.db.sql.select.SqlSelectInputs;


public class BuildUpdateTest 
{
	
	@Before
	public void beforeMethod() {
		org.junit.Assume.assumeTrue(EnvironmentAccess.runDatabaseTests());
	}


	private static final Logger log = LoggerFactory.getLogger(BuildUpdateTest.class);

	@Test
	public void testSingleTableBuildSelectMySql() throws Exception
	{

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
        sqlField.setValue("And this blob has been updated again");
        sqlFields.add(sqlField);

        sqlField = new SqlField("text");
        sqlField.setValue("And this text has been updated again");
        sqlFields.add(sqlField);

        sqlField = new SqlField("decimal");
        Double d = 101.01d;
        sqlField.setValue(d);
        sqlFields.add(sqlField);

        sqlField = new SqlField("int");
        BigInteger i = new BigInteger("101");
        sqlField.setValue(i);
        sqlFields.add(sqlField);
        
        SqlSelectInputs sqlSelectInputs = BuildSelect.buildSelect(storage, "test", "tb_all_types", sqlFields, "mysql");
        sqlSelectInputs.addWhereClause("id=1");
		assertNotNull(sqlSelectInputs);
		ISqlSelectBuildQuery build = new MySqlSelectQuery();
        ISqlTable [] sqlTables = build.buildUpdateSqls(UtilsTestHelper.getExecContext(), sqlSelectInputs);
		log.debug("updateSql:" + sqlTables);
        assertTrue(sqlTables.length > 0);
        assertTrue(sqlTables[0].getUpdateSql().contains("update tb_all_types all_types"));
		assertTrue(sqlTables[0].getUpdateSql().contains("set"));
		assertTrue(sqlTables[0].getUpdateSql().contains("all_types."));

        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/test";
        String username = "root";
        String password = "n1e1i1l1";


		Connection connection = DBConnectionTest.getConnection(driver, url, username, password);
		DBSQL dbSQL = new DBSQL();
		
		for (ISqlTable sqlTable : sqlTables) {
			int rowsUpdated = dbSQL.update(connection, sqlTable.getUpdateSql(), sqlTable.getParams());
		}
		DBConnector.closeQuietly(connection);
        
	
	}

}
