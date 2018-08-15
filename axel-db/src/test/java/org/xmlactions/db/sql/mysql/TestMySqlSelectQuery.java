
package org.xmlactions.db.sql.mysql;


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


public class TestMySqlSelectQuery extends TestCase {

	private static final Logger log = LoggerFactory.getLogger(TestMySqlSelectQuery.class);

	public void testSelect()
			throws IOException, NestedActionException, ClassNotFoundException, InstantiationException,
			IllegalAccessException, InvocationTargetException, NoSuchMethodException, BadXMLException
	{

		Action action = new Action();
		String page = Action.loadPage("src/test/resources", "db/storage.xml");
		BaseAction[] actions = action.processXML(UtilsTestHelper.getExecContext(), page);
		Storage storage = (Storage) actions[0];
		storage.completeStructure();

        List<SqlField> sqlFields = new ArrayList<SqlField>();
        sqlFields.add(new SqlField("tb_name.name"));
        sqlFields.add(new SqlField("tb_address.street1"));
		SqlSelectInputs sqlSelectInputs = BuildSelect.buildSelect(storage, "test", "tb_name", sqlFields, "mysql");
		sqlSelectInputs.addWhereClause("tb_name.name='Neil'");
		sqlSelectInputs.addOrderByClause("name");
		sqlSelectInputs.setLimitFrom("1");
		sqlSelectInputs.setLimitTo("6");

		ISqlSelectBuildQuery build = new MySqlSelectQuery();
        String selectSql = build.buildSelectQuery(UtilsTestHelper.getExecContext(), sqlSelectInputs, null);
		log.debug("selectSql:" + selectSql);
		// select
		// tb_one.name as name,
		// tb_one.address as address,
		// tb_two.street1 as street1,
		// tb_two.street2 as street2,
		// tb_two.city as city
		// from tb_one, tb_two
		// where tb_one.address=tb_two.id
		// order by name
		assertTrue(selectSql.contains("tb_address.street1 as tb_address_street1"));
		assertTrue(selectSql.contains("from tb_name_address tb_name_address"));
		assertTrue(selectSql.contains("join (tb_name tb_name)"));
		assertTrue(selectSql.contains("on tb_name_address.name_id = tb_name.id"));
		assertTrue(selectSql.contains("join (tb_address address)"));
		assertTrue(selectSql.contains("on tb_name_address.address_id = address.id"));
		assertTrue(selectSql.contains("tb_name_address.name_id = tb_name.id"));
		assertTrue(selectSql.contains("tb_name.name='Neil'"));
		assertTrue(selectSql.contains("order by name"));

    }

}
