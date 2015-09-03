package org.xmlactions.schema.docs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmlactions.common.text.XmlCData;
import org.xmlactions.common.xml.XMLAttribute;
import org.xmlactions.common.xml.XMLObject;

public class ToText {
	
	private static final Logger logger = LoggerFactory.getLogger(ToText.class);
	
	private int indent, indentLevel;
	
	protected String toText(XMLObject xo) {
		StringBuilder sb = new StringBuilder();
		sb.append("\n");
		indent = 0;
		indentLevel = 0;
		sb.append(mapSchema(xo));
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
		choice
		;
		private boolean equals(String x) {
			return this.toString().equals(x);
		}
	}
	private StringBuilder mapSchema(XMLObject element) {
		StringBuilder sb = new StringBuilder();
		for (XMLObject child : element.getChildren()) {
			sb.append(mapSchemaElement(child));
		}
		return sb;
	}
	
	private StringBuilder mapSchemaElement(XMLObject element) {
		StringBuilder sb = new StringBuilder();
		if (schemaElementNames.element.equals(element.getElementName())) {
			sb.append(mapElement(element));
			sb.append(mapSchema(element));
		} else if (schemaElementNames.attribute.equals(element.getElementName())) {
			sb.append(mapAttribute(element));
			sb.append(mapSchema(element));
		} else  if (schemaElementNames.complexType.equals(element.getElementName())) {
			sb.append(mapComplexType(element));
		} else  if (schemaElementNames.sequence.equals(element.getElementName())) {
			sb.append(mapSequence(element));
		} else  if (schemaElementNames.simpleType.equals(element.getElementName())) {
			sb.append(mapSimpleType(element));
			sb.append(mapSchema(element));
		} else  if (schemaElementNames.group.equals(element.getElementName())) {
			sb.append(mapGroup(element));
			sb.append(mapSchema(element));
		} else  if (schemaElementNames.annotation.equals(element.getElementName())) {
			sb.append(mapAnnotation(element));
			sb.append(mapSchema(element));
		} else  if (schemaElementNames.documentation.equals(element.getElementName())) {
			sb.append(mapDocumentation(element));
			sb.append(mapSchema(element));
		}
		return sb;
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
	private StringBuilder mapElement(XMLObject element) {
		StringBuilder sb = new StringBuilder();
		String name = element.getAttributeValueAsString(schemaElement.name.toString());
		int attCount = 0;
		addIndent(sb,3);
		sb.append(String.format("element[%s]:", indentLevel));
		if (name != null) {
			sb.append(name);
			attCount++;
		}
		sb.append("\n");
		if (element.getAttributeCount() > attCount) {
			addIndent(sb, 6);
			for (XMLAttribute attr : element.getAttributes()) {
				if (! attr.getKey().equals(schemaElement.name.toString())) {
					sb.append(attributeToString(attr.getKey(), attr.getValueAsString()));
				}
			}
			sb.append("\n");
		}
		return sb;
	}

	private enum schemaAttribues {
		name,
		ref,
		type,
		use,
		minOccurs,
		maxOccurs
	}
	private StringBuilder mapAttribute(XMLObject element) {
		StringBuilder sb = new StringBuilder();
		String name = element.getAttributeValueAsString(schemaAttribues.name.toString());
		int attCount = 0;
		if (name != null) {
			addIndent(sb,3);
			sb.append("attribute:");
			sb.append(name);
			sb.append("\n");
			attCount++;
		}
		if (element.getAttributeCount() > attCount) {
			addIndent(sb, 6);
			for (XMLAttribute attr : element.getAttributes()) {
				if (! attr.getKey().equals(schemaAttribues.name.toString())) {
					sb.append(attributeToString(attr.getKey(), attr.getValueAsString()));
				}
			}
			sb.append("\n");
		}
		return sb;
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
	private StringBuilder mapSimpleType(XMLObject element) {
		StringBuilder sb = new StringBuilder();
		String name = element.getAttributeValueAsString(schemaSimpleTypes.name.toString());
		addIndent(sb,3);
		sb.append("simpleType:");
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
	private StringBuilder mapComplexType(XMLObject element) {
		StringBuilder sb = new StringBuilder();
		String id = element.getAttributeValueAsString(schemaComplexType.id.toString());
		String mixed = element.getAttributeValueAsString(schemaComplexType.mixed.toString());
		if (id != null || mixed != null) {
			addIndent(sb,3);
			if (id != null) {
				sb.append(schemaComplexType.id.toString());
				sb.append(":");
				sb.append(id);
				sb.append(" ");
			}
			if (mixed != null) {
				sb.append(schemaComplexType.mixed.toString());
				sb.append(":");
				sb.append(mixed);
			}
			sb.append("\n");
		}
		// map attributes 1st
		for (XMLObject child : element.getChildren()) {
			if (child.getElementName().equals(schemaElementNames.attribute.toString())) {
				sb.append(mapSchemaElement(child));
			}
		}
		// map all others 2nd
		indent+=3;
		indentLevel++;
		for (XMLObject child : element.getChildren()) {
			if (! child.getElementName().equals(schemaElementNames.attribute.toString())) {
				sb.append(mapSchemaElement(child));
			}
		}
		indentLevel--;
		indent-=3;
		return sb;
	}

	private enum schemaSequence {
		id,
		minOccurs,
		maxOccurs,
		type
	}
	private StringBuilder mapSequence(XMLObject element) {
		StringBuilder sb = new StringBuilder();
		String id = element.getAttributeValueAsString(schemaSequence.id.toString());
		String minOccurs = element.getAttributeValueAsString(schemaSequence.minOccurs.toString());
		String maxOccurs = element.getAttributeValueAsString(schemaSequence.maxOccurs.toString());
		String type = element.getAttributeValueAsString(schemaSequence.type.toString());
		if (id != null || minOccurs != null || maxOccurs != null || type != null) {
			addIndent(sb,3);
			if (id != null) {
				sb.append(schemaSequence.id.toString());
				sb.append(":");
				sb.append(id);
				sb.append(" ");
			}
			if (minOccurs != null) {
				sb.append(schemaSequence.minOccurs.toString());
				sb.append(":");
				sb.append(minOccurs);
			}
			if (maxOccurs != null) {
				sb.append(schemaSequence.maxOccurs.toString());
				sb.append(":");
				sb.append(maxOccurs);
			}
			if (type != null) {
				sb.append(schemaSequence.type.toString());
				sb.append(":");
				sb.append(type);
			}
			sb.append("\n");
		}
		sb.append(mapSchema(element));
		return sb;
	}
	private StringBuilder mapAnnotation(XMLObject element) {
		StringBuilder sb = new StringBuilder();
		if (element.getAttributeValueAsString("id") != null) {
			addIndent(sb,3);
			sb.append(element.getAttributeValueAsString("id"));
			sb.append("\n");
		}
		return sb;
	}

	private StringBuilder mapDocumentation(XMLObject element) {
		StringBuilder sb = new StringBuilder();
		if (element.getContent() != null) {
			sb.append(XmlCData.removeCData(element.getContent()).trim());
			sb.append("\n");
		}
		return sb;
	}
	
	private String attributeToString(String key, String value) {
		if (value != null) {
			return key + ":" + value + " ";
		}
		return "";
	}
	private boolean notAllNull(String... strings) {
		for (String s:strings) {
			if (s != null) {
				return true;
			}
		}
		return false;
	}
}
