package org.xmlactions.pager.actions.dates;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.xmlactions.action.ActionConst;
import org.xmlactions.action.config.IExecContext;

public class TestDateFormatterAction {

	private static Logger log = LoggerFactory.getLogger(TestDateFormatterAction.class);

	private static IExecContext execContext;

	@Before
	public void setUp()
	{

		if (execContext == null) {
			ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(
					ActionConst.SPRING_STARTUP_CONFIG);
			execContext = (IExecContext) applicationContext.getBean(ActionConst.EXEC_CONTEXT_BEAN_REF);
		}
	}


	@Test
	public void testOne() throws Exception {
		DateFormatterAction action = new DateFormatterAction();
		action.setDate_value("25-01-1958");
		action.setOrigin_format("dd-mm-yyyy");
		action.setDestination_format("yyyy-mm-dd");
		String result = action.execute(execContext);
		assertEquals(result, "1958-01-25");
		
		action.setKey("da-result");
		action.execute(execContext);
		result = execContext.getString("da-result");
		assertEquals(result, "1958-01-25");
		
	}

	@Test
	public void testDateTime() throws Exception {
		DateFormatterAction action = new DateFormatterAction();
		action.setDate_value("25-01-1958 10:08:22");
		action.setOrigin_format("dd-MM-yyyy hh:mm:ss");
		action.setDestination_format("yyyy-MM-dd hh:mm:ss");
		String result = action.execute(execContext);
		assertEquals(result, "1958-01-25 10:08:22");
		
		action = new DateFormatterAction();
		action.setDate_value("25-01-1958 10:08:22");
		action.setOrigin_format("dd-MM-yyyy hh:mm:ss");
		action.setDestination_format("yyyy-MM-dd");
		result = action.execute(execContext);
		assertEquals("1958-01-25", result);
		
		action = new DateFormatterAction();
		action.setDate_value("25-01-1958 10:08:22");
		action.setOrigin_format("dd-MM-yyyy");
		action.setDestination_format("yyyy-MM-dd hh:mm:ss");
		result = action.execute(execContext);
		assertEquals("1958-01-25 12:00:00", result);
		
	}

}
