
package org.xmlactions.pager.actions;


import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.text.StrSubstitutor;
import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.xmlactions.action.Action;
import org.xmlactions.action.ReplacementHandlerAction;
import org.xmlactions.action.actions.BaseAction;
import org.xmlactions.action.config.IExecContext;

public class RemoveAction extends BaseAction implements ReplacementHandlerAction
{

	private static Logger log = LoggerFactory.getLogger(RemoveAction.class);

	private String key;

	public String execute(IExecContext execContext) throws Exception
	{
		execContext.remove(getKey());
		return "";
	}

	public String toString()
	{

		return "rewmove [" + getKey() + "]";
	}

	public void setKey(String key)
	{

		this.key = key;
	}

	public String getKey()
	{

		return key;
	}

	public Object getReplacementData(IExecContext execContext, Object innerContent) {
		if (StringUtils.isEmpty((String)innerContent)) {
			execContext.put(getKey(), execContext.replace(getContent()));
		} else {
			execContext.put(getKey(), innerContent);
		}
		return null;
	}
}
