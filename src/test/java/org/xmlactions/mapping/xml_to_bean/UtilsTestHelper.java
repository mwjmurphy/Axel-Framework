package org.xmlactions.mapping.xml_to_bean;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.xmlactions.action.config.IExecContext;
import org.xmlactions.action.config.NoPersistenceExecContext;
import org.xmlactions.common.io.ResourceUtils;



/**
 * Static methods for testing
 * 
 * @author mike
 * 
 */
public class UtilsTestHelper
{

	private static IExecContext execContext;

	/**
	 * Creates an IExecContext thats populated from
	 * /config/db/actions_db.properties
	 * 
	 * @return
	 * @throws IOException
	 */
	public static IExecContext getExecContext() throws IOException
	{

		if (execContext == null) {
			Properties props = new Properties();
			props.load(ResourceUtils.getResourceURL("/config/xml_to_bean.properties").openStream());
			List list = new ArrayList();
			list.add(props);
			execContext = new NoPersistenceExecContext(list,null);
		}
		return execContext;
	}

}
