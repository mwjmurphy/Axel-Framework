package org.xmlactions.db.config;


import org.springframework.beans.BeanUtils;
import org.xmlactions.db.DBConnector;
import org.xmlactions.db.StorageContainer;
import org.xmlactions.db.actions.Database;
import org.xmlactions.db.actions.DbSpecific;
import org.xmlactions.db.sql.select.ISqlSelectBuildQuery;



/**
 */
public class StorageConfig implements IStorageConfig
{
	
	private DBConnector dbConnector;

	private StorageContainer storageContainer;

	private String databaseName;

	private String dbSpecificName;

    /**
     * This is the db interface we use to build the sql.
     */
    private ISqlSelectBuildQuery sqlBuilder;

	public void setDbConnector(DBConnector dbConnector)
	{

		this.dbConnector = dbConnector;
	}

	/**
	 * Get the Database Connector.
	 * <p>
	 * This is setup from a Spring Configuration.
	 * </p>
	 * <p>
	 * A DBConnector provides a means for connecting to a database using a JNDI Lookup or from a set of configuration
	 * parameters.
	 * </p>
	 * 
	 * @return a DBConnector
	 */
	public DBConnector getDbConnector()
	{

		return dbConnector;
	}

	public void setStorageContainer(StorageContainer storageContainer)
	{

		this.storageContainer = storageContainer;
	}

	/**
	 * Get the Storage Container that manages a Storage class.
	 * <p>
	 * This is setup from a Spring Configuration.
	 * </p>
	 * <p>
	 * A StorageContainer manages a Storage class that provides a definition of the configured storage devices such as
	 * databases / xml files.
	 * </p>
	 * 
	 * @return a DBConnector
	 */
	public StorageContainer getStorageContainer()
	{

		return storageContainer;
	}

	public void setDatabaseName(String databaseName)
	{

		this.databaseName = databaseName;
	}

	/**
	 * Get the Database Name.
	 * <p>
	 * This is setup from a Spring Configuration.
	 * </p>
	 * <p>
	 * The Database name is a reference to a Database defined in the Storage configuration.
	 * </p>
	 * 
	 * @return a DBConnector
	 */
	public String getDatabaseName()
	{

		return databaseName;
	}

    public ISqlSelectBuildQuery getSqlBuilder() {
        if (sqlBuilder == null) {
            throw new IllegalArgumentException("sqlBuilder has not been set in this StorageConfig.  This must be set to a class that implements "
                    + ISqlSelectBuildQuery.class.getSimpleName());
        }
        // create new instance of this class.
        return (ISqlSelectBuildQuery)BeanUtils.instantiateClass(sqlBuilder.getClass());
        // return sqlBuilder;
    }

    public ISqlSelectBuildQuery getSqlBuilderQuietly() {
        return sqlBuilder;
    }

    public void setSqlBuilder(ISqlSelectBuildQuery sqlBuilder) {
        this.sqlBuilder = sqlBuilder;
    }

	public void setDbSpecificName(String dbSpecificName) {
		this.dbSpecificName = dbSpecificName;
	}

	public String getDbSpecificName() {
		return dbSpecificName;
	}
	
	/**
	 * Get the DBSpecific for this StorageConfig "using this dbSpecificName".
	 * @return DbSpecific
	 */
	public DbSpecific getDBSpecific() {
		try {
			return getStorageContainer().getStorage().getDatabase(getDatabaseName()).getDbSpecific(getDbSpecificName());
		} catch (Exception ex) {
			throw new IllegalArgumentException("No DbSpecific found for [" + getDbSpecificName() + "]", ex);
		}
	}

}
