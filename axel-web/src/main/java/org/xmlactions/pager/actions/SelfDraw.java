package org.xmlactions.pager.actions;

import org.xmlactions.action.config.IExecContext;
import org.xmlactions.pager.actions.form.templates.Html;


/**
 * Used by beans that can draw themselves.
 * 
 * @author mike.murphy
 *
 */
public interface SelfDraw {
	
	public Html drawHtml(IExecContext execContext);
	public String drawHeader(IExecContext execContext);

}
