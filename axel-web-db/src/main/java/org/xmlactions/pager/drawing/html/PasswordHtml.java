
package org.xmlactions.pager.drawing.html;

import org.xmlactions.action.config.IExecContext;
import org.xmlactions.common.theme.Theme;
import org.xmlactions.db.actions.Password;
import org.xmlactions.pager.actions.form.CommonFormFields;
import org.xmlactions.pager.actions.form.Field;
import org.xmlactions.pager.actions.form.templates.Html;
import org.xmlactions.pager.actions.form.templates.HtmlDiv;
import org.xmlactions.pager.actions.form.templates.HtmlInput;
import org.xmlactions.pager.actions.form.templates.HtmlTd;
import org.xmlactions.pager.actions.form.templates.HtmlTr;
import org.xmlactions.pager.drawing.IDrawField;
import org.xmlactions.pager.drawing.IDrawParams;
import org.xmlactions.pager.drawing.html.DrawDBHTMLHelper;



public class PasswordHtml extends Password implements IDrawField
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

		return new HtmlTr(theme);
	}

	public HtmlTr[] displayForUpdate(String value, Theme theme)
	{

		HtmlTr[] trs = new HtmlTr[1];
		HtmlTr tr = new HtmlTr(theme);
		trs[0] = tr;

		tr.addChild(DrawHtmlField.displayHeader(this, theme));
		HtmlTd td = tr.addTd(theme);
		if (isEditable() == true) {
			HtmlInput input = td.addInput(theme);
			input.setType("password");
			input.setName(DrawDBHTMLHelper.buildName(this));
			input.setSize("" + getPresentation_width());
			input.setMaxlength("" + getLength());
			input.setValue(value);
		} else {
			td.setContent(value);
		}
		return trs;
	}

	public HtmlTr[] displayForAdd(String value, Theme theme)
	{

		HtmlTd td = new HtmlTd(theme);
		HtmlInput input = td.addInput(theme);
		input.setType("password");
		input.setName(DrawDBHTMLHelper.buildName(this));
		input.setSize("" + getPresentation_width());
		input.setMaxlength("" + getLength());
		input.setValue(value);
        return DrawDBHTMLHelper.buildDisplay(theme, getLabelPosition(), DrawHtmlField.displayHeader(this, theme), td);
	}

    public Html displayForList(IExecContext execContext, Field field, String value, Theme theme)
	{

		return new HtmlTd(theme);
	}

	public String displayForList(IDrawParams params, String value, Theme theme)
	{

		return ""; // never display a password for a list
	}

	public HtmlTr[] displayForView(CommonFormFields callingAction, String value, Theme theme)
	{
		HtmlTr[] trs = new HtmlTr[1];
		HtmlTr tr = new HtmlTr(theme);
		trs[0] = tr;

		tr.addChild(DrawHtmlField.displayHeader(this, theme));
		HtmlTd td = tr.addTd(theme);
		if (isEditable() == true) {
			HtmlInput input = td.addInput(theme);
			input.setType("password");
			input.setName(DrawDBHTMLHelper.buildName(this));
			input.setSize("" + getPresentation_width());
			input.setMaxlength("" + getLength());
			input.setValue(value);
			input.setReadonly("true");
		} else {
			td.setContent(value);
		}
		return trs;
	}

	public Html buildAddHtml(String value, Theme theme) {
		HtmlInput input = new HtmlInput(theme);
		input.setType("password");
		input.setName(DrawDBHTMLHelper.buildName(this));
		input.setSize("" + getPresentation_width());
		input.setMaxlength("" + getLength());
		input.setValue(value);
		return input;
	}

	public Html buildUpdateHtml(String value, Theme theme)
	{
		Html html;
		HtmlTr[] trs = new HtmlTr[1];
		HtmlTr tr = new HtmlTr(theme);
		trs[0] = tr;

		tr.addChild(DrawHtmlField.displayHeader(this, theme));
		HtmlTd td = tr.addTd(theme);
		if (isEditable() == true) {
			HtmlInput input = td.addInput(theme);
			html = input;
			input.setType("password");
			input.setName(DrawDBHTMLHelper.buildName(this));
			input.setSize("" + getPresentation_width());
			input.setMaxlength("" + getLength());
			input.setValue(value);
		} else {
			HtmlDiv div = new HtmlDiv(theme);
			html = div;
			div.setContent(value);
		}
		return html;
	}

	public Html buildViewHtml(String value, Theme theme) {
		Html html;
		HtmlTr[] trs = new HtmlTr[1];
		HtmlTr tr = new HtmlTr(theme);
		trs[0] = tr;

		tr.addChild(DrawHtmlField.displayHeader(this, theme));
		HtmlTd td = tr.addTd(theme);
		if (isEditable() == true) {
			HtmlInput input = td.addInput(theme);
			html = input;
			input.setType("password");
			input.setName(DrawDBHTMLHelper.buildName(this));
			input.setSize("" + getPresentation_width());
			input.setMaxlength("" + getLength());
			input.setValue(value);
			input.setReadonly("true");
		} else {
			HtmlDiv div = new HtmlDiv(theme);
			html = div;
			div.setContent(value);
		}
		return html;
	}

	public Html[] displayForView(CommonFormFields callingAction, Field field,
			String value, Theme theme) {
		// TODO Auto-generated method stub
		return null;
	}
}
