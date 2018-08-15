
package org.xmlactions.pager.actions;


import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.xmlactions.action.actions.BaseAction;
import org.xmlactions.action.config.IExecContext;

public class GetAction extends BaseAction
{

	private static Logger log = LoggerFactory.getLogger(GetAction.class);

	private String key;

	public String execute(IExecContext execContext) throws Exception
	{
		return "" + execContext.get(getKey());
	}

	public String toString()
	{

		return "get [" + getKey() + "]";
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
		return execContext.get(getKey());
	}
}
