
package org.xmlactions.action.actions;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmlactions.action.actions.BaseAction;
import org.xmlactions.action.config.IExecContext;


public class Echo extends BaseAction
{

	private final static Logger log = LoggerFactory.getLogger(Echo.class);

	public String execute(IExecContext execContext) throws Exception
	{

		return getContent();
	}
}
