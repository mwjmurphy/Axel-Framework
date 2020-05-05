package org.xmlactions.schema.docs;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmlactions.common.text.XmlCData;
import org.xmlactions.common.xml.XMLAttribute;
import org.xmlactions.common.xml.XMLObject;
import org.xmlactions.pager.actions.escaping.EscapeAction;

public class ToJson {
	
	private static final Logger logger = LoggerFactory.getLogger(ToJson.class);
	
	private int indent, indentLevel;
	
	protected JSONObject toJson(XMLObject xo) {
		indent = 0;
		indentLevel = 0;
		JSONObject jo = mapData(xo);
		return jo;
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
		restriction
		;
		private boolean equals(String x) {
			return this.toString().equals(x);
		}
	}
	private JSONObject mapData(XMLObject element) {
		JSONObject jo = mapSchemaElement(null, element);
		return jo;
	}
	
	private JSONObject mapSchemaElement(JSONObject jo, XMLObject element) {
		if (jo == null) { 
			jo = new JSONObject();
		}
		if (schemaElementNames.element.equals(element.getElementName())) {
			mapElement(jo, element);
		} else if (schemaElementNames.attribute.equals(element.getElementName())) {
			jo = mapAttribute(jo, element);
		} else  if (schemaElementNames.complexType.equals(element.getElementName())) {
			mapComplexType(jo, element);
		} else  if (schemaElementNames.sequence.equals(element.getElementName())) {
			mapSequence(jo, element);
		} else  if (schemaElementNames.simpleType.equals(element.getElementName())) {
			mapSimpleType(jo, element);
//		} else  if (schemaElementNames.group.equals(element.getElementName())) {
//			sb.append(mapGroup(element));
//			sb.append(mapSchema(element));
		} else  if (schemaElementNames.annotation.equals(element.getElementName())) {
			mapAnnotation(jo, element);
		} else  if (schemaElementNames.documentation.equals(element.getElementName())) {
			mapDocumentation(jo, element);
		} else  if (schemaElementNames.schema.equals(element.getElementName())) {
			mapSchema(jo, element);
		} else  if (schemaElementNames.restriction.equals(element.getElementName())) {
			mapRestriction(jo, element);
		}
		if (jo.length() == 0) {
			jo = null;
		}
		return jo;
	}

	private void addIndent(StringBuilder sb, int more) {
		for (int i = 0 ; i < indent+more; i++) {
			sb.append(' ');
		}
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
		;
		private String value;
		private schemaElement(String value) {
			this.value = value;
		}
		public String toString() {
			return value;
		}
	}

	private JSONObject mapSchema(JSONObject top, XMLObject element) {
	
		JSONObject jo = new JSONObject();
		for (XMLAttribute child : element.getAttributes()) {
			jo.put(child.getKey(), child.getValueAsString());
		}
		for (XMLObject child : element.getChildren()) {
			JSONObject j = mapSchemaElement(jo, child);
		}
		top.put(element.getElementName(), jo);
		return top;
	}
	
	/**
		{
			name:code
			params: [
			    name: call
			    use: required
			    type: xsd:string
			    documentation: ""
			]
					
		}
	 */
	private JSONObject mapElement(JSONObject top, XMLObject element) {
		JSONObject jo = new JSONObject();
		JSONObject action = new JSONObject();
		String name = "NONAMEFOUND";
		for (XMLAttribute child : element.getAttributes()) {
			if (child.getKey().equals("name")) {
				name = child.getValueAsString();
			} else if (child.getKey().equals("ref")) {
				// here we need to finmd the reference and get its content
				name = child.getValueAsString();
			} else {
				action.put(child.getKey(), child.getValueAsString());
			}
		}
		for (XMLObject child : element.getChildren()) {
			JSONObject j = mapSchemaElement(action, child);
		}
		// JSONObject action = newJO("element", jo);
		// jo.put("name", name);
		action.put("name", name);
		if (element.getParent() != null && element.getParent().getElementName().equals("schema")) {
			top.append("elements", action);
		} else {
			top.append("params", action);
		}
		// jo.put("attributes", action);
		// top.append("elements", jo);
		return jo;
	}

	private enum schemaAttribues {
		name,
		ref,
		type,
		use,
		minOccurs,
		maxOccurs
	}
	private JSONObject mapAttribute(JSONObject top, XMLObject element) {
		String name = element.getAttributeValueAsString(schemaAttribues.name.toString());
		if (StringUtils.isBlank(name) ) {
			name = schemaAttribues.ref.toString();
		}
		JSONObject jo = new JSONObject();
		for (XMLAttribute attr : element.getAttributes()) {
			if (! attr.getValueAsString().equals(name)) {
				jo.put(attr.getKey(), attr.getValueAsString());
			}
		}
		for (XMLObject child : element.getChildren()) {
			mapSchemaElement(jo, child);
		}
		jo.put("name", name);
		top.append("params", jo);
		return jo;
	}

	private enum restrictionAttribues {
		base
	}
	private JSONObject mapRestriction(JSONObject top, XMLObject element) {
		String name = element.getAttributeValueAsString(restrictionAttribues.base.toString());
		StringBuilder options = new StringBuilder();
		int count = 0;
		String seperator = "";
		for(XMLObject child : element.getChildren()) {
			if (child.getElementName().equals("enumeration")) {
				if (count > 0) {
					seperator = ",";
				}
				options.append(seperator + child.getAttributeValueAsString("value"));
				count++;
			} else {
				mapSchemaElement(top, child);
			}
		}
		if (options.length() > 0) {
			top.put("options", options);
		}
		return top;
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
	private void mapSimpleType(JSONObject top, XMLObject element) {
		JSONObject jo = new JSONObject();
		StringBuilder sb = new StringBuilder();
		String name = element.getAttributeValueAsString(schemaSimpleTypes.name.toString());
		jo.put("name", name);
		for (XMLObject child : element.getChildren()) {
			mapSchemaElement(jo, child);
		}
		top.append("types", jo);
	}

//	   <xsd:group name="table_fields">
//	      <xsd:annotation>
//	         <xsd:documentation>
//	            Implies an inclusion for the list of field elements for a table.
//	         </xsd:documentation>
//	      </xsd:annotation>
//	      <xsd:sequence>
//	         <xsd:choice minOccurs="0" maxOccurs="unbounded">
//	            <xsd:element ref="pk" minOccurs="0" />
//	            <xsd:element ref="fk" minOccurs="0" />
//	            <xsd:element ref="text" minOccurs="0" />
//	            <xsd:element ref="textarea" minOccurs="0" />
//	            <xsd:element ref="link" minOccurs="0" />
//	            <xsd:element ref="password" minOccurs="0" />
//	            <xsd:element ref="image" minOccurs="0" />
//	            <xsd:element ref="int" minOccurs="0" />
//	            <xsd:element ref="timestamp" minOccurs="0" />
//	            <xsd:element ref="datetime" minOccurs="0" />
//	            <xsd:element ref="date" minOccurs="0" />
//	            <xsd:element ref="timeofday" minOccurs="0" />
//	            <xsd:element ref="binary" minOccurs="0" />
//	            <xsd:element ref="select" minOccurs="0" />
//	            <xsd:element ref="table_path" minOccurs="0" />
//	         </xsd:choice>
//	      </xsd:sequence>
//	   </xsd:group>
	private enum schemaGroup {
		name,
	}
	private StringBuilder mapGroup(XMLObject element) {
		StringBuilder sb = new StringBuilder();
		String name = element.getAttributeValueAsString(schemaSimpleTypes.name.toString());
		addIndent(sb,3);
		sb.append("group:");
		sb.append(name);
		sb.append("\n");
		addIndent(sb, 6);
		for (XMLAttribute attr : element.getAttributes()) {
			sb.append(attr.getKey());
			sb.append(':');
			sb.append(attr.getValueAsString());
			sb.append(" ");
		}
		sb.append("\n");
		return sb;
	}
	
	
	private enum schemaComplexType {
		id,
		mixed
	}
	private void mapComplexType(JSONObject jo, XMLObject element) {
		String id = element.getAttributeValueAsString(schemaComplexType.id.toString());
		String mixed = element.getAttributeValueAsString(schemaComplexType.mixed.toString());
		if (id != null || mixed != null) {
			if (id != null) {
				jo.put(schemaComplexType.id.toString(), id);
			}
			if (mixed != null) {
				jo.put(schemaComplexType.mixed.toString(), mixed);
			}
		}
		// map attributes 1st
		for (XMLAttribute att : element.getAttributes()) {
			jo.put(att.getKey(), att.getValueAsString());
		}
		// map all others 2nd
		for (XMLObject child : element.getChildren()) {
			mapSchemaElement(jo, child);
		}
	}

	private enum schemaSequence {
		id,
		minOccurs,
		maxOccurs,
		type
	}
	private void mapSequence(JSONObject top, XMLObject element) {
		JSONArray ja = new JSONArray();
		for (XMLAttribute att : element.getAttributes()) {
			ja.put(newJO(att.getKey(), att.getValueAsString()));
		}
		if (ja.length() > 0) {
			top.put("params", ja);
		}
		for (XMLObject child : element.getChildren()) {
			mapSchemaElement(top, child);
		}
	}
	private void mapAnnotation(JSONObject parent, XMLObject element) {
		for(XMLObject child : element.getChildren()) {
			mapSchemaElement(parent, child);
		}
	}

	private void mapDocumentation(JSONObject parent, XMLObject element) {
		if (element.getContent() != null) {
			String content = XmlCData.removeAllCData(EscapeAction.jsonEscape(element.getContent()));
			content = content.replace("\"", "&quot;");
			parent.put(element.getElementName(), content);
		}
	}
	
	private JSONObject attributeToString(String key, String value) {
		if (value != null) {
			return newJO(key, value);
		}
		return null;
	}
	private boolean notAllNull(String... strings) {
		for (String s:strings) {
			if (s != null) {
				return true;
			}
		}
		return false;
	}
	
	private JSONObject newJO(String name, Object value) {
		JSONObject jo = new JSONObject();
		jo.put(name, value);
		return jo;
	}
}
