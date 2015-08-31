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
 * Copyright (C) 2003, Mike Murphy <mike.murphy@xmlactions.org><mwjmurphy@gmail.com>
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

package org.xmlactions.action;


import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmlactions.action.actions.BaseAction;
import org.xmlactions.action.actions.SetupBeanFromXML;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.common.xml.XMLObject;
import org.xmlactions.common.xml.XMLReaderChar;


public class ReplacementMarker {
	private static Logger log = LoggerFactory.getLogger(ReplacementMarker.class);

	/** page replacement start and end positions. */
	private int start, end;

	private int elementNameLen; // this is the length from the element start '<'
	// to the element end '>'
	private XMLObject xo; // this is the XMLObject built from the content, we
	// need this for the element name and any
	// attributes.
	private List<ReplacementMarker> nestedMarkers = new ArrayList<ReplacementMarker>();
	private BaseAction action;

	ReplacementMarker(int start, int end, String pageContent, char[][] nameSpaces, IExecContext context, String actionMapName)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {
		// log.debug("\n==========\npageContent:" + pageContent + "\n=======");
		setXMLObject(new XMLObject().mapXMLCharToXMLObject(pageContent, nameSpaces));
		setAction(SetupBeanFromXML.createAction(getXMLObject(), XMLReaderChar.getInnerContent(pageContent), context,
				this, actionMapName));
		setStart(start);
		setEnd(end);
		setElementNameLen(XMLReaderChar.getElementNameLen(pageContent));
	}

	ReplacementMarker(int start, int end, String pageContent, IExecContext context, String actionMapName)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException,
			NoSuchMethodException {
		// log.debug("\n==========\npageContent:" + pageContent + "\n=======");
		setXMLObject(new XMLObject().mapXMLCharToXMLObject(pageContent));
		setAction(SetupBeanFromXML.createAction(getXMLObject(), XMLReaderChar.getInnerContent(pageContent), context,
				this, actionMapName));
		setStart(start);
		setEnd(end);
	}

	public StringBuilder doReplace(StringBuilder srcContent, Object replacementContent, int pageOffset) {
		StringBuilder p1 = new StringBuilder();
		if (srcContent.length() == 0) {
			p1.append(replacementContent == null ? "" : replacementContent);
		} else {
			p1.append(srcContent.substring(0, getStart() - pageOffset));
			p1.append(replacementContent == null ? "" : replacementContent);
			p1.append(srcContent.substring(getEnd() - pageOffset));
		}
		return (p1);
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public String getContent() {
		return action.getContent();
	}

	public void setContent(String content) {
		action.setContent(content);
	}

	public XMLObject getXMLObject() {
		return xo;
	}

	public void setXMLObject(XMLObject xo) {
		this.xo = xo;
	}

	public void setNestedMarkers(List<ReplacementMarker> nestedMarkers) {
		this.nestedMarkers = nestedMarkers;
	}

	public List<ReplacementMarker> getNestedMarkers() {
		return nestedMarkers;
	}

	public int getElementNameLen() {
		return elementNameLen;
	}

	public void setElementNameLen(int elementNameLen) {
		this.elementNameLen = elementNameLen;
	}

	public BaseAction getAction() {
		return action;
	}

	public void setAction(BaseAction action) {
		this.action = action;
	}
}
