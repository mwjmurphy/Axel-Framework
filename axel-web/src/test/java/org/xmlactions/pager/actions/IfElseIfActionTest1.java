
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


public class IfElseIfActionTest1 extends TestCase
{

	private static Logger log = LoggerFactory.getLogger(IfElseIfActionTest1.class);

	public void testIf1()
	throws IOException, NestedActionException, DocumentException, ClassNotFoundException,
	InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException,
	BadXMLException
	{

		String page = "<body>\n" + 
		" <pager:if expression=\"10==10\">\n" +
		" 	<pager:elseif expression=\"10==11\">\n" +
		"   <td>this is a td in elseif [10==11]</td>\n" +
		" 	</pager:elseif>\n" +
		"  <td>this is a td in if [10==10]</td>\n" +
		" </pager:if>\n" +
		"</body>";
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				ActionConst.SPRING_STARTUP_CONFIG);

		IExecContext execContext = (IExecContext) applicationContext.getBean(ActionConst.EXEC_CONTEXT_BEAN_REF);
		execContext.put(ActionConst.PAGE_NAMESPACE_BEAN_REF, "pager");
		execContext.put(ActionConst.WEB_REAL_PATH_BEAN_REF, "/pages");
		Action action = new Action();
		action.setNameSpaces(PagerTest.NAMESPACE);
		String resultantPage = action.processPage(execContext, page);
		log.debug("resultantPage:" + resultantPage);
		assertTrue(page.contains("<td>this is a td in if [10==10]</td>"));
	}
	public void testIf2()
	throws IOException, NestedActionException, DocumentException, ClassNotFoundException,
	InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException,
	BadXMLException
	{

		String page = "<body>\n" + 
		" <pager:if expression=\"10==11\">\n" +
		" 	<pager:elseif expression=\"10==10\">\n" +
		"   <td>this is a td in elseif [10==10]</td>\n" +
		" 	</pager:elseif>\n" +
		"  <td>this is a td in if [10==11]</td>\n" +
		" </pager:if>\n" +
		"</body>";
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				ActionConst.SPRING_STARTUP_CONFIG);

		IExecContext execContext = (IExecContext) applicationContext.getBean(ActionConst.EXEC_CONTEXT_BEAN_REF);
		execContext.put(ActionConst.PAGE_NAMESPACE_BEAN_REF, "pager");
		execContext.put(ActionConst.WEB_REAL_PATH_BEAN_REF, "/pages");
		Action action = new Action();
		action.setNameSpaces(PagerTest.NAMESPACE);
		String resultantPage = action.processPage(execContext, page);
		log.debug("resultantPage:" + resultantPage);
		assertTrue(page.contains("<td>this is a td in elseif [10==10]</td>"));
	}


	public void testIf3()
	throws IOException, NestedActionException, DocumentException, ClassNotFoundException,
	InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException,
	BadXMLException
	{

		String page = "<body>\n" + 
		" <pager:if expression=\"10==11\">\n" +
		" 	<pager:elseif expression=\"10==11\">\n" +
		"   <td>this is a td in elseif [10==11]</td>\n" +
		" 	</pager:elseif>\n" +
		" 	<pager:else expression=\"10==10\">\n" +
		"   <td>this is a td in else [10==10]</td>\n" +
		" 	</pager:else>\n" +
		"  <td>this is a td in if [10==11]</td>\n" +
		" </pager:if>\n" +
		"</body>";
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				ActionConst.SPRING_STARTUP_CONFIG);

		IExecContext execContext = (IExecContext) applicationContext.getBean(ActionConst.EXEC_CONTEXT_BEAN_REF);
		execContext.put(ActionConst.PAGE_NAMESPACE_BEAN_REF, "pager");
		execContext.put(ActionConst.WEB_REAL_PATH_BEAN_REF, "/pages");
		Action action = new Action();
		action.setNameSpaces(PagerTest.NAMESPACE);
		String resultantPage = action.processPage(execContext, page);
		log.debug("resultantPage:" + resultantPage);
		assertTrue(page.contains("<td>this is a td in else [10==10]</td>"));
	}
}
