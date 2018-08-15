
package org.xmlactions.db;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Vector;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmlactions.action.actions.BaseAction;
import org.xmlactions.common.date.DateUtils;
import org.xmlactions.common.xml.XMLObject;
import org.xmlactions.db.actions.Binary;
import org.xmlactions.db.actions.CommonStorageField;
import org.xmlactions.db.actions.Database;
import org.xmlactions.db.exception.DBSQLException;
import org.xmlactions.db.sql.select.ISqlTable;
import org.xmlactions.db.sql.select.SqlField;


public class DBSQL
{
	
	private static final Logger logger = LoggerFactory.getLogger(DBSQL.class);
	

	/** Name of root element for a query result */
	public static final String ROOT = "root";

	/** Attribute name for the number of rows returned from a query */
	public static final String NUM_ROWS = "num_rows";

	private static final Logger log = LoggerFactory.getLogger(DBSQL.class);

	private static final String defaultDateFormat = DBUtils.XSD_LONG_DATE_TIME_FMT;	// "dd-MM-yyyy";

    private boolean includeIndex = true;

    /**
     * Converts an Oracle clob to a string.
     * 
     * @return A string containing the clob data or null if an empty clob. 
     * 
     * @throws DBSQLException
     * @throws SQLException
     * 
     */
    public static String clobToString(Clob clob) throws DBSQLException, SQLException
	{
    	if (clob != null) {
			try {
				return IOUtils.toString(clob.getCharacterStream());
			} catch (IOException ex) {
	            throw new DBSQLException(ex.getMessage(), ex);
			}
    	} else {
    		return null;// empty clob
    	}
	}

    /**
     * update to database
     * 
     * @param sql
     *            is the sql update to send to the database
     * @throw DBSQLException
     */
    public static int update(Connection connection, String sql) throws DBSQLException
	{

		Statement stmt = null;
		try {
			stmt = connection.createStatement();
			int something = stmt.executeUpdate(sql);
			return something;
		} catch (SQLException ex) {
			
            throw new DBSQLException("SQL failed for [" + sql + "]\n" + ExceptionUtils.getRootCauseMessage(ex), ex);
		} finally {
			DBConnector.closeQuietly(stmt);
		}
	}

    /**
     * Update to database.
     * 
     * @param sql is the sql update query to send to the database
     * @return number of rows updated.
     * @throws DBSQLException
     */
	public static int update(Connection dbConnection, String sql, List<SqlField> params) throws DBSQLException
	{

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			preparedStatement = dbConnection.prepareStatement(sql);
			populatePreparedStatement(preparedStatement, params);
			int result = preparedStatement.executeUpdate();
			return result;
		} catch (SQLException ex) {
            throw new DBSQLException(buildErrorMessage(ex, sql, params));
		} finally {
			DBConnector.closeQuietly(preparedStatement);
			DBConnector.closeQuietly(resultSet);
		}
	}
	
	protected enum JavaToSqlType {
		FLOAT(java.lang.Float.class, java.sql.Types.FLOAT ),
		DOUBLE(java.lang.Double.class, java.sql.Types.DOUBLE),
		INTEGER(java.lang.Integer.class, java.sql.Types.INTEGER ),
		LONG(java.lang.Long.class, java.sql.Types.BIGINT),
		MATH(java.lang.Math.class, java.sql.Types.DOUBLE),
		NUMBER(java.lang.Number.class, java.sql.Types.NUMERIC),
		SHORT(java.lang.Short.class, java.sql.Types.SMALLINT),
		BOOLEAN(java.lang.Boolean.class, java.sql.Types.BOOLEAN);
		
		
		private Object javaType;
		private int sqlType;
		
		JavaToSqlType(Object javaType, int sqlType) {
			this.javaType = javaType;
			this.sqlType = sqlType;
		}
		
		protected static int getSqlType(Object javaType) {
			for ( JavaToSqlType javaToSqlType : JavaToSqlType.values()) {
				if (javaToSqlType.javaType.equals(javaType.getClass())) {
					return javaToSqlType.sqlType;
				}
 			}
			return -1;
		}
	}

	private static String populatePreparedStatement(PreparedStatement preparedStatement, List<SqlField> params) throws SQLException {
		StringBuilder sb = new StringBuilder();
		if (params != null) {
			int index = 1;

			for (SqlField sqlField : params) {
				CommonStorageField commonStorageField = sqlField.getCommonStorageField();
				String value = (String)sqlField.getValue();
				if (sb.length() != 0) {
					sb.append(", ");
				}
				sb.append(sqlField.getFieldName() + "=" + value);
				if (StringUtils.isEmpty(value)) {
					preparedStatement.setString(index++, (String)sqlField.getValue());
				} else {
					DBDataType dbDataType = DBDataType.getDBDataType(commonStorageField); 
					if (dbDataType.equals(DBDataType.date)) {
						String dateFormat = getDateFormat(commonStorageField);
						if (dateFormat != null) {
							value = DateUtils.format(value, dateFormat, "yyyy-MM-dd");
						}
						Date sqlDate = DBUtils.buildDateFromString(value);
						if (log.isDebugEnabled()) {
							log.debug("Converted Date [" + sqlField.getValue() + "] = [" + sqlDate + "]");
						}
						preparedStatement.setDate(index++, sqlDate);
					} else if (dbDataType.equals(DBDataType.datetime)) {
						String dateFormat = getDateTimeFormat(commonStorageField);
						if (dateFormat != null) {
							value = DateUtils.format(value, dateFormat, "yyyy-MM-dd HH:mm:ss.SSS");
						}
						Date sqlDate = DBUtils.buildDateFromString(value);
						Timestamp timestamp = new Timestamp(sqlDate.getTime());
						if (log.isDebugEnabled()) {
							log.debug("Converted DateTime [" + sqlField.getValue() + "] = [" + timestamp + "]");
						}
						preparedStatement.setTimestamp(index++, timestamp);
					} else if (dbDataType.equals(DBDataType.time)) {
						String dateFormat = getDateTimeFormat(commonStorageField);
						if (dateFormat != null) {
							value = DateUtils.format(value, dateFormat, "HH:mm:ss.SSS");
						}
						Date sqlDate = DBUtils.buildDateFromString(value);
						Time time = new Time(sqlDate.getTime());
						if (log.isDebugEnabled()) {
							log.debug("Converted Time [" + sqlField.getValue() + "] = [" + time + "]");
						}
						preparedStatement.setTime(index++, time);
					} else if (dbDataType.equals(DBDataType.bool)) {
						boolean bValue;
						if (commonStorageField instanceof Binary) {
							bValue = ((Binary)commonStorageField).isTrue((String)sqlField.getValue());
						} else {
							bValue = BooleanUtils.toBoolean((String)sqlField.getValue());
						}
						preparedStatement.setBoolean(index++, bValue);
					} else if (dbDataType.equals(DBDataType.integer)) {
						preparedStatement.setObject(index++, sqlField.getValue());
					} else if (dbDataType.equals(DBDataType.text)) {
						if (sqlField.getValue() instanceof String) {
							preparedStatement.setString(index++, (String)sqlField.getValue());
						} else {
							//preparedStatement.setObject(index++, sqlField.getValue());
							setInputStream(preparedStatement, index++, sqlField.getValue());
						}
					} else if (dbDataType.equals(DBDataType.clob)) {
						preparedStatement.setString(index++, (String)sqlField.getValue());
					} else {
						preparedStatement.setObject(index++, sqlField.getValue());
					}
				}
			}
		}
		return sb.toString();
	}
	
	private static void setInputStream(PreparedStatement preparedStatement, int parameterIndex, Object value) throws SQLException {
		InputStream inputStream = null;
		if (value instanceof byte[]) {
			inputStream = new ByteArrayInputStream((byte[])value);
			preparedStatement.setBinaryStream(parameterIndex, inputStream, ((byte[])value).length);
		} else {
			preparedStatement.setObject(parameterIndex, value);
		}
	}
	
	private static String getDateFormat(CommonStorageField commonStorageField) {
		
		if (StringUtils.isNotBlank(commonStorageField.getDate_format())) {

			return commonStorageField.getDate_format();

		} else {

			// "yyyy-MM-dd"}
			Database database = getDatabase(commonStorageField);
			if (database != null) {
				return database.getDate_format();
			}
		}
		
		return null;
	}
	
	private static String getTimeFormat(CommonStorageField commonStorageField) {
		// "HH:mm:ss.SSS"}
		Database database = getDatabase(commonStorageField);
		if (database != null) {
			return database.getTime_format();
		}
		return null;
	}
	
	private static String getDateTimeFormat(CommonStorageField commonStorageField) {
		// "yyyy-MM-dd HH:mm:ss.SSS"}
		Database database = getDatabase(commonStorageField);
		if (database != null) {
			return database.getDatetime_format();
		}
		return null;
	}
	
	private static Database getDatabase(CommonStorageField commonStorageField) {
		BaseAction table = commonStorageField.getParent();
		if (table != null) {
			BaseAction baDatabase = table.getParent();
			
			if (baDatabase != null && baDatabase instanceof Database) {
				return (Database)baDatabase;
			}
		}
		return null;
	}

    /**
     * insert to database
     * 
     * @param connection
     *            an open database connection
     * @param sql
     *            is the sql insert query to send to the database
     * @throws DBSQLException
     */
	public static int insert(Connection dbConnection, String sql) throws DBSQLException
	{

		Statement stmt = null;
		try {
			stmt = dbConnection.createStatement();
			return (stmt.executeUpdate(sql));
		} catch (SQLException ex) {
            throw new DBSQLException("SQL failed for [" + sql + "]", ex);
		} finally {
			DBConnector.closeQuietly(stmt);
		}
	}

    /**
     * Insert to database using JDBC 3.0 which will return the key of the new
     * row.
     * 
     * @param sql
     *            is the sql insert query to send to the database
     * @return the key for the inserted row
     * @throws DBSQLException
     */
	public static int insertAndReturnKey(Connection dbConnection, String sql) throws DBSQLException
	{

		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = dbConnection.createStatement();
			stmt.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
			rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				return (rs.getInt(1));
			} else {
				// throw an exception from here
				throw new SQLException("Failed to get GeneratedKey for [" + sql + "]");
			}
		} catch (SQLException ex) {
            throw new DBSQLException("SQL failed for [" + sql + "]", ex);
		} finally {
			DBConnector.closeQuietly(stmt);
			DBConnector.closeQuietly(rs);
		}
	}

    /**
     * Insert to database using JDBC 3.0 which will return the key of the new
     * row.
     * 
     * @param sql
     *            is the sql insert query to send to the database
     * @return the key for the inserted row
     * @throws DBSQLException
     */
	public static int insertAndReturnKey(Connection dbConnection, String sql, List<SqlField> params) throws DBSQLException
	{

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String paramList = null;
		try {
			preparedStatement = dbConnection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			paramList = populatePreparedStatement(preparedStatement, params);
			int result = preparedStatement.executeUpdate();
			resultSet = preparedStatement.getGeneratedKeys();
			if (resultSet.next()) {
				return (resultSet.getInt(1));
			} else {
				// throw an exception from here
				throw new SQLException("Failed to get GeneratedKey for [" + sql + "]");
			}
		} catch (SQLException ex) {
            throw new DBSQLException(buildErrorMessage(ex, sql, params));
		} finally {
			DBConnector.closeQuietly(preparedStatement);
			DBConnector.closeQuietly(resultSet);
		}
	}
	
    /**
     * Insert to database using JDBC 3.0 which will return the key of the new
     * row.
     * 
     * @param sql
     *            is the sql insert query to send to the database
     * @param params - these will be used as paramaters in a PreparedStatement
     * @param sqlTable - if not null the pk value will be used in the prepared statement constructior to ne specific about which field is the PK.
     * @return the key for the inserted row
     * @throws DBSQLException
     */
	public static int insertAndReturnKey(Connection dbConnection, String sql, List<SqlField> params, ISqlTable sqlTable) throws DBSQLException
	{

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		String paramList = null;
		try {
			if (sqlTable.getTable() != null && sqlTable.getTable().getPk() != null) {
				preparedStatement = dbConnection.prepareStatement(sql, new String [] {sqlTable.getTable().getPk().getName()});
			} else {
				preparedStatement = dbConnection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			}
			paramList = populatePreparedStatement(preparedStatement, params);
			int result = preparedStatement.executeUpdate();
			resultSet = preparedStatement.getGeneratedKeys();
			if (resultSet.next()) {
				return (resultSet.getInt(1));
			} else {
				// throw an exception from here
				throw new SQLException("Failed to get GeneratedKey for [" + sql + "]");
			}
		} catch (SQLException ex) {
            throw new DBSQLException(buildErrorMessage(ex, sql, params));
		} finally {
			DBConnector.closeQuietly(preparedStatement);
			DBConnector.closeQuietly(resultSet);
		}
	}

	/**
     * Insert to database 
     * 
     * @param sql
     *            is the sql insert query to send to the database
     * @return the result of executeUpdate
     * @throws DBSQLException
     */
	public static int insert(Connection dbConnection, String sql, List<SqlField> params) throws DBSQLException
	{

		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			preparedStatement = dbConnection.prepareStatement(sql);
			populatePreparedStatement(preparedStatement, params);
			int result = preparedStatement.executeUpdate();
			try {
				ResultSet rs = preparedStatement.getGeneratedKeys();
				if (rs.next()) {
					int pk = rs.getInt(1);
					result = pk;
					log.debug("generatedKeys:" + pk + " dbConnection:" + dbConnection.getClientInfo());
				}
			} catch (Exception ex) {
				// do notin
				log.warn(ex.getMessage());
			}
			return result;
		} catch (SQLException ex) {
            throw new DBSQLException(buildErrorMessage(ex, sql, params));
		} finally {
			DBConnector.closeQuietly(preparedStatement);
			DBConnector.closeQuietly(resultSet);
		}
	}
	
	private static String buildErrorMessage(Exception ex, String sql, List<SqlField>params) {
		StringBuilder sb = new StringBuilder();
		sb.append("SQL failed for [" + sql +"] (");
		boolean needComma  = false;
		for (SqlField sqlField : params) {
			if (needComma == true) {
				sb.append(",");
			}
			sb.append(sqlField.getFieldName() + "=" + sqlField.getValue());
			needComma = true;
		}
		sb.append(");\n");
		sb.append("Exception:" + ex.getMessage());
		return sb.toString();
	}

	/**
	 * Executes a batch of commands
	 * 
	 * @param dbConnection
	 *            is the open database connection
	 * @param batch
	 *            is an array of sql commands usually update and or insert
	 * @returns an int array of each sql execution result
	 * @throws SQLException
	 */
	public int[] batch(Connection dbConnection, String[] batch) throws SQLException
	{

		Statement stmt = null;
		try {
			dbConnection.setAutoCommit(false);
			stmt = dbConnection.createStatement();
			for (int i = 0; i < batch.length; i++) {
				stmt.addBatch(batch[i]);
			}
			int results[] = stmt.executeBatch();
			dbConnection.commit();
			dbConnection.setAutoCommit(true);
			return (results);
		} catch (SQLException e) {
			dbConnection.rollback();
			dbConnection.setAutoCommit(true);
			throw e;
		} finally {
			DBConnector.closeQuietly(stmt);
			if (stmt != null) {
				stmt.close();
			}
		}
	}

	/**
	 * Retrieve a query from the db. The result is the column names followed by each row. All columns are separated by
	 * the '|' character.
	 * 
	 * @param (String) query is the sql query to send to the database
	 * @throws SQLException
	 */
	public String query(Connection connection, String sql) throws SQLException
	{

		Statement stmt = null;
		ResultSet rs = null;

		try {
			stmt = connection.createStatement();
			rs = stmt.executeQuery(sql);
			return (query(rs));
		} catch (SQLException ex) {
			throw ex;
		} finally {
			DBConnector.closeQuietly(stmt);
			DBConnector.closeQuietly(rs);
		}
	}

	/**
	 * Retrieve a query from the db. The result is the column names followed by each row. All columns are separated by
	 * the '|' character.
	 * 
	 * @param rs
	 *            is the resultSet from the query
	 * @throws SQLException
	 */
	public String query(ResultSet rs) throws SQLException
	{

		StringBuffer sb = new StringBuffer();
		ResultSetMetaData rsmd = null;

		try {
			rsmd = rs.getMetaData();

			// header row
			for (int a = 1; a < rsmd.getColumnCount() + 1; a++) {
				sb.append("| "); // System.out.print("| ");
				sb.append(rsmd.getColumnName(a));// System.out.print(rsmd.getColumnName(a));
				sb.append(" "); // System.out.print(" ");
			}
			sb.append("\n"); // System.out.println();

			// content row/s
			while (rs.next()) {
				for (int a = 1; a < rsmd.getColumnCount() + 1; a++) {
					sb.append("|"); // System.out.print("|");
					sb.append(" " + rs.getString(a) + " "); // System.out.print(" "
					// + rs.getString(a)
					// + " ");
				}
				sb.append("\n"); // System.out.println();
			}
			return new String(sb);
		} catch (SQLException ex) {
			throw ex;
		} finally {
		}
	}

    /**
     * this gets the result of a count or max etc
     * 
     * @param connection
     *            an open database connection
     * @param sql
     *            is the query.
     * @throws DBSQLException
     * @returns the response number as a string or null
     */
	public static String queryOne(Connection connection, String sql, List<SqlField> params) throws DBSQLException
	{

		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		// execute SQL Insert
		try {
			preparedStatement = connection.prepareStatement(sql);
			populatePreparedStatement(preparedStatement, params);
			rs = preparedStatement.executeQuery();
			return (queryOne(rs));
		} catch (SQLException ex) {
            throw new DBSQLException(buildErrorMessage(ex, sql, params));
		} finally {
			DBConnector.closeQuietly(preparedStatement);
			DBConnector.closeQuietly(rs);
		}
	}

    /**
     * this gets the result of a count or max etc
     * 
     * @param rs
     *            is the resultSet with the executed query.
     * @throws DBSQLException
     * @throws SQLException
     * @returns the response number as a string or null
     */
	public static String queryOne(ResultSet rs) throws DBSQLException, SQLException
	{

		SimpleDateFormat sdf;

		sdf = new SimpleDateFormat(defaultDateFormat);

		ResultSetMetaData rsmd = rs.getMetaData();
		String columnTypes = rsmd.getColumnTypeName(1);
		if (rs.next()) {
			if (columnTypes.equals("CLOB")) {
				Clob result = rs.getClob(1);
				return (clobToString(result));
			} else if (columnTypes.equals("DATE") || columnTypes.equals("DATETIME")) {
				java.sql.Date date = rs.getDate(1);
				if (date != null) {
					return (sdf.format(date));
				}
			} else {
				String s = rs.getString(1);
				return (s != null ? s : "");
			}
		}
		return (null);
	}

    /**
     * Retrieve a query from the db. The result is an XML String containing each
     * column name followed by each row.
     * 
     * @param connection
     *            an open db connection
     * @param sql
     *            is the sql query to send to the database
     * @throws DBSQLException
     */
    public String queryXML(Connection connection, String sql, String rootName) throws DBSQLException
	{

		ResultSet rs = null;
		Statement stmt = null;

		try {
			stmt = connection.createStatement();
			rs = stmt.executeQuery(sql);
			return (queryXML(rs, rootName));
		} catch (SQLException ex) {
            throw new DBSQLException("Query failed for [" + sql + "]", ex);
		} finally {
			DBConnector.closeQuietly(stmt);
			DBConnector.closeQuietly(rs);
		}
	}

    public String queryXML(ResultSet rs, String rootName) throws DBSQLException, SQLException
	{

		StringBuffer sb = new StringBuffer();
		ResultSetMetaData rsmd = null;

		int rowIndex = 0;

		rsmd = rs.getMetaData();
		// header row
		Vector<String> columnNames = new Vector<String>();
		Vector<String> columnTypes = new Vector<String>();
		for (int a = 1; a < rsmd.getColumnCount() + 1; a++) {
			columnNames.add(rsmd.getColumnName(a));
			columnTypes.add(rsmd.getColumnTypeName(a));
		}

		// content row/s
		while (rs.next()) {
			sb.append(" <row index=\"" + (++rowIndex) + "\">\n");
			for (int a = 1; a < rsmd.getColumnCount() + 1; a++) {
				sb.append("  <");
				sb.append(columnNames.get(a - 1));
				sb.append(" type=\"");
				sb.append(columnTypes.get(a - 1));
				sb.append("\"");
				sb.append(" value=\"");
				if ((columnTypes.get(a - 1)).equals("CLOB")) {
					Clob result = rs.getClob(a);
					sb.append(clobToString(result));
				} else {
					String s = rs.getString(a);
					sb.append(s != null ? s : "");
				}
				sb.append("\"/>\n");
			}
			sb.append(" </row>\n");
		}
		if (rootName != null) {
			return ("<" + rootName + " num_rows=\"" + rowIndex + "\">\n" + sb + "</" + rootName + ">");
		}
		return new String(sb);
	}

    /**
     * Sends a query to the database and maps the resultset to an xml string.
     * This 'short' version is aimed at making the xml as short as it can using
     * attributes in place of content.
     * 
     * @param connection
     *            an open database connection
     * @param query
     *            is the database query to execute
     * @param rootName
     *            is an optional root element name used to wrap the resultant
     *            xml rows.
     * @throws DBSQLException
     * @returns an xml containing the query results.
     */
	public String queryXMLShort(Connection connection, String sql, String rootName) throws DBSQLException
	{

		ResultSet rs = null;
		Statement stmt = null;

		try {
			stmt = connection.createStatement();
			rs = stmt.executeQuery(sql);
			return (queryXMLShort(rs, rootName, null));
		} catch (SQLException ex) {
            throw new DBSQLException("SQL failed for [" + sql + "]", ex);
		} finally {
			DBConnector.closeQuietly(stmt);
			DBConnector.closeQuietly(rs);
		}
	}

    /**
     * Sends a query to the database and maps the resultset to an xml string.
     * This 'short' version is aimed at making the xml as short as it can using
     * attributes in place of content.
     * 
     * @param connection
     *            an open database connection
     * @param query
     *            is the database query to execute
     * @param rootName
     *            is an optional root element name used to wrap the resultant
     *            xml rows.
     * @throws DBSQLException
     * @returns an xml containing the query results.
     */
	public String queryXMLShort(Connection connection, String sql, String rootName, List<SqlField> params) throws DBSQLException
	{
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			preparedStatement = connection.prepareStatement(sql);
			populatePreparedStatement(preparedStatement, params);
			resultSet = preparedStatement.executeQuery();
			return (queryXMLShort(resultSet , rootName, null));
		} catch (SQLException ex) {
            throw new DBSQLException(buildErrorMessage(ex, sql, params));
		} finally {
			DBConnector.closeQuietly(preparedStatement);
			DBConnector.closeQuietly(resultSet);
		}
	}

    /**
     * builds the result set as <row index="x" attribute=value ...> where
     * attribute name is the name of the table column.
     * 
     * @param rs
     *            is the ResultSet we use to retrieve the data a row at a time
     * @param rootName
     *            is the root element name
     * @throws SQLException
     * @throws DBSQLException
     */
	public String queryXMLShort(ResultSet rs, String rootName) throws SQLException, DBSQLException
	{

		return (queryXMLShort(rs, rootName, null));
	}

    /**
     * builds the result set as <row index="x" attribute=value ...> where
     * attribute name is the name of the table column.
     * 
     * @param rs
     *            is the ResultSet we use to retrieve the data a row at a time
     * @param rootName
     *            is the root element name
     * @param dataFormat
     *            is the date format we want for all dates, default is
     *            "dd-MM-yyyy"
     * @throws DBSQLException
     * @throws SQLException
     */
    public String queryXMLShort(ResultSet rs, String rootName, String dateFormat) throws DBSQLException, SQLException
	{

		StringBuffer sb = new StringBuffer();
		ResultSetMetaData rsmd = null;

		SimpleDateFormat sdf;
		if (dateFormat != null) {
			sdf = new SimpleDateFormat(dateFormat);
		} else {
			sdf = new SimpleDateFormat("dd-MM-yyyy");
		}

		int rowIndex = 0;

		rsmd = rs.getMetaData();
		// header row
		String[] columnLabel = new String[rsmd.getColumnCount()];
		String[] columnNames = new String[rsmd.getColumnCount()];
		String[] columnTypes = new String[rsmd.getColumnCount()];
		for (int a = 1; a < rsmd.getColumnCount() + 1; a++) {
			columnLabel[a - 1] = rsmd.getColumnLabel(a);
			columnNames[a - 1] = rsmd.getColumnName(a);
			columnTypes[a - 1] = rsmd.getColumnTypeName(a);
		}

		// content row/s
		while (rs.next()) {
			sb.append(" <row index=\"" + (++rowIndex) + "\" ");
			log.debug("rowIndex:" + rowIndex);
			for (int a = 1; a < rsmd.getColumnCount() + 1; a++) {
				// sb.append(columnNames[a-1]);
				sb.append(columnLabel[a - 1]);
				sb.append("=\"");
				if (columnTypes[a - 1].equals("CLOB")) {
					Clob result = rs.getClob(a);
					//sb.append(this.clobToString(result));
                    String escapedXml = StringEscapeUtils.escapeXml(this.clobToString(result));
					sb.append(escapedXml);
				} else if (columnTypes[a - 1].equals("DATE") || columnTypes[a - 1].equals("DATETIME")) {
					java.sql.Date date = rs.getDate(a);
					if (date != null) {
						sb.append(sdf.format(date));
					}
				} else {
					String s = rs.getString(a);
					sb.append(s != null ? s : "");
					// sb.append(rs.getString(a));
				}
				sb.append("\" ");
			}
			sb.append("/>");
		}
		if (rootName != null) {
			return ("<" + rootName + " num_rows=\"" + rowIndex + "\">" + sb + "</" + rootName + ">");
		} else {
			return (new String(sb));
		}
	}

	/**
	 * builds the result set as <row index="x" attribute=value ...> where attribute name is the name of the table
	 * column.
	 * 
	 * @param rs
	 *            is the ResultSet we use to retrieve the data a row at a time
	 * @param elementNames
	 *            is a corresponding name for each attribute
	 * @param rootName
	 *            is the root element name
	 * @param dateFormat
	 *            is the date format we want for all dates, default is "dd-MM-yyyy"
	 * @throws SQLException
	 */
	public String queryXMLShort(ResultSet rs, String[] elementNames, String rootName, String dateFormat)
			throws SQLException
	{

		StringBuffer sb = new StringBuffer();
		ResultSetMetaData rsmd = null;

		SimpleDateFormat sdf;
		if (dateFormat != null) {
			sdf = new SimpleDateFormat(dateFormat);
		} else {
			sdf = new SimpleDateFormat("dd-MM-yyyy");
		}

		String retval = null;
		int rowIndex = 0;
		rsmd = rs.getMetaData();
		// header row
		String[] columnLabel = new String[rsmd.getColumnCount()];
		String[] columnNames = new String[rsmd.getColumnCount()];
		String[] columnTypes = new String[rsmd.getColumnCount()];
		for (int a = 1; a < rsmd.getColumnCount() + 1; a++) {
			// columnLabel[a-1] = rsmd.getColumnLabel(a);
			// columnNames[a-1] = rsmd.getColumnName(a);
			columnTypes[a - 1] = rsmd.getColumnTypeName(a);
		}

		// content row/s
		while (rs.next()) {
			sb.append(" <row index=\"" + (++rowIndex) + "\" ");
			for (int a = 1; a < rsmd.getColumnCount() + 1; a++) {
				sb.append(elementNames[a - 1]);
				sb.append("=\"");
				if (columnTypes[a - 1].equals("CLOB")) {
					Clob result = rs.getClob(a);
					try {
						sb.append(this.clobToString(result));
					} catch (Exception e) {
						sb.append("Unable to process clob:" + e.getMessage());
					}
				} else if (columnTypes[a - 1].equals("DATE") || columnTypes[a - 1].equals("DATETIME")) {
					java.sql.Date date = rs.getDate(a);
					if (date != null) {
						sb.append(sdf.format(date));
					}
				} else {
					String s = rs.getString(a);
					sb.append(s != null ? s : "");
					// sb.append(rs.getString(a));
				}
				sb.append("\" ");
			}
			sb.append("/>");
		}
		if (rootName != null) {
			return ("<" + rootName + " num_rows=\"" + rowIndex + "\">" + sb + "</" + rootName + ">");
		} else {
			return (new String(sb));
		}
	}

	/**
	 * builds the result set as <elementName>resultset.value</elementName>
	 * 
	 * @param rs
	 *            is the ResultSet we use to retrieve the data a row at a time
	 * @param elementNames
	 *            is a corresponding name for each element
	 * @param rootName
	 *            is the root element name
	 * @param dateFormat
	 *            is the date format we want for all dates, default is "dd-MM-yyyy"
	 * @throws SQLException
	 */
	public String queryXMLLong(ResultSet rs, String[] elementNames, String rootName, String dateFormat)
			throws SQLException
	{

		StringBuffer sb = new StringBuffer();
		ResultSetMetaData rsmd = null;

		SimpleDateFormat sdf;
		if (dateFormat != null) {
			sdf = new SimpleDateFormat(dateFormat);
		} else {
			sdf = new SimpleDateFormat("dd-MM-yyyy");
		}

		String retval = null;
		int rowIndex = 0;

		rsmd = rs.getMetaData();
		// header row
		String[] columnNames = new String[rsmd.getColumnCount()];
		String[] columnTypes = new String[rsmd.getColumnCount()];
		int[] columnTypesInt = new int[rsmd.getColumnCount()];
		StringBuffer tb = new StringBuffer();
		for (int a = 1; a < rsmd.getColumnCount() + 1; a++) {

			// columnNames[a-1] = rsmd.getColumnName(a);
			columnTypes[a - 1] = rsmd.getColumnTypeName(a);
			tb.append(columnTypes[a - 1] + ", ");
			// columnTypesInt[a-1] = rsmd.getColumnType(a);
		}
		log.debug("column types:" + tb);

		int rowCount = 0;
		// content row/s
		while (rs.next()) {
			if (rootName != null) {
				sb.append("<" + rootName + ">");
			}
			for (int a = 1; a < rsmd.getColumnCount() + 1; a++) {
				sb.append("<" + elementNames[a - 1] + ">");
				if (columnTypes[a - 1].equals("CLOB")) {
					Clob result = rs.getClob(a);
					try {
						sb.append(this.clobToString(result));
					} catch (Exception e) {
						sb.append("Unable to process clob:" + e.getMessage());
					}
				} else if (columnTypes[a - 1].equals("DATE") || columnTypes[a - 1].equals("DATETIME")) {
					java.sql.Date date = rs.getDate(a);
					if (date != null) {
						sb.append(sdf.format(date));
					}
				} else {
					String s = rs.getString(a);
					sb.append(s != null ? s : "");
					// sb.append(rs.getString(a));
				}
				sb.append("</" + elementNames[a - 1] + ">");
			}
			if (rootName != null) {
				sb.append("</" + rootName + ">");
			}
			rowCount++;
		}
		return ("<row_count>" + rowCount + "</row_count>" + sb.toString());
	}

    /**
     * This will get all the rows from the ResultSet and store these into an
     * xmlObject. The XMLObject can then be added to or converted to an xml
     * String.
     * 
     * @param connection
     *            is the database connection
     * @param sql
     *            is the sql query
     * @param rootName
     *            is the root element name. this is mandatory
     * @param elementNames
     *            is a corresponding name for each element, if this is null then
     *            the label names will be used instead.
     * @param dateFormat
     *            is the date format we want for all dates, if this is null then
     *            the default is "dd-MM-yyyy"
     * @throws SQLException
     * 
     * @returns an XMLObject containing the rows
     * 
     * @throws DBSQLException
     */
	public XMLObject query2XMLObject(Connection connection, String sql, String rootName, String[] elementNames,
            String dateFormat, List<SqlField> params) throws DBSQLException
	{

		PreparedStatement preparedStatement = null;
		ResultSet rs = null;

		try {
			preparedStatement = connection.prepareStatement(sql);
			populatePreparedStatement(preparedStatement, params);
			rs = preparedStatement.executeQuery();
			return (query2XMLObject(rs, rootName, elementNames, dateFormat));
		} catch (Exception ex) {
            throw new DBSQLException(buildErrorMessage(ex, sql, params));
		} finally {
			DBConnector.closeQuietly(rs);
			DBConnector.closeQuietly(preparedStatement);
		}
	}

	/**
	 * This will get all the rows from the ResultSet and store these into an xmlObject. The XMLObject can then be added
	 * to or converted to an xml String.
	 * 
	 * @param rs
	 *            is the ResultSet we use to retrieve the data a row at a time
	 * @param elementNames
	 *            is a corresponding name for each element, if this is null then the label names will be used instead.
	 * @param rootName
	 *            is the root element name. this is mandatory
	 * @param dateFormat
	 *            is the date format we want for all dates, if this is null then the defaultDateFormat is used. @see
	 *            defaultDateFormat
	 * 
	 * @returns an XMLObject containing the rows
	 * 
	 * @throws SQLException
	 */
	public XMLObject query2XMLObject(ResultSet rs, String rootName, String[] elementNames, String dateFormat)
			throws SQLException
	{

		XMLObject xmlRoot = new XMLObject(rootName);
		ResultSetMetaData rsmd = null;

		SimpleDateFormat sdf;
		if (dateFormat == null) {
			dateFormat = defaultDateFormat;
		}
		sdf = new SimpleDateFormat(dateFormat);

		int rowIndex = 0;
		rsmd = rs.getMetaData();
		// header row
		String[] columnLabel = new String[rsmd.getColumnCount()];
		// String [] columnNames = new String[rsmd.getColumnCount()];
		String[] columnTypes = new String[rsmd.getColumnCount()];
		for (int a = 1; a < rsmd.getColumnCount() + 1; a++) {
			columnLabel[a - 1] = rsmd.getColumnLabel(a);
			// columnNames[a-1] = rsmd.getColumnName(a);
			columnTypes[a - 1] = rsmd.getColumnTypeName(a);
			if (logger.isDebugEnabled()) {
				logger.debug("columnLabel:" + columnLabel[a-1]  + " columnType:" + columnTypes[a-1]);
			}
		}

		// content row/s
		while (rs.next()) {
			XMLObject child = new XMLObject("row");
			xmlRoot.addChild(child);
            if (isIncludeIndex()) {
                child.addAttribute("index", (++rowIndex));
            }
			for (int a = 1; a < rsmd.getColumnCount() + 1; a++) {
				String key;
				if (elementNames != null) {
					key = elementNames[a - 1];
				} else {
					key = columnLabel[a - 1];
				}
				if (columnTypes[a - 1].equals("CLOB")) {
					Clob result = rs.getClob(a);
					try {
                        String escapedXml = StringEscapeUtils.escapeXml(this.clobToString(result));
                        child.addAttribute(key, escapedXml);
						// child.addNodeWithContent(key, this.clobToString(result));
					} catch (Exception e) {
                        // child.addNodeWithContent(key,
                        // "Unable to process clob:" + e.getMessage());
                        child.addAttribute(key, "Unable to process clob:" + e.getMessage());
					}
				} else if (columnTypes[a - 1].equals("DATE") || columnTypes[a - 1].equals("DATETIME")) {
					// String dt = rs.getString(a);
					java.sql.Date date = rs.getDate(a);
					// Log.getInstance().debug(JS.getCurrentMethodName_static()
					// + " DATE:" + date.toString() + " COLUMN:" +
					// columnLabel[a-1] + " SDF:" + sdf.format(date) +
					// " DATEFORMAT:" + dateFormat + " dt:" + dt);
					if (date != null) {
						child.addAttribute(key, sdf.format(date));
					}
				} else {
					String s = rs.getString(a);
					child.addAttribute(key, s != null ? s : "");
				}
			}
		}
		if (rootName != null) {
			xmlRoot.addAttribute("num_rows", rowIndex);
		}
		return (xmlRoot);
	}

	/**
	 * This will escape all non-database friendly characters by appendiing the escape character
	 * "\" before them.  i.e. ' = \' and " = \"
	 * 
	 * @param input
	 *            string for storage into a database
	 * @return the escaped input string.
	 */
	public static String escCharacters(String input)
	{

		if (StringUtils.isEmpty(input))
			return input;
		input = input.replace("'", "\\'");
		input = input.replace("\"", "\\\"");
		return (input);
	}

    public void setIncludeIndex(boolean includeIndex) {
        this.includeIndex = includeIndex;
    }

    public boolean isIncludeIndex() {
        return includeIndex;
    }
}
