
package org.xmlactions.db.sql;


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
import org.xmlactions.db.sql.oracle.OracleSelectQuery;
import org.xmlactions.db.sql.select.ISqlSelectBuildQuery;
import org.xmlactions.db.sql.select.SqlField;
import org.xmlactions.db.sql.select.SqlSelectInputs;


public class TestBuildSelectFKAs extends TestCase
{

    private static final Logger log = LoggerFactory.getLogger(TestBuildSelectFKAs.class);

    public void testLookupTableBuildSelectMySql() throws IOException, NestedActionException, ClassNotFoundException,
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
        sqlFields.add(new SqlField("associate.associateid"));
        sqlFields.add(new SqlField("associate.name"));
        sqlFields.add(new SqlField("id1.fk_id_1"));
        sqlFields.add(new SqlField("id1.value"));
        sqlFields.add(new SqlField("id2.fk_id_2"));
        sqlFields.add(new SqlField("id2.value"));
        sqlFields.add(new SqlField("id3.fk_id_2"));
        sqlFields.add(new SqlField("id3.value"));
        SqlSelectInputs sqlSelectInputs = BuildSelect.buildSelect(storage, "test", "associate", sqlFields, "mysql");
        assertNotNull(sqlSelectInputs);
        sqlSelectInputs.setLimitFrom("1");
        sqlSelectInputs.setLimitTo("20");
        ISqlSelectBuildQuery build = new MySqlSelectQuery();
        String selectSql = build.buildSelectQuery(UtilsTestHelper.getExecContext(), sqlSelectInputs, null);
        log.debug("selectSql:" + selectSql);
        assertTrue(selectSql.contains("id1.value as id1_value"));
        assertTrue(selectSql.contains("from tb_associate associate"));
        assertTrue(selectSql.contains("on associate.associateid = id1.fk_id_1 and id1.fk_id_2=1"));
    }

    public void testLookupTableBuildSelectOracle() throws IOException, NestedActionException, ClassNotFoundException,
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
        sqlFields.add(new SqlField("associate.associateid"));
        sqlFields.add(new SqlField("associate.name"));
        sqlFields.add(new SqlField("id1.fk_id_1"));
        sqlFields.add(new SqlField("id1.value"));
        sqlFields.add(new SqlField("id2.fk_id_2"));
        sqlFields.add(new SqlField("id2.value"));
        sqlFields.add(new SqlField("id3.fk_id_2"));
        sqlFields.add(new SqlField("id3.value"));
        SqlSelectInputs sqlSelectInputs = BuildSelect.buildSelect(storage, "test", "associate", sqlFields, "oracle");
        assertNotNull(sqlSelectInputs);
        sqlSelectInputs.setLimitFrom("1");
        sqlSelectInputs.setLimitTo("20");
        ISqlSelectBuildQuery build = new OracleSelectQuery();
        String selectSql = build.buildSelectQuery(UtilsTestHelper.getExecContext(), sqlSelectInputs, null);
        log.debug("selectSql:" + selectSql);
        assertTrue(selectSql.contains("id1.value as id1_value"));
        assertTrue(selectSql.contains("from tb_associate associate"));
        assertTrue(selectSql.contains("on associate.associateid = id1.fk_id_1 and id1.fk_id_2=1"));
    }

}
