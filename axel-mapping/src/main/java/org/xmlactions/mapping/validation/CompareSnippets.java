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
