
package org.xmlactions.pager.actions.form;


import java.util.List;

import org.xmlactions.action.config.IExecContext;
import org.xmlactions.common.theme.Theme;
import org.xmlactions.pager.actions.form.templates.HtmlInput;



public interface FormDrawing
{

	public IExecContext getExecContext();

	public String getId();

	public List<HtmlInput> getHiddenFields();

	public String getWidth();

	public boolean isVisible();

	public String getTitle();

	public Theme getTheme(IExecContext execContext);
	
	/** @deprecated - use getTheme(IExecContext execContext) instead */
	public Theme getTheme();

	public List<Link> getLinks();
}
