
package org.xmlactions.pager.drawing.html;


import org.apache.commons.lang.StringUtils;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.common.theme.Theme;
import org.xmlactions.db.actions.CommonStorageField;
import org.xmlactions.pager.actions.form.CommonFormFields;
import org.xmlactions.pager.actions.form.Field;
import org.xmlactions.pager.actions.form.ThemeConst;
import org.xmlactions.pager.actions.form.templates.Html;
import org.xmlactions.pager.actions.form.templates.HtmlInput;
import org.xmlactions.pager.actions.form.templates.HtmlOption;
import org.xmlactions.pager.actions.form.templates.HtmlSelect;
import org.xmlactions.pager.actions.form.templates.HtmlTd;
import org.xmlactions.pager.actions.form.templates.HtmlTh;
import org.xmlactions.pager.actions.form.templates.HtmlTr;
import org.xmlactions.pager.drawing.html.DrawDBHTMLHelper;


/**
 * Static methods for drawing html fields
 * 
 * @author mike
 * 
 */
public class DrawHtmlField
{

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
        td.setAlign("left");
        HtmlInput input = td.addInput(theme);
        input.setType("text");
        input.setName(name);
        input.setSize("" + commonStorageField.getPresentation_width());
        if (commonStorageField.getLength() > 0) {
            input.setMaxlength("" + commonStorageField.getLength());
        }
        input.setValue(value);
        if (commonStorageField.isMandatory()) {
        	input.setRequired("true");
        }
        if (!StringUtils.isBlank(commonStorageField.getPattern())) {
        	input.setPattern(commonStorageField.getPattern());
        }
        if (!StringUtils.isBlank(commonStorageField.getPlaceholder())) {
        	input.setPlaceholder(commonStorageField.getPlaceholder());
        }
        if (!StringUtils.isBlank(commonStorageField.getTooltip())) {
        	input.setTitle(commonStorageField.getTooltip());
        }

        return tr;
    }

	public static HtmlTr[] displayForAdd(CommonStorageField commonStorageField, String value, String label_position,
			Theme theme)
	{

		HtmlTd td = new HtmlTd(theme);
		td.setAlign("left");
		HtmlInput input = td.addInput(theme);
		input.setType("text");
		input.setName(DrawDBHTMLHelper.buildName(commonStorageField));
		input.setSize("" + commonStorageField.getPresentation_width());
        if (commonStorageField.getLength() > 0) {
            input.setMaxlength("" + commonStorageField.getLength());
        }
		input.setValue(value);
        if (commonStorageField.isMandatory()) {
        	input.setRequired("true");
        }
        if (!StringUtils.isBlank(commonStorageField.getPattern())) {
        	input.setPattern(commonStorageField.getPattern());
        }
        if (!StringUtils.isBlank(commonStorageField.getPlaceholder())) {
        	input.setPlaceholder(commonStorageField.getPlaceholder());
        }
        if (!StringUtils.isBlank(commonStorageField.getTooltip())) {
        	input.setTitle(commonStorageField.getTooltip());
        }

		return DrawDBHTMLHelper.buildDisplay(theme, label_position, displayHeader(commonStorageField, theme), td);

	}

	// public static HtmlTr[] displayForView(CommonStorageField commonStorageField, String value, String label_position, Theme theme) {
	public static HtmlTr[] displayForView(CommonFormFields callingAction, CommonStorageField commonStorageField, String value, String label_position, Theme theme) {
		HtmlTr[] trs;
		if (DrawDBHTMLHelper.isPositionAbove(label_position)) {
			trs = new HtmlTr[2];
		} else {
			trs = new HtmlTr[1];
		}
		HtmlTr tr = new HtmlTr(theme);
		HtmlTd td = tr.addTd(theme);
		td.setContent(DrawHtmlField.buildPresentationName(commonStorageField.getPresentation_name(), commonStorageField.isMandatory(),commonStorageField.isUnique()));
		td.setClazz(theme.getValue(ThemeConst.VIEW_LABEL.toString()));
        td.setTitle(commonStorageField.getTooltip());
		trs[0] = tr;
		
		if (DrawDBHTMLHelper.isPositionAbove(label_position)) {
			tr = new HtmlTr(theme);
			td = tr.addTd(theme);
			td.setContent(value);
			td.setClazz(theme.getValue(ThemeConst.VIEW_CONTENT.toString()));
			td.setHeight(getHeight(commonStorageField));
			// set height here
			trs[1] = tr;
		} else {
			td = tr.addTd(theme);
			td.setContent(value);
			td.setClazz(theme.getValue(ThemeConst.VIEW_CONTENT.toString()));
			td.setAlign("left");
			td.setHeight(getHeight(commonStorageField));
			// set height here.
		}
		return trs;
		//td.setAlign("left");
		//td.setContent(value);
		//HtmlInput input = DrawInputFieldUtils.buildInputForText(commonStorageField, value, theme);
		//input.setReadonly("true");
		//td.addChild(input);
		//return DrawDBHTMLHelper.buildDisplay(theme, label_position, displayHeader(commonStorageField, theme), td);

	}

    public static Html displayForList(CommonStorageField commonStorageField, String value, Theme theme)
	{

        Html html = new Html("");
        html.setContent(value);
        return html;
	}

	public static HtmlTr[] displayForUpdate(CommonStorageField commonStorageField, String value, String label_position,
			Theme theme)
	{

		HtmlTd td = new HtmlTd(theme);
		td.setAlign("left");
		HtmlInput input = DrawInputFieldUtils.buildInputForText(commonStorageField, value, theme);
		td.addChild(input);
		return DrawDBHTMLHelper.buildDisplay(theme, label_position, displayHeader(commonStorageField, theme), td);
	}

	/**
	 * 
	 * @param commonStorageField
	 * @param theme
	 * @return HtmlTh
	 */
	public static HtmlTd displayHeader(CommonStorageField commonStorageField, Theme theme)
	{
		String tooltip = "";
		if (!StringUtils.isBlank(commonStorageField.getTooltip())) {
			tooltip += commonStorageField.getTooltip();
		}
		if (commonStorageField.isMandatory() || commonStorageField.isUnique()) {
			tooltip += " - This field is ";
			if (commonStorageField.isMandatory() && commonStorageField.isUnique()) {
				tooltip += "mandatory and unique";
			} else if (commonStorageField.isMandatory()) {
				tooltip += "mandatory";
			} else if (commonStorageField.isUnique()) {
				tooltip += "unique";
			}
		}
		return buildDisplayHeader(theme, tooltip, commonStorageField.getPresentation_name(), commonStorageField
				.isMandatory(), commonStorageField.isUnique());
	}

	/**
	 * 
	 * @param theme
	 * @param tooltip
	 * @param presentationName
	 * @param isMandatory
	 * @param isUnique
	 * @return HtmlTh
	 */
	public static HtmlTd buildDisplayHeader(Theme theme, String tooltip, String presentationName, boolean isMandatory,
			boolean isUnique)
	{

		HtmlTd td = new HtmlTd(theme);
		if (StringUtils.isNotEmpty(tooltip)) {
			td.setTitle(tooltip);
		}
		td.setAlign("left");
		td.setClazz(theme.getValue(ThemeConst.VIEW_LABEL.toString()));
		td.setContent(buildPresentationName(presentationName, isMandatory, isUnique));
		return td;
	}
	
	public static String buildPresentationName(String presentationName, boolean isMandatory, boolean isUnique)
	{
		return presentationName + (isMandatory == true ? "<small>*</small>" : "")
				+ (isUnique == true ? "<small>*</small>" : "");
		
	}

	/**
	 * Builds a html select drop down list.
	 * @param execContext
	 * @param commonStorageField
	 * @param selectId the select element id used if we want to ajax redraw the select.
	 * @param label_position
	 * @param theme
	 * @param ids
	 * @param labels
	 * @param selected
	 * @return
	 */
	public static HtmlTr[] displayForSelect(IExecContext execContext, CommonStorageField commonStorageField, String selectId, String selectIdentifier, String label_position,
			Theme theme, String [] ids, String [] labels, String [] tooltips, String selected)
	{
		HtmlSelect select = buildSelect(theme,commonStorageField.getTooltip(execContext), selectIdentifier, ids, labels, tooltips, selected);
		select.setId(selectId);
		return displayForSelect(execContext, commonStorageField, label_position, theme, select);
	}

	/**
	 * Builds a html select drop down list into a tr.
	 * @param execContext
	 * @param commonStorageField
	 * @param label_position
	 * @param theme
	 * @param select
	 * @return
	 */
	public static HtmlTr[] displayForSelect(IExecContext execContext, CommonStorageField commonStorageField, String label_position,
			Theme theme, HtmlSelect select )
	{

		HtmlTd td = new HtmlTd(theme);
		td.addChild(select);
		return DrawDBHTMLHelper.buildDisplay(theme, label_position, displayHeader(commonStorageField, theme), td);

	}

	public static HtmlSelect buildSelect(Theme theme, String tooltip, String presentationName, String [] ids, String [] labels, String [] tooltips, String selected) {
		
		HtmlSelect select = new HtmlSelect();
		select.setClazz(theme.getValue(ThemeConst.INPUT_SELECT.toString()));
		select.setName(presentationName);
		if (StringUtils.isNotEmpty(tooltip)) {
			select.setTitle(tooltip);
		}
		for (int index = 0 ; index < ids.length; index++) {
			String id = ids[index];
			String label = labels[index];
			String title = null;
			if (tooltips != null) {
				title = tooltips[index];
			}
			HtmlOption option = new HtmlOption();
			if (id.equals(selected)) {
				option.setSelected("selected");
			}
			option.setValue(id);
			option.setId(id);
			option.setLabel(label);
			option.setContent(label);
			option.setTitle(title);
			select.addChild(option);			
		}
		return select;
	}
	
	private static String getHeight(CommonStorageField commonStorageField) {
		//Field field = commonStorageField.getHtmlField();
		//if (field != null && StringUtils.isNotEmpty(field.getHeight())) {
		//	return field.getHeight();
		//}
		return null;
	}
}
