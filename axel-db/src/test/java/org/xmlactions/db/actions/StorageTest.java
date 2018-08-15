
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
import org.xmlactions.db.actions.CommonStorageField;
import org.xmlactions.db.actions.Database;
import org.xmlactions.db.actions.Storage;
import org.xmlactions.db.actions.Table;

public class StorageTest extends TestCase
{

	private final static Logger log = LoggerFactory.getLogger(StorageTest.class);

	public void testLoadPage() throws IOException
	{

		Action action = new Action();
		String page = action.loadPage("src/test/resources", "db/storage.xml");
		assertNotNull(page);
	}

	public void testLoadStorage()
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

		List<Table> tables = databases.get(0).getTables();
		assertEquals("Invalid number of tables in database [" + databases.get(0).getName() + "]", 11, tables.size());

		List<CommonStorageField> fields = tables.get(0).getFields();
		assertEquals("Invalid number of fields in table [" + tables.get(0).getName(), 8, fields.size());
	}

	public void testLoadStorageWithInsert()
			throws IOException, NestedActionException, ClassNotFoundException, InstantiationException,
			IllegalAccessException, InvocationTargetException, NoSuchMethodException, BadXMLException
	{

		Action action = new Action();
		String page = action.loadPage("src/test/resources", "db/storage_insert.xml");
		BaseAction[] actions = action.processXML(UtilsTestHelper.getExecContext(), page);
		assertEquals("Invalid number of actions returned from processXML", 1, actions.length);
		Storage storage = (Storage) actions[0];
		assertEquals("Missign DBInsert", 2, storage.getDatabases().get(1).getInserts().size());
		
	}


}
