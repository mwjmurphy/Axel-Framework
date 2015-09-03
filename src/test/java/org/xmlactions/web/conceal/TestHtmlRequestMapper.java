package org.xmlactions.web.conceal;

import java.io.UnsupportedEncodingException;

import junit.framework.TestCase;

import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger; import org.slf4j.LoggerFactory;

public class TestHtmlRequestMapper extends TestCase {

    private static final Logger log = LoggerFactory.getLogger(TestHtmlRequestMapper.class);

    public void testQueryPath() throws UnsupportedEncodingException {
        String queryPath = "Hello%20World!!!&p1=2";
        String output = StringEscapeUtils.unescapeHtml(queryPath);
        log.debug("output:" + output);

        output = java.net.URLDecoder.decode(queryPath, "UTF-8");
        log.debug("output:" + output);

    }
}
