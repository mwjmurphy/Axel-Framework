
package org.xmlactions.pager.drawing.html;


import org.apache.commons.lang.StringUtils;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.common.theme.Theme;
import org.xmlactions.db.actions.DateTime;
import org.xmlactions.pager.actions.form.CommonFormFields;
import org.xmlactions.pager.actions.form.Field;
import org.xmlactions.pager.actions.form.templates.Html;
import org.xmlactions.pager.actions.form.templates.HtmlTr;
import org.xmlactions.pager.drawing.IDrawField;


public class DateTimeHtml extends DateTime implements IDrawField
{

    private Field field;

    public Field getHtmlField() {
        return field;
    }

    public void setHtmlField(Field field) {
        this.field = field;
    }

    public HtmlTr displayForSearch(String value, Theme theme)
	{

		return DrawHtmlField.displayForSearch(this, value, theme);
	}

	public HtmlTr[] displayForAdd(String value, Theme theme)
	{

        return DrawHtmlField.displayForAdd(this, value, getLabelPosition(), theme);
	}

	public HtmlTr[] displayForView(CommonFormFields callingAction, String value, Theme theme)
	{

		return DrawHtmlField.displayForView(callingAction, this, value, getLabelPosition(), theme);
	}

    public Html displayForList(IExecContext execContext, Field field, String value, Theme theme)
	{

		return DrawHtmlField.displayForList(this, value, theme);
	}

	public HtmlTr[] displayForUpdate(String value, Theme theme)
	{

        return DrawHtmlField.displayForUpdate(this, value, getLabelPosition(), theme);

	}

	public String displayHeader(Theme theme)
	{

		StringBuilder sb = new StringBuilder();
		String title = "title=\"";
		if (!StringUtils.isBlank(getTooltip())) {
			title += getTooltip();
		}
		if (isMandatory() || isUnique()) {
			title += " - This field is ";
			if (isMandatory() && isUnique()) {
				title += "mandatory and unique";
			} else if (isMandatory()) {
				title += "mandatory";
			} else if (isUnique()) {
				title += "unique";
			}
		}
		title += "\"";
		sb.append("<th " + theme.getTheme("th") + " " + title + ">");
		sb.append(getPresentation_name() + (isMandatory() == true ? "<small>*</small>" : "")
				+ (isUnique() == true ? "<small>*</small>" : ""));
		sb.append("</th>");
		return sb.toString();
	}


	public Html buildAddHtml(String value, Theme theme) {
		return DrawInputFieldUtils.buildInputForText(this, value, theme);
	}

	public Html buildUpdateHtml(String value, Theme theme)
	{
		return DrawInputFieldUtils.buildInputForText(this, value, theme);
	}

    private String getLabelPosition() {
        if (getHtmlField() != null) {
            return getHtmlField().getLabel_position();
        }
        return "";
    }

	public Html buildViewHtml(String value, Theme theme) {
		return DrawInputFieldUtils.buildInputForView(this, value, theme);
	}

	public Html[] displayForView(CommonFormFields callingAction, Field field,
			String value, Theme theme) {
		// TODO Auto-generated method stub
		return null;
	}
}
