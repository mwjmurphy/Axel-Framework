package org.xmlactions.common.util;



import java.util.Calendar;

import org.xmlactions.common.util.DateTime;

import junit.framework.TestCase;



public class DateTimeTest extends TestCase {

	public void testGetDaysInMonth() {
		assertEquals(31, DateTime.getDaysInMonth(0, 2000));
		assertEquals(29, DateTime.getDaysInMonth(1, 2000));
		assertEquals(31, DateTime.getDaysInMonth(2, 2000));
		assertEquals(28, DateTime.getDaysInMonth(1, 2001));
	}

	public void testIsLeapYear() {
		assertTrue(DateTime.isLeapYear(2000));
		assertFalse(DateTime.isLeapYear(2001));
	}

	public void testGetDayOfWeek() {
		assertEquals(7, DateTime.getDayOfWeek(1,0,2000));
		assertEquals(3, DateTime.getDayOfWeek(29,1,2000));
	}

	public void testChangeDateIntIntIntInt() {
		
		Calendar cal = DateTime.changeDate(1, 0, 2000, 0);
		assertEquals(7, cal.get(Calendar.DAY_OF_WEEK));
		cal = DateTime.changeDate(1, 0, 2000, 1);
		assertEquals(1, cal.get(Calendar.DAY_OF_WEEK));
	}

	public void testChangeDateCalendarInt() {
		Calendar cal = DateTime.changeDate(1, 0, 2000, 0);
		cal = DateTime.changeDate(cal, 1);
		assertEquals(1, cal.get(Calendar.DAY_OF_WEEK));
		cal = DateTime.changeDate(cal, -2);
		assertEquals(6, cal.get(Calendar.DAY_OF_WEEK));
	}

	public void testGetDate() {
		String d = DateTime.getDate();
		// date format = dd-mm-yyyy hh:mm:ss
		assertTrue(d.matches("..-..-.... ..:..:.."));
		assertFalse(d.matches("..-..-......:..:.."));
	}

	public void testGetDateLong() {
		String d = DateTime.getDate(1237033922819L);
		assertEquals("14-03-2009 12:32:02", d);
	}

	public void testGetDateLongString() {
		assertEquals("14_03_2009", DateTime.getDate(1237033922819L, "dd_mo_yy"));
		assertEquals("14-MAR-2009", DateTime.getDate(1237033922819L, "dd-MO-yy"));
		assertEquals("14-MAR-2009 12:32:02", DateTime.getDate(1237033922819L, "dd-MO-yy hh:mm:ss"));
	}


	public void testGetDateLongStringString()  {
		assertTrue(DateTime.getDate("14-MAR-2009 12:32:02.000", "dd-MO-yy hh:mm:ss") > 1237033922000L);
		assertTrue(DateTime.getDate("14-MAR-2009 12:32:02.000", "dd-MO-yy hh:mm:ss") < 1237033922999L);
	}

	public void testGetDateStringString() {
	}

	public void testGetDateStringStringString() {
	}

	public void testGetMonthText() {
	}

	public void testGetDayText() {
	}

	public void testGetAbbDayText() {
	}

	public void testGetAbbMonthText() {
	}

	public void testGetMonthFromAbbText() {
	}

	public void testGetSQLDateFormatIntIntIntString() {
	}

	public void testGetSQLDateFormatStringStringStringString() {
	}

	public void testValidateDate() {
	}

	public void testGetMonthNumber() {
	}

	public void testMilliTimeToShowTime() {
	}

	public void testMilliTimeToFullShowTime() {
	}

	public void testConvertDateStringToDateTime() {
	}

	public void testFormatDate() {
	}

}
