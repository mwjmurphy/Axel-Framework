/*
 * Copyright (C) Mike Murphy 2003-2015 <mike.murphy@xmlactions.org><mwjmurphy@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package org.xmlactions.common.xml;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.util.Map;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.lang.StringUtils;


/**
 * @copyright rio software and technologies 2007/2010
 * @author Mike Murphy
 *
 */
public class Transform {

    /**
     * Transform an xml using an xml style sheet transformation (xslt);
     * 
     * @param inputXSL
     *            the stylesheet
     * @param inputXML
     *            the xml
     * @return the transformed xml
     * @throws TransformerException
     */
    public static String transform(InputStream inputXSL, InputStream inputXML) throws TransformerException {

        TransformerFactory tFactory = TransformerFactory.newInstance();

        Transformer transformer = tFactory.newTransformer(new StreamSource(inputXSL));

        OutputStream outputStream = new ByteArrayOutputStream();

        OutputStreamWriter osw = new OutputStreamWriter(outputStream);

        StreamSource ss = new StreamSource(new BufferedReader(new InputStreamReader(inputXML)));
        transformer.transform(new StreamSource(inputXML), new StreamResult(osw));

        return outputStream.toString();
    }

    /**
     * Transform an xml using an xml style sheet transformation (xslt);
     * 
     * @param readerXSL
     *            the stylesheet
     * @param readerXML
     *            the xml
     * @return the transformed xml
     * @throws TransformerException
     */
    public static String transform(Reader readerXSL, Reader readerXML) throws TransformerException {

        TransformerFactory tFactory = TransformerFactory.newInstance();

        Transformer transformer = tFactory.newTransformer(new StreamSource(readerXSL));

        OutputStream outputStream = new ByteArrayOutputStream();

        OutputStreamWriter osw = new OutputStreamWriter(outputStream);

        transformer.transform(new StreamSource(readerXML), new StreamResult(osw));

        return outputStream.toString();
    }

    /**
     * Transform an xml using an xml style sheet transformation (xslt);
     * 
     * @param readerXSL
     *            the stylesheet
     * @param readerXML
     *            the xml
     * @param transformerFactory
     *            - xalan = org.apache.xalan.processor.TransformerFactoryImpl,
     *            can be empty for no transformer
     * @param map
     *            - map of parameters to pass to the transformer, can be null
     *            for no params
     * @return the transformed xml
     * @throws TransformerException
     */
    public static String transform(Reader readerXSL, Reader readerXML, String transformerFactory,
            Map<String, Object> map)
            throws TransformerException {

        // should we set a transformerFactory?
        if (StringUtils.isNotEmpty(transformerFactory)) {
            Transform.setTransformationFactory(transformerFactory);
        } else {
            Transform.setDefaultTransformationFactory();
        }

        TransformerFactory tFactory = TransformerFactory.newInstance();

        // reset to default factory.
        Transform.setDefaultTransformationFactory();

        Transformer transformer = tFactory.newTransformer(new StreamSource(readerXSL));

        // Add any parameters if needed.
        if (map != null) {
            for (String key : map.keySet()) {
                Object obj = map.get(key);
                transformer.setParameter(key, obj);
            }
        }

        OutputStream outputStream = new ByteArrayOutputStream();

        OutputStreamWriter osw = new OutputStreamWriter(outputStream);

        transformer.transform(new StreamSource(readerXML), new StreamResult(osw));

        return outputStream.toString();
    }

	/**
	 * Transform an xml using an xml style sheet transformation (xslt);
	 * @param inputXSL the stylesheet
	 * @param inputXML the xml
	 * @param outputStream the result of the transform
	 * @throws TransformerException
	 */
    public static void transform(InputStream inputXSL, InputStream inputXML, OutputStream outputStream) throws TransformerException {

    	TransformerFactory tFactory = TransformerFactory.newInstance();

        Transformer transformer = tFactory.newTransformer(new StreamSource(inputXSL));

        transformer.transform(new StreamSource(inputXML), new StreamResult(outputStream));
    }

    private final static String xalanTransformerFactory = "org.apache.xalan.processor.TransformerFactoryImpl";
    private final static String defaultTransformerFactory = "com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl";

    private final static String transformFactoryKey = "javax.xml.transform.TransformerFactory";

    public static void setXalanTransformationFactory() {
        setTransformationFactory(xalanTransformerFactory);
    }

    public static void setDefaultTransformationFactory() {
        System.clearProperty(transformFactoryKey);
    }

    public static void setTransformationFactory(String factoryName) {
        System.setProperty(transformFactoryKey, factoryName);
    }
}
