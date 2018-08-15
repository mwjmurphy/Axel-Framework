
package org.xmlactions.db.actions;

import java.util.ArrayList;
import java.util.List;



import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.xmlactions.action.actions.BaseAction;
import org.xmlactions.action.config.IExecContext;


public class Storage extends BaseAction
{

	private String name;

	private List<Database> databases = new ArrayList<Database>();

	private List<Xml> xmls = new ArrayList<Xml>();

	public String execute(IExecContext context) throws Exception
	{

		return null;
	}

	public void setDatabase(Database database)
	{

		databases.add(database);
	}

	/** @return the last database added. */
	public Database getDatabase()
	{

		return databases.get(databases.size() - 1);
	}

	/** @return the first database. */
	public Database getFirstDatabase()
	{

		Validate.isTrue(databases.size() > 0, "No Databases configured in Storage [" + getName() + "]");
		return databases.get(0);
	}

	/**
	 * Get a database by it's name.
	 * <p>
	 * If name is empty it will return the first database in the configuration.
	 * This behavior is implemented as most configurations will only contain one
	 * database.
	 * <p>
	 * 
	 * @param name
	 *            the name of the database we want to use, may be empty if we
	 *            are only using one database.
	 * @return the database
	 * 
	 * @throws IllegalArgumentException
	 *             if the database cannot be found
	 */
	public Database getDatabase(String name) throws IllegalArgumentException
	{

		Database database = null;

		if (!StringUtils.isEmpty(name)) {
			for (Database db : getDatabases()) {
				if (db.getName().equals(name)) {
					database = db;
					break;
				}
			}
		} else {
			if (getDatabases().size() > 0) {
				database = getDatabases().get(0);
				Validate.notNull(database, "No Databases configured in Storage [" + getName() + "]");
			}
		}
		Validate.notNull(database, "Database [" + name + "] not found on Storage [" + getName() + "]");
		return database;
	}

	public List<Database> getDatabases()
	{

		return databases;
	}

	public String toString(int indent)
	{

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < indent; i++) {
			sb.append(' ');
		}
		sb.append("STORAGE:" + getName());
		return sb.toString();
	}

	public void setName(String name)
	{

		this.name = name;
	}

	public String getName()
	{

		return name;
	}

	public void setXml(Xml xml)
	{

		xmls.add(xml);
	}
	public List<Xml> getXmls()
	{

		return xmls;
	}

	public Xml getFirstXml()
	{

		Validate.isTrue(xmls.size() > 0, "No Xmls configured in Storage [" + getName() + "]");
		return xmls.get(0);
	}

	/**
	 * Completes the database definition structure.
	 * <p>
	 * 1. Build backward links to foreign table FKs.
	 * </p>
	 */
	public void completeStructure() {
		for (Database database : getDatabases()) {
			for (Table table : database.getTables()) {
				for (CommonStorageField field : table.getFields()) {
					if (field instanceof FK) {
						buildBackLink(database, (FK)field);
					}
				}
			}
		}
		
	}
	
	private void buildBackLink(Database database, FK fk) {
		try {
		CommonStorageField field = findMatchingFkField(database, fk);
		field.addBackLinkedFk(fk);
		} catch (Exception ex) {
			throw new IllegalArgumentException ("Foreign Key " + fk.getForeign_table() + "." + fk.getForeign_key() + " not found.", ex);
		}
	}

	private CommonStorageField findMatchingFkField(Database database, FK fk) {
		Table table = findMatchingFkTable(database, fk);
		CommonStorageField field = table.getField(fk.getForeign_key());
		return field;
	}
	private Table findMatchingFkTable(Database database, FK fk) {
		return database.getTable(fk.getForeign_table());
	}
	
	/**
	 * A database may contain DBInserts that are used to insert databases from other files. This
	 * method calls the insertion process.
	 * @param execContext
	 * @exception Exception 
	 */
	public void processInserts(IExecContext execContext) throws Exception {
		for (Database database : getDatabases()) {
			database.processInserts(execContext);
		}
	}
}
