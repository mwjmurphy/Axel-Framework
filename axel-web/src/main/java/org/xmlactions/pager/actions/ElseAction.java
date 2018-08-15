
package org.xmlactions.pager.actions;


import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.xmlactions.action.actions.BaseAction;
import org.xmlactions.action.config.IExecContext;

public class ElseAction extends BaseAction
{

	private static Logger log = LoggerFactory.getLogger(ElseAction.class);

	public String execute(IExecContext execContext) throws Exception
	{

		for (BaseAction action : getActions()) {
			processAction(action.getContent(), action, execContext);
		}
		return null;
	}

	public String toString()
	{

		StringBuilder sb = new StringBuilder();
		for (Object action : getActions()) {
			sb.append("\n" + this.getClass().getName() + " action:" + action.getClass().getName());
			sb.append("\n - " + action.toString());
		}
		return sb.toString();
	}

}
