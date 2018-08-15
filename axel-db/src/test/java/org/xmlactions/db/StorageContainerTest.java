
package org.xmlactions.db;

import static org.junit.Assert.*;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.xmlactions.action.Action;
import org.xmlactions.action.NestedActionException;
import org.xmlactions.action.actions.BaseAction;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.action.config.NoPersistenceExecContext;
import org.xmlactions.common.collections.MapUtils;
import org.xmlactions.common.xml.BadXMLException;
import org.xmlactions.db.StorageContainer;
import org.xmlactions.db.actions.Storage;
import org.xmlactions.db.actions.UtilsTestHelper;
import org.xmlactions.db.env.EnvironmentAccess;

import junit.framework.TestCase;


public class StorageContainerTest
{

	private static BaseAction[] actions;

	private static String storageFile = "/db/storage.xml";
	private static String storageFile2 = "/db/storage_insert.xml";
	private static String storageFile3 = "/db/storage_insert.xml";
	
	@Before
	public void beforeMethod() {
		//org.junit.Assume.assumeTrue(EnvironmentAccess.runDatabaseTests());
	}
	
	

	@Test
	public void testConstructor()
			throws Exception
	{

		IExecContext execContext = UtilsTestHelper.getExecContext();
		StorageContainer storageContainer = new StorageContainer(storageFile, execContext);
		assertTrue(storageContainer.getStorage() instanceof Storage);
	}

	@Test
	public void testConstructor2()
			throws Exception
	{

		IExecContext execContext = UtilsTestHelper.getExecContext();
		StorageContainer storageContainer = new StorageContainer(storageFile2, execContext);
		assertTrue(storageContainer.getStorage() instanceof Storage);
	}

	@Test
	public void testConstructorFromPropertyFile()
			throws Exception
	{

		Map<String, Object> map = MapUtils.loadMapFromProperties("/config/db/actions_db.properties");
		List <Object> list = new ArrayList<Object>();
		list.add(map);
		IExecContext execContext = new NoPersistenceExecContext(list,null);
		StorageContainer storageContainer = new StorageContainer(storageFile, execContext);
		assertTrue(storageContainer.getStorage() instanceof Storage);
	}

	@Test
	public void testConstructorFromFiles()
			throws Exception
	{

		Map<String, Object> map = MapUtils.loadMapFromProperties("/config/db/actions_db.properties");
		StorageContainer storageContainer = new StorageContainer(storageFile, "/config/db/actions_db.properties");
		assertTrue(storageContainer.getStorage() instanceof Storage);
	}

	@Test
	public void testInserts()
			throws Exception
	{

		Map<String, Object> map = MapUtils.loadMapFromProperties("/config/db/actions_db.properties");
		StorageContainer storageContainer = new StorageContainer(storageFile2, "/config/db/actions_db.properties");
		assertTrue(storageContainer.getStorage() instanceof Storage);
	}

	@Test
	public void testInsertsError()
			throws Exception
	{

		Map<String, Object> map = MapUtils.loadMapFromProperties("/config/db/actions_db.properties");
		StorageContainer storageContainer = new StorageContainer(storageFile3, "/config/db/actions_db.properties");
		assertTrue(storageContainer.getStorage() instanceof Storage);
	}

	public static BaseAction[] getActions()
	{

		if (actions == null) {

			try {
				Action action = new Action();
				String page = action.loadPage("src/test/resources", storageFile);
				actions = action.processXML(UtilsTestHelper.getExecContext(), page);
			} catch (Throwable e) {
				throw new RuntimeException("Failed to load Storage [" + storageFile + "]:" + e.getMessage());
			}
		}
		return actions;
	}

}
