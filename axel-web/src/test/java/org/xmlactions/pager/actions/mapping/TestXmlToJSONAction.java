package org.xmlactions.pager.actions.mapping;


import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;

import junit.framework.TestCase;

import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.xmlactions.action.Action;
import org.xmlactions.action.ActionConst;
import org.xmlactions.action.NestedActionException;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.common.xml.BadXMLException;
import org.xmlactions.pager.actions.mapping.BeanToXmlAction;

public class TestXmlToJSONAction extends TestCase {

    private static Logger logger = LoggerFactory.getLogger(TestXmlToJSONAction.class);

    private static IExecContext execContext;

    private static final String[] configFiles = { ActionConst.SPRING_STARTUP_CONFIG,
            "/config/spring/test-spring-pager-web-startup.xml" };

    public void setUp() {

        if (execContext == null) {
            ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(configFiles);

            execContext = (IExecContext) applicationContext.getBean(ActionConst.EXEC_CONTEXT_BEAN_REF);
        }
    }

    public void testCreate() throws Exception {
    	String xml = "<xml><child name=\"fred\"/></xml>";
    	XmlToJSONAction xmlToJSONAction = new XmlToJSONAction();
    	xmlToJSONAction.setXml_ref("xml_ref_key");
    	xmlToJSONAction.setKey("xml_key");
    	execContext.put("xml_ref_key", xml);
  		xmlToJSONAction.execute(execContext);
    	String output = execContext.getString("xml_key");
    	assertTrue(output.startsWith("{\"xml\":{\"child"));
    	logger.debug(output);

    }

    public void testAmp() throws Exception {
    	String xml = "<xml><child name=\"fred\"/>Stuff here & stuff here</xml>";
    	XmlToJSONAction xmlToJSONAction = new XmlToJSONAction();
    	xmlToJSONAction.setXml_ref("xml_ref_key");
    	xmlToJSONAction.setKey("xml_key");
    	execContext.put("xml_ref_key", xml);
  		xmlToJSONAction.execute(execContext);
    	String output = execContext.getString("xml_key");
    	assertTrue(output.startsWith("{\"xml\":{\""));
    	logger.debug(output);

    }

    public void testXPath() throws Exception {
    	String xml = "<xml><child name=\"&lt;xml&gt;&lt;child name=&quot;fred&quot;/&gt;&lt;/xml&gt;\"/></xml>";
    	XmlToJSONAction xmlToJSONAction = new XmlToJSONAction();
    	xmlToJSONAction.setXml_ref("xml_ref_key");
    	xmlToJSONAction.setKey("xml_key");
    	xmlToJSONAction.setXml_path("xml/child[@name]");
    	execContext.put("xml_ref_key", xml);
  		xmlToJSONAction.execute(execContext);
    	String output = execContext.getString("xml_key");
    	assertTrue(output.startsWith("{\"xml\":{\""));
    	logger.debug(output);

    }

}
