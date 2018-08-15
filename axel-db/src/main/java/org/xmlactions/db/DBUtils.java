
package org.xmlactions.db;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;


import java.util.Calendar;
import java.util.TimeZone;

import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.xmlactions.db.DBUtils;


public class DBUtils
{
	
    public static final String XSD_LONG_DATE_TIME_FMT = "yyyy-MM-dd HH:mm:ss.SSS";

	private final static Logger log = LoggerFactory.getLogger(DBUtils.class);

	public static void closeQuietly(Connection conn)
	{

		try {
			if (conn != null) {
				conn.close();
			}
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
		}
	}

	public static void closeQuietly(Statement stmt)
	{

		try {
			if (stmt != null) {
				stmt.close();
			}
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
		}
	}

	public static void closeQuietly(ResultSet rset)
	{

		try {
			if (rset != null) {
				rset.close();
			}
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
		}
	}

	public static String getDate(long timeInMillis)
	{

		Date date = new Date(timeInMillis);
		return date.toString();

	}

	/**
	 * 
	 * @param timeInMillis
	 * @param dateFormat
	 *            default is 'yyyy-MM-dd HH:mm'
	 * @return
	 */
	public static String formatDate(long timeInMillis, String dateFormat)
	{

		Date date = new Date(timeInMillis);
		Format formatter;

		// Some examples
		formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String s = formatter.format(date);
		return s;

	}
	
    /**
     * Build a Date from a String date and a formatter
     * @param date as a string format
     * @param formatter 
     * @return date constructed from the date input
     */
    public static Date buildDate(String date, String dateFormat) {
        try {
            return new Date(new SimpleDateFormat(dateFormat).parse(date).getTime());
        } catch (Exception ex) {
            throw new IllegalArgumentException("buildDate:The Date [" + date + "] using a formatter of [" + dateFormat + "] format is incorrect.", ex);
        }
    }

    /**
     * Build a Date from a String date.  The format is expected to match
     * #DBUtils.XSD_LONG_DATE_TIME_FMT
     * @param date as a string format
     * @return date constructed from the date input
     */
    public static Date buildDate(String date) {
    	return buildDate(date, XSD_LONG_DATE_TIME_FMT);
    }

    /**
     * Build a Date from a String date.  The format is expected to match
     * "yyyy-MM-dd hh:mm:ss.sss" or "yyyy-MM-dd" or "hh:mm:ss.sss" 
     * @param date as a string format
     * @return date constructed from the date input
     * @throws SQLException 
     */
    public static Date buildDateFromString(String datetime) throws SQLException {
    	// this is a fix for dojo time format that includes a T at the start of the time.
    	if (datetime.startsWith("T")) {
    		datetime = datetime.substring(1);
    	}

    	String errorString = "Invalid date time format for [" + datetime + "].  Must match 'yyyy-MM-dd hh:mm:ss.sss' or 'yyyy-MM-dd' or 'hh:mm:ss.sss'"; 

    	String [] datePatterns = new String [] {"yyyy","-MM","-dd"};
    	String [] timePatterns = new String [] {"HH",":mm", ":ss", ".SSS"};
    	int datePatternIndex = 0;
    	int timePatternIndex = 0;
    	
    	int wasDate = 1;
    	int wasTime = 2;
    	StringBuilder dateBuilder = new StringBuilder();
    	StringBuilder timeBuilder = new StringBuilder();
    	int lastStoreWas = 0;
    	int lastPos = 0;
    	for (int pos = 0 ; pos < datetime.length()+1; pos++) {
    		char c;
    		if (pos == datetime.length()) {
    			if (lastStoreWas == wasDate) c = '-';
    			else c = ':';
    		} else {
    			c = datetime.charAt(pos);
    		}
    		if(c == '-') {
    			lastStoreWas = wasDate;
    			String pattern = getPatternByIndex(datePatterns, datePatternIndex);
    			if (pattern == null) {
        			throw new SQLException(errorString); 
    			}
    			dateBuilder.append(pattern);
    			datePatternIndex++;
    			lastPos = pos;
    		} else if (c == ':' || c=='.') {
    			lastStoreWas = wasTime; 
    			String pattern = getPatternByIndex(timePatterns, timePatternIndex);
    			if (pattern == null) {
        			throw new SQLException(errorString); 
    			}
    			timeBuilder.append(pattern);
    			timePatternIndex++;
    			lastPos = pos;
    		} else if (c == ' ') {
    			if (lastStoreWas == wasDate) {
        			String pattern = getPatternByIndex(datePatterns, datePatternIndex);
        			if (pattern == null) {
            			throw new SQLException(errorString); 
        			}
        			dateBuilder.append(pattern);
        			datePatternIndex++;
    			} else if (lastStoreWas == wasTime) {
        			String pattern = getPatternByIndex(timePatterns, timePatternIndex);
        			if (pattern == null) {
            			throw new SQLException(errorString); 
        			}
        			timeBuilder.append(pattern);
        			timePatternIndex++;
    			}
    			lastStoreWas = 0;
    			lastPos = pos+1;
    		} else if (isNumber(c)==false) {
    			throw new SQLException(errorString); 
    		}
    	}
    	String pattern = "";
    	if (dateBuilder.length() > 0) {
    		if (timeBuilder.length() > 0) {
    			pattern = dateBuilder.toString() + " " + timeBuilder.toString();
    		} else {
    			pattern = dateBuilder.toString();
    		}
    	} else if (timeBuilder.length() > 0) {
			pattern = timeBuilder.toString();
    	}
    	if (pattern.length() == 0) {
			throw new SQLException(errorString); 
    	}
    	return buildDate(datetime, pattern);
    }
    private static boolean isNumber(char c) {
    	if (c < '0' || c > '9') {
    		return false;
    	}
    	return true;
    }
    private static String getPatternByIndex (String [] patterns, int index) {
    	if (index > -1 && index < patterns.length) {
    		return patterns[index];
    	}
    	return null;
    }
 }
