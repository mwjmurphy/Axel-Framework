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
import org.xmlactions.pager.actions.mapping.XmlToBeanAction;

public class TestXmlToBeanAction extends TestCase {

    private static Logger log = LoggerFactory.getLogger(TestXmlToBeanAction.class);

    private static IExecContext execContext;

    private static final String[] configFiles = { ActionConst.SPRING_STARTUP_CONFIG,
            "/config/spring/test-spring-pager-web-startup.xml" };

    public void setUp() {

        if (execContext == null) {
            ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(configFiles);

            execContext = (IExecContext) applicationContext.getBean(ActionConst.EXEC_CONTEXT_BEAN_REF);
        }
    }

    public void testXmlToBeanActionXmlKey() throws Exception {

        URL url = this.getClass().getResource("/config/mapping/map_xml_to_bean.xml");
        String filename = url.getFile();

        String xml =
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
            "<mb name=\"da name\" number=\"101\"/>";

        XmlToBeanAction xtba = new XmlToBeanAction();
        xtba.setXml_key("the_xml");
        xtba.setMap_file_name(filename);
        xtba.setKey("bean_key");

        execContext.put("the_xml", xml);
        xtba.execute(execContext);

        MapBean mb = (MapBean) execContext.get("bean_key");
        assertTrue(mb.getName().equals("da name"));
        assertTrue(mb.getNumber() == 101);

    }

    public void testXmlToBeanXmlKey() throws Exception {
        
        String xml =
                "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" +
                "<mb name=\"da name\" number=\"101\"/>";
        
        URL url = this.getClass().getResource("/config/mapping/map_xml_to_bean.xml");
        String filename = url.getFile();
        
        url = this.getClass().getResource("/pages/xml_to_bean.xml");
        String xml_filename = url.getFile();
        
        String page = 
                "<pager:map_xml_to_bean" +
                " map_file_name=\"" + filename + "\"" + 
                " xml_key=\"the_xml\"" +
                " key=\"bean_key\"/>";
        
        execContext.put("the_xml", xml);
        
        Action action = new Action("", "", ActionConst.DEFAULT_PAGER_NAMESPACE);
        String newPage = action.processPage(execContext, page);
        
        MapBean mb = (MapBean) execContext.get("bean_key");
        assertTrue(mb.getName().equals("da name"));
        assertTrue(mb.getNumber() == 101);
        
    }

    public void testXmlToBeanPageXmlFile() throws IOException, NestedActionException, ClassNotFoundException,
            InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException,
            BadXMLException {


        URL url = this.getClass().getResource("/config/mapping/map_xml_to_bean.xml");
        String filename = url.getFile();
        
        url = this.getClass().getResource("/pages/xml_to_bean.xml");
        String xml_filename = url.getFile();

        String page = "<pager:map_xml_to_bean" + 
            " map_file_name=\"" + filename + "\"" +
            " xml_file_name=\"" + xml_filename + "\"" +
            " key=\"bean_key\"/>";

        Action action = new Action("", "", ActionConst.DEFAULT_PAGER_NAMESPACE);
        String newPage = action.processPage(execContext, page);

        MapBean mb = (MapBean) execContext.get("bean_key");
        assertTrue(mb.getName().equals("da name"));
        assertTrue(mb.getNumber() == 101);

    }

}
