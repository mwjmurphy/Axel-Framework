package org.xmlactions.db;


import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;


import org.apache.commons.lang.Validate;
import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.xmlactions.db.exception.DBSQLException;


/**
 * Configures and opens a database, intended for use with an in-memory database
 * such as derby or hsqldb, but should also work with mssql, mysql...
 * 
 * @author MichaelMurphy
 * 
 */
public class ServerDB {

	private static final Logger log = LoggerFactory.getLogger(ServerDB.class);

	private boolean serverStarted = false;

	// need this for shutting down derby db
	private String dbURL;

	private String productName;

	private String url,		// database connection
				   driver,	// database driver
				   login,
				   password;

	public ServerDB() {
	}

	/**
	 * Gets a database connection
	 * 
	 * @return Connection
	 * @throws SQLException
	 */
	public Connection getConnection() throws SQLException {
		Connection connection = DriverManager.getConnection(url, login, password);
		productName = connection.getMetaData().getDatabaseProductName();
		return connection;

	}

	public void stopServer() {
		if (serverStarted == true) {
			try {
				String lc = productName.toLowerCase();
				if (productName.contains("derby")) {
					DriverManager.getConnection(dbURL + ";shutdown=true");
				}
			} catch (Exception ex) {
				log.warn(ex.getMessage());
			}
			serverStarted = false;
		}
	}

    /**
     * Create and start a database server.
     * 
     * @param relativeFileName used as a URL to get the properties
     * @throws IOException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws ClassNotFoundException
     * @throws DBSQLException
     * @throws DocumentException
     * @throws SQLException
     */
	public void startServer(String relativeFileName) throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException, DBSQLException, DocumentException, SQLException {
		// only attempt to setup the server if it's not started
		if (serverStarted == false) {
			Document doc = loadXML(relativeFileName);
			validateDoc(doc);
			Element database = doc.getRootElement();
			Element commands = database.element("commands");
			List<Element> sqlList = commands.elements();

			Connection connection = null;
			Statement statement = null;
			try {

				// need the URL for shutting down derby
				dbURL = database.attributeValue("database-url");

				setDriver(database.attributeValue("database-driver"));
				Class<?> clas = Class.forName(getDriver());
				// clas.newInstance();
				url = database.attributeValue("database-url");
				login = database.attributeValue("login-name");
				password = database.attributeValue("login-password");
				connection = getConnection();

				statement = connection.createStatement();

				for (Element element : sqlList) {
					log.debug("executing [" + element.getTextTrim() + "]");
					processSQL(statement, element.getTextTrim());
				}

				// if we get here without a mistake then the server has started
				serverStarted = true;
			} finally {
				DBConnector.closeQuietly(statement);
				DBConnector.closeQuietly(connection);
			}
		}
	}


    private int processSQL(Statement statement, String sql) throws DBSQLException {
		String lc = sql.toLowerCase();
		try {
			if (lc.startsWith("drop")) {
				return drop(statement, sql);
			} else if (lc.startsWith("create")) {
				return create(statement, sql);
			} else if (lc.startsWith("insert")) {
				return insert(statement, sql);
			} else if (lc.startsWith("update")) {
				return update(statement, sql);
			} else if (lc.startsWith("select")) {
				return select(statement, sql);
			} else {
				return other(statement, sql);
			}
		} catch (Exception ex) {
            throw new DBSQLException(ex.getMessage() + " for sql [" + sql + "]",
					ex);
		}
	}

	private int drop(Statement statement, String sql) {
		try {
			statement.execute(sql);
		} catch (Exception ex) {
			// ignore.
			log.debug(sql + " fail ignored:" + ex.getMessage());
		}
		return -1;
	}

	private int create(Statement statement, String sql) throws SQLException {
		statement.execute(sql);
		return -1;
	}

	private int update(Statement statement, String sql) throws SQLException {
		statement.execute(sql);
		return -1;
	}

	private int other(Statement statement, String sql) throws SQLException {
		statement.execute(sql);
		return -1;
	}

	private int insert(Statement statement, String sql) throws SQLException {
		ResultSet resultSet = null;
		log.debug("productName:"
				+ statement.getConnection().getMetaData()
						.getDatabaseProductName());
		statement.executeUpdate(sql, Statement.RETURN_GENERATED_KEYS);
		resultSet = statement.getGeneratedKeys();
		if (resultSet != null && resultSet.next()) {
			int key = resultSet.getInt(1);
			log.debug("Batches key:" + key);
			return key;
		} else {
			throw new SQLException("Unable to retrieve IDENTITY for insert ["
					+ sql + "]");
		}
	}

	private int select(Statement statement, String sql) throws SQLException {
		ResultSet rs = statement.executeQuery(sql);
		while (rs.next()) {
			StringBuilder sb = new StringBuilder();
			sb.append("|");
			for (int column = 1; column <= rs.getMetaData().getColumnCount(); column++) {
				String colName = rs.getMetaData().getColumnName(column);
				sb.append(colName + ":" + rs.getString(column) + "|");
			}
			log.debug(sb.toString());
		}
		return -1;
	}

	private Document loadXML(String relativeFileName) throws DocumentException {
		URL source = ServerDB.class.getResource(relativeFileName);
		SAXReader reader = new SAXReader();
		Document document = reader.read(source);
		return document;
	}

	@SuppressWarnings("unchecked")
	private void validateDoc(Document doc) {
		Element database = doc.getRootElement();
		Validate.notEmpty(database.attributeValue("login-name"),
				"missing database.login-name");
		Validate.notEmpty(database.attributeValue("login-password"),
				"missing database.login-password");
		Validate.notEmpty(database.attributeValue("database-name"),
				"missing database.database-name");
		Validate.notEmpty(database.attributeValue("database-driver"),
				"missing database.database-driver");
		Validate.notEmpty(database.attributeValue("database-url"),
				"missing database.database-url");
		Element commands = database.element("commands");
		Validate.notNull(commands, "missing commands");
		List<Element> sqlList = commands.elements();
		Validate.notNull(sqlList, "no sql in commands");
		Validate.isTrue(sqlList.size() > 0, "no sql in commands");
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	
}
