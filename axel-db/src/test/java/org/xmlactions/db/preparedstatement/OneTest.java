package org.xmlactions.db.preparedstatement;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import junit.framework.TestCase;

import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.junit.Before;
import org.junit.Test;
import org.xmlactions.db.env.EnvironmentAccess;

public class OneTest {

	private static final Logger logger = LoggerFactory.getLogger(OneTest.class);

	@Before
	public void beforeMethod() {
		org.junit.Assume.assumeTrue(EnvironmentAccess.runDatabaseTests());
	}


	@Test
	public void test1() {
		logger.debug("Twice use prepared statement example!\n");
		Connection con = null;
		PreparedStatement prest;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql:localhost:3306/jdbctutorial","root","root");
			try{
				String sql = "SELECT * FROM movies WHERE year_made = ?";
				prest = con.prepareStatement(sql);
				prest.setInt(1,2002);
				ResultSet rs1 = prest.executeQuery();
				logger.debug("List of movies that made in year 2002");
				while (rs1.next()){
					String mov_name = rs1.getString(1);
					int mad_year = rs1.getInt(2);
					System.out.println(mov_name + "\t- " + mad_year);
				}
				prest.setInt(1,2003);
				ResultSet rs2 = prest.executeQuery();
				logger.debug("List of movies that made in year 2003");
				while (rs2.next()){
					String mov_name = rs2.getString(1);
					int mad_year = rs2.getInt(2);
					System.out.println(mov_name + "\t- " + mad_year);
				}
			}
			catch (SQLException s){
				System.out.println("SQL statement is not executed!");
			}
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void test2() {
		logger.debug("Twice use prepared statement example!\n");
		Connection con = null;
		PreparedStatement prest;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql:localhost:3306/jdbctutorial","root","root");
			try{
				String sql = "SELECT * FROM movies WHERE year_made = ?";
				prest = con.prepareStatement(sql);
				prest.setInt(1,2002);
				prest.setObject(2, "xx", java.sql.Types.BLOB);
				ResultSet rs1 = prest.executeQuery();
				logger.debug("List of movies that made in year 2002");
				while (rs1.next()){
					String mov_name = rs1.getString(1);
					int mad_year = rs1.getInt(2);
					System.out.println(mov_name + "\t- " + mad_year);
				}
				prest.setInt(1,2003);
				ResultSet rs2 = prest.executeQuery();
				logger.debug("List of movies that made in year 2003");
				while (rs2.next()){
					String mov_name = rs2.getString(1);
					int mad_year = rs2.getInt(2);
					System.out.println(mov_name + "\t- " + mad_year);
				}
			}
			catch (SQLException s){
				System.out.println("SQL statement is not executed!");
			}
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
}
