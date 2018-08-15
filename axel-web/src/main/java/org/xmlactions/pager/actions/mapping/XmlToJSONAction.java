package org.xmlactions.pager.actions.mapping;
/**
 \page org_xmlactions_xml_to_json XML to JSON Action

 \tableofcontents

 \section axel_xml_to_json Mapping XML data to JSON format

 This action will map an XML file or string to a JSON format.

 The XML is supplied as a file name or as an XML string.

 The output is JSON formatted data

 If the key attribute is set then the JSON data is stored into the execContext using the key.
 Else the JSON data is returned to the client (browser).

 The attributes for this action are:
 <ul>
 	<li>xml_file_name -
 	    The name of the xml file to map to JSON format. The xml_ref attribute can be used instead
 	    of the xml_file_name. The xml_ref will be the key to get the XML as a string value from
 	    the execContext.
 	</li>
 	<li>xml_ref - The key used to get the xml as a String from the execContext.  Instead of using
 		an xml_ref an xml file name may be used by setting the name of the file in the
 		xml_file_name attribute.
 	</li>
	<li>key-  If this is set than the json output will be placed
    	into the execContext with this key.  Can be retrieved from the 
        execContext using the same key.
    </li>
	<li>path - Additional path, prepended to the page. By default it is set to the web context root.
	</li>
 </ul>



 */


import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.json.XML;
import org.xmlactions.action.ActionConst;
import org.xmlactions.action.NestedActionException;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.common.io.ResourceCommon;
import org.xmlactions.common.io.ResourceUtils;
import org.xmlactions.common.xml.BadXMLException;
import org.xmlactions.common.xml.XMLObject;
import org.xmlactions.common.xml.XMLXPathParser;
import org.xmlactions.mapping.json.JSONUtils;
import org.xmlactions.pager.actions.form.CommonFormFields;

public class XmlToJSONAction extends CommonFormFields {


	private static final String actionName = "map_xml_to_json";

	private enum action_attribute_names {
		xml_filename,
		xml_ref,
		key,
		path,
		xml_path,
		escape_xml,
	}

	/** The name of a file that contains the data in xml format */
	private String xml_filename;

	/** the input xml may be stored in the execContext instead with this key */
	private String xml_ref;

	/** the output json will be stored in the execContext with this key if it's set */
	private String key;

	private String path;
	
	private String xml_path;

	/**
	 * If this is set true (default value) than the xml will be escaped before processing. The escaping will only change the & for &amp;  
	 */
	private boolean escape_xml = true;

	public String execute(IExecContext execContext) throws Exception {

		validate(execContext);

		String output = processMapping(execContext);
		if (StringUtils.isNotEmpty(getKey())) {
			execContext.put(key, output);
			return "";
		} else {
			return output;
		}
	}

	public void validate(IExecContext execContext) {
		if (StringUtils.isEmpty(getXml_filename()) && StringUtils.isEmpty(getXml_ref())) {
			throw new IllegalArgumentException(String.format("You must set either the %s or the %s attribute in %s",action_attribute_names.xml_filename, action_attribute_names.xml_ref, actionName));
		}
		if (path == null) {
			path = (String) execContext.get(ActionConst.WEB_REAL_PATH_BEAN_REF);
		}
	}

	private String processMapping(IExecContext execContext) throws Exception {

		JSONObject jsonObject = null;
		String xmlData = null;
		if (StringUtils.isNotEmpty(getXml_filename())) {
			String fileName = null;
			try {
				String name = execContext.replace(getXml_filename());
				fileName = ResourceCommon.buildFileName(path, name);
				xmlData = ResourceUtils.loadFile(fileName);
			} catch (IOException ex) {
				throw new IllegalArgumentException("Unable to load " + fileName, ex);
			}
		} else if (StringUtils.isNotEmpty(getXml_ref())) {
			xmlData = execContext.getString(getXml_ref());
		}
		
		if (StringUtils.isNotEmpty(getXml_path())) {
			String xpathPath = XMLXPathParser.getPath(getXml_path());
			String attributeName = XMLXPathParser.getAttribute(getXml_path());
			if (StringUtils.isEmpty(xpathPath) || StringUtils.isEmpty(attributeName)) {
				throw new IllegalArgumentException("Invalid xpath attribute [" + getXml_path() + "]");
			}
			
			XMLObject xo = new XMLObject().mapXMLCharToXMLObject(xmlData);
			if (StringUtils.isNotEmpty(xpathPath) && StringUtils.isEmpty(attributeName)) {
				xmlData = xo.getContent(xpathPath);
			} else if (StringUtils.isEmpty(xpathPath) && StringUtils.isNotEmpty(attributeName)) {
				xmlData = xo.getAttributeValueAsString(attributeName);
				xmlData = StringEscapeUtils.unescapeHtml(xmlData);
			} else {
				xmlData = xo.getAttribute(xpathPath,  attributeName);
				xmlData = StringEscapeUtils.unescapeHtml(xmlData);
			}
			
		}
		
		jsonObject = JSONUtils.mapXmlToJson(xmlData, isEscape_xml());
		return jsonObject.toString();
	}

	/**
	 * @return the xml_file_name
	 */
	public String getXml_filename() {
		return xml_filename;
	}

	/**
	 * @param xml_filename the xml_filename to set
	 */
	public void setXml_filename(String xml_filename) {
		this.xml_filename = xml_filename;
	}

	/**
	 * @return the xml_ref
	 */
	public String getXml_ref() {
		return xml_ref;
	}

	/**
	 * @param xml_ref the xml_ref to set
	 */
	public void setXml_ref(String xml_ref) {
		this.xml_ref = xml_ref;
	}

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}

	public boolean isEscape_xml() {
		return escape_xml;
	}

	public void setEscape_xml(boolean escape_xml) {
		this.escape_xml = escape_xml;
	}

	public String getXml_path() {
		return xml_path;
	}

	public void setXml_path(String xml_path) {
		this.xml_path = xml_path;
	}



}