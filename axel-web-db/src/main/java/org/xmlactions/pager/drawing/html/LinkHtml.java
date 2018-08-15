
package org.xmlactions.pager.drawing.html;

import org.xmlactions.action.config.IExecContext;
import org.xmlactions.common.theme.Theme;
import org.xmlactions.db.actions.Link;
import org.xmlactions.pager.actions.form.CommonFormFields;
import org.xmlactions.pager.actions.form.Field;
import org.xmlactions.pager.actions.form.templates.Html;
import org.xmlactions.pager.actions.form.templates.HtmlBr;
import org.xmlactions.pager.actions.form.templates.HtmlDiv;
import org.xmlactions.pager.actions.form.templates.HtmlInput;
import org.xmlactions.pager.actions.form.templates.HtmlTd;
import org.xmlactions.pager.actions.form.templates.HtmlTr;
import org.xmlactions.pager.drawing.IDrawField;
import org.xmlactions.pager.drawing.html.DrawDBHTMLHelper;
import org.xmlactions.web.Uploader;



public class LinkHtml extends Link implements IDrawField
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

    public Html displayForList(IExecContext execContext, Field field, String value, Theme theme)
	{
		throw new IllegalArgumentException("check this code");
	}

	

	public HtmlTr[] displayForAdd(String value, Theme theme)
	{

		HtmlTr[] trs = new HtmlTr[1];
		HtmlTr tr = new HtmlTr(theme);
		trs[0] = tr;
		tr.addChild(DrawHtmlField.displayHeader(this, theme));
		HtmlTd td = tr.addTd(theme);
		td.addChild(buildAddHtml(value, theme));

		return (trs);
	}

	public HtmlTr [] displayForView(CommonFormFields callingAction, String value, Theme theme)
	{

		HtmlTr[] trs = new HtmlTr[1];
		HtmlTr tr = new HtmlTr(theme);
		trs[0] = tr;
		tr.addChild(DrawHtmlField.displayHeader(this, theme));
		HtmlTd td = tr.addTd(theme);
		td.addChild(buildViewHtml(value, theme));

		return (trs);
	}

	public HtmlTr[] displayForUpdate(String value, Theme theme)
	{

		HtmlTr[] trs = new HtmlTr[1];
		HtmlTr tr = new HtmlTr(theme);
		trs[0] = tr;
		tr.addChild(DrawHtmlField.displayHeader(this, theme));
		HtmlTd td = tr.addTd(theme);
		td.addChild(buildUpdateHtml(value, theme));

		return (trs);

	}

	public Html buildAddHtml(String value, Theme theme) {
		HtmlDiv div = new HtmlDiv(theme);
		HtmlInput input = div.addInput(theme);
		input.setType("text");
		input.setName(DrawDBHTMLHelper.buildName(this));
		input.setReadonly("true");
		input.setSize("" + getPresentation_width());
		input.setMaxlength("" + getLength());
		input.setValue(value);
		div.addChild(new HtmlBr());
		input = div.addInput(theme);
		input.setType("file");
		input.setName(Uploader.UPLOAD + DrawDBHTMLHelper.buildName(this));
		input.setId("upload");
		input.setValue("upload");
		return div;
	}

	public Html buildUpdateHtml(String value, Theme theme)
	{

		HtmlDiv div = new HtmlDiv(theme);
		HtmlInput input = div.addInput(theme);
		input.setType("text");
		input.setName(DrawDBHTMLHelper.buildName(this));
		input.setReadonly("true");
		input.setSize("" + getPresentation_width());
		input.setMaxlength("" + getLength());
		input.setValue(value);
		div.addChild(new HtmlBr());
		input = div.addInput(theme);
		input.setType("file");
		input.setName(Uploader.UPLOAD + DrawDBHTMLHelper.buildName(this));
		input.setId("upload");
		input.setValue("upload");

		return (div);
	}

	public Html buildViewHtml(String value, Theme theme)
	{

		HtmlDiv div = new HtmlDiv(theme);
		HtmlInput input = div.addInput(theme);
		input.setType("text");
		input.setName(DrawDBHTMLHelper.buildName(this));
		input.setReadonly("true");
		input.setSize("" + getPresentation_width());
		input.setMaxlength("" + getLength());
		input.setValue(value);
		div.addChild(new HtmlBr());
		input = div.addInput(theme);
		input.setType("file");
		input.setName(Uploader.UPLOAD + DrawDBHTMLHelper.buildName(this));
		input.setId("upload");
		input.setValue("upload");
		input.setReadonly("true");

		return (div);
	}

	public Html[] displayForView(CommonFormFields callingAction, Field field,
			String value, Theme theme) {
		// TODO Auto-generated method stub
		return null;
	}
}
