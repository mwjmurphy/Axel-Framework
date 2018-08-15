
package org.xmlactions.pager.actions.form;


import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import junit.framework.TestCase;

import org.apache.bsf.BSFException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.xmlactions.action.Action;
import org.xmlactions.action.ActionConst;
import org.xmlactions.action.NestedActionException;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.common.xml.BadXMLException;

public class FieldTest extends TestCase
{

	private static Logger log = LoggerFactory.getLogger(FieldTest.class);

	private static IExecContext execContext;

	public void setUp()
	{

		if (execContext == null) {
			ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(
					ActionConst.SPRING_STARTUP_CONFIG);
			execContext = (IExecContext) applicationContext.getBean(ActionConst.EXEC_CONTEXT_BEAN_REF);
		}
	}

	public void tearDown()
	{

		execContext = null;
	}

	public void testField() throws BSFException
	{

		execContext.put("xpos", "10");
		Field field = new Field();
		field.setName("name");
		field.setX("100");
		field.setY("200");
		assertEquals("name", field.getName());
		assertEquals("100", field.getX(execContext));
		assertEquals("200", field.getY(execContext));
		field.setX("100*2");
		assertEquals("200", field.getX(execContext));
		field.setX("100+${xpos}");
		assertEquals("110", field.getX(execContext));
	}

	public void testParse()
	throws IOException, NestedActionException, ClassNotFoundException, InstantiationException,
	IllegalAccessException, InvocationTargetException, NoSuchMethodException, BadXMLException
	{

		String page = 
			"<body>\n" +
			"  <pager:field name=\"tb_1.id\" x=\"100\" y=\"100\"/>\n" +
			"</body>";
		Action action = new Action("/pages", "static page", "pager");
		String newPage = action.processPage(execContext, page);
		log.debug("newPage:" + newPage);
		assertEquals("<body>\n  \n</body>", newPage);
	}

	public void testDraw()
		throws IOException, NestedActionException, ClassNotFoundException, InstantiationException,
		IllegalAccessException, InvocationTargetException, NoSuchMethodException, BadXMLException
	{

		String page = 
			"<body>\n" +
			"  <pager:field snippet_ref=\"draw_like_this\" name=\"tb_1.id\" x=\"100\" y=\"100\"/>\n" +
			"</body>";
		Action action = new Action("/pages", "static page", "pager");
		String newPage = action.processPage(execContext, page);
		log.debug("newPage:" + newPage);
		assertEquals("<body>\n  \n</body>", newPage);
	}
}
