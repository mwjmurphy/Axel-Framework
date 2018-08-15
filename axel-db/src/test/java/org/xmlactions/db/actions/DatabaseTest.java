
package org.xmlactions.db.actions;


import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import junit.framework.TestCase;

import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.xmlactions.action.Action;
import org.xmlactions.action.NestedActionException;
import org.xmlactions.action.actions.BaseAction;
import org.xmlactions.common.xml.BadXMLException;
import org.xmlactions.db.DBConfigException;
import org.xmlactions.db.actions.CommonStorageField;
import org.xmlactions.db.actions.Database;
import org.xmlactions.db.actions.DbSpecific;
import org.xmlactions.db.actions.Storage;


public class DatabaseTest extends TestCase
{

	private final static Logger log = LoggerFactory.getLogger(DatabaseTest.class);

	public void testLoadDatabase()
			throws IOException, NestedActionException, ClassNotFoundException, InstantiationException,
			IllegalAccessException, InvocationTargetException, NoSuchMethodException, BadXMLException
	{

		Action action = new Action();
		String page = action.loadPage("src/test/resources", "db/storage.xml");
		BaseAction[] actions = action.processXML(UtilsTestHelper.getExecContext(), page);
		assertEquals("Invalid number of actions returned from processXML", 1, actions.length);

		assertTrue("Action [" + action.getClass().getName() + "] should be a Storage class",
				actions[0] instanceof Storage);

		Storage storage = (Storage) actions[0];
		List<Database> databases = storage.getDatabases();
		assertEquals("Missing Database from Storage:" + storage.getName(), 5, databases.size());

		Database database = databases.get(0);
		assertEquals("tracker", database.getName());
		assertEquals("Invalid number of tables in database [" + database.getName() + "]", 11, database.getTables()
				.size());

        assertTrue("Didn't load sqls", database.getSqls().size() > 0);
        assertTrue("Didn't load db_specifics", database.getDbSpecifics().size() > 0);
        DbSpecific dbSpecific = database.getDbSpecifics().get(0);
        assertTrue("Didn't load db_specific.sqls", dbSpecific.getSqls().size() > 0);
	}

	public void testGetStorageField()
			throws IOException, NestedActionException, ClassNotFoundException, InstantiationException,
			IllegalAccessException, InvocationTargetException, NoSuchMethodException, BadXMLException
	{

		Action action = new Action();
		String page = action.loadPage("src/test/resources", "db/storage.xml");
		BaseAction[] actions = action.processXML(UtilsTestHelper.getExecContext(), page);
		Storage storage = (Storage) actions[0];
		List<Database> databases = storage.getDatabases();
		CommonStorageField sf = databases.get(0).getStorageField("tb_tracker.id");
	}

	public void testBuildQuery()
			throws DBConfigException, IOException, NestedActionException, ClassNotFoundException,
			InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException,
			BadXMLException
	{

		Action action = new Action();
		String page = action.loadPage("src/test/resources", "db/storage.xml");
		BaseAction[] actions = action.processXML(UtilsTestHelper.getExecContext(), page);
		assertEquals("Invalid number of actions returned from processXML", 1, actions.length);

		assertTrue("Action [" + action.getClass().getName() + "] should be a Storage class",
				actions[0] instanceof Storage);

		Storage storage = (Storage) actions[0];
		Database database = storage.getDatabase("oneloginforall");
		String databaseName = "oneloginforall";
		String tableName = "tb_user_rights";
		String[] fields = new String[] { "tb_user_rights.id", "tb_user.name", "tb_projects.name",
				"tb_rights.permission" };
		String[] query = database.buildQuery(databaseName, tableName, fields, null, null, null, -1, -1);
		log.debug("query[0]:" + query[0]);
		log.debug("query[1]:" + query[1]);

	}
}
