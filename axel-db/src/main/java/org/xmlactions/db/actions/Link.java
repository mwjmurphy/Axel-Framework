
package org.xmlactions.db.actions;


import org.apache.commons.lang.StringUtils;
import org.xmlactions.action.config.IExecContext;


public class Link extends CommonStorageField
{

	public String execute(IExecContext execContext) throws Exception
	{

		// TODO Auto-generated method stub
		return null;
	}

	public String toString(int indent)
	{

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < indent; i++) {
			sb.append(' ');
		}
		sb.append(toString());
		return sb.toString();
	}

	public String toString()
	{

		return ("Link:" + getName());
	}

	public String validate(String value)
	{

		String error = null;
		if (StringUtils.isEmpty(value) && this.isMandatory()) {
			error = "Missing Value";
		}
		return buildErrorString(error);
	}
}
