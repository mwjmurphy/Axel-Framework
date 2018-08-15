package org.xmlactions.mapping.validation;

import java.util.List;

public interface IXmlCompare {

    public void addError(String err);

    public List<String> getErrors();

    public String getErrorsAsString();

    /** Compare the content of each matching attribute */
    public boolean isCompareAttributeContent();

    /** Compare the text content of each matching Element */
    public boolean isCompareElementContent();

    /** Ignore whitespace in element content... */
    public boolean isIgnoreWhitespace();

    /** Ignore any extra attributes in the 2nd XML content... */
    public boolean isIgnoreExtraAttributes();

}
