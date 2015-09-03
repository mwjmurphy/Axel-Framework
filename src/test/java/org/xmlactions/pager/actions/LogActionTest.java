
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


public class LogActionTest extends TestCase
{

	private static Logger log = LoggerFactory.getLogger(LogActionTest.class);

	
	private static IExecContext execContext;

	public void setUp()
	{

		if (execContext == null) {
			ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(
					ActionConst.SPRING_STARTUP_CONFIG);
			execContext = (IExecContext) applicationContext.getBean(ActionConst.EXEC_CONTEXT_BEAN_REF);
		}
	}

	public void testLogExecContext()
			throws IOException, DocumentException, ClassNotFoundException, InstantiationException,
			IllegalAccessException, InvocationTargetException, NoSuchMethodException, BadXMLException,
			NestedActionException
	{
		LogAction logAction = new LogAction();
		String output = logAction.execute(execContext);
		log.debug(output);
	}

	public void testLogMap()
			throws IOException, DocumentException, ClassNotFoundException, InstantiationException,
			IllegalAccessException, InvocationTargetException, NoSuchMethodException, BadXMLException,
			NestedActionException
	{
		LogAction logAction = new LogAction();
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("k1","Value k1");
		map.put("k2","Value k2");
		execContext.put("map1", map);
		logAction.setKey("map1");
		String output = logAction.execute(execContext);
		log.debug(output);
	}

}
