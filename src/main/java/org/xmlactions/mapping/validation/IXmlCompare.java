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
