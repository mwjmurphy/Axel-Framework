package org.xmlactions.test.db.h2;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.xmlactions.action.ActionConst;
import org.xmlactions.action.config.ExecContext;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.db.DBConnector;
import org.xmlactions.db.ServerDB;
import org.xmlactions.db.StorageContainer;
import org.xmlactions.db.config.StorageConfig;
import org.xmlactions.db.h2.ATestDatabase;
import org.xmlactions.db.sql.h2.H2SelectQuery;

public class H2TestDatabase extends ATestDatabase {


	private static final Logger log = LoggerFactory.getLogger(H2TestDatabase.class);

	private H2TestDatabase() {
	}

	public static H2TestDatabase getInstance()  throws Exception {

		if (getATestDatabase() == null) {

			log.warn("!!! new H2TestDatabase !!!");

			ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(new String[] {
					ActionConst.SPRING_STARTUP_CONFIG, "classpath*:config/spring/test-spring-pager-web-startup.xml" });
			IExecContext execContext = (ExecContext)applicationContext.getBean(ActionConst.EXEC_CONTEXT_BEAN_REF);
			execContext.setApplicationContext(applicationContext);

			
			ATestDatabase h2td = new H2TestDatabase();
			h2td.setExecContext(execContext);
			h2td.setupDatabase();
			h2td.setATestDatabase(h2td);
		}

		return (H2TestDatabase)getATestDatabase();

	}


	public void setupDatabase() throws Exception {

		setServerDB(new ServerDB());
		getServerDB().startServer("/db/h2_db.xml");		// file comes from axel-db - src/test/resources/db

		String storageFile = "db/h2_test_storage.xml";	// file comes from axel-db - src/test/resources/db
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
