
package org.xmlactions.pager.actions;


import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import junit.framework.TestCase;

import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.dom4j.DocumentException;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.xmlactions.action.Action;
import org.xmlactions.action.ActionConst;
import org.xmlactions.action.NestedActionException;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.common.xml.BadXMLException;

public class MixedHtmlTest extends TestCase
{

	private final static Logger log = LoggerFactory.getLogger(MixedHtmlTest.class);

	public void testNestedPageLoad()
			throws IOException, NestedActionException, DocumentException, ClassNotFoundException,
			InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException,
			BadXMLException
	{

		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				ActionConst.SPRING_STARTUP_CONFIG);

		IExecContext execContext = (IExecContext) applicationContext.getBean(ActionConst.EXEC_CONTEXT_BEAN_REF);

		execContext.put(ActionConst.PAGE_NAMESPACE_BEAN_REF, "pager");
		execContext.put(ActionConst.WEB_REAL_PATH_BEAN_REF, "/pages");
		Action action = new Action("/pages", "mixed_html.xhtml", "pager");
		String page = action.processPage(execContext);
		log.debug("page:" + page);
		assertTrue("page should contain [this is a paragraph]", page.indexOf("this is a paragraph") > -1);
		assertTrue("page should contain [<b>t</b>his is a paragraph]", page.indexOf("<b>t</b>his is a paragraph") > -1);
	}
}
