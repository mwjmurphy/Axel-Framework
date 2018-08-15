package org.xmlactions.db.mysql;

import junit.framework.TestCase;

import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.db.StorageContainer;
import org.xmlactions.db.actions.Storage;
import org.xmlactions.db.actions.UtilsTestHelper;


public class StorageContainerTest extends TestCase
{

	private final static Logger log = LoggerFactory.getLogger(StorageContainerTest.class);

	private final static String storageFile = "/db/storage.xml";

	private final static String propertiesFile = "/db/mysql.properties";

	private static StorageContainer storageContainer;

	public static StorageContainer getStorageContainer() throws Exception
	{
		if (storageContainer == null) {
			IExecContext execContext = UtilsTestHelper.getExecContext();
			storageContainer = new StorageContainer(storageFile, execContext);
		}
		return storageContainer;
	}
	public void testCreate() throws Exception
	{

		assertTrue(getStorageContainer().getStorage() instanceof Storage);
	}

}
