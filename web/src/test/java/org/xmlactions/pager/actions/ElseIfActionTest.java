
package org.xmlactions.pager.actions;


import java.lang.reflect.InvocationTargetException;
import java.util.List;

import junit.framework.TestCase;

import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.xmlactions.action.Action;
import org.xmlactions.action.ActionConst;
import org.xmlactions.action.NestedActionException;
import org.xmlactions.action.ReplacementMarker;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.common.xml.BadXMLException;
import org.xmlactions.pager.PagerTest;


public class ElseIfActionTest extends TestCase
{

	private static Logger log = LoggerFactory.getLogger(ElseIfActionTest.class);

	private static IExecContext execContext;

	public void setUp()
	{

		if (execContext == null) {
			ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(
					ActionConst.SPRING_STARTUP_CONFIG);
			execContext = (IExecContext) applicationContext.getBean(ActionConst.EXEC_CONTEXT_BEAN_REF);
		}

	}

	public void testElseIf1()
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException,
			NoSuchMethodException, BadXMLException, NestedActionException
	{

		String page = "<body>\n" + " <pager:if expression=\"1==2\">\n" + "  we're from inside if 1==2"
				+ " <pager:elseif expression=\"2==2\">" + "  we're from inside elseif 2==2\n"
				+ "  <pager:echo>content from echo</pager:echo>" + " </pager:elseif>" + " </pager:if>\n" + "</body>";
		Action action = new Action(null, null, "pager");
		List<ReplacementMarker> markers = action.findMarkers(page, PagerTest.NAMESPACE, execContext, null);

		String newPage = action.replaceMarkers(execContext, page, markers);
		log.debug("new page:\n" + newPage);
		assertEquals("<body>\n   we're from inside elseif 2==2\n  content from echo \n</body>", newPage);
	}

}
