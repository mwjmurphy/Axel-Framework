
package org.xmlactions.pager.drawing.html;

import org.xmlactions.action.config.IExecContext;
import org.xmlactions.common.theme.Theme;
import org.xmlactions.db.actions.TimeStamp;
import org.xmlactions.pager.actions.form.CommonFormFields;
import org.xmlactions.pager.actions.form.Field;
import org.xmlactions.pager.actions.form.templates.Html;
import org.xmlactions.pager.actions.form.templates.HtmlTr;
import org.xmlactions.pager.drawing.IDrawField;
import org.xmlactions.pager.drawing.IDrawParams;



public class TimeStampHtml extends TimeStamp implements IDrawField
{

    private Field field;

    public Field getHtmlField() {
        return field;
    }

    public void setHtmlField(Field field) {
        this.field = field;
    }

    private String getLabelPosition() {
        if (getHtmlField() != null) {
            return getHtmlField().getLabel_position();
        }
        return "";
    }

	public HtmlTr displayForSearch(String value, Theme theme)
	{

		return DrawHtmlField.displayForSearch(this, value, theme);
	}

    public HtmlTr[] displayForAdd(String value, Theme theme)
	{

		return new HtmlTr[0];
	}

	public HtmlTr[] displayForView(CommonFormFields callingAction, String value, Theme theme)
	{
		return DrawHtmlField.displayForView(callingAction, this, value, getLabelPosition(), theme);
	}

    public Html displayForList(IExecContext execContext, Field field, String value, Theme theme)
	{

		return DrawHtmlField.displayForList(this, value, theme);
	}

    public Html displayForList(IDrawParams params, String value, Theme theme)
	{

		return DrawHtmlField.displayForList(this, value, theme);
	}

	public HtmlTr[] displayForUpdate(String value, Theme theme)
	{

        return DrawHtmlField.displayForUpdate(this, value, getLabelPosition(), theme);
	}

	public Html buildAddHtml(String value, Theme theme) {
		return DrawInputFieldUtils.buildInputForText(this, value, theme);
	}

	public Html buildUpdateHtml(String value, Theme theme)
	{
		return DrawInputFieldUtils.buildInputForText(this, value, theme);
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
