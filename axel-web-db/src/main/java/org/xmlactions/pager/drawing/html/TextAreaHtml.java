
package org.xmlactions.pager.drawing.html;

import org.xmlactions.action.config.IExecContext;
import org.xmlactions.common.theme.Theme;
import org.xmlactions.db.actions.CommonStorageField;
import org.xmlactions.db.actions.TextArea;
import org.xmlactions.pager.actions.form.CommonFormFields;
import org.xmlactions.pager.actions.form.Field;
import org.xmlactions.pager.actions.form.Link;
import org.xmlactions.pager.actions.form.ThemeConst;
import org.xmlactions.pager.actions.form.templates.Html;
import org.xmlactions.pager.actions.form.templates.HtmlDiv;
import org.xmlactions.pager.actions.form.templates.HtmlTd;
import org.xmlactions.pager.actions.form.templates.HtmlTextArea;
import org.xmlactions.pager.actions.form.templates.HtmlTr;
import org.xmlactions.pager.drawing.IDrawField;
import org.xmlactions.pager.drawing.IDrawParams;
import org.xmlactions.pager.drawing.html.DrawDBHTMLHelper;



public class TextAreaHtml extends TextArea implements IDrawField
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

		HtmlTr tr = new HtmlTr(theme);
		tr.addChild(DrawHtmlField.displayHeader(this, theme));
		HtmlTd td = tr.addTd(theme);
		HtmlTextArea textArea = new HtmlTextArea(theme);
		td.addChild(textArea);
		textArea.setName(DrawDBHTMLHelper.buildName(this));
		// FIXME - use different id than tinymce.
		textArea.setId("tinymce");
		textArea.setCols("" + getPresentation_width());
		textArea.setRows("" + getPresentation_height());
		textArea.setContent(value);
		return tr;
	}

	public HtmlTr[] displayForAdd(String value, Theme theme)
	{

		HtmlTd td = new HtmlTd(theme);
		if (isEditable() == true) {
			HtmlTextArea textArea = new HtmlTextArea(theme);
			td.addChild(textArea);
			textArea.setName(DrawDBHTMLHelper.buildName(this));
			// FIXME - use different id than tinymce.
			textArea.setId("tinymce");
			textArea.setCols("" + getPresentation_width());
			textArea.setRows("" + getPresentation_height());
			textArea.setContent(value);
		} else {
			td.setContent(value);
		}
        return DrawDBHTMLHelper.buildDisplay(theme, getLabelPosition(), DrawHtmlField.displayHeader(this, theme), td);

	}

	public HtmlTr[] displayForUpdate(String value, Theme theme)
	{

		HtmlTr[] trs = new HtmlTr[1];
		HtmlTr tr = new HtmlTr(theme);
		trs[0] = tr;

		tr.addChild(DrawHtmlField.displayHeader(this, theme));
		HtmlTd td = tr.addTd(theme);
		HtmlTextArea textArea = new HtmlTextArea(theme);
		td.addChild(textArea);
		textArea.setName(DrawDBHTMLHelper.buildName(this));
		// FIXME - use different id than tinymce.
		textArea.setId("tinymce");
		textArea.setCols("" + getPresentation_width());
		textArea.setRows("" + getPresentation_height());
		textArea.setContent(value);
		return trs;
	}

	public Html [] displayForView(CommonFormFields callingAction, Field field, String value, Theme theme)
	{
		HtmlTr[] trs;
		if (DrawDBHTMLHelper.isPositionAbove(getLabelPosition())) {
			trs = new HtmlTr[2];
		} else {
			trs = new HtmlTr[1];
		}

		HtmlTr tr = new HtmlTr(theme);
		HtmlTd td = tr.addTd(theme);
		td.setClazz(theme.getValue(ThemeConst.VIEW_LABEL.toString()));
		td.setContent(DrawHtmlField.buildPresentationName(getPresentation_name(), ((CommonStorageField)this).isMandatory(),((CommonStorageField)this).isUnique()));
        td.setTitle(getTooltip());
		trs[0] = tr;

		if (DrawDBHTMLHelper.isPositionAbove(getLabelPosition())) {
			tr = new HtmlTr(theme);
			td = tr.addTd(theme);
			td.addChild(drawTextAreaForView(theme, value));
			//td.setClazz(theme.getValue(ThemeConst.VIEW_CONTENT.toString()));
			trs[1] = tr;
		} else {
			td = tr.addTd(theme);
			td.addChild(drawTextAreaForView(theme, value));
			td.setAlign("left");
			td.setClazz(theme.getValue(ThemeConst.VIEW_CONTENT.toString()));
		}
		return trs;
	}

	private Html drawTextAreaForView(Theme theme, String value) {
		HtmlTextArea textArea = new HtmlTextArea(theme);
		textArea.setName(DrawDBHTMLHelper.buildName(this));
		textArea.setCols("" + getPresentation_width());
		textArea.setRows("" + getPresentation_height());
		textArea.setContent(value);
		textArea.setReadonly("true");
		textArea.setDisabled("true");
		return textArea;
	}

    public Html displayForList(IExecContext execContext, Field field, String value, Theme theme)
	{
        if (field.getCode() != null) {
            try {
                value = field.getCode().execute(execContext);
            } catch (Exception e) {
                throw new IllegalArgumentException(e.getMessage(), e);
            }
        }

        Html html = null;
        Link link = field.getLink();
        if (link != null) {
            // FIXME draw link here.
            html = link.draw(execContext, theme);
            html.setContent(value);
        } else {
            html = new Html("");
            html.setContent(value);
        }
        return html;
	}
    
    public Html _displayForList(IExecContext execContext, Field field, String value, Theme theme)
	{
        Html html = null;
        Link link = field.getLink();

        if (field.getCode() != null) {
            try {
                value = field.getCode().execute(execContext);
            } catch (Exception e) {
                throw new IllegalArgumentException(e.getMessage(), e);
            }
        }

        if (link != null) {
            // FIXME draw link here.
            html = link.draw(execContext, theme);
            // html.setContent(value);
        } else {
            HtmlTextArea textArea = new HtmlTextArea(theme);
            textArea.setName(DrawDBHTMLHelper.buildName(this));
            // FIXME - use different id than tinymce.
            textArea.setReadonly("true");
            textArea.setId("tinymce");
            if (field != null && field.getWidth() != null) {
                textArea.setCols("" + field.getWidth());
            } else {
                textArea.setCols("" + getPresentation_width());
            }
            if (field != null && field.getHeight() != null) {
                textArea.setRows("" + field.getHeight());
            } else {
                textArea.setRows("" + getPresentation_height());
            }
            textArea.setContent(value);
            if (field.getTooltip() != null) {
                textArea.setTitle(field.getTooltip());
            } else {
                textArea.setTitle(getTooltip());
            }
            html = textArea;
        }
        return html;
	}
	public String displayForList(IDrawParams params, String value, Theme theme)
	{
		// TODO Auto-generated method stub
		return null;
	}


	public Html buildAddHtml(String value, Theme theme) {
		Html html;
		if (isEditable() == true) {
			HtmlTextArea textArea = new HtmlTextArea(theme);
			html = textArea;
			textArea.setName(DrawDBHTMLHelper.buildName(this));
			// FIXME - use different id than tinymce.
			textArea.setId("tinymce");
			textArea.setCols("" + getPresentation_width());
			textArea.setRows("" + getPresentation_height());
			textArea.setContent(value);
		} else {
			HtmlDiv div = new HtmlDiv(theme);
			html = div;
			div.setContent(value);
		}
		return html;
	}

	public Html buildUpdateHtml(String value, Theme theme)
	{
		return DrawInputFieldUtils.buildInputForText(this, value, theme);
	}

	public Html buildViewHtml(String value, Theme theme) {
		return DrawInputFieldUtils.buildInputForView(this, value, theme);
	}
}
