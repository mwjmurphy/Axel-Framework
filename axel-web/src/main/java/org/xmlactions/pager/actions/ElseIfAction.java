
package org.xmlactions.pager.actions;


import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.xmlactions.action.actions.ActionUtils;
import org.xmlactions.action.actions.BaseAction;
import org.xmlactions.action.config.IExecContext;

public class ElseIfAction extends BaseAction
{

	private static Logger log = LoggerFactory.getLogger(ElseIfAction.class);

	private String expression;

	public String getExpression()
	{

		return expression;
	}

	public void setExpression(String expression)
	{

		this.expression = expression;
	}

	public String execute(IExecContext execContext) throws Exception
	{

		if (ActionUtils.evaluateExpression(execContext, expression)) {
			for (BaseAction action : getActions()) {
				processAction(action.getContent(), action, execContext);
			}
		}
		return null;
	}

	public String toString()
	{

		StringBuilder sb = new StringBuilder();
		sb.append("\nelseif[" + getExpression() + "]");
		for (Object action : getActions()) {
			sb.append("\naction:" + action.getClass().getName());
			sb.append("\n - " + action.toString());
		}
		return sb.toString();
	}

}
