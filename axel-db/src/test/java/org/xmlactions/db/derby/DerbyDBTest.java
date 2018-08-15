package org.xmlactions.db.derby;


import java.io.IOException;
import java.sql.SQLException;

import junit.framework.TestCase;


import org.dom4j.DocumentException;
import org.xmlactions.db.ServerDB;
import org.xmlactions.db.exception.DBSQLException;


public class DerbyDBTest extends TestCase {

	private static ServerDB serverDB;

	public void testServerDB() throws IOException, InstantiationException,
			IllegalAccessException, ClassNotFoundException, SQLException,
			DocumentException, DBSQLException {
		serverDB = new ServerDB();
		serverDB.startServer("/db/derby_db.xml");
		serverDB.stopServer();
	}

}
