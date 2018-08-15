package org.xmlactions.mapping.validation;

import org.dom4j.Element;

public class CompareSnippets extends TraverseXml {

    public String compareSnippet(Element xmlElement1, Element xmlElement2, String fullPath) {
        Element element1 = getSnippet(xmlElement1, fullPath);
        Element element2 = getSnippet(xmlElement2, fullPath);

        CompareXml compareXml = new CompareXml();
        boolean result = compareXml.compare(element1, element2);
        return compareXml.getErrorsAsString();
    }
}
