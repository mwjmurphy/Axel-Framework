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
