package org.xmlactions.pager.actions.form.templates;


@SuppressWarnings("serial")
public class HtmlButton extends Html
{

	public HtmlButton()
	{

		super(HtmlEnum.label_button.getAttributeName());
	}
	/** Assigns the name of the button control. */
	public void setName(String value)
	{
		put(HtmlEnum.name.getAttributeName(), value);
	}

	 

	/** Assigns an initial value to the button. */
	public void setValue(String value)
	{
		put(HtmlEnum.value.getAttributeName(), value);
	}

	 

	/**
	 * Specifies the type of button. Possible values:
	 *  - submit (default)
	 *  - reset
	 *  - button
	 */
	public void setType(String value) {
           put(HtmlEnum.type.getAttributeName(), value);
	}

	 


}
