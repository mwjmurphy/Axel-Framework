package org.xmlactions.pager.jasper;


import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.util.HashMap;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.xmlactions.action.config.IExecContext;

public class ReportGenerator {
	
	private static final Logger logger = LoggerFactory.getLogger(ReportGenerator.class);
	

	/**
	 * 
	 * @param conn a database connection if needed.
	 * @param map parameters if needed
	 * @param jasperFileName the compiled jasper report file name
	 * @return
	 * @throws JRException
	 * @throws ClassNotFoundException
	 */
	public  byte[] generateJasperToPdf (Connection conn, HashMap<String, Object>map, String jasperFileName) throws JRException, ClassNotFoundException {
		JasperPrint jprint = (JasperPrint) JasperFillManager.fillReport(jasperFileName, map, conn);
		
		// JasperViewer.viewReport(jprint);
		
		byte [] report = JasperExportManager.exportReportToPdf(jprint);

		if (logger.isDebugEnabled()) {
			logger.debug("pdfImage.size:" + report.length);
		}

		return report;
	}

	/**
	 * 
	 * @param conn a database connection if needed.
	 * @param execContext parameters if needed
	 * @param jasperFileName the compiled jasper report file name
	 * @return
	 * @throws JRException
	 * @throws ClassNotFoundException
	 */
	public  byte[] generateJasperToPdf (Connection conn, IExecContext execContext, String jasperFileName) throws JRException, ClassNotFoundException {

		JasperPrint jprint = (JasperPrint) JasperFillManager.fillReport(jasperFileName, execContext, conn);
			
		byte [] report = JasperExportManager.exportReportToPdf(jprint);

		if (logger.isDebugEnabled()) {
			logger.debug("pdfImage.size:" + report.length);
		}

		return report;
	}


	/**
	 * 
	 * @param conn a database connection if needed.
	 * @param map parameters if needed
	 * @param jasperFileName the compiled jasper report file name
	 * @return
	 * @throws JRException
	 * @throws ClassNotFoundException
	 */
	public  char[] generateJasperToHtml (Connection conn, HashMap<String, Object>map, String jasperFileName) throws JRException, ClassNotFoundException {
		
		JasperPrint jprint = (JasperPrint) JasperFillManager.fillReport(jasperFileName, map, conn);
		
		// JasperViewer.viewReport(jprint);
		File file = null;
		InputStream inputStream = null;
		char [] html = null;
		try {
			file = File.createTempFile("jreport_", "deleteme");
			JasperExportManager.exportReportToHtmlFile(jprint, file.getAbsolutePath());
			inputStream = new FileInputStream(file);
			html = IOUtils.toCharArray(inputStream);
		} catch (Exception ex) {
			IOUtils.closeQuietly(inputStream);
			FileUtils.deleteQuietly(file);
			logger.error(ex.getMessage(), ex);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("html.size:" + html.length);
		}

		return html;
	}


}
