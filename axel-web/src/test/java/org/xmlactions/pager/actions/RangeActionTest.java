
package org.xmlactions.pager.actions;


import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.dom4j.DocumentException;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.xmlactions.action.Action;
import org.xmlactions.action.ActionConst;
import org.xmlactions.action.NestedActionException;
import org.xmlactions.action.ReplacementMarker;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.common.xml.BadXMLException;
import org.xmlactions.pager.PagerTest;


public class RangeActionTest extends TestCase
{

	private static Logger log = LoggerFactory.getLogger(RangeActionTest.class);

	
	private static IExecContext execContext;

	public void setUp()
	{

		if (execContext == null) {
			ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(
					ActionConst.SPRING_STARTUP_CONFIG);
			execContext = (IExecContext) applicationContext.getBean(ActionConst.EXEC_CONTEXT_BEAN_REF);
		}
	}

	public void testIncrement()
			throws IOException, DocumentException, ClassNotFoundException, InstantiationException,
			IllegalAccessException, InvocationTargetException, NoSuchMethodException, BadXMLException,
			NestedActionException
	{
		RangeAction rangeAction = new RangeAction();
		rangeAction.setFrom(1);
		rangeAction.setTo(10);
		rangeAction.setName("range_0_10");
		rangeAction.execute(execContext);
		int n = Integer.parseInt(execContext.replace("${range_0_10}"));
		assertTrue(n == 1);
		for (int x = 0 ; x < 100 ; x++) {
			// log.debug("increment:" + x + ":" + execContext.replace("${range_0_9}"));
		}
		n = Integer.parseInt(execContext.replace("${range_0_10}"));
		assertTrue(n == 2);
	}

	public void testDecrement()
			throws IOException, DocumentException, ClassNotFoundException, InstantiationException,
			IllegalAccessException, InvocationTargetException, NoSuchMethodException, BadXMLException,
			NestedActionException
	{
		RangeAction rangeAction = new RangeAction();
		rangeAction.setFrom(10);
		rangeAction.setTo(1);
		rangeAction.setName("range_10_1");
		rangeAction.execute(execContext);
		int n = Integer.parseInt(execContext.replace("${range_10_1}"));
		assertTrue(n == 10);
		for (int x = 0 ; x < 100 ; x++) {
			// log.debug("decrement:" + x + ":" + execContext.replace("${range_10_1}"));
		}
		n = Integer.parseInt(execContext.replace("${range_10_1}"));
		assertTrue(n == 9);
	}

	public void testRandom()
			throws IOException, DocumentException, ClassNotFoundException, InstantiationException,
			IllegalAccessException, InvocationTargetException, NoSuchMethodException, BadXMLException,
			NestedActionException
	{
		RangeAction rangeAction = new RangeAction();
		rangeAction.setFrom(1);
		rangeAction.setTo(10);
		rangeAction.setRandom(true);
		rangeAction.setName("range_0_10");
		rangeAction.execute(execContext);
		for (int x = 0 ; x < 100 ; x++) {
			int n = Integer.parseInt(execContext.replace("${range_0_10}"));
			// log.debug("random:" + x + ":" + execContext.replace("${range_0_10}"));
			assertTrue(n >= 1 && n <= 10);
		}
	}


}
