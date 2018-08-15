package org.xmlactions.common.xml;


import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;

import javax.xml.transform.TransformerException;

import junit.framework.TestCase;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmlactions.common.io.ResourceUtils;
import org.xmlactions.common.xml.Transform;

public class TransformTest extends TestCase {

    private static final Logger logger = LoggerFactory.getLogger(TransformTest.class);

    public void testTramsformInputStream() throws TransformerException, IOException {
        String xmlFileName = "/transform/storage.xsd";
        String xsltFileName = "/transform/xsd_to_html.xslt";

        InputStream xmlInputStream = ResourceUtils.getInputStream(xmlFileName);
        InputStream xsltInputStream = ResourceUtils.getInputStream(xsltFileName);

        String output = Transform.transform(xsltInputStream, xmlInputStream);
        // logger.debug(output);
    }

    public void testTramsformReader() throws TransformerException, IOException {
        String xmlFileName = "/transform/storage.xsd";

        String xsltFileName = "/transform/xsd_to_html.xslt";

        Reader xmlReader = new StringReader(ResourceUtils.loadFile(xmlFileName));
        Reader xsltReader = new StringReader(ResourceUtils.loadFile(xsltFileName));


        String output = Transform.transform(xsltReader, xmlReader);
        // logger.debug(output);
    }

}
