package org.xmlactions.db.h2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmlactions.db.DBConnector;
import org.xmlactions.db.ServerDB;
import org.xmlactions.db.StorageContainer;
import org.xmlactions.db.actions.UtilsTestHelper;
import org.xmlactions.db.config.StorageConfig;
import org.xmlactions.db.sql.h2.H2SelectQuery;

public class H2TestDatabase extends ATestDatabase {

	
	private static final Logger log = LoggerFactory.getLogger(H2TestDatabase.class);

	private H2TestDatabase() {
	}
	
	public static H2TestDatabase getInstance()  throws Exception {
		
		if (getATestDatabase() == null) {
			
			log.warn("!!! new H2TestDatabase !!!");
			ATestDatabase h2td = new H2TestDatabase();
			h2td.setExecContext(UtilsTestHelper.getExecContext());
			h2td.setupDatabase();
			h2td.setATestDatabase(h2td);
		}
		
		return (H2TestDatabase)getATestDatabase();
		
	}

	
	public void setupDatabase() throws Exception {

		setServerDB(new ServerDB());
		getServerDB().startServer("/db/h2_db.xml");

		String storageFile = "db/h2_test_storage.xml";
		StorageContainer storageContainer = new StorageContainer(storageFile, getExecContext());

		DBConnector dbConnector = new DBConnector("org.h2.Driver", "jdbc:h2:~/target/test", "sa", "password");

		StorageConfig storageConfig = new StorageConfig();
		storageConfig.setStorageContainer(storageContainer);
		storageConfig.setDatabaseName("h2_test");
		storageConfig.setSqlBuilder(new H2SelectQuery());
		storageConfig.setDbSpecificName("h2");
		storageConfig.setDbConnector(dbConnector);
		setStorageConfig(storageConfig);
   	}
	

}
