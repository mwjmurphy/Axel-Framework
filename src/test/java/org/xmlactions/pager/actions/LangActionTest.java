
package org.xmlactions.pager.actions;


import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
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


public class LangActionTest extends TestCase
{

	private static Logger log = LoggerFactory.getLogger(LangActionTest.class);

	
	private static IExecContext execContext;

	public void setUp()
	{

		if (execContext == null) {
			ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(
					ActionConst.SPRING_STARTUP_CONFIG);
			execContext = (IExecContext) applicationContext.getBean(ActionConst.EXEC_CONTEXT_BEAN_REF);
		}
	}

	public void testReplacement()
			throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException,
			InvocationTargetException, NoSuchMethodException, BadXMLException, NestedActionException
	{

		String page = "<axel:lang key=\"edit_page.add_page\" />";
		Action action = new Action(null, null, "axel");
		List<ReplacementMarker> markers = action.findMarkers(page, PagerTest.NAMESPACE_AXEL, execContext, null);

		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				ActionConst.SPRING_STARTUP_CONFIG);
		Map<String, Object> execContext = (Map<String, Object>) applicationContext
				.getBean(ActionConst.EXEC_CONTEXT_BEAN_REF);

		String newPage = action.replaceMarkers((IExecContext) execContext, page, markers);
		assertEquals("add new page", newPage);

	}


}
