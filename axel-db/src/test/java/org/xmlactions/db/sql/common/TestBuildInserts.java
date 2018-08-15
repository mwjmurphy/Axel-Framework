package org.xmlactions.db.sql.common;


import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.xmlactions.action.Action;
import org.xmlactions.action.actions.BaseAction;
import org.xmlactions.db.actions.Storage;
import org.xmlactions.db.actions.Table;
import org.xmlactions.db.actions.UtilsTestHelper;
import org.xmlactions.db.sql.BuildSelect;
import org.xmlactions.db.sql.mysql.MySqlSelectQuery;
import org.xmlactions.db.sql.oracle.OracleSelectQuery;
import org.xmlactions.db.sql.select.ISelectInputs;
import org.xmlactions.db.sql.select.ISqlSelectBuildQuery;
import org.xmlactions.db.sql.select.ISqlTable;
import org.xmlactions.db.sql.select.SqlField;

public class TestBuildInserts extends TestCase {

    private static final Logger log = LoggerFactory.getLogger(TestBuildInserts.class);

    public void testBuildInsert1Table() throws Exception {
        Action action = new Action();
        String page = Action.loadPage("src/test/resources", "db/storage.xml");
        BaseAction[] actions = action.processXML(UtilsTestHelper.getExecContext(), page);
        assertEquals("Invalid number of actions returned from processXML", 1, actions.length);

        assertTrue("Action [" + action.getClass().getName() + "] should be a Storage class",
                actions[0] instanceof Storage);

        Storage storage = (Storage) actions[0];

        List<SqlField> sqlFields = new ArrayList<SqlField>();
        SqlField sqlField;
        sqlField = new SqlField("street1");
        sqlField.setValue("street1");
        sqlFields.add(sqlField);
        sqlField = new SqlField("street2");
        sqlField.setValue("street2");
        sqlFields.add(sqlField);
        sqlField = new SqlField("city");
        sqlField.setValue("city");
        sqlFields.add(sqlField);
        ISelectInputs selectInputs = BuildSelect.buildSelect(storage, "test", "tb_address", sqlFields, "mysql");
        assertNotNull(selectInputs);

        ISqlSelectBuildQuery build = new MySqlSelectQuery();
        ISqlTable [] iSqlTables = build.buildInsertSqls(UtilsTestHelper.getExecContext(), selectInputs);
        String selectSql = iSqlTables[0].getInsertSql();
        log.debug("selectSql:" + selectSql);
        assertTrue(selectSql.contains("insert into tb_address"));
        assertTrue(selectSql.contains("(tb_address.street1, tb_address.street2"));
        assertTrue(selectSql.contains("values (?, ?, ?)"));

    }

    public void testBuildInsertMultipleTables() throws Exception {
        Action action = new Action();
        String page = Action.loadPage("src/test/resources", "db/storage.xml");
        BaseAction[] actions = action.processXML(UtilsTestHelper.getExecContext(), page);
        assertEquals("Invalid number of actions returned from processXML", 1, actions.length);

        assertTrue("Action [" + action.getClass().getName() + "] should be a Storage class",
                actions[0] instanceof Storage);

        Storage storage = (Storage) actions[0];

        List<SqlField> sqlFields = new ArrayList<SqlField>();
        SqlField sqlField;
        sqlField = new SqlField("address.street1");
        sqlField.setValue("street1");
        sqlFields.add(sqlField);
        sqlField = new SqlField("address.street2");
        sqlField.setValue("street2");
        sqlFields.add(sqlField);
        sqlField = new SqlField("address.city");
        sqlField.setValue("city");
        sqlFields.add(sqlField);

        sqlField = new SqlField("tb_name_list.name");
        sqlField.setValue("name");
        sqlFields.add(sqlField);

        ISelectInputs selectInputs = BuildSelect.buildSelect(storage, "test", "tb_address", sqlFields, "mysql");
        assertNotNull(selectInputs);
        assertEquals(2, selectInputs.getSqlTables().length);

        ISqlSelectBuildQuery build = new MySqlSelectQuery();
        ISqlTable[] sqlTables = build.buildInsertSqls(UtilsTestHelper.getExecContext(), selectInputs);
        assertEquals(2, sqlTables.length);
        assertTrue(sqlTables[0].getInsertSql().contains("insert into tb_name_list"));
    }

    public void testBuildInsertMultipleTablesWithTablePath() throws Exception {
        Action action = new Action();
        String page = Action.loadPage("src/test/resources", "db/storage.xml");
        BaseAction[] actions = action.processXML(UtilsTestHelper.getExecContext(), page);
        assertEquals("Invalid number of actions returned from processXML", 1, actions.length);

        assertTrue("Action [" + action.getClass().getName() + "] should be a Storage class",
                actions[0] instanceof Storage);

        Storage storage = (Storage) actions[0];

        List<SqlField> sqlFields = new ArrayList<SqlField>();
        SqlField sqlField;
        sqlField = new SqlField("address.street1");
        sqlField.setValue("street1");
        sqlFields.add(sqlField);
        sqlField = new SqlField("address.street2");
        sqlField.setValue("street2");
        sqlFields.add(sqlField);
        sqlField = new SqlField("address.city");
        sqlField.setValue("city");
        sqlFields.add(sqlField);

        sqlField = new SqlField("tb_name.name");
        sqlField.setValue("name");
        sqlFields.add(sqlField);

        ISelectInputs selectInputs = BuildSelect.buildSelect(storage, "test", "tb_address", sqlFields, "mysql");
        assertNotNull(selectInputs);
        assertEquals(3, selectInputs.getSqlTables().length);

        ISqlSelectBuildQuery build = new MySqlSelectQuery();
        ISqlTable[] sqlTables = build.buildInsertSqls(UtilsTestHelper.getExecContext(), selectInputs);
        for (ISqlTable sqlTable : sqlTables) {
        	log.debug("sqlTable:" + sqlTable.getInsertSql());
        }
        assertEquals(3, sqlTables.length);
        assertTrue(sqlTables[0].getInsertSql().contains("insert into tb_name_address"));
        //assertTrue(selectSql.contains("(tb_address.street1, tb_address.street2"));
        //assertTrue(selectSql.contains("values ('street1'"));
    }
}
