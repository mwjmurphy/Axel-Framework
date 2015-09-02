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

package org.xmlactions.mapping.bean_to_xml;


import java.util.ArrayList;
import java.util.List;


import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.xmlactions.action.actions.BaseAction;
import org.xmlactions.action.config.IExecContext;

public class Bean extends BaseAction {

	private static Logger log = LoggerFactory.getLogger(Bean.class);

	private String id;
	private String name;
    private String prefix;
	private String clas;
	private List<MapperElement> elements;
	private List<MapperAttribute> attributes;
	
	public String execute(IExecContext execContext) throws Exception {
		return null;
	}
	
	public Element processBean(BeanToXml map, Object obj) {
        Element element = DocumentHelper.createElement(BeanToXmlUtils.buildName(getPrefix(), getName()));
        for (Namespace namespace : map.getNamespaces()) {
            element.addNamespace(namespace.getPrefix(), namespace.getUri());
        }
		for (MapperAttribute attribute : getAttributes()) {
			attribute.buildXml(map, element, obj);
		}
		for (MapperElement node : getElements()) {
			node.buildXml(map, element, obj);
		}
		return element;
	}

	public Element processBean(BeanToXml map, Element parent, Object obj) {
        Element element = BeanToXmlUtils.addElement(parent, getPrefix(), getName());
        boolean processed = false;
		for (MapperAttribute attribute : getAttributes()) {
			attribute.buildXml(map, element, obj);
			processed = true;
		}
		for (MapperElement node : getElements()) {
			node.buildXml(map, element, obj);
			processed = true;
		}
		if (processed == false && obj != null) {
			element.addText((String)obj);
		}
		return element;
	}

    public Element processBean(BeanToXml map, Element parent, Object obj, String namespacePrefix, String elementName) {
		Element element;
		if (StringUtils.isNotEmpty(elementName )) {
            element = BeanToXmlUtils.addElement(parent, namespacePrefix, elementName);
		} else {
            element = BeanToXmlUtils.addElement(parent, getPrefix(), getName());
		}
		for (MapperAttribute attribute : getAttributes()) {
			attribute.buildXml(map, element, obj);
		}
		for (MapperElement node : getElements()) {
			node.buildXml(map, element, obj);
		}
		return element;
	}

	public List<MapperElement> getElements() {
		if (this.elements == null) {
			this.elements = new ArrayList<MapperElement>();
		}
		return elements;
	}

	public void setElements(List<MapperElement> elements) {
		this.elements = elements;
	}

	public void setElement(MapperElement element) {
		getElements().add(element);
	}
	
	public List<MapperAttribute> getAttributes() {
		if (this.attributes == null) {
			this.attributes = new ArrayList<MapperAttribute>();
		}
		return attributes;
	}

	public void setAttributes(List<MapperAttribute> attributes) {
		this.attributes = attributes;
	}

	public void setAttribute(MapperAttribute attribute) {
		getAttributes().add(attribute);
	}

	public String getName() {
		return StringUtils.isEmpty(name) ? getId() : name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClas() {
		return clas;
	}

	public void setClas(String clas) {
		this.clas = clas;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }

}
