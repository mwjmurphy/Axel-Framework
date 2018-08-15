
package org.xmlactions.pager.actions;


import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.xmlactions.action.actions.ActionUtils;
import org.xmlactions.action.actions.BaseAction;
import org.xmlactions.action.config.IExecContext;

public class IfAction extends BaseAction
{

	private static Logger log = LoggerFactory.getLogger(IfAction.class);

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

		// log.debug("expression:" + expression);
		if (ActionUtils.evaluateExpression(execContext, expression)) {
			setReplacementContent(getContent());
			for (BaseAction action : getActions()) {
				if (action instanceof ElseIfAction || action instanceof ElseAction) {
                    // log.debug("skipping elseif / else:" + action.toString());
					action.setReplacementContent("");
				} else {
                    // log.debug("execute:" + action.toString());
					processAction(action.getContent(), action, execContext);
				}
			}
		} else if (getActions().size() > 0){
			// see if we have any elseif or else
            setReplacementContent("");
            // log.debug("look for elseif and else");
			for (BaseAction action : getActions()) {
				if (action instanceof ElseIfAction) {
					if (ActionUtils.evaluateExpression(execContext, ((ElseIfAction) action).getExpression())) {
                        // log.debug("execute elseif:" + action.toString());
						action.setReplacementContent(action.getContent());
						processAction(action.getContent(), action, execContext);
						break;
					}
				} else if (action instanceof ElseAction) {
                    // log.debug("execute else:" + action.toString());
					action.setReplacementContent(action.getContent());
					processAction(action.getContent(), action, execContext);
					break;
				}
			}
		} else {
			return "";
		}
		return null;
	}

	public String toString()
	{

		StringBuilder sb = new StringBuilder();
		sb.append("\nif[" + getExpression() + "]");
		for (Object action : getActions()) {
			sb.append("\naction:" + action.getClass().getName());
			sb.append("\n - " + action.toString());
		}
		return sb.toString();
	}

}
