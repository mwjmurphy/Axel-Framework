
package org.xmlactions.test.db;


import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.xmlactions.action.NestedActionException;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.action.config.NoPersistenceExecContext;
import org.xmlactions.common.collections.MapUtils;
import org.xmlactions.common.io.ResourceUtils;
import org.xmlactions.common.xml.BadXMLException;
import org.xmlactions.db.StorageContainer;
import org.xmlactions.db.actions.Storage;

import junit.framework.TestCase;


public class StorageContainerTest extends TestCase
{

	private static Storage storage;

	public void setUp()
	{

		if (storage == null) {
			try {
				Properties props = new Properties();
				props.load(ResourceUtils.getResourceURL("/config/db/actions_db.properties").openStream());
				Map<String, Object> map = MapUtils.propertiesToMap(props);
				List<Object>list = new ArrayList<Object>();
				list.add(map);
				IExecContext execContext = new NoPersistenceExecContext(list, null);
				StorageContainer storageContainer = new StorageContainer("/db/storage.xml", execContext);
				storage = storageContainer.getStorage();
			} catch (Throwable e) {
				fail("Error Loading action properties for [/config/db/actions_db.properties]\n" + e.getMessage());
			}
		}
	}

	public void testConstructorWithProperties()
			throws Exception
	{

		Properties props = new Properties();
		props.load(ResourceUtils.getResourceURL("/config/db/actions_db.properties").openStream());
		Map<String, Object> map = MapUtils.propertiesToMap(props);
		List<Object>list = new ArrayList<Object>();
		list.add(map);
		IExecContext execContext = new NoPersistenceExecContext(list, null);
		execContext.putAll(map);

		StorageContainer storageContainer = new StorageContainer("/db/storage.xml", execContext);
		Storage storage = storageContainer.getStorage();
		assertNotNull(storage);
	}

	public void testConstructorWithPropertiesFile()
			throws Exception
	{

		Properties props = new Properties();
		props.load(ResourceUtils.getResourceURL("/config/db/actions_db.properties").openStream());
		Map<String, Object> map = MapUtils.loadMapFromProperties("/config/db/actions_db.properties");
		List<Object>list = new ArrayList<Object>();
		list.add(map);
		IExecContext execContext = new NoPersistenceExecContext(list, null);
		execContext.putAll(map);

		StorageContainer storageContainer = new StorageContainer("/db/storage.xml", execContext);
		Storage storage = storageContainer.getStorage();
		assertNotNull(storage);
	}

	public void testConstructorFromFiles()
			throws Exception
	{

		Map<String, Object> map = MapUtils.loadMapFromProperties("/config/db/actions_db.properties");
		StorageContainer storageContainer = new StorageContainer("/db/storage.xml", "/config/db/actions_db.properties");
		assertTrue(storageContainer.getStorage() instanceof Storage);
	}

	public void testConstructor()
			throws IOException, NestedActionException, ClassNotFoundException, InstantiationException,
			IllegalAccessException, InvocationTargetException, NoSuchMethodException, BadXMLException
	{

		assertTrue(storage instanceof Storage);
	}
}
