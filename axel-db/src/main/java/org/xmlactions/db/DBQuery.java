/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.xmlactions.db;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import org.xmlactions.db.DBUtils;



/**
 * 
 * @author MichaelMurphy
 */
public class DBQuery
{

	Connection connection;

	public DBQuery(Connection connection)
	{

		this.connection = connection;
	}

	/**
	 * @author MMURPHY
	 * @since 6-JUN-05 Retrieve a query from the db. The result is the column
	 *        names followed by each row. All columns are separated by the '|'
	 *        character.
	 * @param (String) query is the sql query to send to the database
	 */
	public String query(String query) throws Exception
	{

		Statement stmt = null;
		ResultSet rs = null;

		// execute SQL Insert
		try {
			stmt = connection.createStatement();
			rs = stmt.executeQuery(query);
			return (query(rs));
		} catch (SQLException ex) {
			throw ex;
		} finally {
			DBUtils.closeQuietly(rs);
			DBUtils.closeQuietly(stmt);
		}
	}

	public String query(ResultSet rs) throws Exception
	{

		StringBuffer sb = new StringBuffer();
		ResultSetMetaData rsmd = null;

		// execute SQL Insert
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

}
