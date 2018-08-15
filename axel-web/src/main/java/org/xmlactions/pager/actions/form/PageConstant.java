
package org.xmlactions.pager.actions.form;

/**
 * These are the constants we use for getting information between the browser
 * page and the server.
 * 
 * @author mike
 * 
 */
public enum PageConstant
{
	PAGE("page"), ROWS("rows"), ID("id");

	private String value;

	PageConstant(String ref)
	{

		this.value = ref;
	}

	public String toString()
	{

		return value;
	}

	/**
	 * Build a constant, prepending id + _
	 * 
	 * @param id
	 * @param value
	 * @return
	 */
	public static String buildWithID(String id, String value)
	{

		return (id + "_" + value);
	}
}
