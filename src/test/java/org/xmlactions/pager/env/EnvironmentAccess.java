package org.xmlactions.pager.env;

public class EnvironmentAccess {

	/**
	 * 
	 * @return true if we want to test live database
	 */
	public static boolean runRemoteTests() {
		return System.getenv("axel-test-remote") != null;
	}
}
