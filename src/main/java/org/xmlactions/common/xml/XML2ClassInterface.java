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

/*
 * XML2ClassInterface.java
 *
 * Created on August 5, 2005, 3:22 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package org.xmlactions.common.xml;

/**
 *
 * @author MMURPHY
 */
public interface XML2ClassInterface
{
   /**
    * @param nodeName is the name of the node ie. <element> then nodeName = 'element'.
    * @param parser contains the node itelf
    * @param depth if set true means that the parser has moved in a node. i.e. <root><root1> -> when parser reaches root1 then depth = true;
    */
   public void parseNode(String nodeName, XMLParser parser, boolean depth) throws Exception;
}
