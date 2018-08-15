package org.xmlactions.db.config;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.junit.Before;
import org.junit.Test;
import org.xmlactions.action.NestedActionException;
import org.xmlactions.common.xml.BadXMLException;
import org.xmlactions.db.DBConnector;
import org.xmlactions.db.StorageContainer;
import org.xmlactions.db.actions.Storage;
import org.xmlactions.db.config.StorageConfig;
import org.xmlactions.db.env.EnvironmentAccess;




public class TestStorageConfig
{

	@Before
	public void beforeMethod() {
		org.junit.Assume.assumeTrue(EnvironmentAccess.runDatabaseTests());
	}
	
	@Test
	public void testCreate()
			throws Exception
	{

		DBConnector dbConnector = new DBConnector();
		dbConnector.setDataSourceReferenceName("jdbc/MySQLDB");
		dbConnector.setInitContextLookup("java:/comp/env");

		StorageContainer storageContainer = new StorageContainer("/db/storage.xml", "/config/db/actions_db.properties");

		StorageConfig storageConfig = new StorageConfig();
		storageConfig.setDatabaseName("test");
		storageConfig.setDbConnector(dbConnector);
		storageConfig.setStorageContainer(storageContainer);

		Storage storage = storageContainer.getStorage();
		storage.getDatabase(storageConfig.getDatabaseName());
		assertNotNull(storage);

	}
}
