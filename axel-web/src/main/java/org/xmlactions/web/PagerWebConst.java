
package org.xmlactions.web;

import org.xmlactions.action.ActionWebConst;
import org.xmlactions.action.config.IExecContext;

public class PagerWebConst
{
	/**
	 * If we want to retrieve the execContext from the execContext using ${execContext}
	 */
	public final static String EXEC_CONTEXT = "execContext";
	

    /**
     * HttpRequest parameters are stored in execContext as a Named Map using
     * this identifying string.
     */
    public final static String REQUEST = ActionWebConst.REQUEST;

    /**
     * HttpRequest parameters are stored in execContext as an ArrayList using
     * this identifying string.
     */
    public final static String REQUEST_LIST = "request.list";

    /**
     * HttpRequest parameters are stored in execContext as an ArrayList using
     * this identifying string.
     */
    public final static String REQUEST_HEADERS = "request.headers";

    /**
     * HttpRequest is stored in execContext as a HttpRequest using this
     * identifying string.
     */
    public final static String HTTP_REQUEST = "http.request";

    /**
     * HttpResponse is stored in execContext as a HttpResponse using this
     * identifying string.
     */
    public final static String HTTP_RESPONSE = "http.response";

    /**
     * HttpSession is stored in execContext as a HttpSession using this
     * identifying string.
     */
    public final static String HTTP_SESSION = "http.session";

	/**
	 * Gets the current page name from the execContext. This gets stored from
	 * the PagerServlet when it gets the page name from the http request.
	 */
	public final static String PAGE_NAME = "page.name";

	/**
	 * Gets the current page uri from the execContext. This gets stored from
	 * the PagerServlet when it gets the page uri from the http request.
	 */
	public final static String PAGE_URI = "page.uri";

	/**
	 * Gets the current page url from the execContext. This gets stored from
	 * the PagerServlet when it gets the page url from the http request.
	 */
	public final static String PAGE_URL = "page.url";

    /**
     * Gets the current page server name from the execContext. This gets stored
     * from the PagerServlet when it gets the page url from the http request.
     */
    public final static String PAGE_SERVER_NAME = "page.server.name";

    /**
     * Gets the current page application name from the execContext. This gets
     * stored from the PagerServlet when it gets the page url from the http
     * request.
     */
    public final static String PAGE_APP_NAME = "page.app.name";

    /**
     * If this is set than the pager won't perform a StrSubstution on the page
     * before returing it to the browser.
     */
    public final static String NO_STR_SUBST = "no_str_subst";

	/**
	 * Gets a value from the HttpRequest params contained within the map.
	 * 
	 * @param execContext
	 *            containing the HttpRequest parameters
	 * @param key
	 *            the key to the parameter
	 * @return the value or null if not found
	 */
	public static String getRequestParameter(IExecContext execContext, String key)
	{
		
		return (String) execContext.get(buildRequestKey(key));

	}

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

		return ActionWebConst.buildRequestKey(key);
	}

	/**
	 * Gets the current httpRequest page.
	 * 
	 * @param execContext
	 * @return the name of the httpRequest page.
	 */
	public static String getPageName(IExecContext execContext)
	{

		return (String) execContext.get(PAGE_NAME);

	}

	/**
	 * Gets the current httpRequest page uri.
	 * 
	 * @param execContext
	 * @return the name of the httpRequest page uri.
	 */
	public static String getPageURI(IExecContext execContext)
	{

		return (String) execContext.get(PAGE_URI);

	}
	/**
	 * Gets the current httpRequest page url.
	 * 
	 * @param execContext
	 * @return the name of the httpRequest page url.
	 */
	public static String getPageURL(IExecContext execContext)
	{

		return (String) execContext.get(PAGE_URL);

	}
}
