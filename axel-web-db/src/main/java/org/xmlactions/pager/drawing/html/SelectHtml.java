
package org.xmlactions.pager.drawing.html;


import org.apache.commons.lang.StringUtils;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.common.theme.Theme;
import org.xmlactions.common.xml.XMLObject;
import org.xmlactions.db.actions.CommonStorageField;
import org.xmlactions.db.actions.Select;
import org.xmlactions.pager.actions.form.CommonFormFields;
import org.xmlactions.pager.actions.form.Field;
import org.xmlactions.pager.actions.form.Link;
import org.xmlactions.pager.actions.form.ThemeConst;
import org.xmlactions.pager.actions.form.templates.Html;
import org.xmlactions.pager.actions.form.templates.HtmlBr;
import org.xmlactions.pager.actions.form.templates.HtmlDiv;
import org.xmlactions.pager.actions.form.templates.HtmlInput;
import org.xmlactions.pager.actions.form.templates.HtmlSpan;
import org.xmlactions.pager.actions.form.templates.HtmlTd;
import org.xmlactions.pager.actions.form.templates.HtmlTr;
import org.xmlactions.pager.drawing.IDrawField;
import org.xmlactions.pager.drawing.html.DrawDBHTMLHelper;


public class SelectHtml extends Select implements IDrawField
{

	private static final String ALIGN = "left";

    private String label_position;
    private String select = "single";
    private Field field;

	public HtmlTr displayForSearch(String value, Theme theme)
	{

		return DrawHtmlField.displayForSearch(this, value, theme);
	}

	public HtmlTr[] displayForAdd(String value, Theme theme)
	{

        return displayForAdd(this, value, label_position, theme);
	}

	public HtmlTr[] displayForView(CommonFormFields callingAction, String value, Theme theme)
	{

		return DrawHtmlField.displayForView(callingAction, this, value, label_position, theme);
	}

	public HtmlTr[] displayForUpdate(String value, Theme theme)
	{

		return DrawHtmlField.displayForUpdate(this, value, label_position, theme);
	}
	

	public void setLabelPosition(String position)
	{

		this.label_position = position;

	}

    public Html displayForList(IExecContext execContext, Field field, String value, Theme theme) {
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

	public Html buildAddHtml(String value, Theme theme) {
		return DrawInputFieldUtils.buildInputForText(this, value, theme);
	}

	public Html buildUpdateHtml(String value, Theme theme)
	{
		return DrawInputFieldUtils.buildInputForText(this, value, theme);
	}

    private HtmlTr[] displayForAdd(CommonStorageField commonStorageField, String value,
            String label_position,
			Theme theme)
	{

        if (getHtmlField() == null || getHtmlField().getPopulatorXml() == null) {
            throw new IllegalArgumentException("Error:" + this.getClass().getSimpleName()
                    + " - The xml populator has not been set.");
            // throw new IllegalArgumentException("Error:" +
            // this.getClass().getSimpleName() + " - "
            // + execContext.getLocalizedString("config/lang/pager",
            // "SelectHtml.no_field_populator_xml"));

        }
        XMLObject root = new XMLObject().mapXMLCharToXMLObject(getHtmlField().getPopulatorXml());
        HtmlTd td = new HtmlTd(theme);
        if (getHtmlField().getAlign() != null) {
            td.setAlign(getHtmlField().getAlign());
        } else {
            td.setAlign("left");
        }
        td.setContent("");
	    for (XMLObject option : root.getChildren()) {
        	HtmlSpan span = new HtmlSpan();
            HtmlInput input = span.addInput(theme);
            span.setClazz(theme.getValue(ThemeConst.INPUT_CHECK.toString()));
            if (DrawDBHTMLHelper.isDirectionDown(getDirection())) {
            	HtmlBr br = new HtmlBr();
            	br.addChild(span);
                td.addChild(br);
            } else {
                td.addChild(span);
            }
            if (isMultiSelect()) {
                input.setType("checkbox");
            } else {
                input.setType("radio");
            }
    		input.setName(DrawDBHTMLHelper.buildName(commonStorageField));
            if (option.getAttributeCount() > 0) {
                input.setValue(option.getAttributeAsString(0));
            }
            if (option.getAttributeCount() > 1) {
            	HtmlDiv div = input.addDiv(theme);
            	
                input.setContent(option.getAttributeAsString(1));
            }
            if (option.getAttributeCount() > 2) {
                input.setTitle(option.getAttributeAsString(2));
            }
	    }
		return DrawDBHTMLHelper.buildDisplay(theme, label_position, DrawHtmlField.displayHeader(commonStorageField, theme), td);

	}

	private String getDirection() {
        String direction = "down";// default value
        if (getHtmlField() != null) {
            if (StringUtils.isNotEmpty(getHtmlField().getDirection())) {
                direction = getHtmlField().getDirection();
            }
	    }
        return direction;
	}

    public boolean isMultiSelect() {
        if (getSelect() != null && getSelect().equalsIgnoreCase("single")) {
            return false;
        }
        return true;
    }

    public boolean isSingleSelect() {
        if (getSelect() != null && getSelect().equalsIgnoreCase("multiple")) {
            return false;
        }
        return true;
    }

    public void setSelect(String select) {
        this.select = select;
    }

    public String getSelect() {
        return select;
    }
    
    public Field getHtmlField() {
        return field;
    }

    public void setHtmlField(Field field) {
        this.field = field;
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
