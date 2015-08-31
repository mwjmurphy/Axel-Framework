package org.xmlactions.common.date;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmlactions.common.date.DateUtils;

import junit.framework.TestCase;

public class DateUtilsTest extends TestCase {

	private static final Logger log = LoggerFactory.getLogger(DateUtilsTest.class);
	public void testGetDate() {
		long t = DateUtils.getDate("2010-10-02", "yyyy-MM-dd");
		assertTrue(  t == 1285974000000l ? true : t == 1285977600000l ? true : false);
	}
}
