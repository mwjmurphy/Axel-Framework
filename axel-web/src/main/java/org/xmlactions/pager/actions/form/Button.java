
package org.xmlactions.pager.actions.form;


import org.apache.commons.lang.StringUtils;
import org.xmlactions.action.actions.BaseAction;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.common.theme.Theme;
import org.xmlactions.pager.actions.form.templates.HtmlInput;


/**
 * Container class for html buttons.
 * 
 * @author mike
 * 
 */
public class Button extends BaseAction implements IDraw
{

	private String name; // the display name for the button

	private String uri; // where the form goes to, not mandatory

	// private boolean submit; // if set to true then this link is used to
	// perform

	// private String actionScript; // this will get set by the class servicing
	// the

	public String execute(IExecContext execContext)
	{

		// nop
		return null;
	}

	public HtmlInput draw(IExecContext execContext, Theme theme)
	{

		HtmlInput input = new HtmlInput();

		input.setClazz(theme.getValue(ThemeConst.INPUT_BUTTON.toString()));
		input.setType("button");
		input.setContent(getName());
		input.setValue(getName());

		if (!StringUtils.isEmpty(getUri())) {
			String formId = BaseFormAction.findFormId(this);
			input.setOnClick("captureInputsToForm('" + formId + "');" + "submitForm('" + formId + "','" + getUri()
					+ "');return false;");
		}
		
		return input;
		
		/*

		HtmlTd td = new HtmlTd();
		td.setClazz(theme.getThemeForClass(ThemeConst.TD.toString()));
		
		HtmlInput input = new HtmlInput();
		td.addChild(input);

		input.setClazz(theme.getThemeForClass(ThemeConst.INPUT_BUTTON.toString()));

		if (!StringUtils.isEmpty(getUri())) {
			String formId = BaseFormAction.findFormId(this);
			input.setOnClick("captureInputsToForm('" + formId + "');" + "submitForm('" + formId + "','"
					+ getUri() + "');return false;\"");
		}		

		String onclick = "";
		if (!StringUtils.isEmpty(getUri())) {
			String formId = BaseFormAction.findFormId(this);
			onclick = "onclick=\"captureInputsToForm('" + formId + "');" + "submitForm('" + formId + "','" + getUri()
					+ "');return false;\"";
		}
		return "<td " + theme.getTheme(ThemeConst.TD.toString()) + ">" + "<input " + onclick + " " + attributes + " "
				+ theme.getTheme(ThemeConst.INPUT_BUTTON.toString()) + " type=\"submit\" value=\"" + getName()
				+ "\"/></td>";
		*/
	}

	public String getName()
	{

		return name;
	}

	public void setName(String name)
	{

		this.name = name;
	}

	public void setUri(String uri)
	{

		this.uri = uri;
	}

	public String getUri()
	{

		return uri;
	}
}
