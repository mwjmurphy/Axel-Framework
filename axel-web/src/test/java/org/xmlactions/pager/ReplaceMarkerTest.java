
package org.xmlactions.pager;


import java.lang.reflect.InvocationTargetException;
import java.util.List;

import junit.framework.TestCase;

import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.dom4j.DocumentException;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.xmlactions.action.ActionConst;
import org.xmlactions.action.ActionMarkers;
import org.xmlactions.action.ReplacementMarker;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.common.xml.BadXMLException;

public class ReplaceMarkerTest extends TestCase
{

	private final static Logger log = LoggerFactory.getLogger(ReplaceMarkerTest.class);

	private static IExecContext execContext;

	public void setUp()
	{

		if (execContext == null) {
			ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(
					ActionConst.SPRING_STARTUP_CONFIG);
			execContext = (IExecContext) applicationContext.getBean(ActionConst.EXEC_CONTEXT_BEAN_REF);
		}
	}

	/**
	 * TODO needs some code changes to get this test working. public void
	 * testCreateNoNS() throws DocumentException { PagerMarkers pagerMarkers =
	 * new PagerMarkers(); List<ReplacementMarker> markers =
	 * pagerMarkers.getReplacementList(" <echo>this is an simple echo</echo>");
	 * assertEquals(1, markers.size()); assertEquals("this is an simple echo",
	 * markers.get(0).getContent()); markers = pagerMarkers.getReplacementList(
	 * "<root><pager:echo>this is an simple echo</pager:echo></root>"); }
	 * 
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws ClassNotFoundException
	 * @throws BadXMLException
	 */
	public void testCreateWithNS()
			throws DocumentException, ClassNotFoundException, InstantiationException, IllegalAccessException,
			InvocationTargetException, NoSuchMethodException, BadXMLException
	{

		String page = "<pager:echo>this <b>is</b> an simple echo</pager:echo>";
		ActionMarkers actionMarkers = new ActionMarkers();
		List<ReplacementMarker> markers = actionMarkers.getReplacementList(page, PagerTest.NAMESPACE, execContext, null);
		assertEquals(1, markers.size());
		assertEquals("this <b>is</b> an simple echo", markers.get(0).getContent());

		page = "<root><pager:echo>this is <pager:echo>an <b>simple</b></pager:echo> echo</pager:echo></root>";
		markers = actionMarkers.getReplacementList(page, PagerTest.NAMESPACE, execContext, null);
		assertEquals(1, markers.size());
		assertEquals(1, markers.get(0).getNestedMarkers().size());
		assertEquals("an <b>simple</b>", markers.get(0).getNestedMarkers().get(0).getContent());
		assertEquals("this is <pager:echo>an <b>simple</b></pager:echo> echo", markers.get(0).getContent());
	}
}
