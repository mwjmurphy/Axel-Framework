package org.xmlactions.pager.env;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EnvironmentAccess {

	private static final Logger logger = LoggerFactory.getLogger(EnvironmentAccess.class);
	
	/**
	 * 
	 * @return true if we want to test live database
	 */
	public static boolean runDatabaseTests() {
		boolean result = System.getenv("axel-test-db") != null;
		if (result == false) {
			logger.warn("Not running database tests - because 'axel-test-db' is not set as an environmental property. To run the database tests set axel-test-db=true in the environment.");
		}
		return result;
	}
}
