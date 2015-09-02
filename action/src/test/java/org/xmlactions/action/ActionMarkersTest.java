
package org.xmlactions.action;


import java.lang.reflect.InvocationTargetException;
import java.util.List;

import junit.framework.TestCase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.xmlactions.action.Action;
import org.xmlactions.action.ActionConst;
import org.xmlactions.action.NestedActionException;
import org.xmlactions.action.ReplacementMarker;
import org.xmlactions.action.config.ExecContext;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.common.xml.BadXMLException;

public class ActionMarkersTest extends TestCase
{

	private final static Logger log = LoggerFactory.getLogger(ActionMarkersTest.class);

	private static final String [] configFiles = {
		"/config/spring/spring-config.xml"
	};
	private static ApplicationContext applicationContext = new ClassPathXmlApplicationContext(configFiles);

	IExecContext execContext = null;
	public void setUp()
	{

		if (execContext == null) {
			execContext = (ExecContext)applicationContext.getBean(ActionConst.EXEC_CONTEXT_BEAN_REF);
			execContext.setApplicationContext(applicationContext);
			execContext.put("echo", org.xmlactions.action.actions.Echo.class);
		}
	}

	public void testFindEchos()
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException,
			NoSuchMethodException, NestedActionException, BadXMLException
	{

		String page = "<pager:echo >zssd</pager:echo>";
		Action action = new Action("test", "test", "pager");
		List<ReplacementMarker> markers = action.findMarkers(page, ActionTest.NAMESPACE, execContext, null);
		assertEquals(1, markers.size());
		String newPage = action.replaceMarkers(execContext, page, markers);

		assertEquals("zssd", newPage);
		log.debug("result:" + newPage.toString());
	}

}
