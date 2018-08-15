
package org.xmlactions.db.mysql;


import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;


import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.xmlactions.db.DBSQL;
import org.xmlactions.db.actions.BaseStorageField;
import org.xmlactions.db.actions.Table;
import org.xmlactions.db.exception.DBSQLException;
import org.xmlactions.db.template.ICreateTable;


public class CreateTable implements ICreateTable
{

	private final static Logger log = LoggerFactory.getLogger(CreateTable.class);

	public boolean exists(Connection connection, String dbName, String tbName) throws SQLException
	{

		boolean exists = false;
		DatabaseMetaData dbm = connection.getMetaData();
		ResultSet rs = dbm.getTables(dbName, null, tbName, null);
		while (rs.next()) {
			// for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
			// log.debug("column[" + (i + 1) + "] = [" + rs.getString(i + 1));
			// }
			String catalog = rs.getString(1);
			String table = rs.getString(3);
			// log.debug("existing table:" + catalog + "." + table);
			if (table.equalsIgnoreCase(tbName)) {
				exists = true;
				break;
			}
		}
		return exists;
	}

    public int createTable(Connection connection, String dbName, Table table) throws SQLException, DBSQLException
	{

		StringBuilder sb = new StringBuilder();
		String additional = "";
		sb.append("create table `" + dbName + "`.`" + table.getName() + "` (\n");

		CreateField createField = new CreateField();
		for (int i = 0; i < table.getFields().size(); i++) {
			BaseStorageField field = table.getFields().get(i);
			String fieldSQL = createField.createFieldSQL(field);
			if (i > 0)
				sb.append(",\n ");
			sb.append(fieldSQL);
		}
		sb.append(") ENGINE=InnoDB DEFAULT CHARSET=utf8;");

		String sql = connection.nativeSQL(sb.toString());
		// log.debug("create table:" + sql);
		int result = DBSQL.insert(connection, sql);
		return result;
	}

    public int dropTable(Connection connection, String dbName, String tbName) throws SQLException, DBSQLException
	{

		String sql = connection.nativeSQL("drop table `" + dbName + "`.`" + tbName + "`");
		int result = DBSQL.insert(connection, sql);
		return result;
	}

}
