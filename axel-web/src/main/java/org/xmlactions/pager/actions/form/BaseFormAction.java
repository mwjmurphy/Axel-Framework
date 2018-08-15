
package org.xmlactions.pager.actions.form;


import java.util.ArrayList;
import java.util.List;


import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.xmlactions.action.actions.BaseAction;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.common.theme.Theme;
import org.xmlactions.pager.actions.form.templates.Html;
import org.xmlactions.pager.actions.form.templates.HtmlA;
import org.xmlactions.pager.actions.form.templates.HtmlInput;


/**
 * Contains fields and links which are a common base requirement for all form
 * actions
 * 
 * @author mike
 * 
 */
public abstract class BaseFormAction extends BaseAction
{

	private static final Logger log = LoggerFactory.getLogger(BaseFormAction.class);

	// java.util.List<Field> fields = new ArrayList<Field>();

	private java.util.List<Link> links = new ArrayList<Link>();

	private java.util.List<Button> buttons = new ArrayList<Button>();

	/**
	 * Add a link to the list
	 * 
	 * @param link
	 */
	public void setLink(Link link)
	{

		this.links.add(link);
	}

	/**
	 * Set the list of links
	 * 
	 * @param links
	 */
	public void setLinks(List<Link> links)
	{

		this.links = links;
	}

	/**
	 * @return last Link in list or null if list contains no Links
	 */
	public Link getLink()
	{

		if (links.size() > 0) {
			return links.get(links.size() - 1);
		}
		return null;
	}

	/**
	 * @return list of links
	 */
	public java.util.List<Link> getLinks()
	{

		return links;
	}

	public static Html[] buildLinks(IExecContext execContext, List<Link> links, Theme theme)
	{
		Html [] htmlAs = new HtmlA[links.size()];
		
		int i = 0;
		for (Link link : links) {
			htmlAs[i++] = link.draw(execContext, theme);
		}
		return htmlAs;
	}

	public static Html[] addLinksToExecContext(IExecContext execContext, List<Link> links, Theme theme)
	{
		Html [] htmlAs = new HtmlA[links.size()];
		
		int i = 0;
		for (Link link : links) {
			Html htmlA  = link.draw(execContext, theme);
			execContext.put(link.getName(), htmlA);
			htmlAs[i++] = htmlA;
		}
		return htmlAs;
	}

	/**
	 * Add a button to the list
	 * 
	 * @param button
	 */
	public void setButton(Button button)
	{

		this.buttons.add(button);
	}

	/**
	 * @return last Button in list or null if list contains no Buttons
	 */
	public Button getButton()
	{

		if (buttons.size() > 0) {
			return buttons.get(buttons.size() - 1);
		}
		return null;
	}

	/**
	 * @return list of buttons
	 */
	public java.util.List<Button> getButtons()
	{

		return buttons;
	}

	public static HtmlInput[] buildButtons(IExecContext execContext, List<Button> buttons, Theme theme)
	{

		HtmlInput[] inputs = new HtmlInput[buttons.size()];

		int i = 0;
		for (Button button : buttons) {
			inputs[i++] = button.draw(execContext, theme);
		}
		return inputs;
	}

	public static HtmlInput[] addButtonsToExecContext(IExecContext execContext, List<Button> buttons, Theme theme)
	{

		HtmlInput[] inputs = new HtmlInput[buttons.size()];

		int i = 0;
		for (Button button : buttons) {
			HtmlInput input = button.draw(execContext, theme);
			execContext.put(button.getName(), input);
			inputs[i++] = input;
		}
		return inputs;
	}

	public static HtmlInput buildTextInput(Theme theme, String name, String id, String value, int size, int maxLength)
	{

		return buildInput(theme, name, id, value, size, maxLength, "text", false);
	}

	public static HtmlInput buildPasswordInput(Theme theme, String name, String id, String value, int size,
			int maxLength)
	{

		return buildInput(theme, name, id, value, size, maxLength, "password", false);
	}

	/**
	 * 
	 * @param theme
	 * @param name
	 * @param id
	 * @param value
	 * @param size
	 *            < 1 wont include size in the html
	 * @param maxLength
	 *            < 1 wont include maxLength in the html
	 * @param inputType
	 *            - text, password, ...
	 * @param disabled
	 *            if true the input is disabled.
	 * @return
	 */
	public static HtmlInput buildInput(Theme theme, String name, String id, String value, int size, int maxLength,
			String inputType, boolean disabled)
	{

		HtmlInput input = new HtmlInput();
		input.setClazz(theme.getValue(ThemeConst.INPUT_TEXT.toString()));
		input.setName(id);
		input.setId(id);
		input.setType(inputType);
		input.setValue(value);
		if (size > 0) {
			input.setSize("" + size);
		}
		if (maxLength > 0) {
			input.setMaxlength("" + maxLength);
		}
		if (disabled == true) {
			//input.setDisabled("disabled");
			input.setReadonly("true");
		}
		// return "<input " + theme.getTheme(ThemeConst.INPUT_TEXT.toString()) + " name=\"" + id + "\" id=\"" + id
		// + "\" type=\"" + inputType + "\" value=\"" + value + "\"" + (size > 0 ? " size=\"" + size + "\"" : "")
		// + (maxLength > 0 ? " maxlength=\"" + maxLength + "\"" : "")
		// + (disabled == true ? "disabled=\"disabled\"" : "") + "/>";
		return input;
	}

	/**
	 * This moves up the parent tree looking for an IForm. When / if it finds
	 * one it returns the id.
	 * 
	 * @return
	 */
	public static String findFormId(BaseAction action)
	{

		BaseAction parent = action;
		while ((parent = parent.getParent()) != null) {
			if (parent instanceof IForm) {
				return ((IForm) parent).getFormId();
			}
		}
		log.warn("Unable to locate a form.id for [" + action.getClass().getName() + "]");
		return "";
	}

	/**
	 * Builds a hidden input field
	 * 
	 * @param key
	 *            the name of the field
	 * @param value
	 *            the value for the field
	 * @return
	 */
	public HtmlInput buildHiddenInput(String key, String value)
	{

		HtmlInput input = new HtmlInput();
		input.setName(key);
		input.setType("hidden");
		input.setValue(value);
		return input;
	}

}