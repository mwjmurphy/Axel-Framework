package org.xmlactions.db.env;

public class EnvironmentAccess {

	/**
	 * 
	 * @return true if we want to test live database
	 */
	public static boolean runDatabaseTests() {
		return System.getenv("axel-test-db") != null;
	}
}
