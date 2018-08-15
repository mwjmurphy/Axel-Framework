package org.xmlactions.db.h2;

import java.sql.Connection;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.db.ServerDB;
import org.xmlactions.db.StorageContainer;
import org.xmlactions.db.actions.Database;
import org.xmlactions.db.actions.Storage;
import org.xmlactions.db.actions.Table;
import org.xmlactions.db.config.StorageConfig;

public abstract class ATestDatabase {

	
	private static final Logger log = LoggerFactory.getLogger(ATestDatabase.class);
	private static ATestDatabase aTestDatabase = null;
	private ServerDB serverDB;
	private StorageConfig storageConfig;
	private IExecContext execContext;
	
	
	abstract public void setupDatabase() throws Exception;	

	public void stopDatabase() throws Exception {
		log.debug("stop database server");
		serverDB.stopServer();
	}

	public static ATestDatabase getATestDatabase() {
		return aTestDatabase;
	}

	public void setATestDatabase(ATestDatabase aTestDatabase) {
		ATestDatabase.aTestDatabase = aTestDatabase;
	}

	public IExecContext getExecContext() {
		return execContext;
	}

	public void setExecContext(IExecContext execContext) {
		this.execContext = execContext;
	}
	
	public void setServerDB(ServerDB serverDB) {
		this.serverDB = serverDB;
	}
	
	public ServerDB getServerDB() {
		return this.serverDB;
	}
	
	public Connection getConnection() throws SQLException {
		return serverDB.getConnection();
	}
	
	public StorageContainer getStorageContainer() {
		return getStorageConfig().getStorageContainer();
	}
	
	public void setStorageConfig(StorageConfig storageConfig) {
		this.storageConfig = storageConfig;
	}

	public StorageConfig getStorageConfig() {
		return storageConfig;
	}
	
	public Storage getStorage() {
		return getStorageContainer().getStorage();
	}
	
	public Database getDatabase(String name) {
		return getStorage().getDatabase(name);
	}
	
	public Table getTable(String dbName, String tbName) {
		return getDatabase(dbName).getTable(tbName);
	}
	


}
