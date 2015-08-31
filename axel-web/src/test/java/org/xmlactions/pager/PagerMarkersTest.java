
package org.xmlactions.pager;


import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import junit.framework.TestCase;

import org.dom4j.DocumentException;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.xmlactions.action.Action;
import org.xmlactions.action.ActionConst;
import org.xmlactions.action.ActionMarkers;
import org.xmlactions.action.ReplacementMarker;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.common.xml.BadXMLException;

public class PagerMarkersTest extends TestCase
{

	private static IExecContext execContext;

	public void setUp()
	{

		if (execContext == null) {
			ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(
					ActionConst.SPRING_STARTUP_CONFIG);
			execContext = (IExecContext) applicationContext.getBean(ActionConst.EXEC_CONTEXT_BEAN_REF);
		}
	}

	public void testFindEchos()
			throws IOException, DocumentException, ClassNotFoundException, InstantiationException,
			IllegalAccessException, InvocationTargetException, NoSuchMethodException, BadXMLException
	{

		Action action = new Action();
		String page = action.loadPage("src/test/resources", "/pages/echo.xhtml");
		ActionMarkers markers = new ActionMarkers();
		List<ReplacementMarker> list = markers.getReplacementList(page, PagerTest.NAMESPACE, execContext, null);
		assertEquals(2, list.size());
	}

	public void testFindComplex()
			throws IOException, DocumentException, ClassNotFoundException, InstantiationException,
			IllegalAccessException, InvocationTargetException, NoSuchMethodException, BadXMLException
	{

		Action action = new Action();
		String page = action.loadPage("src/test/resources", "/pages/complex.xhtml");
		ActionMarkers markers = new ActionMarkers();
		List<ReplacementMarker> list = markers.getReplacementList(page, PagerTest.NAMESPACE, execContext, null);
		assertEquals(3, list.size());
		ReplacementMarker rm = list.get(0);
		assertEquals(2, rm.getXMLObject().getChildCount());
	}

	public void testFindIf()
			throws IOException, DocumentException, ClassNotFoundException, InstantiationException,
			IllegalAccessException, InvocationTargetException, NoSuchMethodException, BadXMLException
	{

		Action action = new Action();
		String page = action.loadPage("src/test/resources", "/pages/if.xhtml");
		ActionMarkers markers = new ActionMarkers();
		List<ReplacementMarker> list = markers.getReplacementList(page, PagerTest.NAMESPACE, execContext, null);
		assertEquals(8, list.size());
		ReplacementMarker rm = list.get(0);
		assertEquals(2, rm.getXMLObject().getChildCount());
	}

}
