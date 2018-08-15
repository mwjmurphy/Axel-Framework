package org.xmlactions.ee.actions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.junit.Test;
import org.xmlactions.action.Action;
import org.xmlactions.action.NestedActionException;
import org.xmlactions.action.actions.BaseAction;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.common.xml.BadXMLException;
import org.xmlactions.db.actions.Database;
import org.xmlactions.db.actions.Storage;
import org.xmlactions.db.actions.Table;
import org.xmlactions.web.HttpParam;
import org.xmlactions.web.conceal.HtmlRequestMapper;

public class TestProcessIncomingParams {

	@Test
	public void testInit() throws Exception {
		Action action = new Action();
		String page = action.loadPage("src/test/resources", "db/test_storage.xml");
		BaseAction[] actions = action.processXML(UtilsTestHelper.getExecContext(), page);
		assertEquals("Invalid number of actions returned from processXML", 1, actions.length);
		Storage storage = (Storage) actions[0];
		Database testDB = storage.getDatabase("test");
		assertNotNull(testDB);
		Table table = testDB.getTable("tb_projects");
		assertNotNull(table);
	}
	
	@Test
	public void testGetExec() throws IOException {
		IExecContext execContext = UtilsTestHelper.getExecContext();
		assertNotNull(execContext);
	}
	
	@Test
	public void testProcessIncomingParams() throws Exception {
		ProcessIncomingParams processIncomingParams = new ProcessIncomingParams();
		Action action = new Action();
		String page = action.loadPage("src/test/resources", "db/test_storage.xml");
		BaseAction[] actions = action.processXML(UtilsTestHelper.getExecContext(), page);
		Storage storage = (Storage) actions[0];
		Database database = storage.getDatabase("test");
		
        List<HttpParam> paramList = new ArrayList<HttpParam>();
        paramList.add(new HttpParam("table_Name", "tb_projects"));
        paramList.add(new HttpParam("tb_projects.id", "12"));
        paramList.add(new HttpParam("tb_projects.name", "A project name"));

		IExecContext execContext = UtilsTestHelper.getExecContext();

		processIncomingParams.processRequest(database,  paramList, execContext);
		
	}
}
