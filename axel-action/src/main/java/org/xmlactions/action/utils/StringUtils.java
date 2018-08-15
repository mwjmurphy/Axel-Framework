package org.xmlactions.action.utils;

import org.apache.commons.lang.Validate;
import org.xmlactions.action.config.IExecContext;

/**
 * Provides String utilities for ExecContext.
 * 
 * @author mike.murphy
 *
 */
public class StringUtils {
	
	private static final String seperatorChar = ":";

	/**
	 * Perform a String.replaceAll
	 * <p>
	 * The replacementPattern format is "replace:value:regex:replacement"
	 * <br/>"replace" replacement instruction
	 * <br/>"value" the string to perform the replacement on
	 * <br/>"regex" the expression for the replacement
	 * <br/>"replacement" the replacement value
	 * </p>
	 * 
	 * @return the value after the replacement.
	 * 
	 */
	public static String replace(IExecContext execContext, String entryPattern) {
		
			String parts[] = entryPattern.split(seperatorChar);
			Validate.isTrue(parts.length==4, "Invalid entry string for replace. It must match this pattern [replace:value:regex:replacement]");
			String value = execContext.getStringQuietly(parts[1]);
			String regex = execContext.getStringQuietly(parts[2]);
			String replacement = execContext.getStringQuietly(parts[3]);
			
			String result = value.replaceAll(regex, replacement);
			return result;

	}
}
