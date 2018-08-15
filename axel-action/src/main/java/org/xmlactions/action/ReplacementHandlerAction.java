package org.xmlactions.action;

import org.xmlactions.action.config.IExecContext;

public interface ReplacementHandlerAction {
	
	public Object getReplacementData(IExecContext execContext, Object innerContent);

}
