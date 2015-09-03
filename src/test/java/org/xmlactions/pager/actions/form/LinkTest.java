
package org.xmlactions.pager.actions.form;


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import junit.framework.TestCase;

import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.xmlactions.action.config.NoPersistenceExecContext;
import org.xmlactions.common.io.ResourceUtils;
import org.xmlactions.common.text.EscapeUtils;
import org.xmlactions.common.theme.Theme;
import org.xmlactions.pager.actions.form.Link;
import org.xmlactions.pager.actions.form.templates.Html;
import org.xmlactions.pager.actions.form.templates.HtmlA;

public class LinkTest extends TestCase {

    private static final Logger logger = LoggerFactory.getLogger(LinkTest.class);

    private static Theme getTheme() throws IOException {
        Properties props = new Properties();
        InputStream is = ResourceUtils.getInputStream("/themes/riostl.properties");
        props.load(is);
        Theme theme = new Theme(props);

        return theme;
    }

    public void testDraw() throws IOException {

        Theme theme = getTheme().getTheme("riostl");
        Link link = Link.newLink("name", "button", "tooltip");
        Html htmlA = link.draw(new NoPersistenceExecContext(null, null), theme);

        logger.debug("htmlA:" + htmlA);
    }

    public void testEscapeHtml() {
        String result = EscapeUtils.escapeUrl("percent");
        assertEquals("percent", result);
        result = EscapeUtils.escapeUrl("%ail%");
        assertEquals("%25ail%25", result);
    }

}
