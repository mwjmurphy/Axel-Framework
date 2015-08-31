
package org.xmlactions.action.actions;


import java.util.Map;

import org.xmlactions.action.actions.BaseAction;
import org.xmlactions.action.config.IExecContext;



public class Param extends BaseAction
{

	private String value;

	public String getValue()
	{

		return value;
	}

	public void setValue(String value)
	{

		this.value = value;
	}

	public Object getResolvedValue(Map<String, Object> context)
	{

		// StrSubstitutor ss = new StrSubstitutor();
		Object obj = context.get(getValue());
		if (obj == null) {
			return getValue();
		}
		return obj;
	}

	public String execute(IExecContext context) throws Exception
	{

		return null;
	}

	public String toString()
	{

		return value;
	}

}
