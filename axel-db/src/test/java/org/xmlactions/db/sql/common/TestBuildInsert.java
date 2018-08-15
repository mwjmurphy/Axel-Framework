
package org.xmlactions.db.sql.common;


import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.xmlactions.action.Action;
import org.xmlactions.action.NestedActionException;
import org.xmlactions.action.actions.BaseAction;
import org.xmlactions.common.xml.BadXMLException;
import org.xmlactions.db.actions.Storage;
import org.xmlactions.db.actions.UtilsTestHelper;
import org.xmlactions.db.sql.BuildSelect;
import org.xmlactions.db.sql.mysql.MySqlSelectQuery;
import org.xmlactions.db.sql.select.ISqlSelectBuildQuery;
import org.xmlactions.db.sql.select.ISqlTable;
import org.xmlactions.db.sql.select.SqlField;
import org.xmlactions.db.sql.select.SqlSelectInputs;


public class TestBuildInsert extends TestCase
{

    private static final Logger log = LoggerFactory.getLogger(TestBuildInsert.class);

    public void testSingleTableBuildSelectMySql() throws IOException, NestedActionException, ClassNotFoundException,
            InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException,
            BadXMLException {

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
        sqlField.setValue("STREET1");
        sqlFields.add(sqlField);
        sqlField = new SqlField("street2");
        sqlField.setValue("STREET2");
        sqlFields.add(sqlField);
        sqlField = new SqlField("city");
        sqlField.setValue("TRAMORE");
        sqlFields.add(sqlField);
        SqlSelectInputs sqlSelectInputs = BuildSelect.buildSelect(storage, "test", "tb_address", sqlFields, "mysql");
        assertNotNull(sqlSelectInputs);
        ISqlSelectBuildQuery build = new MySqlSelectQuery();
        String insertSql = build.buildInsertSql(UtilsTestHelper.getExecContext(), sqlSelectInputs);
        log.debug("insertSql:" + insertSql);
        assertTrue(insertSql.contains("insert into tb_address"));
        assertTrue(insertSql.contains("tb_address.street1,"));
        assertTrue(insertSql.contains("values (?, ?, ?)"));
    }

    public void testCreatePKTableBuildSelectMySql() throws IOException, NestedActionException, ClassNotFoundException,
            InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException,
            BadXMLException {

        Action action = new Action();
        String page = Action.loadPage("src/test/resources", "db/storage.xml");
        BaseAction[] actions = action.processXML(UtilsTestHelper.getExecContext(), page);
        assertEquals("Invalid number of actions returned from processXML", 1, actions.length);

        assertTrue("Action [" + action.getClass().getName() + "] should be a Storage class",
                actions[0] instanceof Storage);

        Storage storage = (Storage) actions[0];

        List<SqlField> sqlFields = new ArrayList<SqlField>();
        SqlField sqlField;
        sqlField = new SqlField("description");
        sqlField.setValue("description");
        sqlFields.add(sqlField);
        SqlSelectInputs sqlSelectInputs = BuildSelect.buildSelect(storage, "test", "tb_hobby", sqlFields, "mysql");
        assertNotNull(sqlSelectInputs);
        ISqlSelectBuildQuery build = new MySqlSelectQuery();
        ISqlTable[] iSqlTables = build.buildInsertSqls(UtilsTestHelper.getExecContext(), sqlSelectInputs);
        String insertSql = iSqlTables[0].getInsertSql();
        log.debug("insertSql:" + insertSql);
        assertTrue(insertSql.contains("insert into tb_hobby"));
        assertTrue(insertSql.contains("tb_hobby.description"));
        assertTrue(insertSql.contains("values (?, ?)"));
    }

}
