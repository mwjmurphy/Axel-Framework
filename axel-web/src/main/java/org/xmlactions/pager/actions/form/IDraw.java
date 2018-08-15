
package org.xmlactions.pager.actions.form;

import org.xmlactions.action.config.IExecContext;
import org.xmlactions.common.theme.Theme;
import org.xmlactions.pager.actions.form.templates.Html;



/**
 * For supporters of draw.
 * 
 * @author mike
 * 
 */
public interface IDraw
{

	/**
	 * Draw the Element
	 * 
	 * @param theme
	 * @return an HTML object
	 */
	public Html draw(IExecContext execContext, Theme theme);
}
