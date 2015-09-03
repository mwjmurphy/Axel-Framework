
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


public class IfElseAndElseIfTest extends TestCase
{

	private static Logger log = LoggerFactory.getLogger(IfElseAndElseIfTest.class);

	public void testPagerLoad()
			throws IOException, NestedActionException, DocumentException, ClassNotFoundException,
			InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException,
			BadXMLException
	{

		String page =
			"<body>\n" +
			"<pager:if expression=\"10==10\">\n" +
			"<td>this is a td in if [10==10]</td>\n"+
			"<pager:elseif expression=\"11==11\">\n" +
			"<td>this is a td in if [11==11]</td>\n"+
			"</pager:elseif>." +
			"</pager:if>\n" +
			"</body>";
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				ActionConst.SPRING_STARTUP_CONFIG);

		IExecContext execContext = (IExecContext) applicationContext.getBean(ActionConst.EXEC_CONTEXT_BEAN_REF);
		execContext.put(ActionConst.PAGE_NAMESPACE_BEAN_REF, "pager");
		execContext.put(ActionConst.WEB_REAL_PATH_BEAN_REF, "/pages");
		Action action = new Action("/pages", "if2.xhtml", "pager");
		List<ReplacementMarker> markers = action.findMarkers(page, PagerTest.NAMESPACE, execContext, null);
		String newPage = action.replaceMarkers(execContext, page, markers);
		log.debug("page:" + page);
		log.debug("newPage:" + newPage);
		assertTrue(page.contains("this is a td in if [10==10]"));
	}
	
	public void testXMLParser() throws IOException, NestedActionException, ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, BadXMLException{
		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				ActionConst.SPRING_STARTUP_CONFIG);

		IExecContext execContext = (IExecContext) applicationContext.getBean(ActionConst.EXEC_CONTEXT_BEAN_REF);
		Action action = new Action(null,"/pages/page_content.xml","pager");
		action.setNameSpaces(new char [0][]);
		action.addNameSpace("pager");
		String output = action.processPage(execContext);
		assertTrue(output.indexOf("1.page2 content")>= 0);
		assertTrue(output.indexOf("6.page2 content")>= 0);
		log.debug(output);
		
	}

	String test3 = 
		"<xml xmlns=\"http://www.w3.org/1999/xhtml\"\n" + 
		"		xmlns:pager=\"http://www.riostl.com/schema/pager\"\n" + 
		"		xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
		"		xsi:schemaLocation=\"http://www.riostl.com/schema/pager http://www.riostl.com/schema/pager.xsd\">\n" +
		"		<pager:if expression=\"'walnutdoors.ie'.toLowerCase().indexOf('walnutdoors.ie') &gt;= 0\">\n" +
		"			expression:walnutdoors\n" +
		"			<pager:elseif expression=\"1==2\">\n" +
		"				expression:1==2\n" +
		"			</pager:elseif>\n" +
		"			<pager:elseif expression=\"'oakdoors.ie'.toLowerCase().indexOf('oakdoors.ie') &gt;= 0\">\n" +
		"				expression:oakdoors\n" +
		"			</pager:elseif>\n" +
		"			<pager:else>\n" +
		"				default\n" +
		"			</pager:else>\n" +
		"		</pager:if>\n" +
		"	</xml>\n";

	public void testPager3()
	throws IOException, NestedActionException, DocumentException, ClassNotFoundException,
	InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException,
	BadXMLException
	{

		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				ActionConst.SPRING_STARTUP_CONFIG);

		IExecContext execContext = (IExecContext) applicationContext.getBean(ActionConst.EXEC_CONTEXT_BEAN_REF);
		execContext.put(ActionConst.PAGE_NAMESPACE_BEAN_REF, "pager");
		execContext.put(ActionConst.WEB_REAL_PATH_BEAN_REF, "/pages");
		Action action = new Action("/pages", "if2.xhtml", "pager");
		List<ReplacementMarker> markers = action.findMarkers(test3, PagerTest.NAMESPACE, execContext, null);
		String newPage = action.replaceMarkers(execContext, test3, markers);
		log.debug("page:" + test3);
		log.debug("newPage:" + newPage);
		assertTrue(newPage.contains("expression:walnutdoors"));
	}

}
