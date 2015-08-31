package org.xmlactions.pager.actions;

import junit.framework.TestCase;

import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.xmlactions.action.ActionConst;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.pager.actions.TransformAction;


public class TransformActionTest extends TestCase {

	private static final Logger log = LoggerFactory.getLogger(TransformActionTest.class);
	
	private static IExecContext execContext;

	public void setUp()
	{

		if (execContext == null) {
			ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(
					ActionConst.SPRING_STARTUP_CONFIG);
			execContext = (IExecContext) applicationContext.getBean(ActionConst.EXEC_CONTEXT_BEAN_REF);
			execContext.setApplicationContext(applicationContext);
		}
	}


	public void testTransform() throws Exception {
		TransformAction ta = new TransformAction();
		ta.setXslt_file_name("/schema/xsd_to_html.xslt");
		ta.setXml_file_name("/schema/pager_actions.xsd;/schema/pager_attributes.xsd;/schema/pager_types.xsd");
		try {
			String output = ta.execute(execContext);
			log.debug("output:" + output);
		} catch (Exception ex) {
			// expected faul
		}
		
	}
}
