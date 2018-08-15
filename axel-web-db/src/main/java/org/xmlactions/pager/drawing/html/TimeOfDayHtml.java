
package org.xmlactions.pager.drawing.html;


import org.apache.commons.lang.StringUtils;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.common.theme.Theme;
import org.xmlactions.db.actions.CommonStorageField;
import org.xmlactions.db.actions.TimeOfDay;
import org.xmlactions.pager.actions.form.CommonFormFields;
import org.xmlactions.pager.actions.form.Field;
import org.xmlactions.pager.actions.form.ThemeConst;
import org.xmlactions.pager.actions.form.templates.Html;
import org.xmlactions.pager.actions.form.templates.HtmlInput;
import org.xmlactions.pager.actions.form.templates.HtmlTd;
import org.xmlactions.pager.actions.form.templates.HtmlTh;
import org.xmlactions.pager.actions.form.templates.HtmlTr;
import org.xmlactions.pager.drawing.IDrawField;
import org.xmlactions.pager.drawing.html.DrawDBHTMLHelper;
import org.xmlactions.pager.freeware.FreewareConstants;


public class TimeOfDayHtml extends TimeOfDay implements IDrawField
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
        return displayForAdd(this, value, getLabelPosition(), theme);
	}

	public HtmlTr [] displayForView(CommonFormFields callingAction, String value, Theme theme)
	{
		return DrawHtmlField.displayForView(callingAction, this, value, getLabelPosition(), theme);
	}

    public Html displayForList(IExecContext execContext, Field field, String value, Theme theme)
	{

		return DrawHtmlField.displayForList(this, value, theme);
	}

	public HtmlTr[] displayForUpdate(String value, Theme theme)
	{

        return displayForUpdate(this, value, getLabelPosition(), theme);
	}

	public Html buildAddHtml(String value, Theme theme) {
		return DrawInputFieldUtils.buildInputForDate(this, value, theme);
	}

	public Html buildUpdateHtml(String value, Theme theme)
	{
		return DrawInputFieldUtils.buildInputForTime(this, value, theme);
	}

	public static HtmlTr[] displayForUpdate(CommonStorageField commonStorageField, String value, String label_position,
			Theme theme)
	{

		HtmlTd td = new HtmlTd(theme);
		HtmlInput input = DrawInputFieldUtils.buildInputForTime(commonStorageField, value, theme);
		td.addChild(input);
		return DrawDBHTMLHelper.buildDisplay(theme, label_position, DrawHtmlField.displayHeader(commonStorageField, theme), td);
	}

	public static HtmlTr[] displayForAdd(CommonStorageField commonStorageField, String value, String label_position,
			Theme theme)
	{

		HtmlTd td = new HtmlTd(theme);
		HtmlInput input = td.addInput(theme);
		input.setType("text");
		input.setClazz(FreewareConstants.time.toString() + " " + theme.getValue(ThemeConst.INPUT_TEXT.toString()));
		input.setName(DrawDBHTMLHelper.buildName(commonStorageField));
		input.setSize("" + commonStorageField.getPresentation_width());
        if (commonStorageField.getLength() > 0) {
            input.setMaxlength("" + commonStorageField.getLength());
        }
		input.setValue(value);
		return DrawDBHTMLHelper.buildDisplay(theme, label_position, DrawHtmlField.displayHeader(commonStorageField, theme), td);

	}

    public static HtmlTr displayForSearch(CommonStorageField commonStorageField, String value, Theme theme) {
        String name = DrawDBHTMLHelper.buildName(commonStorageField);
        return displayForSearch(commonStorageField, value, theme, name);
    }

    public static HtmlTr displayForSearch(CommonStorageField commonStorageField, String value, Theme theme, String name) {
        HtmlTr tr = new HtmlTr(theme);
        HtmlTh th = tr.addTh(theme);
        if (StringUtils.isNotEmpty(commonStorageField.getTooltip())) {
            th.setTitle(commonStorageField.getTooltip());
        }
        String header;
        if (commonStorageField.getRefFk() != null && commonStorageField.getRefFk().getPresentation_name() != null) {
            header = commonStorageField.getRefFk().getPresentation_name();
        } else {
            header = commonStorageField.getPresentation_name();
        }
        th.setContent(header
                    + (commonStorageField.isMandatory() == true ? "<small>*</small>" : "")
                    + (commonStorageField.isUnique() == true ? "<small>*</small>" : ""));
        HtmlTd td = tr.addTd(theme);
        if (!StringUtils.isBlank(commonStorageField.getTooltip())) {
            td.setTitle(commonStorageField.getTooltip());
        }
        HtmlInput input = td.addInput(theme);
        input.setType("text");
		input.setClazz(FreewareConstants.time.toString() + " " + theme.getValue(ThemeConst.INPUT_TEXT.toString()));
        input.setName(name);
        input.setSize("" + commonStorageField.getPresentation_width());
        if (commonStorageField.getLength() > 0) {
            input.setMaxlength("" + commonStorageField.getLength());
        }
        input.setValue(value);

        return tr;
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
