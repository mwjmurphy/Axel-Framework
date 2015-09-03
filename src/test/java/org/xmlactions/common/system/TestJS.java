package org.xmlactions.common.system;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestJS {

	private static final Logger logger = LoggerFactory.getLogger(TestJS.class);
	
	@Test
	public void testGetEnv() {
		logger.debug("USERNAME:" + JS.getEnv("USERNAME"));
		logger.debug("username:" + JS.getEnv("username"));
	}

	@Test
	public void testGetProperty() {
		logger.debug("file.separator:" + JS.getProperty("file.separator"));
	}
}
