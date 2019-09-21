package org.xmlactions.action.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.math.NumberUtils;

public class Convert {

	public static String toString(Object o) {
		if (o == null) {
			return null;
		}
		
		return o.toString();
	}
	
	public static Boolean toBoolean(Object o) {
		if (o == null) {
			return null;
		}
		if (o instanceof Boolean) {
			return (Boolean)o;
		}
		
		Boolean b = Boolean.parseBoolean("" + o);
		
		return b;
	}
	
	public static Double toDouble(Object o) {
		if (o == null) {
			return null;
		}
		if (o instanceof Double) {
			return (Double)o;
		}
		
		if (NumberUtils.isNumber("" + o)) {
			Double d = Double.parseDouble("" + o);
			return d;
		}
		return null;
		
	}

	
	public static Integer toInteger(Object o) {
		if (o == null) {
			return null;
		}

		if (o instanceof Integer) {
			return (Integer)o;
		}
		
		if (NumberUtils.isNumber("" + o) && ("" + o).indexOf('.') < 0)  {
			Integer i = Integer.parseInt("" + o);
			return i;
		}
		return null;
		
	}


	public static Long toLong(Object o) {
		if (o == null) {
			return null;
		}
		String s = o.toString();
		
		Long l = Long.parseLong(s);
		
		return l;

	}
	
	public static Timestamp toTimestamp(Object o) {
		if (o == null) {
			return null;
		}
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS".substring(0, o.toString().length()));
			Date parsedDate = dateFormat.parse(o.toString());
			Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
			return timestamp;
		} catch (ParseException e) {
		}
		return null;
	}
	
	public static Object toDefault(Object value, Object orDefault) {
		return value == null ? orDefault : value;
	}

}
