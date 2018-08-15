
package org.xmlactions.pager.actions.form;


import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.common.theme.Theme;
import org.xmlactions.pager.drawing.html.DrawHTMLHelper;


/**
 * Builds a login record presentation.
 * <p>
 * The login fields come from the database schema.
 * </p>
 * <p>
 * parameters / attributes will build the html attributes needed for display.
 * </p>
 * 
 * @author mike
 * 
 */
public class Reset extends BaseFormAction
{

	private static final Logger LOG = LoggerFactory.getLogger(Reset.class);

	/** if set false hide the form, default = true */
	private boolean visible = true;

	/**
	 * unique (supposedly) id for this Login form. There may be more than one on
	 * a page and this is used to differentiate one from the other.
	 */
	private String id;

	/** If set show this title for the form */
	private String title;

	/** The theme we want to use to draw the search form */
	private String theme_name;

	public String execute(IExecContext execContext)
	{

		Theme theme = execContext.getThemes();
		String output = "";
		try {
			output = buildPresentation(execContext, theme);
		} catch (Exception ex) {
			throw new IllegalArgumentException(ex.getMessage(), ex);
			// Validate.isTrue(false, ex.getMessage());
		}
		return output;
	}

	private String buildPresentation(IExecContext execContext, Theme theme)
	{

		StringBuilder sb = new StringBuilder();

		StringBuffer fieldList = new StringBuffer();

		if (isVisible()) {
			sb.append("<table id=\"" + getId() + "\" " + theme.getValue(ThemeConst.TABLE.toString()) + ">");
		} else {
			sb.append("<table id=\"" + getId() + "\" " + theme.getValue(ThemeConst.TABLE.toString(), "hide") + ">");
		}
		if (!StringUtils.isEmpty(getTitle())) {
			sb.append("<tr " + theme.getValue(ThemeConst.ROW.toString()) + ">");
			sb.append("<th " + theme.getValue(ThemeConst.HEADER.toString()) + ">");
			sb.append(getTitle());
			sb.append("</td>");
			sb.append("</tr>");
		}

		boolean foundSubmitLink = false;
		for (Link link : getLinks()) {
			if (link.isSubmit()) {
				link.setActionScript("return(reset());");
				foundSubmitLink = true;
				if (StringUtils.isEmpty(link.getUri())) {
					link.setUri("javascript:hide('" + getId() + "');");
				}
			}
		}
		if (foundSubmitLink == false) {
			Validate.isTrue(false, "No 'submit' link has been set for this Login action.");
		}
		sb.append(DrawHTMLHelper.buildLinksAndButtons(execContext, this, theme, "right"));
		sb.append("</table>");
		return sb.toString();
	}

	public String toString()
	{

		return "reset";
	}

	public void setTheme_name(String themeName)
	{

		this.theme_name = themeName;
	}

	public String getTheme_name()
	{

		return theme_name;
	}

	public void setTitle(String title)
	{

		this.title = title;
	}

	public String getTitle()
	{

		return title;
	}

	public void setVisible(boolean visible)
	{

		this.visible = visible;
	}

	public boolean isVisible()
	{

		return visible;
	}

	public void setId(String id)
	{

		this.id = id;
	}

	/** Get the ID for this login Form. if the id is null will return "login_" */
	public String getId()
	{

		if (StringUtils.isEmpty(id))
			return "reset_";
		return id;
	}

}