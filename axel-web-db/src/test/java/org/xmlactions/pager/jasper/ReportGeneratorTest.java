/*
 * User links:
 * http://jasperreports.sourceforge.net/tutorial/index.html
 * http://jasperreports.sourceforge.net/documentation.html
 * http://jasperreports.sourceforge.net/quick.how.to.html
 */
package org.xmlactions.pager.jasper;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;

import org.apache.commons.io.FileUtils;
import org.xmlactions.common.io.ResourceUtils;
import org.xmlactions.pager.jasper.ReportGenerator;

import junit.framework.TestCase;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

// import com.mycompany.helper.* ;
// import com.mycompany.dbi.*;

public class ReportGeneratorTest extends TestCase {
	public static void main(String[] args) {
		HashMap hm = null;
		// System.out.println("Usage: ReportGenerator ....");
		try {
			System.out.println("Start ....");
			// Get jasper report
			//String jrxmlFileName = "C:/reports/C1_report.jrxml";
			//String jasperFileName = "C:/reports/C1_report.jasper";
			//String pdfFileName = "C:/reports/C1_report.pdf";
			String jrxmlResourceName = "/jasper/students.jrxml";
			String jasperResourceName = "/jasper/students.jasper";
			String pdfFileName = "students.pdf";
			URL url = ResourceUtils.getResourceURL(jrxmlResourceName);
			String jrxmlFileName = url.getFile();
			url = ResourceUtils.getResourceURL(jasperResourceName);
			String jasperFileName = url.getFile();
			
			JasperCompileManager.compileReportToFile(jrxmlFileName, jasperFileName);

			// String dbUrl = props.getProperty("jdbc.url");
			String dbUrl = "jdbc:mysql://192.168.0.11:3306/test";
			// String dbDriver = props.getProperty("jdbc.driver");
			String dbDriver = "com.mysql.jdbc.Driver";
			// String dbUname = props.getProperty("db.username");
			String dbUname = "schools";
			// String dbPwd = props.getProperty("db.password");
			String dbPwd = "schools";
			// Load the JDBC driver
			Class.forName(dbDriver);
			// Get the connection
			Connection conn = DriverManager.getConnection(dbUrl, dbUname, dbPwd);
			// Create arguments
			// Map params = new HashMap();
			hm = new HashMap();
			hm.put("ID", "123");
			hm.put("DATENAME", "April 2006");
			// Generate jasper print
			JasperPrint jprint = (JasperPrint) JasperFillManager.fillReport(jasperFileName, hm, conn);
			// Export pdf file
			JasperExportManager.exportReportToPdfFile(jprint, pdfFileName);

			System.out.println("Done exporting reports to pdf");

		} catch (Exception e) {
			System.out.print("Exceptiion" + e);
		}
	}

	public void testCompiled() {
		HashMap hm = new HashMap();
		// System.out.println("Usage: ReportGenerator ....");
		try {
			System.out.println("Start ....");
			// Get jasper report
			//String jrxmlFileName = "C:/reports/C1_report.jrxml";
			//String jasperFileName = "C:/reports/C1_report.jasper";
			//String pdfFileName = "C:/reports/C1_report.pdf";
			String jrxmlResourceName = "/jasper/students.jrxml";
			String jasperResourceName = "/jasper/students.jasper";
			String pdfFileName = "c:/students.pdf";
			URL url = ResourceUtils.getResourceURL(jrxmlResourceName);
			String jrxmlFileName = url.getFile();
			url = ResourceUtils.getResourceURL(jasperResourceName);
			String jasperFileName = url.getFile();
			
			// JasperCompileManager.compileReportToFile(jrxmlFileName, jasperFileName);

			// String dbUrl = props.getProperty("jdbc.url");
			String dbUrl = "jdbc:mysql://192.168.0.11:3306/school";
			// String dbDriver = props.getProperty("jdbc.driver");
			String dbDriver = "com.mysql.jdbc.Driver";
			// String dbUname = props.getProperty("db.username");
			String dbUname = "schools";
			// String dbPwd = props.getProperty("db.password");
			String dbPwd = "schools";
			// Load the JDBC driver
			Class.forName(dbDriver);
			// Get the connection
			Connection conn = DriverManager.getConnection(dbUrl, dbUname, dbPwd);
			// Create arguments
			// Map params = new HashMap();
			hm.put("ID", "123");
			hm.put("DATENAME", "April 2006");
			// Generate jasper print
			JasperPrint jprint = (JasperPrint) JasperFillManager.fillReport(jasperFileName, hm, conn);
			// Export pdf file
			JasperExportManager.exportReportToPdfFile(jprint, pdfFileName);

			System.out.println("Done exporting reports to pdf");

		} catch (Exception e) {
			System.out.print("Exceptiion" + e);
		}
	}

	public void testReportGenerator() {
		HashMap hm = new HashMap();
		// System.out.println("Usage: ReportGenerator ....");
		try {
			System.out.println("Start ....");
			String jasperResourceName = "/jasper/students.jasper";
			String pdfFileName = "/students.pdf";
			URL url = ResourceUtils.getResourceURL(jasperResourceName);
			String jasperFileName = url.getFile();
			
			// JasperCompileManager.compileReportToFile(jrxmlFileName, jasperFileName);

			// String dbUrl = props.getProperty("jdbc.url");
			String dbUrl = "jdbc:mysql://192.168.0.11:3306/school";
			// String dbDriver = props.getProperty("jdbc.driver");
			String dbDriver = "com.mysql.jdbc.Driver";
			// String dbUname = props.getProperty("db.username");
			String dbUname = "schools";
			// String dbPwd = props.getProperty("db.password");
			String dbPwd = "schools";
			// Load the JDBC driver
			Class.forName(dbDriver);
			// Get the connection
			Connection conn = DriverManager.getConnection(dbUrl, dbUname, dbPwd);
			// Create arguments
			// Map params = new HashMap();
			hm.put("ID", "123");
			hm.put("DATENAME", "April 2006");
			
			ReportGenerator reportGenerator = new ReportGenerator();
			byte [] pdf = reportGenerator.generateJasperToPdf(conn, hm, jasperFileName);
			
			File outFile = new File(pdfFileName);
			FileUtils.writeByteArrayToFile(outFile, pdf);

		} catch (Exception e) {
			System.out.print("Exceptiion" + e);
		}
	}

}
