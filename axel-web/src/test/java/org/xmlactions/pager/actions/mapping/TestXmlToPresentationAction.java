package org.xmlactions.pager.actions.mapping;


import junit.framework.TestCase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.xmlactions.action.ActionConst;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.pager.actions.form.PresentationFormAction;

public class TestXmlToPresentationAction extends TestCase {

    private static Logger logger = LoggerFactory.getLogger(TestXmlToPresentationAction.class);

    private static IExecContext execContext;

    private static final String[] configFiles = { ActionConst.SPRING_STARTUP_CONFIG,
            "/config/spring/test-spring-pager-web-startup.xml" };

    public void setUp() {

        if (execContext == null) {
            ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(configFiles);

            execContext = (IExecContext) applicationContext.getBean(ActionConst.EXEC_CONTEXT_BEAN_REF);
        }
    }

    public void testXmlFile() throws Exception {
    	XmlToPresentationAction xmlToPresentationAction = new XmlToPresentationAction();
    	xmlToPresentationAction.setXml_path("root/row");
    	xmlToPresentationAction.setXml_filename("/org/xmlactions/pager/actions/mapping/data.xml");
    	xmlToPresentationAction.setPresentation_form("/org/xmlactions/pager/actions/mapping/xml_presentation.html");
    	String output = xmlToPresentationAction.execute(execContext);
    	logger.debug(output);
    }

    public void testXmlData() throws Exception {
    	XmlToPresentationAction xmlToPresentationAction = new XmlToPresentationAction();
    	xmlToPresentationAction.setXml_path("vessel_gear_types_category");
    	xmlToPresentationAction.setXml_filename("/org/xmlactions/pager/actions/mapping/data.xml");
    	xmlToPresentationAction.setPresentation_form("/org/xmlactions/pager/actions/mapping/xml_presentation.html");
    	String output = xmlToPresentationAction.execute(execContext);
    	logger.debug(output);
    }

    public void testXmlDataFromForm() throws Exception {
    	PresentationFormAction form = new PresentationFormAction();
    	form.setContent("This is the test xml presentation form\n${row_index}	index=${index}\nname=${name}");
    	XmlToPresentationAction xmlToPresentationAction = new XmlToPresentationAction();
    	xmlToPresentationAction.setXml_path("vessel_gear_types_category");
    	xmlToPresentationAction.setXml_filename("/org/xmlactions/pager/actions/mapping/data.xml");
    	xmlToPresentationAction.setForm(form);
    	String output = xmlToPresentationAction.execute(execContext);
    	logger.debug(output);
    }

}
