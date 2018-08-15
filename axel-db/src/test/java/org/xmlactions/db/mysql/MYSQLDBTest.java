package org.xmlactions.db.mysql;


import java.io.IOException;
import java.sql.SQLException;

import junit.framework.TestCase;

import org.dom4j.DocumentException;
import org.xmlactions.common.system.JS;
import org.xmlactions.db.ServerDB;
import org.xmlactions.db.exception.DBSQLException;


public class MYSQLDBTest extends TestCase {

	private static ServerDB serverDB;

	public void testServerDB() throws IOException, InstantiationException,
			IllegalAccessException, ClassNotFoundException, SQLException,
			DocumentException, DBSQLException {
		// TODO Find a better way of detecting whether to run this test or not
		if ("mike-laptop".equalsIgnoreCase(JS.getEnv("USERDOMAIN"))) {
			serverDB = new ServerDB();
			serverDB.startServer("/db/mysql_db.xml");
			serverDB.stopServer();
		}
	}

}
