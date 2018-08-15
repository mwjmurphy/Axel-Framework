package org.xmlactions.pager.actions.form.templates;

import org.xmlactions.common.theme.Theme;
import org.xmlactions.pager.actions.form.ThemeConst;




public class HtmlInput extends HtmlEvents
{	

	public HtmlInput()
	{
		super(HtmlEnum.label_input.getAttributeName());
	}

	public HtmlInput(Theme theme)
	{
		super(HtmlEnum.label_input.getAttributeName());
		setClazz(theme.getValue(ThemeConst.INPUT_TEXT.toString()));
	}

	public void setAccept(String value)
	{

		put(HtmlEnum.accept.getAttributeName(), value);
	}

	public void setAlign(String value)
	{

		put(HtmlEnum.align.getAttributeName(), value);
	}

	public void setAlt(String value)
	{

		put(HtmlEnum.alt.getAttributeName(), value);
	}

	public void setChecked(String value)
	{

		put(HtmlEnum.checked.getAttributeName(), value);
	}

	public void setMaxlength(String value)
	{

		put(HtmlEnum.maxlength.getAttributeName(), value);
	}

	public void setName(String value)
	{

		put(HtmlEnum.name.getAttributeName(), value);
	}

	public void setReadonly(String value)
	{

		put(HtmlEnum.readonly.getAttributeName(), value);
	}

	public void setSize(String value)
	{

		put(HtmlEnum.size.getAttributeName(), value);
	}

	public void setSrc(String value)
	{

		put(HtmlEnum.src.getAttributeName(), value);
	}

	public void setType(String value)
	{

		put(HtmlEnum.type.getAttributeName(), value);
	}

	public void setValue(String value)
	{
		put(HtmlEnum.value.getAttributeName(), value);
	}


}
