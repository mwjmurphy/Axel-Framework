
package org.xmlactions.db.actions;


import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.xmlactions.action.actions.BaseAction;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.common.io.ResourceUtils;
import org.xmlactions.common.xml.XMLObject;


public class Xml extends BaseAction
{

	private final static Logger log = LoggerFactory.getLogger(Xml.class);

	private XMLObject xml;

	private long lastLoaded;

	private String name;

	private String resource;

	public String execute(IExecContext execContext) throws Exception
	{

		// TODO Auto-generated method stub
		return null;
	}


	public void setName(String name)
	{

		this.name = name;
	}

	public String getName()
	{

		return name;
	}


	public void setLastLoaded(long lastLoaded)
	{

		this.lastLoaded = lastLoaded;
	}


	public long getLastLoaded()
	{

		return lastLoaded;
	}


	public void setXml(XMLObject xml)
	{

		this.xml = xml;
	}


	public XMLObject getXml()
	{

		if (xml == null) {
			xml = loadXml();
		}
		return xml;
	}

	private XMLObject loadXml() {
		XMLObject xo = new XMLObject();
		try {
			String xml = ResourceUtils.loadFile(getResource());
			// FIXME - set the datatime of loaded resource
			return xo.mapXMLCharToXMLObject(xml);
		} catch (Exception ex) {
			throw new IllegalArgumentException(ex.getMessage(), ex);
		}
	}


	public void setResource(String resource)
	{

		this.resource = resource;
	}


	public String getResource()
	{

		return resource;
	}


}
