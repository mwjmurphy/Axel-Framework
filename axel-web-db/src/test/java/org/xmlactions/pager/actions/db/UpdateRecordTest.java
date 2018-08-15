
package org.xmlactions.pager.actions.db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.xmlactions.action.ActionConst;
import org.xmlactions.action.config.ExecContext;
import org.xmlactions.pager.actions.form.ClientParamNames;
import org.xmlactions.pager.actions.form.processor.PostCodeProcessor;
import org.xmlactions.pager.actions.form.processor.PreCodeProcessor;
import org.xmlactions.pager.env.EnvironmentAccess;
import org.xmlactions.web.HttpParam;
import org.xmlactions.web.PagerWebConst;


public class UpdateRecordTest
{

	private final static Logger log = LoggerFactory.getLogger(UpdateRecordTest.class);

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
	public void testUpdateRecord() throws Exception
	{

		List<HttpParam> params = new ArrayList<HttpParam>();
		params.add(new HttpParam("Name", "test name"));
		params.add(new HttpParam("Address", "test address"));
		params.add(new HttpParam(ClientParamNames.TABLE_NAME_MAP_ENTRY, "tb_hobby"));
		params.add(new HttpParam(ClientParamNames.STORAGE_CONFIG_REF, "storageConfig"));
		params.add(new HttpParam(ClientParamNames.PK_VALUE, "1"));
		execContext.put(PagerWebConst.REQUEST_LIST, params);
		
		UpdateRecord updateRecord = new UpdateRecord();
		try {
			String s = updateRecord.execute(execContext);
			if (s.startsWith("EX")) {
				fail(s);
			}
			log.debug("response from updateRecord [" + s + "]");
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
	public void testPreProcessor() {
		UpdateRecord updateRecord = new UpdateRecord();
		PreCodeProcessor preCodeProcessor = updateRecord.getPreCodeProcessor();
		PostCodeProcessor postCodeProcessor = updateRecord.getPostCodeProcessor();
		
		preCodeProcessor.addProcessorParam(ClientParamNames.PRE_PROCESSOR + ".2.1", "2.param.1");
		preCodeProcessor.addProcessorParam(ClientParamNames.PRE_PROCESSOR + ".2", "CodeCall.method.2");
		preCodeProcessor.addProcessorParam(ClientParamNames.PRE_PROCESSOR + ".2.2", "2.param.2");
		preCodeProcessor.addProcessorParam(ClientParamNames.PRE_PROCESSOR + ".1", "CodeCall.method.1");
		preCodeProcessor.addProcessorParam(ClientParamNames.PRE_PROCESSOR + ".1.1", "1.param.1");
		preCodeProcessor.addProcessorParam(ClientParamNames.PRE_PROCESSOR + ".1.2", "1.param.2");
		assertEquals("CodeCall.method.1", preCodeProcessor.getProcessorsMap().get(ClientParamNames.PRE_PROCESSOR + ".1").getCall());

		postCodeProcessor.addProcessorParam(ClientParamNames.POST_PROCESSOR + ".2.1", "2.param.1");
		postCodeProcessor.addProcessorParam(ClientParamNames.POST_PROCESSOR + ".2", "CodeCall.method.2");
		postCodeProcessor.addProcessorParam(ClientParamNames.POST_PROCESSOR + ".2.2", "2.param.2");
		postCodeProcessor.addProcessorParam(ClientParamNames.POST_PROCESSOR + ".1", "CodeCall.method.1");
		postCodeProcessor.addProcessorParam(ClientParamNames.POST_PROCESSOR + ".1.1", "1.param.1");
		postCodeProcessor.addProcessorParam(ClientParamNames.POST_PROCESSOR + ".1.2", "1.param.2");
		assertEquals("CodeCall.method.2", preCodeProcessor.getProcessorsMap().get(ClientParamNames.PRE_PROCESSOR + ".2").getCall());

	}
}
