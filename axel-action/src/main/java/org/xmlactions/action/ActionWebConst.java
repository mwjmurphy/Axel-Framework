package org.xmlactions.action;

public class ActionWebConst {
	
    /**
     * HttpRequest parameters are stored in execContext as a Named Map using
     * this identifying string.
     */
    public final static String REQUEST = "request";


	/**
	 * Builds the key prepending the HttpRequest named map name in the
	 * execContext.
	 * 
	 * @param key
	 *            the key name for the HttpRequest parameter
	 * @return the full key name that includes the HttpRequest named map.
	 */
	public static String buildRequestKey(String key)
	{

		return (REQUEST + ":" + key);
	}


}
