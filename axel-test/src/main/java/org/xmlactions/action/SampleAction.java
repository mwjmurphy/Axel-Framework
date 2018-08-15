package org.xmlactions.action;

import org.xmlactions.action.actions.BaseAction;
import org.xmlactions.action.config.IExecContext;

public class SampleAction extends BaseAction {

	private String name;
	
	@Override
	public String execute(IExecContext execContext) throws Exception {
		if (getName() == null) {
			return "no name attribute set";
		} else {
			return "the name attribute is [" + getName() + "]";
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	

}
