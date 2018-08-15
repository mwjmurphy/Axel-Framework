
package org.xmlactions.db;


import java.util.Hashtable;

import org.xmlactions.db.DBWriteUtils;


import junit.framework.TestCase;

public class DBWriteUtilsTest extends TestCase
{

	public void testBuildUpdateTableRow()
	{

		Hashtable<String, String> map = new Hashtable<String, String>();
		map.put("name", "fred");
		map.put("email", "fred@bedrock.com");
		String update = DBWriteUtils.buildUpdateTableRow("tb_user", map, "where 1==1");
		assertEquals("update tb_user set email='fred@bedrock.com',name='fred' where 1==1", update);
	}

	public void testBuildAddTableRow()
	{

		Hashtable<String, String> map = new Hashtable<String, String>();
		map.put("name", "fred");
		map.put("email", "fred@bedrock.com");
		String insert = DBWriteUtils.buildAddTableRow("one4all", "tb_user", map);
		// log.debug("insert:" + insert);
		assertEquals("insert into one4all.tb_user (email,name) values ('fred@bedrock.com','fred')", insert);
	}

}
