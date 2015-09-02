
package org.xmlactions.pager.actions;


import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.apache.commons.lang.text.StrSubstitutor;
import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.xmlactions.action.Action;
import org.xmlactions.action.ActionConst;
import org.xmlactions.action.NestedActionException;
import org.xmlactions.action.ReplacementMarker;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.common.xml.BadXMLException;

public class IfActionStrSubstutitorTest extends TestCase
{

	private static Logger log = LoggerFactory.getLogger(IfActionStrSubstutitorTest.class);

	private static IExecContext execContext;

	public void setUp()
	{

		if (execContext == null) {
			ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(
					ActionConst.SPRING_STARTUP_CONFIG);
			execContext = (IExecContext) applicationContext.getBean(ActionConst.EXEC_CONTEXT_BEAN_REF);
		}
	}

	public void testIfStrSubstutitor()
			throws NestedActionException, ClassNotFoundException, InstantiationException, IllegalAccessException,
			InvocationTargetException, NoSuchMethodException, BadXMLException
	{

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("k1", "value of k1");
		map.put("true", true);
		execContext.addNamedMap("named.map", map);
		// log.debug("named.map:t1:" + execContext.get("named.map:true"));
		String page = "<body>\n" + "<pager:if expression=\"true==${named.map:true}\">\n"
				+ " We got true [${named.map:true}]\n" + " </pager:if>\n\n" + "</body>";
		Action action = new Action(null, null, "pager");
		List<ReplacementMarker> markers = action.findMarkers(page, ActionConst.DEFAULT_PAGER_NAMESPACE, execContext, null);

		String newPage = action.replaceMarkers(execContext, page, markers);
		// log.debug("new page:\n" + newPage);
		assertTrue(newPage.indexOf("We got true") >= 0);
	}

	public void testNullStrSubstutitor()
	{

		Map<String, String> map = new HashMap<String, String>();
		String content = StrSubstitutor.replace(null, map);
		assertEquals(null, content);
	}
}
