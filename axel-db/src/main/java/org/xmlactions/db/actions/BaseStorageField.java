
package org.xmlactions.db.actions;


import org.apache.commons.lang.StringUtils;
import org.xmlactions.action.actions.BaseAction;

/**
 * This layer provides the common fields abstraction and the display
 * abstraction.
 * 
 * @author mike
 * 
 */
public abstract class BaseStorageField extends BaseAction
{

	public abstract String getName();

	public abstract String getAlias();

    /**
     * If we want to call a function from the sql. The function must use the
     * '%s' String.format syntax to have the field name replaced in the
     * function. Like to_char(%s, 'DD/MM/RRRR HH24:MI.SS')
     */
    public abstract String getFunction_ref();

	public abstract String toString(int indent);

	/**
	 * Validate the value against the configured settings. Call this before
	 * attempting to store the value to the database
	 */
	public abstract String validate(String value);

	protected String buildErrorString(String error)
	{

		if (StringUtils.isEmpty(error)) {
			return "";
		}
		return (error);
	}

}
