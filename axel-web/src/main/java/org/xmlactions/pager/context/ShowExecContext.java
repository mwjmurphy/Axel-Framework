package org.xmlactions.pager.context;


import java.util.Iterator;
import java.util.Map;


import org.apache.commons.configuration.Configuration;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.action.config.PropertyContainer;
import org.xmlactions.web.PagerWebConst;


/**
 * Shows the content of an execContext.
 * @author Mike Murphy
 *
 */
public class ShowExecContext {

	private static final String lf = "<br/>\n";
	public static String show(IExecContext execContext) {
		StringBuilder sb = new StringBuilder();
		for (String key : execContext.keySet()) {
			if (PagerWebConst.EXEC_CONTEXT.equals(key)) {
				continue;
			}
			if (key.toLowerCase().indexOf("$created") >= 0) {
				continue;
			}
			Object obj = execContext.get(key);
			if (obj instanceof Map) {
				sb.append(lf + showMap((Map<String,?>)obj, key));
			} else if (obj instanceof PropertyContainer) {
				sb.append(lf + showConfiguration(((PropertyContainer)obj).getConfiguration(), key));
			} else if (obj instanceof Configuration) {
				sb.append(lf + showConfiguration(((Configuration)obj), key));
			} else {
				sb.append(lf + key + " = " + execContext.get(key).toString());
			}
		}
		return sb.toString();
	}
	
	public static String showMap(Map<String,?>map, String name) {
		StringBuilder sb = new StringBuilder();
		for (String key : map.keySet()) {
			Object obj = map.get(key);
			if (obj instanceof Map) {
				sb.append(lf + showMap((Map<String,?>)obj, key));
			} else {
				sb.append(lf + name + "." + key + " = " + map.get(key).toString());
			}
		}
		return sb.toString();
	}
	public static String showConfiguration(Configuration configuration, String name) {
		StringBuilder sb = new StringBuilder();
		Iterator<String> iterator = configuration.getKeys();
		while(iterator.hasNext()) {
			String key = iterator.next();
			Object obj = configuration.getProperty(key);
			sb.append(lf + name + "." + key + " = " + obj.toString());
		}
		return sb.toString();
	}

}
