package org.xmlactions.mapping.validation;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Attribute;
import org.dom4j.Element;

public abstract class CompareElements extends CompareAttributes {

    String path;

    protected boolean compareElements(String path, Element element1, Element element2) {
        boolean result = true;
        if (element1.getName().equals(element2.getName())) {
            if (isCompareElementContent()) {
                if (compareElementText(element1, element2) == false) {
                    addError("Element content [" + path + "/\"" + element1.getText()
                            + "\"] does not match [" + element2.getName() + "/\"" + element2.getStringValue() + "\"]");
                    result = false;
                }
            }
            if (result == true) {
                result = compareAttributes(element1, element2);
            }
        } else {
            addError("Element Name [" + element1.getName() + "] does not match ["
                    + element2.getName() + "]");
            result = false;
        }

        return result;
    }

    protected boolean compareChildren(String path, Element element1, Element element2) {
        boolean result = true;

        if (element1.elements().size() == element2.elements().size()) {
            List<Element> children = element1.elements();
            for (Element element : children) {
                Element matchingElement = findMatchingElement(element, (List<Element>) element2.elements());
                if (matchingElement == null) {
                    addError("No matching element found for [" + element1.getName() + "]");
                    result = false;
                    break;
                } else {
                    result = compareElements(path + "/" + element.getName(), element, matchingElement);
                    if (result == false) {
                        break;
                    }
                }
            }
        } else {
            addError("Element count does not match between  : [" + element1.getName() + "]["
                    + element1.elements().size() + "] and [" + element2.getName() + "][" + element2.elements().size()
                    + "] ");
        }
        return result;
    }
    
    private Element findMatchingElement(Element element, List<Element> list) {
        Element matchingElement = null;
        for (Element match : list) {
            if (element.getName().equals(match.getName())) {
                List<Attribute> atts1 = element.attributes();
                List<Attribute> atts2 = match.attributes();
                boolean foundMatch = true;
                for (Attribute att1 : atts1) {
                    Attribute att2 = findMatchingAttribute(att1, atts2);
                    if (att2 == null) {
                        foundMatch = false;
                        break;
                    }
                }
                if (foundMatch == true) {
                    matchingElement = match;
                    break;
                }
            }
        }
        return matchingElement;
    }

    private boolean compareElementText(Element element1, Element element2) {
        String t1 = element1.getText();
        String t2 = element2.getText();
        // String t1 = element1.getStringValue();
        // String t2 = element2.getStringValue();
        if (isIgnoreWhitespace()) {
            t1 = StringUtils.deleteWhitespace(t1);
            t2 = StringUtils.deleteWhitespace(t2);
        }
        return t1.equals(t2);
    }

}
