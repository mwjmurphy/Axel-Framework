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

public class TestBeanToXmlAction extends TestCase {

    private static Logger log = LoggerFactory.getLogger(TestBeanToXmlAction.class);

    private static IExecContext execContext;

    private static final String[] configFiles = { ActionConst.SPRING_STARTUP_CONFIG,
            "/config/spring/test-spring-pager-web-startup.xml" };

    public void setUp() {

        if (execContext == null) {
            ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(configFiles);

            execContext = (IExecContext) applicationContext.getBean(ActionConst.EXEC_CONTEXT_BEAN_REF);
        }
    }

    public void testBeanToXmlAction() throws Exception {

        URL url = this.getClass().getResource("/config/mapping/map_bean_to_xml.xml");
        String filename = url.getFile();

        BeanToXmlAction btxa = new BeanToXmlAction();
        btxa.setBean_key("the_bean");
        btxa.setMap_file_name(filename);
        btxa.setKey("bean_key");

        MapBean mb = new MapBean();
        mb.setName("da name");
        mb.setNumber(101);

        execContext.put("the_bean", mb);
        btxa.execute(execContext);

        String xml = execContext.getString("bean_key");
        assertTrue(xml.contains("da name"));
        assertTrue(xml.contains("101"));

    }

    public void testBeanToXmlPage() throws IOException, NestedActionException, ClassNotFoundException,
            InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException,
            BadXMLException {

        URL url = this.getClass().getResource("/config/mapping/map_bean_to_xml.xml");
        String filename = url.getFile();
        

        String page = "<pager:map_bean_to_xml" + 
            " map_file_name=\"" + filename + "\"" +
            " bean_key=\"the_bean\"" +
            " key=\"bean_key\"/>";

        MapBean mb = new MapBean();
        mb.setName("da name");
        mb.setNumber(101);

        execContext.put("the_bean", mb);

        Action action = new Action("", "", ActionConst.DEFAULT_PAGER_NAMESPACE);
        String newPage = action.processPage(execContext, page);

        String xml = execContext.getString("bean_key");
        assertTrue(xml.contains("da name"));
        assertTrue(xml.contains("101"));

    }

}
