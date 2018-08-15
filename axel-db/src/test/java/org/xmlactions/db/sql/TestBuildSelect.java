
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
import org.xmlactions.db.sql.select.ISqlSelectBuildQuery;
import org.xmlactions.db.sql.select.SqlField;
import org.xmlactions.db.sql.select.SqlSelectInputs;


public class TestBuildSelect extends TestCase
{

	private static final Logger log = LoggerFactory.getLogger(TestBuildSelect.class);

	public void testSingleTableBuildSelectMySql()
			throws IOException, NestedActionException, ClassNotFoundException, InstantiationException,
			IllegalAccessException, InvocationTargetException, NoSuchMethodException, BadXMLException
	{

		Action action = new Action();
		String page = Action.loadPage("src/test/resources", "db/storage.xml");
		BaseAction[] actions = action.processXML(UtilsTestHelper.getExecContext(), page);
		assertEquals("Invalid number of actions returned from processXML", 1, actions.length);

		assertTrue("Action [" + action.getClass().getName() + "] should be a Storage class",
				actions[0] instanceof Storage);

		Storage storage = (Storage) actions[0];

        List<SqlField> sqlFields = new ArrayList<SqlField>();
        sqlFields.add(new SqlField("street1"));
        sqlFields.add(new SqlField("street2"));
        sqlFields.add(new SqlField("city"));
        SqlSelectInputs sqlSelectInputs = BuildSelect.buildSelect(storage, "test", "tb_address", sqlFields, "mysql");
		assertNotNull(sqlSelectInputs);
		ISqlSelectBuildQuery build = new MySqlSelectQuery();
        String selectSql = build.buildSelectQuery(UtilsTestHelper.getExecContext(), sqlSelectInputs, null);
		log.debug("selectSql:" + selectSql);
        assertTrue(selectSql.contains("tb_address.street1 as \"tb_address.street1\""));
		assertTrue(selectSql.contains("from tb_address"));
	}

	public void testTwoTableBuildSelectMySql()
			throws IOException, NestedActionException, ClassNotFoundException, InstantiationException,
			IllegalAccessException, InvocationTargetException, NoSuchMethodException, BadXMLException
	{

		Action action = new Action();
		String page = Action.loadPage("src/test/resources", "db/storage.xml");
		BaseAction[] actions = action.processXML(UtilsTestHelper.getExecContext(), page);
		assertEquals("Invalid number of actions returned from processXML", 1, actions.length);

		assertTrue("Action [" + action.getClass().getName() + "] should be a Storage class",
				actions[0] instanceof Storage);

		Storage storage = (Storage) actions[0];

        List<SqlField> sqlFields = new ArrayList<SqlField>();
        sqlFields.add(new SqlField("tb_hobby.description"));
        sqlFields.add(new SqlField("tb_name_hobby.id"));
        SqlSelectInputs sqlSelectInputs = BuildSelect.buildSelect(storage, "test", "tb_name_hobby", sqlFields, "mysql");
		assertNotNull(sqlSelectInputs);
		ISqlSelectBuildQuery build = new MySqlSelectQuery();
        String selectSql = build.buildSelectQuery(UtilsTestHelper.getExecContext(), sqlSelectInputs, null);
		log.debug(selectSql);
        assertTrue(selectSql.contains("tb_hobby.description as \"tb_hobby.description\""));
		assertTrue(selectSql.contains("from tb_name_hobby"));
		assertTrue(selectSql.contains("join (tb_hobby"));
		assertTrue(selectSql.contains("on tb_name_hobby.hobby_id = tb_hobby.id"));

	}
	public void testMultipleTableBuildSelectMySql()
			throws IOException, NestedActionException, ClassNotFoundException, InstantiationException,
			IllegalAccessException, InvocationTargetException, NoSuchMethodException, BadXMLException
	{

		Action action = new Action();
		String page = Action.loadPage("src/test/resources", "db/storage.xml");
		BaseAction[] actions = action.processXML(UtilsTestHelper.getExecContext(), page);
		assertEquals("Invalid number of actions returned from processXML", 1, actions.length);

		assertTrue("Action [" + action.getClass().getName() + "] should be a Storage class",
				actions[0] instanceof Storage);

		Storage storage = (Storage) actions[0];

        List<SqlField> sqlFields = new ArrayList<SqlField>();
		// tableAndFieldNames.add("tb_name.name");
        sqlFields.add(new SqlField("tb_name.name"));
        sqlFields.add(new SqlField("tb_address.street1"));
        sqlFields.add(new SqlField("tb_address.city"));
        sqlFields.add(new SqlField("tb_address_category.description"));
        sqlFields.add(new SqlField("tb_hobby.description"));
		SqlSelectInputs sqlSelectInputs = BuildSelect.buildSelect(storage, "test", "tb_name_address", sqlFields, "mysql");
		assertNotNull(sqlSelectInputs);
		ISqlSelectBuildQuery build = new MySqlSelectQuery();
        String selectSql = build.buildSelectQuery(UtilsTestHelper.getExecContext(), sqlSelectInputs, null);
		log.debug(selectSql);
        assertTrue(selectSql.contains("tb_name.name as \"tb_name.name\""));
        assertTrue(selectSql.contains("tb_address.street1 as \"tb_address.street1\""));
		assertTrue(selectSql.contains("from tb_name"));
        assertTrue(selectSql.contains("tb_name_address"));
	}
}
