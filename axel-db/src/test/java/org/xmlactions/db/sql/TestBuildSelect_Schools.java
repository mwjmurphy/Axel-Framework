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


public class TestBuildSelect_Schools extends TestCase {

	private static final Logger log = LoggerFactory.getLogger(TestBuildSelect_Schools.class);

	public void testMultipleTableBuildSelectMySql1()
	throws IOException, NestedActionException, ClassNotFoundException, InstantiationException,
	IllegalAccessException, InvocationTargetException, NoSuchMethodException, BadXMLException
	{

		Action action = new Action();
		String page = Action.loadPage("src/test/resources", "db/school_storage.xml");
		BaseAction[] actions = action.processXML(UtilsTestHelper.getExecContext(), page);
		assertEquals("Invalid number of actions returned from processXML", 1, actions.length);

		assertTrue("Action [" + action.getClass().getName() + "] should be a Storage class",
				actions[0] instanceof Storage);

		Storage storage = (Storage) actions[0];

        List<SqlField> sqlFields = new ArrayList<SqlField>();
        
        sqlFields.add(new SqlField("tb_person.id"));
        sqlFields.add(new SqlField("tb_person_school_link.id"));
        sqlFields.add(new SqlField("tb_person.lastname"));
        sqlFields.add(new SqlField("tb_person.firstname"));
        sqlFields.add(new SqlField("tb_person.dob"));
        sqlFields.add(new SqlField("tb_person.nationality"));
        sqlFields.add(new SqlField("tb_person.person_type"));
        sqlFields.add(new SqlField("tb_school_class.name"));
        sqlFields.add(new SqlField("tb_sen.dis_code"));

		SqlSelectInputs sqlSelectInputs = BuildSelect.buildSelect(storage, "school", "tb_person", sqlFields, "mysql");
		assertNotNull(sqlSelectInputs);
		sqlSelectInputs.addWhereClause("tb_person_school_link.school_id=1 and tb_person.person_type='STU'");
		sqlSelectInputs.addOrderByClause("tb_person.lastname, tb_person.firstname");

		ISqlSelectBuildQuery build = new MySqlSelectQuery();
        String selectSql = build.buildSelectQuery(UtilsTestHelper.getExecContext(), sqlSelectInputs, null);
		log.debug(selectSql);
		
		int index1 = selectSql.indexOf("left join (tb_person tb_person)");
		assertTrue(index1 >= 0);
		int index2 = selectSql.indexOf("join (tb_sen tb_sen)");
		assertTrue(index2 >= 0);
		assertTrue(index2 > index1);
		assertTrue(selectSql.contains("on tb_person_school_link.person_id = tb_person.id"));
		assertTrue(selectSql.contains("on tb_sen.person_id = tb_person.id"));
		assertTrue(selectSql.contains("where tb_person_school_link.school_id=1 and tb_person.person_type='STU'"));
		assertTrue(selectSql.contains("order by tb_person.lastname, tb_person.firstname"));
	}
	
	public void testMultipleTableBuildSelectMySql2()
	throws IOException, NestedActionException, ClassNotFoundException, InstantiationException,
	IllegalAccessException, InvocationTargetException, NoSuchMethodException, BadXMLException
	{

		Action action = new Action();
		String page = Action.loadPage("src/test/resources", "db/school_storage.xml");
		BaseAction[] actions = action.processXML(UtilsTestHelper.getExecContext(), page);
		assertEquals("Invalid number of actions returned from processXML", 1, actions.length);

		assertTrue("Action [" + action.getClass().getName() + "] should be a Storage class",
				actions[0] instanceof Storage);

		Storage storage = (Storage) actions[0];

        List<SqlField> sqlFields = new ArrayList<SqlField>();
        
        sqlFields.add(new SqlField("tb_person.id"));
        sqlFields.add(new SqlField("tb_person_school_link.id"));
        sqlFields.add(new SqlField("tb_class_student.id"));
        sqlFields.add(new SqlField("tb_person.lastname"));
        sqlFields.add(new SqlField("tb_person.firstname"));
        sqlFields.add(new SqlField("tb_person.dob"));
        sqlFields.add(new SqlField("tb_person.nationality"));
        sqlFields.add(new SqlField("tb_person.person_type"));
        sqlFields.add(new SqlField("tb_school_class.name"));
        sqlFields.add(new SqlField("tb_sen.dis_code"));

		SqlSelectInputs sqlSelectInputs = BuildSelect.buildSelect(storage, "school", "tb_person", sqlFields, "mysql");
		assertNotNull(sqlSelectInputs);
		sqlSelectInputs.addWhereClause("tb_person_school_link.school_id=1 and tb_person.person_type='STU'");
		sqlSelectInputs.addOrderByClause("tb_person.lastname, tb_person.firstname");

		ISqlSelectBuildQuery build = new MySqlSelectQuery();
        String selectSql = build.buildSelectQuery(UtilsTestHelper.getExecContext(), sqlSelectInputs, null);
		log.debug(selectSql);
		
		int index1 = selectSql.indexOf("left join (tb_person tb_person)");
		assertTrue(index1 >= 0);
		int index2 = selectSql.indexOf("join (tb_sen tb_sen)");
		assertTrue(index2 >= 0);
		assertTrue(index2 > index1);
		assertTrue(selectSql.contains("on tb_person_school_link.person_id = tb_person.id"));
		assertTrue(selectSql.contains("on tb_sen.person_id = tb_person.id"));
		assertTrue(selectSql.contains("where tb_person_school_link.school_id=1 and tb_person.person_type='STU'"));
		assertTrue(selectSql.contains("order by tb_person.lastname, tb_person.firstname"));
	}

}
