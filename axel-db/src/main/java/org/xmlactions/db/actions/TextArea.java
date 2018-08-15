
package org.xmlactions.db.actions;


import org.apache.commons.lang.StringUtils;
import org.xmlactions.action.config.IExecContext;


public class TextArea extends CommonStorageField
{

	private boolean want_html_edit;


	public boolean isWant_html_edit()
	{

		return want_html_edit;
	}

	public void setWant_html_edit(boolean wantHtmlEdit)
	{

		want_html_edit = wantHtmlEdit;
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
		sb.append("TextArea:" + getName());
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
