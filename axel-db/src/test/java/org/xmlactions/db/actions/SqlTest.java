package org.xmlactions.db.actions;

import java.util.List;

import org.xmlactions.db.actions.Binary;
import org.xmlactions.db.sql.select.SqlField;

import junit.framework.TestCase;

public class SqlTest extends TestCase {
	
	public void testGetListOfParams() {
		Sql sql = new Sql();
		sql.setParams("v1=xxx, v2='v2', v3=&ltabc&gt, v4=, v5");
		List<SqlField> list = sql.getListOfParams(null);
		assertEquals(5, list.size());
	}
}
