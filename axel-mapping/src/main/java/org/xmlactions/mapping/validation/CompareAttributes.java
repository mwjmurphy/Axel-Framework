package org.xmlactions.mapping.validation;

import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Element;

public abstract class CompareAttributes implements IXmlCompare {

    protected boolean compareAttributes(Element element1, Element element2) {
        boolean result = true;
        if (isIgnoreExtraAttributes() == false && element1.attributeCount() != element2.attributeCount()) {
            addError("Attribute count does not match between [" + element1.getName() + "] and [" + element2.getName()
                    + "]");
            result = false;
        } else {
            List<Attribute> atts1 = element1.attributes();
            List<Attribute> atts2 = element2.attributes();
            for (Attribute att1 : atts1) {
                Attribute att2 = findMatchingAttribute(att1, atts2);
                if (att2 == null) {
                    addError("No matching attribute found [" + element1.getName() + "@" + att1.getName() + "["
                            + att1.getValue() + "]] in element ["
                            + element2.getName() + "]");
                    result = false;
                    break;
                }
            }
        }
        return result;
    }

    protected Attribute findMatchingAttribute(Attribute att1, List<Attribute> atts) {
        Attribute matchingAttribute = null;
        for (Attribute att : atts) {
            if (att1.getName().equals(att.getName())) {
                // found matching attribute, now compare content
                if (isCompareAttributeContent()) {
                    if (att1.getText().equals(att.getText())) {
                        matchingAttribute = att;
                    }
                } else {
                    matchingAttribute = att;
                }
                break;
            }
        }
        return matchingAttribute;
    }

}
