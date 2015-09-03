/*
 * Copyright (C) Mike Murphy 2003-2015 <mike.murphy@xmlactions.org><mwjmurphy@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

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
