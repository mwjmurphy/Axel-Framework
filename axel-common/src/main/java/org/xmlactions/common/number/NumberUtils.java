package org.xmlactions.common.number;


public class NumberUtils
{

    /**
     * Convert a String to an Integer
     * 
     * @param value
     *            - the text value to convert to an integer
     * @param failureInteger
     *            - if the conversion fails then return this integer.
     * @return the value as an integer or the failureInteger if the conversion
     *         fails.
     */
    public static final int createInteger(String value, int failureInteger) {
        int number;
        try {
            number = Integer.parseInt(value);
        } catch (Exception ex) {
            number = failureInteger;
        }
        return number;
    }

    public static final int createInteger(String value, String exceptionMessage) {
        try {
            int v = Integer.parseInt(value);
            return v;
        } catch (Exception ex) {
            throw new NumberFormatException(exceptionMessage);
        }
    }

    public static final Double createDouble(String value) {
        Double d;
        try {
            d = Double.valueOf(value);
        } catch (Exception ex) {
            throw new NumberFormatException("Unable to convert [" + value + "] to a Double");
        }
        return d;
    }


}
