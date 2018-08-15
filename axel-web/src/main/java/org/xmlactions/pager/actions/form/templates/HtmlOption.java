package org.xmlactions.pager.actions.form.templates;


public class HtmlOption extends Html
{

	public HtmlOption()
	{

		super(HtmlEnum.label_option.getAttributeName());
	}

	/** Specifies that this option will be pre-selected when the user first loads the page. */
	public void setSelected(String value)
	{
		put(HtmlEnum.selected.getAttributeName(), value);
	}

	/** Specifies the initial value of the option item. */
	public void setValue(String value) {
          put(HtmlEnum.value.getAttributeName(), value);
	}

	/** Specifies a label to be used as an alternative to the option item's contents. Useful if you'd prefer a shorter, more concise label. */
	public void setLabel(String value) {
            put(HtmlEnum.label.getAttributeName(), value);
	}


}
