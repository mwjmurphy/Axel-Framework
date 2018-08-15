
package org.xmlactions.pager.actions.db;

import static org.junit.Assert.fail;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.xmlactions.action.ActionConst;
import org.xmlactions.action.config.ExecContext;
import org.xmlactions.pager.actions.form.ClientParamNames;
import org.xmlactions.pager.env.EnvironmentAccess;
import org.xmlactions.web.HttpParam;
import org.xmlactions.web.PagerWebConst;


public class InsertRecordTest
{

	private final static Logger log = LoggerFactory.getLogger(InsertRecordTest.class);

	private static ExecContext execContext;

	@Before
	public void beforeMethod() {
		org.junit.Assume.assumeTrue(EnvironmentAccess.runDatabaseTests());
		setUp();
	}


	public void setUp()
	{

		if (execContext == null) {
			ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(new String[] {
					ActionConst.SPRING_STARTUP_CONFIG, "classpath*:config/spring/test-spring-pager-web-startup.xml" });
			execContext = (ExecContext)applicationContext.getBean(ActionConst.EXEC_CONTEXT_BEAN_REF);
			execContext.setApplicationContext(applicationContext);
		}
	}

	@Test
	public void testAddRecord() throws Exception
	{


		List<HttpParam> params = new ArrayList<HttpParam>();
		params.add(new HttpParam("TypeID", "test name"));
		params.add(new HttpParam("Comment", "test address"));
		params.add(new HttpParam(ClientParamNames.TABLE_NAME_MAP_ENTRY, "tb_single_select"));
		params.add(new HttpParam(ClientParamNames.STORAGE_CONFIG_REF, "storageConfig"));
		execContext.put(PagerWebConst.REQUEST_LIST, params);

		InsertRecord addRecord = new InsertRecord();
		try {
			String s = addRecord.execute(execContext);
			if (s.startsWith("EX")) {
				fail(s);
			}
			log.debug("response from addRecord [" + s + "]");
		} catch (SQLException ex) {
			log.error(ex.getMessage(), ex);
			fail(ex.getMessage());
		} catch (IllegalArgumentException ex) {
			// TODO use a simulated table to perform full insert test.
			log.error(ex.getMessage(), ex);
			fail(ex.getMessage());
		}

	}

	@Test
	public void testAddRecordToMultipleTables() throws Exception
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("tb_linked_parent.description", "linked parent description");
		map.put("tb_linked_child.description", "linked child description");
		map.put(ClientParamNames.TABLE_NAME_MAP_ENTRY, "tb_linked_child");
		map.put(ClientParamNames.STORAGE_CONFIG_REF, "storageConfig");
		execContext.addNamedMap(PagerWebConst.REQUEST, map);

		InsertRecord addRecord = new InsertRecord();
		try {
			String s = addRecord.execute(execContext);
			if (s.startsWith("EX")) {
				fail(s);
			}
			log.debug("response from addRecord [" + s + "]");
		} catch (SQLException ex) {
			log.error(ex.getMessage(), ex);
			fail(ex.getMessage());
		} catch (IllegalArgumentException ex) {
			// TODO use a simulated table to perform full insert test.
			log.error(ex.getMessage(), ex);
			fail(ex.getMessage());
		}

	}

	@Test
	public void testAddRecordToMultipleTablesWithTablePath() throws Exception
	{
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("tb_address.street1", "street1 address");
		map.put("tb_address.street2", "street1 address");
		map.put("tb_address.city", "city address");
		map.put("tb_name.name", "name");
		map.put(ClientParamNames.TABLE_NAME_MAP_ENTRY, "tb_name");
		map.put(ClientParamNames.STORAGE_CONFIG_REF, "storageConfig");
		execContext.addNamedMap(PagerWebConst.REQUEST, map);

		InsertRecord addRecord = new InsertRecord();
		try {
			String s = addRecord.execute(execContext);
			if (s.startsWith("EX")) {
				fail(s);
			}
			log.debug("response from addRecord [" + s + "]");
		} catch (SQLException ex) {
			log.error(ex.getMessage(), ex);
			fail(ex.getMessage());
		} catch (IllegalArgumentException ex) {
			// TODO use a simulated table to perform full insert test.
			log.error(ex.getMessage(), ex);
			fail(ex.getMessage());
		}

	}

}
