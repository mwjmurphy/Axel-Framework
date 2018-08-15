
package org.xmlactions.db.actions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import junit.framework.TestCase;

import org.apache.commons.beanutils.MethodUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.junit.Before;
import org.junit.Test;
import org.xmlactions.action.Action;
import org.xmlactions.action.NestedActionException;
import org.xmlactions.action.actions.BaseAction;
import org.xmlactions.common.xml.BadXMLException;
import org.xmlactions.db.actions.BaseStorageField;
import org.xmlactions.db.actions.CommonStorageField;
import org.xmlactions.db.actions.Database;
import org.xmlactions.db.actions.Date;
import org.xmlactions.db.actions.FK;
import org.xmlactions.db.actions.Int;
import org.xmlactions.db.actions.PK;
import org.xmlactions.db.actions.Select;
import org.xmlactions.db.actions.Storage;
import org.xmlactions.db.actions.Table;
import org.xmlactions.db.actions.Text;
import org.xmlactions.db.actions.TextArea;
import org.xmlactions.db.actions.TimeOfDay;
import org.xmlactions.db.actions.TimeStamp;
import org.xmlactions.db.env.EnvironmentAccess;

public class FieldsTest
{

	private final static Logger log = LoggerFactory.getLogger(FieldsTest.class);

	private final static BaseStorageField[] fields = new BaseStorageField[] { 
		new PK(),
		new Text(),
		new Text(),
		new TextArea(),
		new FK(),
		new FK(),
		new FK(),
		new FK()
	};

	@Before
	public void beforeMethod() {
		org.junit.Assume.assumeTrue(EnvironmentAccess.runDatabaseTests());
	}
	

	@Test
	public void testLoadFields()
			throws IOException, NestedActionException, ClassNotFoundException, InstantiationException,
			IllegalAccessException, InvocationTargetException, NoSuchMethodException, BadXMLException
	{

		Action action = new Action();
		String page = action.loadPage("src/test/resources", "db/storage.xml");
		BaseAction[] actions = action.processXML(UtilsTestHelper.getExecContext(), page);
		assertEquals("Invalid number of actions returned from processXML", 1, actions.length);

		assertTrue("Action [" + action.getClass().getName() + "] should be a Storage class",
				actions[0] instanceof Storage);

		Storage storage = (Storage) actions[0];
		List<Database> databases = storage.getDatabases();
		List<Table> tables = databases.get(0).getTables();
		Table table = tables.get(0);
		int index = 0;
		if (table.getFields().size() != fields.length) {
			fail(table.getName() + " fields.size = " + table.getFields().size() + " must equal fields.length = " + fields.length);
		}
		for (BaseStorageField field : table.getFields()) {
			field.getClass().getName().equals(fields[index++].getClass().getName());
			MethodUtils.invokeMethod(this, "validateField", field);
		}
	}

	@Test
	public void testLoadAllTypes() throws Exception
	{
		
		Action action = new Action();
		String page = action.loadPage("src/test/resources", "db/storage.xml");
		BaseAction[] actions = action.processXML(UtilsTestHelper.getExecContext(), page);
		assertEquals("Invalid number of actions returned from processXML", 1, actions.length);
		
		assertTrue("Action [" + action.getClass().getName() + "] should be a Storage class",
				actions[0] instanceof Storage);
		
		Storage storage = (Storage) actions[0];
		assertNotNull(storage);
		Database database = storage.getDatabase("test");
		assertNotNull(database);
		Table table = database.getTable("tb_all_types");
		assertNotNull(table);
		List<CommonStorageField> fieldList = table.getFields();
		assertEquals(12, fieldList.size());
		int index = 0;
		for (CommonStorageField field : table.getFields()) {
			MethodUtils.invokeMethod(this, "validateField", field);
		}
	}


	public void validateField(PK pk)
	{

		assertFalse("Invalid Name for PK", StringUtils.isEmpty(pk.getName()));
	}

	public void validateField(FK fk)
	{
		assertFalse("Invalid Name for FK", StringUtils.isEmpty(fk.getName()));
		assertFalse("Invalid Foreign Key", StringUtils.isEmpty(fk.getForeign_key()));
		assertFalse("Invalid Foreign Table", StringUtils.isEmpty(fk.getForeign_table()));
	}

	public void validateField(Text text)
	{
		assertFalse("Invalid Name for Text", StringUtils.isEmpty(text.getName()));
	}

	public void validateField(TextArea textArea)
	{
		assertFalse("Invalid Name for TextArea", StringUtils.isEmpty(textArea.getName()));
	}

	public void validateField(Int intAction)
	{
		assertFalse("Invalid Name for Int", StringUtils.isEmpty(intAction.getName()));
	}

	public void validateField(Date date)
	{
		assertFalse("Invalid Name for Date", StringUtils.isEmpty(date.getName()));
	}

	public void validateField(TimeStamp timestamp)
	{
		assertFalse("Invalid Name for TimeStamp", StringUtils.isEmpty(timestamp.getName()));
	}

	public void validateField(TimeOfDay timeOfDay)
	{
		assertFalse("Invalid Name for TimeOfDay", StringUtils.isEmpty(timeOfDay.getName()));
	}
	public void validateField(Select select)
	{
		assertFalse("Invalid Name for Select", StringUtils.isEmpty(select.getName()));
	}
}
