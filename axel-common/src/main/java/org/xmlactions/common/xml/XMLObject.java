/*
 * XMLObject.java
 *
 * Created on August 30, 2005, 3:22 PM
 */

package org.xmlactions.common.xml;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmlactions.common.system.JS;


/**
 * 
 * @author MMURPHY
 */
@SuppressWarnings("serial")
public class XMLObject implements Serializable {
	private static Logger log = LoggerFactory.getLogger(XMLObject.class);

	private String nameSpace;
	private String elementName;
	private List<XMLAttribute> attributes;
	private String content;
	private List<XMLObject> children;
	private XMLObject parent;

	public String getNameSpace() {
		return nameSpace;
	}

	public void setNameSpace(String nameSpace) {
		this.nameSpace = nameSpace;
	}

	/**
	 * Creates a new instance of XMLObject Some examples of usage // find first
	 * instance of root2 to node in path 'element_root/root1/root2' XMLObject
	 * xo1 = xo.findXMLObjectByPath("element_root/root1/root2"); // find index
	 * instance of root3 to node in path 'element_root/root1/root2/root3' xo1 =
	 * xo.findXMLObjectByPath("element_root/root1/root2/root3", 1); // get
	 * attribute value for 'index' in 'element_root/root1/root2/root3' str =
	 * xo.getAttribute("element_root/root1/root2/root3", "index"); // get
	 * attribute value for 'index' in indexed 'element_root/root1/root2/root3'
	 * str = xo.getAttribute("element_root/root1/root2/root3", 2, "index");
	 */
	public XMLObject() {
		setup();
	}

	public XMLObject(String elementName) {
		setup();
		this.elementName = elementName;
	}

	protected void setup() {
		attributes = new ArrayList<XMLAttribute>();
		elementName = null;
		content = null;
		children = new ArrayList<XMLObject>();
		parent = null;
	}

	public void setElementName(String elementName) {
		this.elementName = elementName;
	}

	/*
	 * = = = = = = = = = = = = = = = =
	 * 
	 * ATTRIBUTE STUFF
	 * 
	 * = = = = = = = = = = = = = = = =
	 */

	public void setAttributes(List<XMLAttribute> attributes) {
		this.attributes = attributes;
	}

	public void addAttribute(String key, String value) {
		XMLAttribute att = new XMLAttribute(key, value);
		attributes.add(att);
	}

	public void addAttribute(String key, int value) {
		this.addAttribute(key, "" + value);
	}

	public void addContent(String content) {
		this.content = content;
	}

	/**
	 * add a new node with the content as an child of this node.
	 */
	public XMLObject addNodeWithContent(String nodeName, String content) {
		XMLObject xo = new XMLObject(nodeName);
		xo.addContent(content);
		this.addChild(xo);
		return (xo);
	}

	public String getElementName() {
		return (this.elementName);
	}

	/** returns the element name including the namespace if there is one */
	public String getName() {
		return ((this.nameSpace != null ? this.nameSpace + ':' : "") + this.elementName);
	}

	public XMLAttribute getXMLAttribute(int index) {
		if (index < 0 || index >= attributes.size())
			return (null);
		return ((XMLAttribute) attributes.get(index));
	}

	public XMLAttribute getXMLAttribute(String key) {
		for (int iLoop = 0; iLoop < attributes.size(); iLoop++) {
			if (((XMLAttribute) attributes.get(iLoop)).key.equals(key)) {
				return ((XMLAttribute) attributes.get(iLoop));
			}
		}
		return (null);
	}

	private Object getXMLAttributeValue(String key) {
		for (int iLoop = 0; iLoop < attributes.size(); iLoop++) {
			if (((XMLAttribute) attributes.get(iLoop)).key.equals(key)) {
				return (((XMLAttribute) attributes.get(iLoop)).value);
			}
		}
		return (null);
	}

	/** Get the attribute value for this key, will return null if no key exists. */
	private Object getXMLAttributeValue(int index) {
		return (((XMLAttribute) attributes.get(index)).value);
	}

	/** Get the attribute value for this key, will return null if no key exists. */
	public Object getAttributeValue(String key) {
		return (getXMLAttributeValue(key));
	}

	/**
	 * Get the attribute value for this key as a string, will return "" if no
	 * key exists.
	 */
	public Object getAttributeValueNoNull(String key) {
		Object j = getXMLAttributeValue(key);
		return (j == null ? (Object) "" : j);
	}

	/**
	 * Get the attribute value for this key as a string, will return null if no
	 * key exists.
	 */
	public String getAttributeValueAsString(String key) {
		return ((String) getXMLAttributeValue(key));
	}

	/** Get the attribute value for this index. */
	public String getAttributeAsString(int index) {
		return ((String) getXMLAttributeValue(index));
	}

	/** Get the attribute key for this index. */
	public String getAttributeKeyAsString(int index) {
		XMLAttribute att = this.getXMLAttribute(index);
		if (att != null)
			return (att.getKey());
		return (null);
	}

	/** Get the number of attribute for this element. */
	public int getAttributeCount() {
		return (this.attributes.size());
	}

	/** Get the list of attributes for this element. */
	public List<XMLAttribute> getAttributes() {
		return (this.attributes);
	}

	public void updateAttribute(String key, String newValue) {
		updateAttribute(key, (Object) newValue);
	}

	public void updateAttribute(String key, Object newValue) {
		for (int iLoop = 0; iLoop < attributes.size(); iLoop++) {
			if (((XMLAttribute) attributes.get(iLoop)).key.equals(key)) {
				XMLAttribute att = new XMLAttribute(key, newValue);
				attributes.set(iLoop, att);
			}
		}
	}

	/*
	 * = = = = = = = = = = = = = = = =
	 * 
	 * CONTENT STUFF
	 * 
	 * = = = = = = = = = = = = = = = =
	 */

	public String getContent() {
		return (this.content);
	}

	/*
	 * = = = = = = = = = = = = = = = =
	 * 
	 * CHILDREN STUFF
	 * 
	 * = = = = = = = = = = = = = = = =
	 */
	public void addChild(XMLObject xmlObject) {
		xmlObject.parent = this;
		children.add(xmlObject);
	}

	public void addChild(int index, XMLObject xmlObject) {
		xmlObject.parent = this;
		children.add(index, xmlObject);
	}

	public XMLObject getChild(int index) {
		return ((XMLObject) children.get(index));
	}

	public List<XMLObject> getChildren() {
		return children;
	}

	/**
	 * Get a child element starting a from index and containing childElementName
	 * Will start at the entered from position getting each child element. If
	 * the child element name matches the will return the index of the mathching
	 * child.
	 * 
	 * @param from
	 *            is the index of the child that we want to start searching from
	 * @param childElementName
	 *            is the element we are looking for
	 * 
	 * @return the index of the found child or a -1 if not found.
	 */
	public int getChildIndex(int from, String childElementName) {
		for (int i = from; i < this.getChildCount(); i++) {
			XMLObject xo = (XMLObject) children.get(i);
			if (xo.elementName.equals(childElementName))
				return (i);
		}
		return (-1); // doesn't exist
	}

	public void removeChild(int index) {
		children.remove(index);
	}

	public void addChildAfter(XMLObject before, XMLObject xo) {
		int index = findChild(before);
		if (index > -1)
			index++;
		addChild(index, xo);
	}

	public void removeChild(XMLObject xo) {
		children.remove(xo);
	}

	public int getChildCount() {
		return (children.size());
	}

	/**
	 * Cound the number of children this XMLObject has that match the entered
	 * childElementName (not case sensitive)
	 * 
	 * @param childElementName
	 *            only count elements that match this element name
	 * @return
	 */
	public int getChildCount(String childElementName) {
		int count = 0;
		for (int iLoop = 0; iLoop < this.getChildCount(); iLoop++) {
			XMLObject child = this.getChild(iLoop);
			if (child.getElementName().equalsIgnoreCase(childElementName)) {
				count++;
			}
		}
		return (count);
	}

	public XMLObject getParent() {
		return (parent);
	}

	public int findChild(XMLObject xo) {
		for (int iLoop = 0; iLoop < this.getChildCount(); iLoop++) {
			if (this.getChild(iLoop).equals(xo)) {
				return (iLoop);
			}
		}
		return (-1); // not found
	}

	/**
	 * Finds and returns a XMLObject of nodeName or returns null if not found
	 * Traverses node and subsequent child node searching for the nodeName.
	 */
	public XMLObject findNode(XMLObject xo, String nodeName) {
		XMLObject fxo;
		if (xo.getElementName().equals(nodeName) == true) {
			return (xo);
		}
		for (int iLoop = 0; iLoop < xo.getChildCount(); iLoop++) {
			fxo = xo.getChild(iLoop);
			if (fxo.getElementName().equals(nodeName)) {
				return (fxo);
			}
			if (fxo.getChildCount() > 0) {
				XMLObject fxo2 = findNode(fxo, nodeName);
				if (fxo2 != null) {
					return (fxo2);
				}
			}
		}
		return (null); // not found
	}

	/**
	 * Finds and returns a child XMLObject with nodeName or returns null if not
	 * found
	 */
	public XMLObject findChildNode(String nodeName) {
		XMLObject fxo;
		for (int iLoop = 0; iLoop < this.getChildCount(); iLoop++) {
			fxo = this.getChild(iLoop);
			if (fxo.getElementName().equals(nodeName)) {
				return (fxo);
			}
		}
		return (null); // not found
	}

	/**
	 * replaces the oldXO with the newXO by first determining the parent of the
	 * oldXO and then replacing the child.
	 * 
	 * @param oldXO
	 *            is the XMLObject to replace
	 * @param newXO
	 *            is the XMLObject to replace it with.
	 */
	public void replace(XMLObject oldXO, XMLObject newXO) {
		XMLObject xo = oldXO.getParent();
		if (xo != null) {
			int index = xo.findChild(oldXO);
			if (index > -1) {
				xo.removeChild(oldXO);
				xo.addChild(index, newXO);
			}
		}
	}

	/** removes the xml object from it's parent if there is a parent */
	public void remove(XMLObject oldXO) {
		XMLObject xo = oldXO.getParent();
		if (xo != null) {
			xo.removeChild(oldXO);
		}
	}

	/**
	 * removes the xml object from it's parent if there is a parent.
	 */
	public void remove() {
		if (getParent() != null) {
			getParent().removeChild(this);
		}
	}

	/*
	 * = = = = = = = = = = = = = = = = = = = = =
	 * 
	 * Mapping XML using paths
	 * 
	 * = = = = = = = = = = = = = = = = = = = = =
	 */
	/**
	 * Gets an attribute value as a string using path and key
	 * 
	 * @param path
	 *            is the xml path to the node i.e. 'root/node1/node2'
	 * @param key
	 *            is the attribute value we want from the node.
	 * @returns the value if node found or null if not found.
	 */
	public String getAttribute(String path, String key) throws Exception {
		XMLObject xo = this.findXMLObjectByPath(path);
		if (xo != null)
			return ((String) xo.getAttributeValue(key));
		// throw new Exception (this.getClass().getName() +
		// ".getAttribute. Path not found for '" + path + "'");
		return (null);
	}

	/**
	 * Gets an attribute value as a string using path and key
	 * 
	 * @param path
	 *            is the xml path to the node i.e. 'root/node1/node2'
	 * @param index
	 *            is the node2 index as child of node1.
	 * @param key
	 *            is the attribute value we want from the node.
	 * @returns the value if node found or null if not found.
	 */
	public String getAttribute(String path, int index, String key)
			throws Exception {
		XMLObject xo = this.findXMLObjectByPath(path, index);
		if (xo != null) {
			return ((String) xo.getAttributeValue(key));
		}
		// throw new Exception (this.getClass().getName() +
		// ".getAttribute. Path not found for '" + path + "' at index " + +
		// index);
		return (null);
	}

	/**
	 * Gets a node and any children as a string using path and index
	 * 
	 * @param path
	 *            is the xml path to the node i.e. 'root/node1/node2'
	 * @param index
	 *            is the node2 index as child of node1.
	 * @returns the node and children found or null if not found.
	 */
	public XMLObject getNode(String path, int index) throws Exception {
		return (this.findXMLObjectByPath(path, index));
	}

	/**
	 * Gets content as a string using path to reach node.
	 * 
	 * @param path
	 *            is the xml path to the node i.e. 'root/node1/node2'
	 * @returns the value if node found or null if not found.
	 */
	public String getContent(String path) {
		XMLObject xo = this.findXMLObjectByPath(path);
		if (xo != null)
			return ((String) xo.getContent());
		return (null);
	}

	/**
	 * Gets content as a string using path and index to reach node.
	 * 
	 * @param path
	 *            is the xml path to the node i.e. 'root/node1/node2'
	 * @param key
	 *            is the attribute value we want from the node.
	 * @param index
	 *            is the node2 index as child of node1.
	 * @returns the value if node found or null if not found.
	 */
	public String getContent(String path, int index) {
		XMLObject xo = this.findXMLObjectByPath(path, index);
		if (xo != null)
			return ((String) xo.getContent());
		return (null);
	}

	/**
	 * Using a path such as 'root/node1/node2' will return an XMLObject for
	 * node2 if found else null.
	 * 
	 * @param path
	 *            is the xml path such as 'root/node1/node2'
	 * @return the XMLObject if found or null if not.
	 */
	public XMLObject findXMLObjectByPath(String path) {
		String[] paths = mapPaths(path);
		XMLObject xo = this;
		if (paths.length > 0) {
			if (this.elementName.equals(paths[0])) {
				xo = this;
				for (int iLoop = 1; iLoop < paths.length && xo != null; iLoop++) {
					xo = xo.findChildNode(paths[iLoop]);
				}
				return (xo);
			} else {
				return (null); // first element doesn't match'
			}
		}
		return (this);
	}

	/**
	 * Using a path such as 'root/node1/node2' will return the index count of
	 * node2. if index = 1 will return the first instance of node2 if index is 2
	 * will return the second instance of node2
	 * 
	 * @param path
	 *            is the xml path such as 'root/node1/node2'
	 * @param index
	 *            is the index into last node name of path
	 * @return the XMLObject if found or null if not.
	 */
	public XMLObject findXMLObjectByPath(String path, int index) {
		if (path == null || path.length() == 0)
			return (this);
		String[] paths = mapPaths(path);
		XMLObject xo = this;
		if (paths.length > 0) {
			if (this.elementName.equals(paths[0])) {
				xo = this;
				for (int iLoop = 1; iLoop < paths.length - 1 && xo != null; iLoop++) {
					xo = xo.findChildNode(paths[iLoop]);
				}
				if (xo != null) {
					if (paths.length == 1 && index == 0) {
						return xo;
					} else if (paths.length == 1 && index > 0) {
						return null;
					} else {
						// now get the indexed of child matching path[iLoop]
						for (int i = 0, i2 = 0; i < xo.getChildCount(); i++) {
							XMLObject xo1 = xo.getChild(i);
							if (xo1.elementName.equals(paths[paths.length - 1])) {
								if (i2 >= index) {
									return (xo1);
								}
								i2++;
							}
						}
					}
				}
				return (null);
			} else {
				return (null); // first element doesn't match'
			}
		}
		return (null);
	}

	private String[] mapPaths(String path) {
		List<String> paths = new ArrayList<String>();
		// path separator is '/'
		int index, lastPos = 0;
		while (true) {
			index = path.indexOf('/', lastPos);
			if (index < 0) {
				// finished
				paths.add(path.substring(lastPos));
				break;
			} else {
				paths.add(path.substring(lastPos, index));
			}
			lastPos = index + 1;
		}
		return ((String[]) paths.toArray(new String[0]));

	}

	/*
	 * = = = = = = = = = = = = = = = = = = = = =
	 * 
	 * Mapping XML to XMLObject and vice versa
	 * 
	 * = = = = = = = = = = = = = = = = = = = = =
	 */

	/**
	 * Map an XML String to a XMLObject
	 * 
	 * @deprecated Please now use mapXMLCharToXMLObject()
	 * @see mapXMLCharToXMLObject()
	 */
	public XMLObject mapXMLToXMLObject(String xml) throws Exception {
		XMLObject obj = null;
		// the following space is a kludge so we can very quickly detect the
		// root element name.
		XMLParser parser = new XMLParser((" " + xml).getBytes());
		MapXML2XMLObject mapped = new MapXML2XMLObject(parser, obj);
		obj = mapped.parent;
		while (obj.parent != null)
			obj = obj.parent;
		return (obj);
	}

	class MapXML2XMLObject extends XML2Class {
		XMLObject parent;

		MapXML2XMLObject(XMLParser parser, XMLObject parent) throws Exception {
			this.parent = parent;
			try {
				parse(parser);
			} catch (Exception ex) {
				throw ex;
				// Log.getInstance().error(ex);
			}
		}

		public void parseNode(String nodeName, XMLParser parser, boolean depth)
				throws Exception {
			String content = parser.getContentAsString();
			parser.reset();
			XMLObject obj = new XMLObject();
			obj.setElementName(nodeName);
			obj.addContent(content);
			obj.setAttributes(parser.getAttributes());

			if (parent == null)
				parent = obj;
			else
				parent.addChild(obj);

			new MapXML2XMLObject(parser, obj);
		}
	}

	/**
	 * Map an XML String to a XMLObject
	 */
	public XMLObject mapXMLCharToXMLObject(String xml) {
		XMLObject obj = null;
		// the following space is a kludge so we can very quickly detect the
		// root element name.
		XMLParserChar parser = new XMLParserChar((" " + xml).toCharArray());
		MapXMLChar2XMLObject mapped = new MapXMLChar2XMLObject(parser, obj);
		obj = mapped.parent;
		while (obj.parent != null)
			obj = obj.parent;
		return (obj);
	}

	/**
	 * Map an XML String to a XMLObject
	 * 
	 * @param xml
	 *            is the xml to map
	 * @param nameSpace
	 *            will only map elements with this namespace
	 */
	public XMLObject mapXMLCharToXMLObject(String xml, String nameSpace) {
		XMLObject obj = null;
		// the following space is a kludge so we can very quickly detect the
		// root element name.
		XMLParserChar parser = new XMLParserChar((" " + xml).toCharArray());
		MapXMLChar2XMLObject mapped = new MapXMLChar2XMLObject(parser, obj,
				nameSpace);
		obj = mapped.parent;
		while (obj.parent != null)
			obj = obj.parent;
		return (obj);
	}

	/**
	 * Map an XML String to a XMLObject
	 * 
	 * @param xml
	 *            is the xml to map
	 * @param nameSpaces
	 *            will only map elements with these namespaces
	 */
	public XMLObject mapXMLCharToXMLObject(String xml, char [][] nameSpaces) {
		XMLObject obj = null;
		// the following space is a kludge so we can very quickly detect the
		// root element name.
		XMLParserChar parser = new XMLParserChar((" " + xml).toCharArray());
		MapXMLChar2XMLObject mapped = new MapXMLChar2XMLObject(parser, obj, nameSpaces);
		obj = mapped.parent;
		while (obj.parent != null)
			obj = obj.parent;
		return (obj);
	}

	class MapXMLChar2XMLObject extends XML2ClassChar {
		XMLObject parent;
		String nameSpace;

		MapXMLChar2XMLObject(XMLParserChar parser, XMLObject parent) {
			this.parent = parent;
			parse(parser);
		}

		MapXMLChar2XMLObject(XMLParserChar parser, XMLObject parent,
				String nameSpace) {
			this.parent = parent;
			this.nameSpace = nameSpace;
			parse(parser, nameSpace);
		}

		MapXMLChar2XMLObject(XMLParserChar parser, XMLObject parent, char [][] nameSpaces) {
			this.parent = parent;
			this.nameSpace = nameSpace;
			parse(parser, nameSpaces);
		}

		public void parseNode(String nodeName, XMLParserChar parser,
				boolean depth) {
			String content = parser.getContentAsString();
			parser.reset();
			XMLObject obj = new XMLObject();
			int index = nodeName.indexOf(':');
			if (index > 0) {
				obj.setNameSpace(nodeName.substring(0, index));
				obj.setElementName(nodeName.substring(index + 1));
				// log.debug("ns:" + obj.getNameSpace() + " name:" +
				// obj.getElementName());
			} else {
				obj.setElementName(nodeName);
			}
			obj.addContent(content);
			obj.setAttributes(parser.getAttributes());

			if (parent == null)
				parent = obj;
			else
				parent.addChild(obj);

			if (nameSpace != null) {
				new MapXMLChar2XMLObject(parser, obj, nameSpace);
			} else {
				new MapXMLChar2XMLObject(parser, obj);
			}
		}

	}

	/**
	 * Map an XMLObject to an XMLString
	 */
	public String mapXMLObject2XML(XMLObject root) {
		XMLBuilder builder = new XMLBuilder(false);
		xmlAllNodes(root, builder, 0, false);
		return (builder.toString());
	}

	/**
	 * Map an XMLObject to an XMLString
	 */
	public String mapXMLObject2XML(XMLObject root, boolean lineFeed) {
		XMLBuilder builder = new XMLBuilder(false);
		xmlAllNodes(root, builder, 0, lineFeed);
		return (builder.toString());
	}

	public void xmlAllNodes(XMLObject node, XMLBuilder builder, int indent,
			boolean lineFeed) {
		if (lineFeed == true) {
			builder.addLineFeed();
			builder.append(node.toXMLString(indent,
					node.getChildCount() == 0 ? true : false));
		} else
			builder.append(node.toXMLString(0, node.getChildCount() == 0 ? true
					: false));
		for (int iLoop = 0; iLoop < node.getChildCount(); iLoop++) {
			xmlAllNodes(node.getChild(iLoop), builder, indent + 2, lineFeed);
		}

		if (node.getChildCount() > 0) {
			if (lineFeed == true) {
				builder.addLineFeed();
				builder.indent(indent);
			}
			builder.append("</" + node.getName() + ">");
		}
	}

	/*
	 * = = = = = = = = = = = = = = = =
	 * 
	 * toString STUFF
	 * 
	 * = = = = = = = = = = = = = = = =
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(getElementName());
		for (int iLoop = 0; iLoop < attributes.size(); iLoop++) {
			XMLAttribute att = (XMLAttribute) attributes.get(iLoop);
			sb.append("\n " + att.key + "=\"" + att.value + "\"");
		}
		//
		String content = getContent();
		if (content != null) {
			content = content.trim();
			if (content != null && content.length() > 0) {
				sb.append("\n'" + content + "'");
			}
		}
		return (sb.toString());
	}

	public String toHTMLString() {
		StringBuffer sb = new StringBuffer();
		sb.append("<html>");
		sb.append("<head>");
		sb.append("<style type=\"text/css\">");
		sb.append("h3,.h3");
		sb.append("{");
		sb.append("  color: #ff9900;");
		sb.append("  font-family: Verdana, Arial, Helvetica, sans-serif;");
		sb.append("  margin: 0;");
		sb.append("  font-size: 8px;");
		sb.append("  font-weight: bold;");
		sb.append("  font-variant: small-caps;");
		sb.append("}");
		sb.append(".body");
		sb.append("{");
		sb.append("  background-color: #e7e7fb;");
		sb.append("  color: #636531;");
		sb.append("  font-family: Verdana, Arial, Helvetica, sans-serif;");
		sb.append("  margin: 0;");
		sb.append("  font-size: 9px;");
		sb.append("}");
		sb.append("p,.p");
		sb.append("{");
		sb.append("  color: #080b84;");
		sb.append("  font-family: Verdana, Arial, Helvetica, sans-serif;");
		sb.append("  margin: 4;");
		sb.append("  font-size: 10px;");
		sb.append("  text-indent: 0;");
		sb.append("}");
		sb.append(".content");
		sb.append("{");
		sb.append("  color: #feac00;");
		// sb.append("  color: #ff9900;");
		sb.append("  font-family: Verdana, Arial, Helvetica, sans-serif;");
		sb.append("  margin: 0;");
		sb.append("  font-size:10px;");
		sb.append("}");
		sb.append("legend,.legend");
		sb.append("{");
		sb.append("  color: #feac00;");
		sb.append("  font-family: Verdana, Arial, Helvetica, sans-serif;");
		sb.append("  margin: 4;");
		sb.append("  font-weight: bold;");
		sb.append("  font-size:10px;");
		sb.append("  font-variant: small-caps ");
		sb.append("}");
		sb.append(".legendtext");
		sb.append("{");
		sb.append("  color: #080b84;");
		sb.append("  font-family: Verdana, Arial, Helvetica, sans-serif;");
		sb.append("  margin: 1;");
		sb.append("  font-size:10px;");
		sb.append("}");
		sb.append("</style>");
		sb.append("</head>");
		sb.append("   <body class=\"body\">");
		sb.append("<table border=\"1\">");
		sb.append("<tr><td class=\"legend\" align=\"center\" colspan=\"2\"><b>"
				+ getElementName() + "</b></td></tr>");
		// sb.append("<p>" + getElementName() + "</b><br>");
		for (int iLoop = 0; iLoop < attributes.size(); iLoop++) {
			XMLAttribute att = (XMLAttribute) attributes.get(iLoop);
			sb.append("<tr><td class=\"legend\" align=\"right\"><b>" + att.key
					+ "</b></td><td class=\"content\">\"" + att.value
					+ "\"</td></tr>");
		}
		sb.append("</table>");
		String content = getContent();
		if (getContent() != null && getContent().trim().length() > 0) {
			sb.append("<p>");
			sb.append("<hr>");
			sb.append(content.trim().replace("\n", "<br>"));
			sb.append("<p>");
		}
		sb.append("</html>");
		return (sb.toString());
	}

	public String mapXMLObject2HTML() {
		StringBuffer sb = new StringBuffer();
		sb.append("<html>");
		sb.append("<head>");
		sb.append("<style type=\"text/css\">");
		sb.append("h3,.h3");
		sb.append("{");
		sb.append("  color: #ff9900;");
		sb.append("  font-family: Verdana, Arial, Helvetica, sans-serif;");
		sb.append("  margin: 0;");
		sb.append("  font-size: 8px;");
		sb.append("  font-weight: bold;");
		sb.append("  font-variant: small-caps;");
		sb.append("}");
		sb.append(".body");
		sb.append("{");
		sb.append("  background-color: #e7e7fb;");
		sb.append("  color: #636531;");
		sb.append("  font-family: Verdana, Arial, Helvetica, sans-serif;");
		sb.append("  margin: 0;");
		sb.append("  font-size: 9px;");
		sb.append("}");
		sb.append("p,.p");
		sb.append("{");
		sb.append("  color: #080b84;");
		sb.append("  font-family: Verdana, Arial, Helvetica, sans-serif;");
		sb.append("  margin: 4;");
		sb.append("  font-size: 10px;");
		sb.append("  text-indent: 0;");
		sb.append("}");
		sb.append(".content");
		sb.append("{");
		sb.append("  color: #feac00;");
		// sb.append("  color: #ff9900;");
		sb.append("  font-family: Verdana, Arial, Helvetica, sans-serif;");
		sb.append("  margin: 0;");
		sb.append("  font-size:10px;");
		sb.append("}");
		sb.append("legend,.legend");
		sb.append("{");
		sb.append("  color: #feac00;");
		sb.append("  font-family: Verdana, Arial, Helvetica, sans-serif;");
		sb.append("  margin: 4;");
		sb.append("  font-weight: bold;");
		sb.append("  font-size:10px;");
		sb.append("  font-variant: small-caps ");
		sb.append("}");
		sb.append(".legendtext");
		sb.append("{");
		sb.append("  color: #080b84;");
		sb.append("  font-family: Verdana, Arial, Helvetica, sans-serif;");
		sb.append("  margin: 1;");
		sb.append("  font-size:10px;");
		sb.append("}");
		sb.append("</style>");
		sb.append("</head>");
		sb.append("   <body class=\"body\">");
		sb.append("<table border=\"1\">");
		sb.append(mapNode2HTML(this, 0));
		sb.append("</table>");
		sb.append("</html>");
		return (sb.toString());
	}

	private String mapNode2HTML(XMLObject xo, int indent) {
		StringBuffer sb = new StringBuffer();
		sb
				.append("<tr><td class=\"legend\" align=\"center\" colspan=\"3\"><b>");
		for (int iLoop = 0; iLoop < indent; iLoop++) {
			sb.append("&nbsp;&nbsp;");
		}
		sb.append(xo.getElementName() + "</b></td></tr>");
		// sb.append("<p>" + getElementName() + "</b><br>");
		for (int iLoop = 0; iLoop < xo.attributes.size(); iLoop++) {
			XMLAttribute att = (XMLAttribute) xo.attributes.get(iLoop);
			sb.append("<tr><td class=\"legend\" align=\"right\"><b>" + att.key
					+ "</b></td><td class=\"content\">\"" + att.value
					+ "\"</td></tr>");
		}
		String content = xo.getContent();
		if (getContent() != null && getContent().trim().length() > 0) {
			sb.append("<tr><td class=\"legend\" align=\"center\"><p>"
					+ content.trim().replace("\n", "<br>")
					+ "</p></td></tr>");
		}
		for (int iLoop = 0; iLoop < xo.getChildCount(); iLoop++) {
			sb.append(mapNode2HTML(xo.getChild(iLoop), indent + 1));
		}
		return (sb.toString());
	}

	/**
	 * Converts the xml object into an XML String
	 * 
	 * @param closeElement
	 *            if set true will terminate the node using either '/&gt;' or
	 *            '&lt;/elementName&gt;'. if set false will close the node with
	 *            '&gt;' provided no content has been added.
	 */
	public String toXMLString(boolean closeElement) {
		return (toXMLString(0, closeElement));
	}

	/**
	 * Converts the xml object into an XML String
	 * 
	 * @param indent
	 *            is the indenting spaces to add at the beginning of the xml
	 * @param closeElement
	 *            if set true will terminate the node using either '/&gt;' or
	 *            '&lt;/elementName&gt;'. if set false will close the node with
	 *            '&gt;' provided no content has been added.
	 */
	public String toXMLString(int indent, boolean closeElement) {
		XMLBuilder builder = new XMLBuilder(false);
		builder.indent(indent);
		builder.addOpenElement(getName());
		for (int iLoop = 0; iLoop < attributes.size(); iLoop++) {
			XMLAttribute att = (XMLAttribute) attributes.get(iLoop);
			builder.addAttribute(att.key, (String) att.value);
		}

		// we now have an open element with maybe some attributes.
		String content = getContent();
		if (content != null) {
			content = content.trim();
			if (content != null && content.length() > 0) {
				builder.closeOpenElement();
				builder.addContent(content);
				if (closeElement == true)
					builder.closeElement(getName());
			} else {
				if (closeElement == true)
					builder.closeElement();
				else
					builder.closeOpenElement();
			}
		} else {
			if (closeElement == true)
				builder.closeElement();
			else
				builder.closeOpenElement();
		}
		return (builder.toString());
	}

	public static void main(String[] args) throws Exception {

		XMLObject xo = new XMLObject();
		xo = xo.mapXMLToXMLObject(xo.buildTestXML());
		log.debug(JS.getCurrentMethodName_static() + " xml:\n"
				+ xo.mapXMLObject2XML(xo, true));
	}

	private String buildTestXML() {
		String xml1 = "<?xml version=\"1.0\" encoding=\"iso-8859-1\"?>"
				+ "<reservationlist xmlns:xsi=\"http://www.w3.org/1999/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"Visanet Back-Office v3.xsd\">"
				+ "   <reservation agencycontrol=\"\" extid=\"P8\" extrecloc=\"KSHZW1\" gds=\"GX\" pcc=\"\" recloc=\"KSHZW2\" >"
				+ "      <ticket altamount=\"\" altcurrency=\"\" currency=\"BRL\""
				+ "         issuedate=\"2006-11-03\" number=\"90042021005535\""
				+ "         ticketamount=\"898.00\" transactiontype=\"N\" triptype=\"D\">"
				+ "         <passenger name=\"Romerio Ferreira\"/>"
				+ "         <creditcard altamount=\"\" altcurrency=\"\" amount=\"893.28\""
				+ "            auth=\"240146\" authdate=\"2006-11-03\" code=\"VI\" currency=\"BRL\""
				+ "            exp=\"05/09\" isverifiedonline=\"false\" number=\"4695719000002375\"/>"
				+ "         <tax altamount=\"\" altcurrency=\"\" amount=\"31.20\" code=\"EV\" currency=\"BRL\"/>"
				+ "         <segment arrivaldate=\"2006-11-07\" arrivaltime=\"14:19\""
				+ "            carriercode=\"P8\" class=\"O\" departurecitycode=\"CGH\""
				+ "            departuredate=\"2006-11-07\" departuretime=\"12:26\""
				+ "            destinationcitycode=\"PPB\" number=\"1\"/>"
				+ "         <segment arrivaldate=\"2006-11-10\" arrivaltime=\"16:43\""
				+ "            carriercode=\"P8\" class=\"O\" departurecitycode=\"PPB\""
				+ "            departuredate=\"2006-11-10\" departuretime=\"14:55\""
				+ "            destinationcitycode=\"CGH\" number=\"2\"/>"
				+ "      </ticket>"
				+ "      <mis code=\"CostCenter\" value=\"22061151\"/>"
				+ "      <mis code=\"ServiceOrder\" value=\"\"/>"
				+ "      <mis code=\"AgencyControl\" value=\"\"/>"
				+ "      <mis code=\"Department\" value=\"\"/>"
				+ "      <mis code=\"CompanyAuthorization\" value=\"\"/>"
				+ "      <mis code=\"EmployeeNumber\" value=\"\"/>"
				+ "      <mis code=\"DiscountAmount\" value=\"35.92\"/>"
				+ "      <mis code=\"ChargedAmount\" value=\"893.28\"/>"
				+ "      <mis code=\"FreeText\" value=\"1-1-48380\"/>"
				+ "   </reservation>" + "</reservationlist>";

		String xml2 = "      <Call name = \"Check if open cursors reached limit\""
				+ "					 factory = \"System\""
				+ "					 process = \"Math\""
				+ "					 call = \"Integer Greater Than Or Equal\">"
				+ "      <Param name = \"int 1\" value = \"${open_cursor_index}\"/>"
				+ "      <Param name = \"int 2\" value = \"${DB_MaxOpenCursors}\"/>"
				+ "      <Response name = \"result\">"
				+ "        <Fail name = \"if int1 gteq int2 returns true\" response = \"true\" action = \"\">"
				+ "			    <!--================================================="
				+ "					    RESET THE DATABASE CONNECTION"
				+ " 			=================================================-->"
				+ "			    <Call	name = \"Reset DB Connection\""
				+ "								factory = \"Redfin CBA\" "
				+ "								process = \"Redfin CBA Local DB Processing\""
				+ "								call = \"Reset DB Connection\">"
				+ "			      <description>Reset an open database connection in DataCapture Class - DB Class</description>"
				+ "			      <Param name = \"DataCaptureClass\"/>"
				+ "			      <Response name = \"Exception\">"
				+ "			        <Fail name = \"exception thrown\" response = \"exception\" action = \"terminate all processes\">"
				+ "			          <Call name = \"fail\" factory = \"System\" process = \"Log\" call = \"log and wait\">"
				+ "			            <Param name = \"Error Notification\" value = \"${ProcessName} Failed to reset DBConnection\"/>"
				+ "			            <Param name = \"Show Error and Wait\" value = \"10000\"/>"
				+ "			          </Call>"
				+ "			        </Fail>"
				+ "			      </Response>"
				+ "			    </Call>"
				+ "			    <Call name = \"Reset Open Cursor Index\" factory = \"System\" process = \"Math\" call = \"Make Integer\">"
				+ "						<Param name = \"initial value\" value = \"-1\"/>"
				+ "			      <Response name = \"open_cursor_index\"/>"
				+ "			    </Call>"
				+ "				</Fail>"
				+ "			</Response>"
				+ "		</Call>";
		return (xml2);
	}

}
