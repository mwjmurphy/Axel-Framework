
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
import org.xmlactions.common.xml.XMLObject;
import org.xmlactions.db.DBConfigException;
import org.xmlactions.db.actions.CommonStorageField;
import org.xmlactions.db.actions.Database;
import org.xmlactions.db.actions.Storage;
import org.xmlactions.db.actions.Xml;


public class XmlTest extends TestCase
{

	private final static Logger log = LoggerFactory.getLogger(XmlTest.class);

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
		List<Xml> xmls = storage.getXmls();
		assertEquals("Missing Xml from Storage:" + storage.getName(), 1, xmls.size());

		Xml xml = xmls.get(0);
		XMLObject xo = xml.getXml();
		assertNotNull("XMLObject not loaded for [" + xml.getResource() + "]", xo);
		// assertEquals("tracker", database.getName());
		// assertEquals("Invalid number of tables in database [" + database.getName() + "]", 11, database.getTables()
		// .size());
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
