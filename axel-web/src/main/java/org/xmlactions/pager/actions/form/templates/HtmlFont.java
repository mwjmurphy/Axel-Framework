package org.xmlactions.pager.actions.form.templates;


@SuppressWarnings("serial")
public class HtmlFont extends Html
{

	public HtmlFont()
	{

		super(HtmlEnum.label_font.getAttributeName());
	}

	/** Specifies the font size. */
	public void setSize(String value) {

		put(HtmlEnum.size.getAttributeName(), value);
	}

	/** Specifies the font color. */
	public void setColor(String value) {

		put(HtmlEnum.color.getAttributeName(), value);
	}

	/** Specifies the font face. */
	public void setFace(String value) {

		put(HtmlEnum.face.getAttributeName(), value);
	}


}
