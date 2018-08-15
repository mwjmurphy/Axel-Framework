
package org.xmlactions.pager.actions.dates;


import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmlactions.action.actions.BaseAction;
import org.xmlactions.action.config.IExecContext;

/**
 * Takes 4 parameters
 * 1) value
 * 2) origin format
 * 3) destination format
 * 4) key to store value back into execContext - Optional
 * @author mike.murphy
 *
 */
public class DateFormatterAction extends BaseAction
{

	private static Logger log = LoggerFactory.getLogger(DateFormatterAction.class);

	private String date_value;			// date value including hrs, mins etc if needed
	private String origin_format;		// java based datatime formatter
	private String destination_format;	// java based datetime formatter
	private String key;					// if we want to put the result back into the execContext

	public String execute(IExecContext execContext) throws Exception
	{
		validate(execContext);
		
		SimpleDateFormat originFormatter = new SimpleDateFormat(execContext.replace(origin_format));
		SimpleDateFormat destinationFormatter = new SimpleDateFormat(execContext.replace(destination_format));
		Date date = originFormatter.parse(execContext.replace(date_value));
		
		String result = destinationFormatter.format(date);
		
		if (StringUtils.isNotBlank(getKey())) {
			execContext.put(getKey(), result);
			return "";
		} else {
			return result;
		}
	}
	
	private void validate(IExecContext execContext) {
		Validate.notEmpty(date_value, "The date_value attribute must be set for this \"date_formatter\" action.");
		Validate.notEmpty(origin_format, "The origin_format attribute must be set for this  \"date_formatter\" action.");
		Validate.notEmpty(destination_format, "The destination_format attribute must be set for this  \"date_formatter\" action.");
		// the key value is optional
	}
	

	
	/**
	 * @return the date_value
	 */
	public String getDate_value() {
		return date_value;
	}

	/**
	 * @param date_value the date_value to set
	 */
	public void setDate_value(String date_value) {
		this.date_value = date_value;
	}

	/**
	 * @return the origin_format
	 */
	public String getOrigin_format() {
		return origin_format;
	}

	/**
	 * @param origin_format the origin_format to set
	 */
	public void setOrigin_format(String origin_format) {
		this.origin_format = origin_format;
	}

	/**
	 * @return the destination_format
	 */
	public String getDestination_format() {
		return destination_format;
	}

	/**
	 * @param destination_format the destination_format to set
	 */
	public void setDestination_format(String destination_format) {
		this.destination_format = destination_format;
	}

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}
}
