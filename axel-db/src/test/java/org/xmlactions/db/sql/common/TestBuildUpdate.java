
package org.xmlactions.db.sql.common;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.junit.Test;
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


public class TestBuildUpdate
{

	private static final Logger log = LoggerFactory.getLogger(TestBuildUpdate.class);

	@Test
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
		sqlSelectInputs.addWhereClause("tb_address.id=1");
        //String updateSql = build.buildUpdateSql(UtilsTestHelper.getExecContext(), sqlSelectInputs);
		ISqlTable[] iSqlTables = build.buildUpdateSqls(UtilsTestHelper.getExecContext(), sqlSelectInputs);
		String updateSql = iSqlTables[0].getUpdateSql();
		log.debug("updateSql:" + updateSql);
        assertTrue(updateSql.contains("update tb_address address"));
		assertTrue(updateSql.contains("set"));
		assertTrue(updateSql.contains("address.street1="));
		assertTrue(updateSql.contains("where tb_address.id=1"));
	}

}
