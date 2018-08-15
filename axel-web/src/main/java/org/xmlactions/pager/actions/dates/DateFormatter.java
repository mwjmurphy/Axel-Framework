package org.xmlactions.pager.actions.dates;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.common.date.DateUtils;

public class DateFormatter {

	/**
	 * @param execContext - we will do a replace on each of the parts of date formatter.
	 * @param currentValue - the origin_formatted date
	 * @param formatters - this contains two date formatters as "origin_format,destination_format"  
	 * @return
	 */
	public static String formatValue(IExecContext execContext, String currentValue, String formatters) {
		if (StringUtils.isBlank(currentValue) ) {
			return null;
		}
		String formats [] = splitFormatValue(execContext, formatters);
		return DateUtils.format(execContext.replace(currentValue),execContext.replace(StringEscapeUtils.unescapeHtml(formats[0].trim())),execContext.replace(StringEscapeUtils.unescapeHtml(formats[1].trim())));
//			SimpleDateFormat originFormatter = new SimpleDateFormat(execContext.replace(StringEscapeUtils.unescapeHtml(formats[0].trim())));
//			SimpleDateFormat destinationFormatter = new SimpleDateFormat(execContext.replace(StringEscapeUtils.unescapeHtml(formats[1].trim())));
//			date = originFormatter.parse(execContext.replace(currentValue));
//			String result = destinationFormatter.format(date);
//			return result;
	}
	
	private static String [] splitFormatValue(IExecContext execContext, String formatters) {
		String [] splits = formatters.split(",");
		if (splits.length != 2) {
			throw new IllegalArgumentException("Error in format value. The format should be \"format1,format2\" but it was \"" + formatters + "\"");
		}
		splits[0] = execContext.replace(splits[0]);
		splits[1] = execContext.replace(splits[1]);
		return splits;
	}
	

}
