
package org.xmlactions.db.actions;


import java.util.List;

import junit.framework.TestCase;

import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.xmlactions.action.Action;
import org.xmlactions.action.actions.BaseAction;
import org.xmlactions.db.DBConfigException;
import org.xmlactions.db.actions.Database;
import org.xmlactions.db.actions.PK;
import org.xmlactions.db.actions.Storage;
import org.xmlactions.db.actions.Table;


public class TableTest extends TestCase
{

	private final static Logger log = LoggerFactory.getLogger(TableTest.class);

	private static Storage storage;

	public void setUp()
	{

		if (storage == null) {
			try {
				Action action = new Action();
				String page = action.loadPage("src/test/resources", "db/storage.xml");
				BaseAction[] actions = action.processXML(UtilsTestHelper.getExecContext(), page);
				assertEquals("Invalid number of actions returned from processXML", 1, actions.length);

				assertTrue("Action [" + action.getClass().getName() + "] should be a Storage class",
						actions[0] instanceof Storage);

				storage = (Storage) actions[0];
			} catch (Throwable ex) {
				throw new IllegalArgumentException(ex);
			}
		}

	}

	public void testLoadTables()
	{

		List<Database> databases = storage.getDatabases();
		List<Table> tables = databases.get(0).getTables();
		assertEquals(11, tables.size());
		Table table = tables.get(0);
		assertEquals("tb_tracker", table.getName());
		assertEquals("Invalid number of fields in table [" + table.getName(), 8, table.getFields().size());
		PK pk = table.getPk();
		assertEquals("id", pk.getName());
		table = tables.get(1);
		assertEquals("tb_track_progress", table.getName());
	}

	public void testQuery1() throws DBConfigException
	{

		Database database = storage.getDatabase("tracker");
		assertNotNull("tracker database not found in storage", database);

		String[] sql = database.buildQuery(null, "tb_project", null, null, null, null, -1, -1);
		// log.debug("sql:" + sql);
		String expected = "select tb_project.id as \"tb_project_id\",tb_project.description as \"tb_project_description\"\n"
				+ " from \n" + " tracker.tb_project tb_project\n" + " where 1 = 1 ";
		assertEquals("database.buildQuery", expected, sql[1]);
	}

	public void testQuery2() throws DBConfigException
	{

		Database database = storage.getDatabase("tracker");
		assertNotNull("tracker database not found in storage", database);

		String[] sql = database.buildQuery(database.getName(), "tb_tracker", null, null, null, null, -1, -1);
		String expected = "select tb_tracker.id as \"tb_tracker_id\",tb_tracker.timestamp as \"tb_tracker_timestamp\",tb_tracker.description as \"tb_tracker_description\",tb_tracker.fulldescription as \"tb_tracker_fulldescription\",tb_tracker.severityid as \"tb_tracker_severityid\",tb_severity.id as \"tb_severity_id\",tb_severity.description as \"tb_severity_description\",tb_tracker.statusid as \"tb_tracker_statusid\",tb_status.id as \"tb_status_id\",tb_status.description as \"tb_status_description\",tb_status.tooltip as \"tb_status_tooltip\",tb_tracker.projectid as \"tb_tracker_projectid\",tb_project.id as \"tb_project_id\",tb_project.description as \"tb_project_description\",tb_tracker.userid as \"tb_tracker_userid\",tb_user.id as \"tb_user_id\",tb_user.name as \"tb_user_name\",tb_user.firstname as \"tb_user_firstname\",tb_user.lastname as \"tb_user_lastname\",tb_user.email as \"tb_user_email\",tb_user.home_phone as \"tb_user_home_phone\",tb_user.cell_phone as \"tb_user_cell_phone\",tb_user.business_phone as \"tb_user_business_phone\",tb_user.password as \"tb_user_password\"\n"
			+ " from \n"
			+ " tracker.tb_tracker tb_tracker,\n"
			+ " tracker.tb_severity tb_severity,\n"
			+ " tracker.tb_status tb_status,\n"
			+ " tracker.tb_project tb_project,\n"
			+ " tracker.tb_user tb_user\n"
			+ " where 1 = 1 \n"
			+ " and tb_tracker.severityid = tb_severity.id\n"
			+ " and tb_tracker.statusid = tb_status.id\n"
			+ " and tb_tracker.projectid = tb_project.id\n"
			+ " and tb_tracker.userid = tb_user.id";
		assertEquals("database.buildQuery", expected, sql[1]);
	}

	public void testQuery3() throws DBConfigException
	{

		Database database = storage.getDatabase("tracker");
		assertNotNull("tracker database not found in storage", database);

		String[] sql = database.buildQuery(database.getName(), "tb_project", new String[] { "tb_project.id",
				"tb_project.description" }, null, null, null, -1, -1);
		// log.debug("sql:" + sql);
		String expected = "select tb_project_id as \"tb_project.id\",tb_project_description as \"tb_project.description\" from ( select tb_project.id as \"tb_project_id\",tb_project.description as \"tb_project_description\"\n"
				+ " from \n" + " tracker.tb_project tb_project\n" + " where 1 = 1  ) tb";
		assertEquals("database.buildQuery", expected, sql[1]);
	}

	public void testQuery4() throws DBConfigException
	{

		Database database = storage.getDatabase("tracker");
		assertNotNull("tracker database not found in storage", database);

		String[] sql = database.buildQuery(database.getName(), "tb_tracker", new String[] { "tb_tracker.id",
				"tb_tracker.description", "tb_user.name", "tb_status.description" }, null, null, null, -1, -1);
		// log.debug("sql:" + sql);
		String expected = "select tb_tracker_id as \"tb_tracker.id\",tb_tracker_description as \"tb_tracker.description\",tb_user_name as \"tb_user.name\",tb_status_description as \"tb_status.description\" from ( select tb_tracker.id as \"tb_tracker_id\",tb_tracker.timestamp as \"tb_tracker_timestamp\",tb_tracker.description as \"tb_tracker_description\",tb_tracker.fulldescription as \"tb_tracker_fulldescription\",tb_tracker.severityid as \"tb_tracker_severityid\",tb_severity.id as \"tb_severity_id\",tb_severity.description as \"tb_severity_description\",tb_tracker.statusid as \"tb_tracker_statusid\",tb_status.id as \"tb_status_id\",tb_status.description as \"tb_status_description\",tb_status.tooltip as \"tb_status_tooltip\",tb_tracker.projectid as \"tb_tracker_projectid\",tb_project.id as \"tb_project_id\",tb_project.description as \"tb_project_description\",tb_tracker.userid as \"tb_tracker_userid\",tb_user.id as \"tb_user_id\",tb_user.name as \"tb_user_name\",tb_user.firstname as \"tb_user_firstname\",tb_user.lastname as \"tb_user_lastname\",tb_user.email as \"tb_user_email\",tb_user.home_phone as \"tb_user_home_phone\",tb_user.cell_phone as \"tb_user_cell_phone\",tb_user.business_phone as \"tb_user_business_phone\",tb_user.password as \"tb_user_password\"\n"
					+ " from \n"
					+ " tracker.tb_tracker tb_tracker,\n"
					+ " tracker.tb_severity tb_severity,\n"
					+ " tracker.tb_status tb_status,\n"
					+ " tracker.tb_project tb_project,\n"
					+ " tracker.tb_user tb_user\n"
					+ " where 1 = 1 \n"
					+ " and tb_tracker.severityid = tb_severity.id\n"
					+ " and tb_tracker.statusid = tb_status.id\n"
					+ " and tb_tracker.projectid = tb_project.id\n"
					+ " and tb_tracker.userid = tb_user.id ) tb";			
		assertEquals("database.buildQuery", expected, sql[1]);
	}

	public void testGetBean() throws DBConfigException
	{

		Database database = storage.getDatabase("oneloginforall");
		assertNotNull("oneloginforall database not found in storage", database);

		Table tb_projects = database.getTable("tb_projects");
		assertEquals("org.xmlactions.db.testbeans.TbProjects", tb_projects.getBean());
	}

}
