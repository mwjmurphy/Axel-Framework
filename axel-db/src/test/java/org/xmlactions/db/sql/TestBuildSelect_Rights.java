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


public class TestBuildSelect_Rights extends TestCase {

	private static final Logger log = LoggerFactory.getLogger(TestBuildSelect_Rights.class);

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
        sqlFields.add(new SqlField("tb_rights.id"));
        sqlFields.add(new SqlField("tb_projects.name"));
        sqlFields.add(new SqlField("tb_rights.permission"));


		SqlSelectInputs sqlSelectInputs = BuildSelect.buildSelect(storage, "oneloginforall", "tb_rights", sqlFields, "mysql");
		assertNotNull(sqlSelectInputs);
		ISqlSelectBuildQuery build = new MySqlSelectQuery();
        String selectSql = build.buildSelectQuery(UtilsTestHelper.getExecContext(), sqlSelectInputs, null);
		log.debug(selectSql);
		assertTrue(selectSql.contains("tb_rights.id as \"tb_rights.id\""));
		assertTrue(selectSql.contains("from tb_rights tb_rights"));
		assertTrue(selectSql.contains("left join (tb_projects"));
		assertTrue(selectSql.contains("on tb_rights.projectid = tb_projects.id"));
	}

}
