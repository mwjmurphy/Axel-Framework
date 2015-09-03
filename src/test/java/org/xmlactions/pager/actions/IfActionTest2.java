
package org.xmlactions.pager.actions;


import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

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


public class IfActionTest2 extends TestCase
{

	private static Logger log = LoggerFactory.getLogger(IfActionTest2.class);

	public void testPagerLoad()
			throws IOException, NestedActionException, DocumentException, ClassNotFoundException,
			InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException,
			BadXMLException
	{

		String page = "<body>\n" + 
			" <pager:if expression=\"10==10\">\n" +
			"  <td>this is a td in if [10==10]</td>\n" +
			" </pager:if>\n" + "</body>";
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				ActionConst.SPRING_STARTUP_CONFIG);

		IExecContext execContext = (IExecContext) applicationContext.getBean(ActionConst.EXEC_CONTEXT_BEAN_REF);
		execContext.put(ActionConst.PAGE_NAMESPACE_BEAN_REF, "pager");
		execContext.put(ActionConst.WEB_REAL_PATH_BEAN_REF, "/pages");
		Action action = new Action("/pages", "if2.xhtml", "pager");
		List<ReplacementMarker> markers = action.findMarkers(page, PagerTest.NAMESPACE, execContext, null);
		String newPage = action.replaceMarkers(execContext, page, markers);
		log.debug("page:" + page);
		assertTrue(page.contains("this is a td in if [10==10]"));
	}
}
