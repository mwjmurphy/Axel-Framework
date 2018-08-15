/*
 * XMLAttribute.java
 *
 * Created on August 30, 2005, 3:27 PM
 *
 * To change this template, choose Tools | Options and locate the template under
 * the Source Creation and Management node. Right-click the template and choose
 * Open. You can then make changes to the template in the Source Editor.
 */

package org.xmlactions.common.xml;

/**
 *
 * @author Mike
 */
public class XMLAttribute
{
	String key;
	Object value;
	
	XMLAttribute(String key, Object value)
	{
		this.key = key;
		this.value = value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public Object getValue()
	{
		return(value);
	}

	public String getValueAsString()
	{
		return((String)value);
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getKey()
	{
		return(key);
	}


}
