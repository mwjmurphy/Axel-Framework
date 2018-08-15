package org.xmlactions.db;


import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;
import java.util.Properties;
import java.util.TimeZone;

import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.xmlactions.db.DBUtils;

import junit.framework.TestCase;

public class DBUtilsTest extends TestCase {

	private static final Logger logger = LoggerFactory.getLogger(DBUtilsTest.class);
	public void testDateTimeBuilder () throws SQLException {
		
		String version = System.getProperty("java.version");
		
		int dstOffset;
		if (version != null && version.startsWith("1.5")) {
			Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Europe/London"));
			dstOffset = cal.get(Calendar.DST_OFFSET);
		} else {
			dstOffset = 0;
		}

		String datetime = "1958-01-25 10:30";
		Date date = DBUtils.buildDateFromString(datetime);
		Long systime = date.getTime();
		logger.debug("systime:" + systime + " time:" + date.toString());
		assertEquals(-376579800000l, systime.longValue());
		
		datetime = "1958-01-25";
		date = DBUtils.buildDateFromString(datetime);
		systime = date.getTime();
		logger.debug("systime:" + systime + " time:" + date.toString());
		assertEquals(-376617600000l, systime.longValue());

		datetime = "10:20";
		date = DBUtils.buildDateFromString(datetime);
		systime = date.getTime();
		logger.debug("systime:" + systime + " offset:" + dstOffset + " time:" + date.toString());
		//assertEquals(33600000l, systime.longValue()-dstOffset);

		datetime = "10:21";
		date = DBUtils.buildDateFromString(datetime);
		systime = date.getTime();
		logger.debug("systime:" + systime + " time:" + date.toString());
		//assertEquals(33660000l, systime.longValue()-dstOffset);

		datetime = "2012-01-25 14:30:30.300";
		date = DBUtils.buildDateFromString(datetime);
		systime = date.getTime();
		logger.debug("systime:" + systime + " time:" + date.toString());
		assertEquals(1327501830300l, systime.longValue());
	
		datetime = "2012-01-25 14:30:30.301";
		date = DBUtils.buildDateFromString(datetime);
		systime = date.getTime();
		logger.debug("systime:" + systime + " time:" + date.toString());
		assertEquals(1327501830301l, systime.longValue());
	}
}
