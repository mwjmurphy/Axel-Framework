
package org.xmlactions.pager.actions.form;


import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.pager.actions.form.CommonFormFields;
import org.xmlactions.pager.actions.form.templates.HtmlInput;
import org.xmlactions.pager.drawing.IDrawParams;


/**
 * Can insert a field_hide into a field_list for an Add.  This will create an input type=hidden in the html
 * 
 * @author mike
 * 
 */
public class FieldHide extends CommonFormFields
{
	private static final Logger log = LoggerFactory.getLogger(FieldHide.class);

	/** The value for the input may be stored here or in the content if not here. */
	private String value;
	private String pre_format;
	private String post_format;
	
	public String execute(IExecContext execContext)
	{
		return null;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getValue() {
		if (value == null) {
			return getContent();
		}
		return value;
	}
	
	public HtmlInput buildHiddenInput(IExecContext execContext) {
		Object value = execContext.replace(getValue());
		return buildHiddenInput(execContext, "" + value);
	}

	public HtmlInput buildHiddenInput(IExecContext execContext, String value) {
		Object v1 = execContext.get(getName());
		
		HtmlInput input = new HtmlInput();
		input.setName(getName());
		input.setValue(value);
		input.setType("hidden");
		return input;
	}

	public String toString() {
		return getName();
	}

	/**
	 * @return the pre_format
	 */
	public String getPre_format() {
		return pre_format;
	}

	/**
	 * @param pre_format the pre_format to set
	 */
	public void setPre_format(String pre_format) {
		this.pre_format = pre_format;
	}

	/**
	 * @return the post_format
	 */
	public String getPost_format() {
		return post_format;
	}

	/**
	 * @param post_format the post_format to set
	 */
	public void setPost_format(String post_format) {
		this.post_format = post_format;
	}
}
