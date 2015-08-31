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

package org.xmlactions.common.date;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

public class DateUtils {

	/**
	 * Converts a String date to a dateInMillis 
	 * <p>
	 * Letter 	Date or Time Component 	Presentation 	Examples<br/>
	 * G 	Era designator 	Text 	AD<br/>
	 * y 	Year 	Year 	1996; 96<br/>
	 * M 	Month in year 	Month 	July; Jul; 07<br/>
	 * w 	Week in year 	Number 	27<br/>
	 * W 	Week in month 	Number 	2<br/>
	 * D 	Day in year 	Number 	189<br/>
	 * d 	Day in month 	Number 	10<br/>
	 * F 	Day of week in month 	Number 	2<br/>
	 * E 	Day in week 	Text 	Tuesday; Tue<br/>
	 * a 	Am/pm marker 	Text 	PM<br/>
	 * H 	Hour in day (0-23) 	Number 	0<br/>
	 * k 	Hour in day (1-24) 	Number 	24<br/>
	 * K 	Hour in am/pm (0-11) 	Number 	0<br/>
	 * h 	Hour in am/pm (1-12) 	Number 	12<br/>
	 * m 	Minute in hour 	Number 	30<br/>
	 * s 	Second in minute 	Number 	55<br/>
	 * S 	Millisecond 	Number 	978<br/>
	 * z 	Time zone 	General time zone 	Pacific Standard Time; PST; GMT-08:00<br/>
	 * Z 	Time zone 	RFC 822 time zone 	-0800<br/>
	 * </p>
	 * @param textDate - the date we want converted.
	 * @param format - the format we want it convert to.
	 * @return the date as a long in millis
	 */
	public static long getDate(String textDate, String format) {
		DateFormat df = new SimpleDateFormat(format);

		try {
			Date date = df.parse(textDate);
			return date.getTime();
		} catch (Exception ex) {
			throw new IllegalArgumentException(ex.getMessage(), ex);
		}
	}

	/**
	 * Converts a dateInMillis to a String
	 * <p>
	 * Letter 	Date or Time Component 	Presentation 	Examples<br/>
	 * G 	Era designator 	Text 	AD<br/>
	 * y 	Year 	Year 	1996; 96<br/>
	 * M 	Month in year 	Month 	July; Jul; 07<br/>
	 * w 	Week in year 	Number 	27<br/>
	 * W 	Week in month 	Number 	2<br/>
	 * D 	Day in year 	Number 	189<br/>
	 * d 	Day in month 	Number 	10<br/>
	 * F 	Day of week in month 	Number 	2<br/>
	 * E 	Day in week 	Text 	Tuesday; Tue<br/>
	 * a 	Am/pm marker 	Text 	PM<br/>
	 * H 	Hour in day (0-23) 	Number 	0<br/>
	 * k 	Hour in day (1-24) 	Number 	24<br/>
	 * K 	Hour in am/pm (0-11) 	Number 	0<br/>
	 * h 	Hour in am/pm (1-12) 	Number 	12<br/>
	 * m 	Minute in hour 	Number 	30<br/>
	 * s 	Second in minute 	Number 	55<br/>
	 * S 	Millisecond 	Number 	978<br/>
	 * z 	Time zone 	General time zone 	Pacific Standard Time; PST; GMT-08:00<br/>
	 * Z 	Time zone 	RFC 822 time zone 	-0800<br/>
	 * </p>
	 * @param dateInMillis - the date we want converted.
	 * @param format - the format we want it convert to.
	 * @return the date as a String.
	 */
	public static String getDate(long dateInMillis, String format) {
		DateFormat df = new SimpleDateFormat(format);

		try {
			String date = df.format(new Date(dateInMillis));
			return date;
		} catch (Exception ex) {
			throw new IllegalArgumentException(ex.getMessage(), ex);
		}
	}

	public static String format(String currentValue, String originFormat, String destinationFormat) {
		if (! StringUtils.isEmpty(currentValue)) {
			try {
				SimpleDateFormat originFormatter = new SimpleDateFormat(originFormat);
				SimpleDateFormat destinationFormatter = new SimpleDateFormat(destinationFormat);
				Date date = originFormatter.parse(currentValue);
				String result = destinationFormatter.format(date);
				return result;
			} catch (ParseException e) {
				throw new IllegalArgumentException("Error in formatter, unable to format [" + currentValue + "] using [" + originFormat + " to " + destinationFormat +"] " + e.getMessage());
			}
		} else {
			return "";
		}
	}

}
