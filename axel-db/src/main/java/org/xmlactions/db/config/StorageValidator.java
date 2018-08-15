package org.xmlactions.db.config;


import java.net.URL;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.db.StorageContainer;
import org.xmlactions.db.actions.CommonStorageField;
import org.xmlactions.db.actions.Database;
import org.xmlactions.db.actions.FK;
import org.xmlactions.db.actions.PK;
import org.xmlactions.db.actions.Storage;
import org.xmlactions.db.actions.Table;

/**
 * This is used to validate the content of a database storage definition.
 * @author mike.murphy
 *
 */
public class StorageValidator {
	
	private static final Logger log = LoggerFactory.getLogger(StorageValidator.class);
	private StringBuilder sb;
	public String validate(URL resourceFile, IExecContext execContext) {

		sb = new StringBuilder();
		try {
			StorageContainer storageContainer = new StorageContainer(resourceFile, execContext);
			Storage storage = storageContainer.getStorage();
			addProgress("validate storage[" + resourceFile.getPath() + "]");
			if (log.isDebugEnabled()) {
				log.debug("validate storage[" + resourceFile.getPath() + "]");
			}
			for (Database database : storage.getDatabases()) {
				validateDatabase(execContext, storage, database);
			}
		} catch (Exception ex) {
			throw new IllegalArgumentException(ex.getMessage(),ex);
		}
		return sb.toString();
	}
	
	public String validate(String resourceFileName, IExecContext execContext) {

		sb = new StringBuilder();
		try {
			StorageContainer storageContainer = new StorageContainer(resourceFileName, execContext);
			Storage storage = storageContainer.getStorage();
			addProgress("validate storage[" + resourceFileName + "]");
			if (log.isDebugEnabled()) {
				log.debug("validate storage[" + resourceFileName + "]");
			}
			for (Database database : storage.getDatabases()) {
				validateDatabase(execContext, storage, database);
			}
		} catch (Exception ex) {
			throw new IllegalArgumentException(ex.getMessage(),ex);
		}
		return sb.toString();
	}
	
	public String validate(String resourceFileName, IExecContext execContext, String storageConfigBeanName) {

		sb = new StringBuilder();
		try {
			StorageConfig storageConfig = (StorageConfig)execContext.get(storageConfigBeanName);
			Storage storage = storageConfig.getStorageContainer().getStorage();
			addProgress("validate storage[" + storageConfigBeanName + "]");
			if (log.isDebugEnabled()) {
				log.debug("validate storage[" + storageConfigBeanName + "]");
			}
			for (Database database : storage.getDatabases()) {
				validateDatabase(execContext, storage, database);
			}
		} catch (Exception ex) {
			throw new IllegalArgumentException(ex.getMessage(),ex);
		}
		return sb.toString();
	}
	
	private void validateDatabase(IExecContext execContext, Storage storage, Database database) {
		addProgress("validate database[" + database.getName() + "]");
		if (log.isDebugEnabled()) {
			log.debug("validate database[" + database.getName() + "]");
		}
		if (StringUtils.isEmpty(database.getName())) {
			addError("missing database name");
		}
			
		for (Table table : database.getTables()) {
			validateTable(execContext, storage, database, table);
		}
	}
	private void validateTable(IExecContext execContext, Storage storage, Database database, Table table) {
		addProgress("validate table[" + table.getName() + "]");
		//if (log.isDebugEnabled()) {
		//	log.debug("validate table[" + table.getName() + "]");
		//}
		if (StringUtils.isEmpty(table.getName())) {
			addError("missing table name in database [" + database.getName() + "]");
		}
		
		for (CommonStorageField field : table.getFields()) {
			if (StringUtils.isEmpty(field.getName())) {
				addError("missing name attribute from a field in table [" + table.getName() + "]");
				return;
			}
			validateField(execContext, storage, database, table, field);
		}
	
	}
	private void validateField(IExecContext execContext, Storage storage, Database database, Table table, CommonStorageField field) {
		addProgress("validate field[" + field.getName() + "]");
		if (field instanceof PK) {
			validatePK(execContext, storage, database, table, (PK)field);
		} else if (field instanceof FK) {
			validateFK(execContext, storage, database, table, (FK)field);
		}
	}
	
	private void validatePK(IExecContext execContext, Storage storage, Database database, Table table, PK field) {
		addProgress("validate PK field[" + field.getName() + "]");
		//if (log.isDebugEnabled()) {
		//	log.debug("validate PK field[" + field.getName() + "]");
		//}
	}
	
	private void validateFK(IExecContext execContext, Storage storage, Database database, Table table, FK field) {
		addProgress("validate FK field[" + field.getName() + "]");
		//if (log.isDebugEnabled()) {
		//	log.debug("validate FK field[" + field.getName() + "]");
		//}
		if (StringUtils.isEmpty(field.getForeign_table())) {
			addError("missing foreign_table attribute from fk [" + field.getName() + "]");
		}
		Table foreignTable = database.getTable(field.getForeign_table());
		if (foreignTable == null) {
			addError("Foreign Table not found using table_name [" + field.getForeign_table() + "] for fk [" + field.getName() + "]");
		}
		if (StringUtils.isEmpty(field.getForeign_key())) {
			addError("missing foreign_key attribute from fk [" + field.getName() + "]");
		}
		CommonStorageField foreignField = foreignTable.getField(field.getForeign_key());
		if (foreignField == null) {
			addError("Foreign Field not found in foreign_table [" + field.getForeign_table() + "] for foreign_key [" + field.getForeign_key() + "]");
		}
	}
	
	
	private void addError(String msg) {
		sb.append(" - ERROR - " + msg + "\n");
		log.error(msg);
	}
	private void addProgress(String msg) {
		sb.append(msg + "\n");
	}

}
