package org.xmlactions.mapping.xml_to_bean;


import java.util.List;


import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.xmlactions.action.actions.BaseAction;
import org.xmlactions.action.config.IExecContext;

public class PropertyAlias extends BaseAction
{

	private static Logger log = LoggerFactory.getLogger(PropertyAlias.class);
	
	private String name;

	public String execute(IExecContext execContext) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}


	public PropertyAlias getPropertyAlias(List<PropertyAlias> list, String name) {
		for (PropertyAlias propertyAlias : list) {
			if (name.equals(propertyAlias.getName())) {
				return propertyAlias;
			}
		}
		return null;
	}
}
