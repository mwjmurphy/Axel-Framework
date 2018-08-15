
package org.xmlactions.pager.actions.http;


import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmlactions.action.actions.BaseAction;
import org.xmlactions.action.config.IExecContext;

/**
 * Takes 1 parameter
 * 1) value
 * 2) origin format
 * 3) destination format
 * 4) key to store value back into execContext - Optional
 * @author mike.murphy
 *
 */
public class ContentTypeAction extends BaseAction
{

	private static Logger log = LoggerFactory.getLogger(ContentTypeAction.class);

	private String value;	// The ContentType we want to set in the HttpResponse. 

	public String execute(IExecContext execContext) throws Exception
	{
		validate(execContext);
		
		if (StringUtils.isNotBlank(getValue())) {
			execContext.put(IExecContext.CONTENT_TYPE_KEY, execContext.replace(getValue()));
		}
		return "";
	}
	
	private void validate(IExecContext execContext) {
		Validate.notEmpty(value, "The value attribute must be set for this \"content_type\" action.");
		// the key value is optional
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}



}
