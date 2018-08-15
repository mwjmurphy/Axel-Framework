package org.xmlactions.db;

import org.xmlactions.db.DBDataType;
import org.xmlactions.db.actions.Date;
import org.xmlactions.db.actions.PK;
import org.xmlactions.db.actions.Text;
import org.xmlactions.db.actions.TextArea;

import junit.framework.TestCase;

public class DBDataTypeTest extends TestCase {

	public void testMatchDBActions() {
		PK pk = new PK();
		DBDataType dbDataType = DBDataType.getDBDataType(pk);
		assertTrue ( dbDataType.equals(DBDataType.integer));

		Date date = new Date();
		dbDataType = DBDataType.getDBDataType(date);
		assertTrue ( dbDataType.equals(DBDataType.date));

		Text text = new Text();
		dbDataType = DBDataType.getDBDataType(text);
		assertTrue ( dbDataType.equals(DBDataType.text));

		TextArea textarea = new TextArea();
		dbDataType = DBDataType.getDBDataType(textarea);
		assertTrue ( dbDataType.equals(DBDataType.text));
	}
}
