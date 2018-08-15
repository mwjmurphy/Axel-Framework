package org.xmlactions.schema.docs;

import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmlactions.common.text.XmlCData;
import org.xmlactions.common.xml.XMLAttribute;
import org.xmlactions.common.xml.XMLObject;
import org.xmlactions.pager.actions.form.templates.Html;
import org.xmlactions.pager.actions.form.templates.HtmlDiv;

public class ToHtml {
	
	private static final Logger logger = LoggerFactory.getLogger(ToHtml.class);
	private static final String
		HEADER_BIG="h3",
		HEADER_MEDIUM="h4",
		HEADER_SMALL="h5";
	
	private static final String BS_CONTAINER="container";
	
	protected String toHtml(XMLObject xo, String filename) {
		HtmlDiv htmlDiv = new HtmlDiv();
		htmlDiv.setClazz(BS_CONTAINER + " axel_schema");
		Html leftCol = new HtmlDiv();
		htmlDiv.addChild(leftCol);
		leftCol.setClazz("col-sm-12");
		leftCol.put("role", "main");
//		Html a = leftCol.add("a");
//		a.put("name","index");
//		a.setContent(" ");	// top
//		leftCol.add(HEADER_BIG).setContent("Schema Description for \"" + filename + "\"");
//		getDocumentation(leftCol, xo);
		// addIndex(xo, filename);
		mapSchema(leftCol, xo);
//		Html rightCol = new HtmlDiv();
//		htmlDiv.addChild(rightCol);
//		rightCol.setClazz("col-md-3");
//		rightCol.addChild(createIndex(xo, filename));

		return htmlDiv.toString();
	}

	protected String getRootDocs(XMLObject xo, String filename) {
		HtmlDiv htmlDiv = new HtmlDiv();
		htmlDiv.setClazz(BS_CONTAINER + " axel_schema");
		getDocumentation(htmlDiv, xo);
		return htmlDiv.toString();
	}
	
	private Html createIndex(XMLObject xo, String filename) {
		Html div = new HtmlDiv();
		div.setClazz("bs-docs-sidebar hidden-print hidden-xs hidden-sm affix");
		div.put("role","complementary");
		div.setContent(addIndex(xo, filename));
		
//		HtmlDiv indexDiv = new HtmlDiv(); 
//		indexDiv.setClazz("axel_schema");
//		HtmlDiv navDiv = new HtmlDiv();
//		indexDiv.addChild(navDiv);
//		navDiv.setClazz("navigator");
//		navDiv.setId("schema_navigator");
//		navDiv.setContent("Nav");
//		HtmlDiv div = new HtmlDiv();
//		navDiv.addChild(div);
//		div.setClazz("index");
//		div.setId("schema_navigator_index");
//		div.setContent(addIndex(xo, filename));
//		return indexDiv;
		
		return div;
	}
	
	private String addIndex(XMLObject element, String filename) {
		StringBuilder sb = new StringBuilder();
		sb.append("<br/><b>" + filename + " - elements</b><hr/>");
		sb.append("<ul class=\"nav bs-docs-sidenav\">");
		for (XMLObject child : element.getChildren()) {
			// only process type element
			if (child.getElementName().equals("element")) {
				String name = child.getAttributeValueAsString("name");
				sb.append("<li><a href=\"#" + name + "\" title=\"" + StringEscapeUtils.escapeHtml(getDocumentationFirstLine(child)) + "\">" + name + "</a></li>");
			}
		}
		return sb.toString();
	}

	public String buildIndex(XMLObject element) {
		StringBuilder sb = new StringBuilder();
//		Html a = leftCol.add("a");
//		a.put("name","index");
//		a.setContent(" ");	// top
		sb.append("<table class=\"axel_schema\">");
		for (XMLObject child : element.getChildren()) {
			// only process type element
			if (child.getElementName().equals("element")) {
				String name = child.getAttributeValueAsString("name");
				// sb.append("<li class=\"list-group-item\"><a href=\"#" + name + "\">" + name + "</a> - "+ StringEscapeUtils.escapeHtml(getDocumentationFirstLine(child)) + "</li>\n");
				sb.append("<tr><td><a href=\"#" + name + "\">" + name + "</a></td><td style=\"padding-left:10px\">"	+ getDocumentationFirstLine(child) + "</td></tr>\n");
			}
		}
		sb.append("</table>");
		return sb.toString();
	}

	
	private enum schemaElementNames {
		schema,
		element,
		attribute,
		complexType,
		sequence,
		annotation,
		documentation,
		simpleType,
		group,
		all,
		anyAttribute,
		choice,
		enumeration,
		restriction
		;
		private boolean equals(String x) {
			return this.toString().equals(x);
		}
	}
	private void mapSchema(Html html, XMLObject element) {
		
		for (XMLObject child : element.getChildren()) {
			mapSchemaElement(html, child);
		}

	}
	
	private void mapSchemaElement(Html html, XMLObject element) {
		StringBuilder sb = new StringBuilder();
		if (schemaElementNames.element.equals(element.getElementName())) {
			mapElement(html, element);
		} else if (schemaElementNames.attribute.equals(element.getElementName())) {
			mapAttribute(html, element);
		} else if (schemaElementNames.group.equals(element.getElementName())) {
			mapGroup(html, element);
		} else if (schemaElementNames.simpleType.equals(element.getElementName())) {
			mapSimpleType(html, element);
		}
	}
	private void mapElementChildren(Html html, XMLObject element) {
		for (XMLObject child : element.getChildren()) {
			if (schemaElementNames.complexType.equals(child.getElementName())) {
				mapComplexType(html, child);
			} else if (schemaElementNames.sequence.equals(child.getElementName())) {
				mapElementChildren(html, child);
			} else if (schemaElementNames.choice.equals(child.getElementName())) {
				mapElementChildren(html, child);
			} else if (schemaElementNames.element.equals(child.getElementName())) {
				mapChildElement(html, child);
			} else if (schemaElementNames.restriction.equals(child.getElementName())) {
				mapRestriction(html, child);
			} else if (schemaElementNames.enumeration.equals(child.getElementName())) {
				mapEnumeration(html, child);
			}
		}
		html.add("br");
	}

	private enum schemaElement {
		name("name"),
		id("id"),
		block("block"),
		form("form"),
		ref("ref"),
		minOccurs("minOccurs"),
		maxOccurs("maxOccurs"),
		_default("default"),
		fixed("fixed"),
		nillable("nillable"),
		base("base"),
		_value("value"),
		;
		private String value;
		private schemaElement(String value) {
			this.value = value;
		}
		public String toString() {
			return value;
		}
	}

	private void mapElement(Html html, XMLObject element) {
		String name = element.getAttributeValueAsString(schemaElement.name.toString());
		if (name != null) {
			Html a = html.add("a");
			a.put("name", name);
			a.setContent(" ");	// link marker
			html.add(HEADER_BIG).setContent(name + " <small>- element</small>");
			Html div = html.add("div");
			div.put("style", "float:right;");
			a = div.add("a");
			a.put("href","#index");
			a.setContent("index");
			html.add("hr");
		}
		getDocumentation(html, element);
		mapElementChildren(html,element);
	}

	private void mapAttribute(Html html, XMLObject element) {
		
		String name = element.getAttributeValueAsString(schemaElement.name.toString());
		if (name != null) {
			Html a = html.add("a");
			a.put("name", name);
			a.setContent(" ");	// link marker
			html.add(HEADER_BIG).setContent(name + " <small>- attribute</small>");
			Html div = html.add("div");
			div.put("style", "float:right;");
			a = div.add("a");
			a.put("href","#index");
			a.setContent("index");
			html.add("hr");
		}
		getDocumentation(html, element);
		// mapAttributeChildren(html,element);
	}

	private void mapGroup(Html html, XMLObject element) {
		String name = element.getAttributeValueAsString(schemaElement.name.toString());
		if (name != null) {
			Html a = html.add("a");
			a.put("name", name);
			a.setContent(" ");	// link marker
			html.add(HEADER_BIG).setContent(name + " <small>- group</small>");
			Html div = html.add("div");
			div.put("style", "float:right;");
			a = div.add("a");
			a.put("href","#index");
			a.setContent("index");
			html.add("hr");
		}
		getDocumentation(html, element);
		mapElementChildren(html,element);
	}

	private void mapChildElement(Html html, XMLObject element) {
		Html div = html.add("div");
		div.setClazz("element");
	
		String name = element.getAttributeValueAsString(schemaElement.name.toString());
		if (name != null) {
			Html p = div.add("div");
			p.setClazz("name");
			p.setContent(name);
		}
		
		for (XMLAttribute attr : element.getAttributes()) {
			if (! attr.getKey().equals(schemaElement.name.toString())) {
				if (attr.getKey().equals("ref")) {
					Html p = div.add("div");
					p.setClazz("name");
					p.setContent(attr.getKey() + "- <a href=\"#" + attr.getValue() + "\">" + attr.getValue() + "</a>");
				} else {
					Html p = div.add("div");
					p.setClazz("detail");
					p.setContent(attributeToString(attr));
				}
				
			}
		}
		getDocumentation(div, element);
	}

	private enum schemaAttribues {
		name,
		ref,
		type,
		use,
		minOccurs,
		maxOccurs
	}
	private void mapAttributeChild(Html html, XMLObject element) {
		
		Html div = html.add("div");
		div.setClazz("attribute");
		String name = element.getAttributeValueAsString(schemaAttribues.name.toString());
		if (name != null) {
			Html p = div.add("div");
			p.setClazz("name");
			p.setContent("<h4>" + name + " <small>- attribute</small><h4>");
		}

		for (XMLAttribute attr : element.getAttributes()) {
			if (! attr.getKey().equals(schemaAttribues.name.toString())) {
				if (attr.getKey().equals("ref")) {
					Html p = div.add("div");
					p.setClazz("name");
					p.setContent("<h4>" + attr.getKey() + " <small>- <a href=\"#" + attr.getValue() + "\">" + attr.getValue() + "</a></small></h4>");
				} else {
					Html p = div.add("div");
					p.setClazz("detail");
					p.setContent("<i>" + attributeToString(attr.getKey(), attr.getValueAsString()) + "</i>");
				}
			}
		}
		getDocumentation(div, element);
	}

//	   <xsd:simpleType name="select_options">
//	      <xsd:annotation>
//	         <xsd:documentation>
//	            Can select a single or multiple select set of radio buttons or check boxes.
//	         </xsd:documentation>
//	      </xsd:annotation>
//	      <xsd:restriction base="xsd:string">
//	         <xsd:enumeration value="single" />
//	         <xsd:enumeration value="multiple" />
//	      </xsd:restriction>
//	   </xsd:simpleType>
	private enum schemaSimpleTypes {
		name,
	}
	private void mapSimpleType(Html html, XMLObject element) {
		String name = element.getAttributeValueAsString(schemaElement.name.toString());
		if (name != null) {
			Html a = html.add("a");
			a.put("name", name);
			a.setContent(" ");	// link marker
			html.add(HEADER_BIG).setContent(name + " <small>- simpleType</small>");
			Html div = html.add("div");
			div.put("style", "float:right;");
			a = div.add("a");
			a.put("href","#index");
			a.setContent("index");
			html.add("hr");
		}
		getDocumentation(html, element);
		mapElementChildren(html, element);
	}

	private void mapRestriction(Html html, XMLObject element) {
		String name = element.getAttributeValueAsString(schemaElement.base.toString());
		if (name != null) {
			html.add(HEADER_MEDIUM).setContent("restriction <small>- base:" + name + "</small>");
		}
		getDocumentation(html, element);
		mapElementChildren(html, element);
	}

	private void mapEnumeration(Html html, XMLObject element) {
		String name = element.getAttributeValueAsString(schemaElement._value.toString());
		if (name != null) {
			Html div = html.add("div");
			div.setClazz("detail");
			div.setContent("enumeration:<b>" + name + "</b>");
		}
		getDocumentation(html, element);
		mapElementChildren(html, element);
	}

	private enum schemaGroup {
		name,
	}
	private void mapGroup(XMLObject element) {
	}
	
	
	private enum schemaComplexType {
		id,
		mixed
	}
	private void mapComplexType(Html html, XMLObject element) {
		// map attributes 1st
		html.add(HEADER_MEDIUM).setContent("attributes");
		for (XMLObject child : element.getChildren()) {
			if (child.getElementName().equals(schemaElementNames.attribute.toString())) {
				mapAttributeChild(html, child);
			}
		}
		// map all others 2nd
		html.add(HEADER_MEDIUM).setContent("child elements");
		mapElementChildren(html, element);
	}

	private enum schemaSequence {
		id,
		minOccurs,
		maxOccurs,
		type
	}
	
	private void getDocumentation(Html html, XMLObject element) {
		for (XMLObject anno : element.getChildren()) {
			if ("annotation".equals(anno.getElementName())) {
				for (XMLObject doc : anno.getChildren()) {
					if ("documentation".equals(doc.getElementName())) {
						//getDocumentation(html, doc.getContent());
						getDocumentation(html, doc.mapXMLObject2XML(doc));
					}
				}
			}
		}
		
	}
	private void getDocumentation(Html html, String content) {
		if (content != null) {
			String s = XmlCData.removeAllCData(content);
			//s = s.replaceAll("<xsd:documentation>", "");
			//s = s.replaceAll("</xsd:documentation>", "");
			String [] strings = s.split("\n");
			StringBuilder sb = new StringBuilder();
			for (int loop = 0 ; loop < strings.length; loop++) {
				String string = strings[loop];
				String trimmed = string.trim();
				if (trimmed.length() > 0) {
					sb.append(string);
				} else {
					Html p = html.add("p");
					p.setClazz("document");
					p.setContent(sb.toString());
					sb = new StringBuilder();
				}
			}
			if (sb.length() > 0) {
				Html p = html.add("p");
				p.setClazz("document");
				p.setContent(sb.toString());
				sb = new StringBuilder();
			}
		}
	}

	private String getDocumentationFirstLine(XMLObject element) {
		for (XMLObject anno : element.getChildren()) {
			if ("annotation".equals(anno.getElementName())) {
				for (XMLObject doc : anno.getChildren()) {
					if ("documentation".equals(doc.getElementName())) {
						return getDocumentationFirstLine(doc.getContent());
					}
				}
			}
		}
		return "";
	}
	
	private String getDocumentationFirstLine(String content) {
		if (content != null) {
			String s = XmlCData.removeCData(content).trim();
			String [] strings = s.split("\n");
			StringBuilder sb = new StringBuilder();
			for (int loop = 0 ; loop < strings.length; loop++) {
				String string = strings[loop];
				String trimmed = string.trim();
				if (trimmed.length() > 0) {
					sb.append(trimmed + " ");
				} else {
					if (sb.length() > 0) {
						break;
					}
				}
			}
			return sb.toString();
		}
		return "";
	}

	private String attributeToString(XMLAttribute attr) {
		return attributeToString(attr.getKey(), attr.getValueAsString());
	}
	
	private String attributeToString(String key, String value) {
		if (value != null) {
			return key + " - " + value;
		}
		return "";
	}
}
