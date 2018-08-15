package org.xmlactions.pager.drawing.html;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit38.AbstractJUnit38SpringContextTests;
import org.xmlactions.action.ActionConst;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.common.theme.Theme;
import org.xmlactions.db.actions.TextArea;

@ContextConfiguration(locations = { ActionConst.SPRING_STARTUP_CONFIG,
        "/config/spring/test-spring-pager-web-startup.xml" })
public class TestTextAreaHtml extends AbstractJUnit38SpringContextTests {

    private static final Logger logger = LoggerFactory.getLogger(TestTextAreaHtml.class);
    public void testOne() {

        IExecContext execContext = (IExecContext) applicationContext.getBean(ActionConst.EXEC_CONTEXT_BEAN_REF);
        execContext.setApplicationContext(applicationContext);

        Theme theme = execContext.getThemes().getTheme("riostl");
        TextAreaHtml textArea = new TextAreaHtml();
        textArea.setContent("the text area content");
        textArea.setPresentation_height(5);
        textArea.setPresentation_width(50);
        textArea.setPresentation_name("Da Text Area");
        textArea.setName("Da Name");
        TextArea ta = new TextArea();
        ta.setName("Da Parent Name");
        textArea.setParent(ta);
        //Html html = textArea.displayForList(execContext, (Field) null, "ccc", theme);
        //logger.debug(html.toString());

    }
}
