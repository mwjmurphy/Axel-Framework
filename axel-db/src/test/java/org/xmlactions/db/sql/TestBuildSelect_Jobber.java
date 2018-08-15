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


public class TestBuildSelect_Jobber extends TestCase {

	private static final Logger log = LoggerFactory.getLogger(TestBuildSelect_Jobber.class);

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
        sqlFields.add(new SqlField("tb_job.brief_description"));
        sqlFields.add(new SqlField("tb_job_group.description"));
        sqlFields.add(new SqlField("tb_job_category.description"));
        sqlFields.add(new SqlField("tb_job.create_date"));
        //sqlFields.add(new SqlField("tb_job_links.id"));

		SqlSelectInputs sqlSelectInputs = BuildSelect.buildSelect(storage, "jobber", "tb_job", sqlFields, "mysql");
		assertNotNull(sqlSelectInputs);
		ISqlSelectBuildQuery build = new MySqlSelectQuery();
        String selectSql = build.buildSelectQuery(UtilsTestHelper.getExecContext(), sqlSelectInputs, null);
		log.debug(selectSql);
		assertTrue(selectSql.contains("join (tb_job"));
		assertTrue(selectSql.contains("on tb_job_links.id_job = tb_job.id"));
		assertTrue(selectSql.contains("join (tb_job_category"));
		assertTrue(selectSql.contains("on tb_job_links.id_job_category = tb_job_category.id"));
		assertTrue(selectSql.contains("left join (tb_job_group"));
		assertTrue(selectSql.contains("on tb_job_category.id_group = tb_job_group.id"));
	}

}
