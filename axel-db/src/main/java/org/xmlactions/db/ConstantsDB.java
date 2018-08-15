
package org.xmlactions.db;

public class ConstantsDB
{

	/**
	 * If the execContext contains a matching tracker class then the saveTrace method will
	 * be called for each insert, update, delete and select.
	 */
    public static final String KEY_TRACER_DB_CLASS = "tracer.db.class";
    public static final String TRACE_INSERT = "INSERT";	// worker id for insert
    public static final String TRACE_UPDATE = "UPDATE"; // worker id for update
    public static final String TRACE_DELETE = "DELETE";	// worker id for delete
    public static final String TRACE_SELECT = "SELECT";	// worker id for select

	public static final String localeResourceName = "config/lang/rio-db";

	public static final String KEY_database_missing_tablename = "database.missing_tablename";

	public static final String KEY_database_missing_fieldname = "database.missing_fieldname";

    public static final String SQL_INSERT_PK_VALUE = "pk.value";
    
    /** This is the key to getting the ENFORCE_CONCURRENTY setting (true/false) from the execContext.*/
    public static final String ENFORCE_CONCURRENCY = "enforce.concurrency";
   
}
