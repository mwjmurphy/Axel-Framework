
package org.xmlactions.pager.drawing.html;

import org.xmlactions.action.config.IExecContext;
import org.xmlactions.common.theme.Theme;
import org.xmlactions.db.actions.PK;
import org.xmlactions.pager.actions.form.CommonFormFields;
import org.xmlactions.pager.actions.form.Field;
import org.xmlactions.pager.actions.form.Link;
import org.xmlactions.pager.actions.form.ThemeConst;
import org.xmlactions.pager.actions.form.templates.Html;
import org.xmlactions.pager.actions.form.templates.HtmlInput;
import org.xmlactions.pager.actions.form.templates.HtmlTd;
import org.xmlactions.pager.actions.form.templates.HtmlTr;
import org.xmlactions.pager.drawing.IDrawField;
import org.xmlactions.pager.drawing.IDrawParams;
import org.xmlactions.pager.drawing.html.DrawDBHTMLHelper;



public class PKHtml extends PK implements IDrawField
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
		HtmlInput input = td.addInput(theme);
		input.setType("text");
		input.setName(DrawDBHTMLHelper.buildName(this));
		input.setValue(value);
		return tr;

	}

	public HtmlTr[] displayForAdd(String value, Theme theme)
	{

		return new HtmlTr[0]; // PK is only created when inserted into db, never by operator
	}

	public HtmlTr[] displayForUpdate(String value, Theme theme)
	{
        return DrawHtmlField.displayForUpdate(this, value, getLabelPosition(), theme);
		/*
		HtmlTr[] trs = new HtmlTr[1];
		HtmlTr tr = new HtmlTr(theme);
		trs[0] = tr;

		// need this when updating the data.
		HtmlInput input = tr.addInput(theme);
		input.setDisabled("true");	// can see but cant edit
		//input.setType("hidden");
		input.setName(DrawDBHTMLHelper.buildName(this));
		input.setValue(value);

		tr.addChild(DrawHtmlField.displayHeader(this, theme));
		HtmlTd td = tr.addTd(theme);
		td.setValign(value);
		return trs;
		*/
	}

    public Html displayForList(IExecContext execContext, Field field, String value, Theme theme)
	{
    	/**
    	 *   <a onclick = "submitLinkWithParams('l1','searchwithlist.xhtml');return false;" class = "none" href = "null">
                            <span class = "link_menu">go</span>
                        </a>
    	 */
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
            html.setContent(value);
        } else {
            html = new Html("");
            html.setContent(value);
    	}
        return html;
	}

	public String displayForList(IDrawParams params, String value, Theme theme)
	{

		return value;
		// StringBuilder sb = new StringBuilder();
		// sb.append("<td " + theme.getTheme("td") + ">");
		// sb.append(value);
		// sb.append("</td>");
		// return (sb.toString());
	}

	public HtmlTr [] displayForView(CommonFormFields callingAction, String value, Theme theme)
	{
		HtmlTr[] trs;
		if (DrawDBHTMLHelper.isPositionAbove(getLabelPosition())) {
			trs = new HtmlTr[2];
		} else {
			trs = new HtmlTr[1];
		}
		HtmlTr tr = new HtmlTr(theme);
		HtmlTd td = tr.addTd(theme);
        td.setContent(DrawHtmlField.buildPresentationName(getPresentation_name(), isMandatory(), isUnique()));
        td.setClazz(theme.getValue(ThemeConst.VIEW_LABEL.toString()));
        td.setTitle(getTooltip());
		trs[0] = tr;
		
		if (DrawDBHTMLHelper.isPositionAbove(getLabelPosition())) {
			tr = new HtmlTr(theme);
			td = tr.addTd(theme);
			td.setContent(value);
            td.setClazz(theme.getValue(ThemeConst.VIEW_CONTENT.toString()));
			trs[1] = tr;
		} else {
			td = tr.addTd(theme);
			td.setContent(value);
            td.setClazz(theme.getValue(ThemeConst.VIEW_CONTENT.toString()));
			td.setAlign("left");
		}
		return trs;

	}

	public Html buildAddHtml(String value, Theme theme) {
		return new Html("PK");
	}

	public Html buildUpdateHtml(String value, Theme theme)
	{

		// need this when updating the data.
		HtmlInput input = new HtmlInput(theme);
		input.setType("hidden");
		input.setName(DrawDBHTMLHelper.buildName(this));
		input.setValue(value);

		return input;
	}

	public Html buildViewHtml(String value, Theme theme) {
		// need this when updating the data.
		HtmlInput input = new HtmlInput(theme);
		input.setType("hidden");
		input.setName(DrawDBHTMLHelper.buildName(this));
		input.setValue(value);

		return input;
	}
	
	public Html[] displayForView(CommonFormFields callingAction, Field field,
			String value, Theme theme) {
		// TODO Auto-generated method stub
		return null;
	}

	public String toString() {
		return (String.format("DB PK name:%s", this.getName()));
	}
}
