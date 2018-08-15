
package org.xmlactions.common.util;

public class RioStringUtils
{

	/**
	 * Returns a value converted to a String. A null value is converted to an
	 * empty String "".
	 * 
	 * @param value
	 *            the value we want to convert
	 * @return the converted value or "" if null.
	 */
	public static String convertToString(Object value)
	{

		if (value == null)
			return "";
		return value.toString();
	}
}
