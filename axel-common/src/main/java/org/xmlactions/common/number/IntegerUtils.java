package org.xmlactions.common.number;


public class IntegerUtils
{

	/**
	 * Convert a String to an Integer
	 * 
	 * @param value
	 *            - the text value to convert to an integer
	 * @param failureInteger
	 *            - if the conversion fails then return this integer.
	 * @return the value as an integer or the failureInteger if the conversion fails.
	 */
	public static final int createInteger(String value, int failureInteger)
	{
		int number;
		try {
			number = Integer.parseInt(value);
		} catch (Exception ex) {
			number = failureInteger;
		}
		return number;
	}

	/**
	 * Convert a String to an Integer
	 * 
	 * @param value
	 *            - the text value to convert to an integer
	 * @param exceptionMessage
	 *            - A NumberFormatException with this message is thrown if not a valid integer.
	 * @return the value as an integer or throws a NumberFormatException with the passed exceptionMessage
	 */
	public static final int createInteger(String value, String exceptionMessage) {
		try {
			int v = Integer.parseInt(value);
			return v;
		} catch (Exception ex) {
			throw new NumberFormatException(exceptionMessage);
		}
	}

	/**
	 * Checks if an String is an integer.
	 * 
	 * @param value
	 *            - the text value to convert to an integer
	 * @return true if it is a valid integer else false.
	 */
	public static final boolean isInteger(String value)
	{
		try {
			Integer.parseInt(value);
		} catch (Exception ex) {
			return false;
		}
		return true;
	}

}
