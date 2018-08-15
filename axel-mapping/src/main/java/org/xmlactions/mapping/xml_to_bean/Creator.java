package org.xmlactions.mapping.xml_to_bean;


import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.xmlactions.action.actions.BaseAction;
import org.xmlactions.action.config.IExecContext;

public class Creator extends BaseAction
{

	private static Logger log = LoggerFactory.getLogger(Creator.class);
	
	private String element;

	public String execute(IExecContext execContext) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public void setElement(String element) {
		this.element = element;
	}

	public String getElement() {
		return element;
	}
	
}
