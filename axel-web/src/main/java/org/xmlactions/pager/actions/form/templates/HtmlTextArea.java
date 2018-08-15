package org.xmlactions.pager.actions.form.templates;

import org.xmlactions.common.theme.Theme;
import org.xmlactions.pager.actions.form.ThemeConst;




public class HtmlTextArea extends Html
{

	public HtmlTextArea()
	{
		super(HtmlEnum.label_textarea.getAttributeName());
	}

	public HtmlTextArea(Theme theme)
	{
		super(HtmlEnum.label_textarea.getAttributeName());
		setClazz(theme.getValue(ThemeConst.INPUT_TEXTAREA.toString()));
	}

	public void setName(String value)
	{
		put(HtmlEnum.name.getAttributeName(), value);
	}

	 


	/** Specifies the height of the textarea based on the number of visible lines of text. If there's more text than this allows, users can scroll using the textarea's scrollbars. */
	public void setRows(String value)
	{
		put(HtmlEnum.rows.getAttributeName(), value);
	}

	 


	/** Specifies the width of the textarea based on the number of visible character widths.  */
	public void setCols(String value)
	{
		put(HtmlEnum.cols.getAttributeName(), value);
	}


}
