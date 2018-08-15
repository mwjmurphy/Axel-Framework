
package org.xmlactions.pager.drawing;

import org.xmlactions.action.actions.BaseAction;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.common.theme.Theme;
import org.xmlactions.pager.actions.form.CommonFormFields;
import org.xmlactions.pager.actions.form.Field;
import org.xmlactions.pager.actions.form.templates.Html;
import org.xmlactions.pager.actions.form.templates.HtmlTr;



/**
 * All form drawing fields implements this interface.
 * 
 * @author mike
 * 
 */
public interface IDrawField
{
	
    public BaseAction getParent();
    
    public boolean isMandatory();
    public boolean isUnique();
    public String getPresentation_name();

   
	/**
	 * Name of the field, matches the field name in the database table.
	 */
    public String getName();

	/**
	 * Displays the value for a Search form.
	 * <p>
	 * A Search form displays the fields similar to an update form, where the operator enters information for a search
	 * in the presented fields.
	 * </p>
	 * 
	 * @param value
	 * @param theme
	 * @return the display TR
	 */
	public HtmlTr displayForSearch(String value, Theme theme);

	/**
	 * Displays the value for an Add form.
	 * <p>
	 * An Add form displays the fields similar to an update form, where the operator enters information for a new record
	 * in the presented fields.
	 * </p>
	 * 
	 * @param value
	 * @param label_position
	 * @param theme
	 * @return the display TR[]
	 */
    public HtmlTr[] displayForAdd(String value, Theme theme);

	/**
	 * Displays the value for a View form.
	 * <p>
	 * A View form displays the fields similar to an update form, where the operator views the presented field
	 * information without the option to edit.
	 * </p>
	 * 
	 * @param callingAction
	 * @param value
	 * @param theme
	 * @return the display TR
	 */
	public Html[] displayForView(CommonFormFields callingAction, Field field, String value, Theme theme);

    /**
     * Displays the value for a List form (table).
     * <p>
     * A List form displays rows of records in a table presentation.
     * </p>
     * 
     * @param execContext
     * @param field
     *            - may be used to overwrite configuration settings.
     * @param value
     * @param theme
     * @return the display TD
     */
    public Html displayForList(IExecContext execContext, Field field, String value, Theme theme);

	/**
	 * Displays the value for an Update form.
	 * <p>
	 * An Update form displays the fields for editing, where the operator edits the presented fields.
	 * </p>
	 * 
	 * @param value
	 * @param theme
	 * @return the display string
	 */
	public HtmlTr[] displayForUpdate(String value, Theme theme);

	/**
	 * Builds the bare input for an update/edit form
	 * @param value
	 * @param theme
	 * @return an Html to insert into the form
	 */
	public Html buildAddHtml(String value, Theme theme);
	/**
	 * Builds the bare input for an update/edit form
	 * @param value
	 * @param theme
	 * @return an Html to insert into the form
	 */
	public Html buildUpdateHtml(String value, Theme theme);
	/**
	 * Builds the bare input for an view form
	 * @param value
	 * @param theme
	 * @return an Html to insert into the form
	 */
	public Html buildViewHtml(String value, Theme theme);
}
