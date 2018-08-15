package org.xmlactions.mapping.validation;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class CompareXml extends CompareElements {

    private boolean compareAttributeContent = true;
    private boolean compareElementContent = true;
    private boolean ignoreWhitespace = false;
    private boolean ignoreExtraAttributes = true;

    List<String> errors = new ArrayList<String>();

    /**
     * Compare two elements.
     * 
     * @param element1
     * @param element2
     * @return true if the elements match else false. If the result is false
     *         retrieve a list of failures by looking at the List of errors from
     *         a call to #getErrors()
     */
    public boolean compare(Element element1, Element element2) {

        boolean result = compareElements(element1.getName(), element1, element2);
        if (result == true) {
            result = compareChildren(element1.getName(), element1, element2);
        }

        return result;
    }

    /**
     * Compare two elements.
     * 
     * @param xml1
     * @param xml2
     * @return true if the xml1 and xml2 match else false. If the result is
     *         false retrieve a list of failures by looking at the List of
     *         errors from a call to #getErrors()
     */
    public boolean compare(String xml1, String xml2) {
        Element element1;
        Element element2;

        try {
            element1 = DocumentHelper.parseText(xml1).getRootElement();
            element2 = DocumentHelper.parseText(xml2).getRootElement();
        } catch (Exception ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }

        boolean result = compareElements(element1.getName(), element1, element2);
        if (result == true) {
            result = compareChildren(element1.getName(), element1, element2);
        }

        return result;
    }

    public void addError(String error) {
        errors.add(error);
    }

    public boolean isCompareElementContent() {
        return compareElementContent;
    }

    public void setCompareElementContent(boolean compareElementContent) {
        this.compareElementContent = compareElementContent;
    }

    public boolean isCompareAttributeContent() {
        return compareAttributeContent;
    }

    public void setCompareAttributeContent(boolean compareAttributeContent) {
        this.compareAttributeContent = compareAttributeContent;
    }

    public boolean isIgnoreWhitespace() {
        return ignoreWhitespace;
    }

    public void setIgnoreWhitespace(boolean ignoreWhitespace) {
        this.ignoreWhitespace = ignoreWhitespace;
    }

    public void setIgnoreExtraAttributes(boolean ignoreExtraAttributes) {
        this.ignoreExtraAttributes = ignoreExtraAttributes;
    }

    public boolean isIgnoreExtraAttributes() {
        return ignoreExtraAttributes;
    }

    public List<String> getErrors() {
        return errors;
    }

    public String getErrorsAsString() {
        StringBuilder sb = new StringBuilder();
        for (String err : getErrors()) {
            sb.append(err + "\n");
        }
        return sb.toString();
    }

}
