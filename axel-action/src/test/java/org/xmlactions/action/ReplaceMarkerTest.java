package org.xmlactions.action;


import java.lang.reflect.InvocationTargetException;
import java.util.List;

import junit.framework.TestCase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.dom4j.DocumentException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.xmlactions.action.ActionConst;
import org.xmlactions.action.ActionMarkers;
import org.xmlactions.action.ReplacementMarker;
import org.xmlactions.action.config.ExecContext;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.common.xml.BadXMLException;

public class ReplaceMarkerTest extends TestCase {

	private final static Logger log = LoggerFactory.getLogger(ReplaceMarkerTest.class);

	private static IExecContext execContext;

	private static final String[] configFiles = { "/config/spring/spring-config.xml" };
	private static ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
			configFiles);

	public void setUp() {
		if (execContext == null) {
			execContext = (ExecContext) applicationContext
					.getBean(ActionConst.EXEC_CONTEXT_BEAN_REF);
			execContext.setApplicationContext(applicationContext);
			execContext.put("echo", org.xmlactions.action.actions.Echo.class);
		}
	}

	/**
	 * @throws DocumentException
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws BadXMLException
	 */
	public void testCreateWithNS() throws DocumentException,
			ClassNotFoundException, InstantiationException,
			IllegalAccessException, InvocationTargetException,
			NoSuchMethodException, BadXMLException {
		String page = "<pager:echo>this <b>is</b> an simple echo</pager:echo>";
		ActionMarkers actionMarkers = new ActionMarkers();
		List<ReplacementMarker> markers = actionMarkers.getReplacementList(
				page, ActionTest.NAMESPACE, execContext, null);
		assertEquals(1, markers.size());
		assertEquals("this <b>is</b> an simple echo", markers.get(0)
				.getContent());

		page = "<root><pager:echo>this is <pager:echo>an <b>simple</b></pager:echo> echo</pager:echo></root>";
		markers = actionMarkers.getReplacementList(page, ActionTest.NAMESPACE,
				execContext, null);
		assertEquals(1, markers.size());
		assertEquals(1, markers.get(0).getNestedMarkers().size());
		assertEquals("an <b>simple</b>", markers.get(0).getNestedMarkers().get(
				0).getContent());
		assertEquals("this is <pager:echo>an <b>simple</b></pager:echo> echo",
				markers.get(0).getContent());
	}
	
}
