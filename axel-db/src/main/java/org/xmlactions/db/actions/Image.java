
package org.xmlactions.db.actions;


import org.apache.commons.lang.StringUtils;
import org.xmlactions.action.config.IExecContext;


public class Image extends CommonStorageField
{

	private int max_width;

	private int max_height;

	public int getMax_width()
	{

		return max_width;
	}

	public void setMax_width(int maxWidth)
	{

		max_width = maxWidth;
	}

	public int getMax_height()
	{

		return max_height;
	}

	public void setMax_height(int maxHeight)
	{

		max_height = maxHeight;
	}

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
		sb.append("IMAGE:" + getName());
		return sb.toString();
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
