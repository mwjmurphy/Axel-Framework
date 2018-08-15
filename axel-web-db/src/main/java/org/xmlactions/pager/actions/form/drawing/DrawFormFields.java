package org.xmlactions.pager.actions.form.drawing;


import java.util.HashMap;
import java.util.List;
import java.util.Map;





import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.text.StrSubstitutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.common.theme.Theme;
import org.xmlactions.common.xml.XMLAttribute;
import org.xmlactions.common.xml.XMLObject;
import org.xmlactions.db.actions.Binary;
import org.xmlactions.db.actions.CommonStorageField;
import org.xmlactions.db.actions.Table;
import org.xmlactions.db.actions.TextArea;
import org.xmlactions.pager.actions.form.CommonFormFields;
import org.xmlactions.pager.actions.form.Field;
import org.xmlactions.pager.actions.form.Link;
import org.xmlactions.pager.actions.form.ThemeConst;
import org.xmlactions.pager.actions.form.templates.Html;
import org.xmlactions.pager.actions.form.templates.HtmlA;
import org.xmlactions.pager.actions.form.templates.HtmlDiv;
import org.xmlactions.pager.actions.form.templates.HtmlEnum;
import org.xmlactions.pager.actions.form.templates.HtmlInput;
import org.xmlactions.pager.actions.form.templates.HtmlSpan;
import org.xmlactions.pager.actions.form.templates.HtmlTable;
import org.xmlactions.pager.actions.form.templates.HtmlTd;
import org.xmlactions.pager.actions.form.templates.HtmlTextArea;
import org.xmlactions.pager.actions.form.templates.HtmlTh;
import org.xmlactions.pager.actions.form.templates.HtmlTr;
import org.xmlactions.pager.drawing.html.BinaryHtml;
import org.xmlactions.pager.drawing.html.DrawDBHTMLHelper;
import org.xmlactions.pager.drawing.html.DrawHtmlField;
import org.xmlactions.pager.drawing.html.SelectHtml;
import org.xmlactions.pager.drawing.html.TextAreaHtml;


public abstract class DrawFormFields extends CommonFormFields {
	
	private static final Logger logger = LoggerFactory.getLogger(DrawFormFields.class);

	private static final String
	    LABEL = "label",
		VALUE = "value",
		TOOLTIP = "tooltip",
		NAME = "name",
		ID = "id",
		HEIGHT = "height",
		WIDTH = "width",
		COLS = "cols",
		ROWS = "rows";


	public Html [] displayForView(IExecContext execContext, String row_map_name, CommonFormFields formField, CommonStorageField storageField, String value, Theme theme) {
		Map<String, Object> dataMap = buildDataMap(execContext, formField, storageField, value);
		if (storageField instanceof TextArea) {
            return displayViewForTextArea(execContext, row_map_name, dataMap, formField, storageField, value, theme);
		} else if (storageField instanceof Binary){
            return displayViewForBinary(execContext, row_map_name, dataMap, formField, storageField, value, theme);
		} else {
            return displayViewForText(execContext, row_map_name, dataMap, formField, storageField, value, theme);
		}
	}
	public Html [] displayForList(IExecContext execContext, String row_map_name, CommonFormFields formField, CommonStorageField storageField, String value, Theme theme) {
		Map<String, Object> dataMap = buildDataMapForList(execContext, formField, storageField, value);
		if (storageField instanceof TextArea) {
            return displayListForTextArea(execContext, row_map_name, dataMap, formField, storageField, value, theme);
		} else if (storageField instanceof BinaryHtml){
            return displayListForBinary(execContext, row_map_name, dataMap, formField, (BinaryHtml)storageField, value, theme);
		} else {
            return displayListForText(execContext, row_map_name, dataMap, formField, storageField, value, theme);
		}
	}
	public Html [] displayForAdd(IExecContext execContext, String row_map_name, CommonFormFields formField, CommonStorageField storageField, String value, Theme theme) {
		Map<String, Object> dataMap = buildDataMap(execContext, formField, storageField, value);
		if (storageField instanceof TextArea) {
            return displayAddForTextArea(execContext, row_map_name, formField, storageField, value, theme, true);
		} else if (storageField instanceof BinaryHtml) {
            return displayAddForBinary(execContext, row_map_name, formField, (BinaryHtml) storageField, value, theme, true);
		} else if (storageField instanceof SelectHtml) {
            return displayAddForSelect(execContext, row_map_name, formField, (SelectHtml) storageField, value, theme, true);
		} else {
            return displayAddForText(execContext, row_map_name, formField, storageField, value, theme, true);
		}
	}
	
	public Html [] displayForEdit(IExecContext execContext, String row_map_name, CommonFormFields formField, CommonStorageField storageField, String value, Theme theme) {
		Map<String, Object> dataMap = buildDataMap(execContext, formField, storageField, value);
		if (storageField instanceof TextArea) {
            return displayAddForTextArea(execContext, row_map_name, formField, storageField, value, theme, true);
		} else if (storageField instanceof BinaryHtml) {
            return displayAddForBinary(execContext, row_map_name, formField, (BinaryHtml) storageField, value, theme, true);
		} else if (storageField instanceof SelectHtml) {
            return displayAddForSelect(execContext, row_map_name, formField, (SelectHtml) storageField, value, theme, true);
		} else {
            return displayAddForText(execContext, row_map_name, formField, storageField, value, theme, true);
		}
	}
	
	public Html [] displayForSearch(IExecContext execContext, String row_map_name, CommonFormFields formField, CommonStorageField storageField, String value, Theme theme) {
		Map<String, Object> dataMap = buildDataMap(execContext, formField, storageField, value);
		if (storageField instanceof TextArea) {
            return displayAddForTextArea(execContext, row_map_name, formField, storageField, value, theme, false);
		} else if (storageField instanceof BinaryHtml) {
            return displayAddForBinary(execContext, row_map_name, formField, (BinaryHtml) storageField, value, theme, false);
		} else if (storageField instanceof SelectHtml) {
            return displayAddForSelect(execContext, row_map_name, formField, (SelectHtml) storageField, value, theme, false);
		} else {
            return displayAddForText(execContext, row_map_name, formField, storageField, value, theme, false);
		}
	}
	
	public Html buildInputForEdit(IExecContext execContext, String row_map_name, CommonFormFields formField, CommonStorageField storageField, String value, Theme theme) {
		Map<String, Object> dataMap = buildDataMap(execContext, formField, storageField, value);
		if (storageField instanceof TextArea) {
            return buildTextAreaInputForAddAndEdit(execContext, row_map_name, formField, storageField, value, theme);
		} else if (storageField instanceof SelectHtml) {
			return null;
            //return displayAddForSelect(execContext, formField, (SelectHtml) storageField, value, theme);
		} else {
            return buildTextInputForAddAndEdit(execContext, formField, storageField, value, theme);
		}
	}
	
	private String getHeight(CommonFormFields formField, CommonStorageField storageField) {
		if (formField.getHeight() != null) {
			return formField.getHeight();
		} else if (storageField.getPresentation_height() > 0) {
			return "" + storageField.getPresentation_height();
		} else {
			return null;
		}
	}

    private String getWidth(CommonFormFields formField, CommonStorageField storageField) {
        if (formField.getWidth() != null) {
            return formField.getWidth();
        } else if (storageField.getPresentation_width() > 0) {
            return "" + storageField.getPresentation_width();
        } else {
            return null;
        }
    }

    private String getWidthForList(CommonFormFields formField, CommonStorageField storageField) {
        if (formField.getWidth() != null) {
            return formField.getWidth();
        //} else if (storageField.getPresentation_width() > 0) {
        //    return "" + storageField.getPresentation_width();
        } else {
            return null;
        }
    }

    private String getWidth(String firstChoice, String secondChoice) {
        if (firstChoice != null && firstChoice.trim().length() > 0) {
            return firstChoice;
        } else if (secondChoice != null && secondChoice.trim().length() > 0) {
            return secondChoice;
        } else {
            return null;
        }
    }

	public static String getTooltip(CommonFormFields formField, CommonStorageField storageField) {
		if (formField.getTooltip() != null){
			return formField.getTooltip();
		} else if (storageField.getTooltip() != null){
			return storageField.getTooltip();
		}
		return null;
	}

	public static String getPresentation_name(CommonFormFields formField, CommonStorageField storageField){
		if (formField.getPresentation_name() != null){
			return formField.getPresentation_name();
		} else if (storageField.getPresentation_name() != null){
			return storageField.getPresentation_name();
		}
		return "";	// should always be set but just in case.
	}

	private String getLabel_position(CommonFormFields formField, CommonStorageField storageField) {
		if (formField.getLabel_position() != null){
			return formField.getLabel_position();
		}
		return getLabel_position();
	}

	private String getDirection(CommonFormFields formField, CommonStorageField storageField) {
        String direction = "down";// default value
		if (formField.getDirection() != null){
			return formField.getDirection();
		}
        return direction;
	}

	
	
	
    private Html[] displayViewForTextArea(IExecContext execContext, String row_map_name, Map<String, Object> dataMap, CommonFormFields formField,
            CommonStorageField storageField, String value, Theme theme)
	{
		HtmlTr[] trs;
		if (DrawDBHTMLHelper.isPositionAbove(getLabel_position(formField, storageField))) {
			trs = new HtmlTr[2];
		} else {
			trs = new HtmlTr[1];
		}

		HtmlTr tr = new HtmlTr(theme);
		HtmlTd td = tr.addTd(theme);
		td.setClazz(theme.getValue(ThemeConst.VIEW_LABEL.toString()));
		td.setContent((String)dataMap.get(LABEL));
        td.setTitle((String)dataMap.get(TOOLTIP));
		trs[0] = tr;

		if (DrawDBHTMLHelper.isPositionAbove(getLabel_position(formField, storageField))) {
			tr = new HtmlTr(theme);
			td = tr.addTd(theme);
			td.addChild(drawTextAreaForView(dataMap, theme));
			td.setTitle((String)dataMap.get(TOOLTIP));
			trs[1] = tr;
		} else {
			td = tr.addTd(theme);
			td.addChild(drawTextAreaForView(dataMap, theme));
			td.setTitle((String)dataMap.get(TOOLTIP));
			td.setAlign("left");
			td.setClazz(theme.getValue(ThemeConst.VIEW_CONTENT.toString()));
		}
        /**
         * Add the value and label to execContext so they can be retrieved by presentationForm
         */
		addToExecContext(execContext, row_map_name, buildName(storageField), value, getPresentation_name(formField, storageField), (String)dataMap.get(TOOLTIP));
		return trs;
	}
	
    private Html[] displayListForTextArea(IExecContext execContext, String row_map_name, Map<String, Object> dataMap, CommonFormFields formField,
            CommonStorageField storageField, String value, Theme theme)
	{
        String tooltip = execContext.replace(getTooltip(formField, storageField));
		Html [] html = new Html[1];
		HtmlTd td = new HtmlTd();
		html[0] = td;
		td.setClazz(theme.getValue(ThemeConst.VIEW_LABEL.toString()));
        td.setTitle(tooltip);
		td.addChild(drawTextAreaForView(dataMap, theme));
		td.setAlign("left");
		td.setClazz(theme.getValue(ThemeConst.VIEW_CONTENT.toString()));

		/**
         * Add the value and label to execContext so they can be retrieved by presentationForm
         */
		addToExecContext(execContext, row_map_name, buildName(storageField), value, getPresentation_name(formField, storageField), (String)dataMap.get(TOOLTIP));
		return html;
	}

    /**
     * 
     * @param execContext
     * @param formField
     * @param storageField
     * @param value
     * @param theme
 	 * @param includeRequiredMarker - true = draw marker for mandatory and unique
     * @return
     */
    private Html[] displayAddForTextArea(IExecContext execContext, String row_map_name, CommonFormFields formField,
            CommonStorageField storageField, String value, Theme theme, boolean includeRequiredMarker)
	{
        String tooltip = execContext.replace(getTooltip(formField, storageField));
		HtmlTr[] trs;
		if (DrawDBHTMLHelper.isPositionAbove(getLabel_position(formField, storageField))) {
			trs = new HtmlTr[2];
		} else {
			trs = new HtmlTr[1];
		}

		HtmlTr tr = new HtmlTr(theme);
		HtmlTd td = tr.addTd(theme);
		td.setClazz(theme.getValue(ThemeConst.VIEW_LABEL.toString()));
		if (includeRequiredMarker) {
			td.setContent(DrawHtmlField.buildPresentationName(getPresentation_name(formField, storageField), storageField.isMandatory(), storageField.isUnique()));
		} else {
			td.setContent(getPresentation_name(formField, storageField));
		}
        td.setTitle(tooltip);
		trs[0] = tr;

		if (DrawDBHTMLHelper.isPositionAbove(getLabel_position(formField, storageField))) {
			tr = new HtmlTr(theme);
			td = tr.addTd(theme);
	        td.setTitle(tooltip);
	        HtmlTextArea htmlTextArea = (HtmlTextArea)drawTextAreaForAdd(theme, buildName(storageField), value, getWidth(formField, storageField), getHeight(formField, storageField));
	        addStorageAttributes(htmlTextArea, storageField, execContext);
	        addAttributes(htmlTextArea, formField);
			td.addChild(htmlTextArea);
			trs[1] = tr;
		} else {
			td = tr.addTd(theme);
			HtmlTextArea htmlTextArea = (HtmlTextArea)drawTextAreaForAdd(theme, buildName(storageField), value, getWidth(formField, storageField), getHeight(formField, storageField));
	        addStorageAttributes(htmlTextArea, storageField, execContext);
	        addAttributes(htmlTextArea, formField);
			td.addChild(htmlTextArea);
			td.setAlign("left");
			td.setClazz(theme.getValue(ThemeConst.VIEW_CONTENT.toString()));
	        td.setTitle(tooltip);
		}
        /**
         * Add the value and label to execContext so they can be retrieved by presentationForm
         */
		addToExecContext(execContext, row_map_name, buildName(storageField), value, getPresentation_name(formField, storageField), tooltip);
		return trs;
	}
	

    private Html[] displayListForText(IExecContext execContext, String row_map_name, Map<String, Object> dataMap, CommonFormFields formField,
            CommonStorageField storageField, String value, Theme theme) {

        Html[] htmls = new Html[1];

        HtmlTd td = new HtmlTd();
        htmls[0] = td;
        td.setClazz(theme.getValue(ThemeConst.VIEW_LABEL.toString()));
        td.setTitle((String)dataMap.get(TOOLTIP));
        td.setClazz(theme.getValue(ThemeConst.VIEW_CONTENT.toString()));
        td.setAlign("left");
        td.setHeight((String)dataMap.get(HEIGHT));
        td.setWidth((String)dataMap.get(WIDTH));

        if (formField instanceof Field) {
            Field field = (Field) formField;
            if (field.getCode() != null) {
                try {
                    value = field.getCode().execute(execContext);
                } catch (Exception e) {
                    throw new IllegalArgumentException(e.getMessage(), e);
                }
            }
            Link link = field.getLink();
            if (link != null) {
                Html html = null;
                html = link.draw(execContext, theme);
                if (html instanceof HtmlA) {
                	html.setContent(value);
                }
                td.addChild(html);
            } else {
                td.setContent(value);
            }
        } else {
            td.setContent(value);
        }
        /**
         * Add the value and label to execContext so they can be retrieved by presentationForm
         */
		addToExecContext(execContext, row_map_name, buildName(storageField), value, getPresentation_name(formField, storageField), (String)dataMap.get(TOOLTIP));
        return htmls;
	}

    private Html[] displayListForBinary(IExecContext execContext, String row_map_name, Map<String, Object> dataMap, CommonFormFields formField,
            BinaryHtml storageField, String value, Theme theme) {

        Html[] htmls = new Html[1];

        HtmlTd td = new HtmlTd();
        htmls[0] = td;
        td.setClazz(theme.getValue(ThemeConst.VIEW_LABEL.toString()));
        td.setTitle((String)dataMap.get(TOOLTIP));
        td.setClazz(theme.getValue(ThemeConst.VIEW_CONTENT.toString()));
        td.setAlign("left");
        td.setHeight((String)dataMap.get(HEIGHT));
        td.setWidth((String)dataMap.get(WIDTH));

        if (formField instanceof Field) {
            Field field = (Field) formField;
            if (field.getCode() != null) {
                try {
                    value = field.getCode().execute(execContext);
                } catch (Exception e) {
                    throw new IllegalArgumentException(e.getMessage(), e);
                }
            }
            if (StringUtils.isNotEmpty(value)) {
            	if (storageField.isTrue(value)) {
            		value = storageField.getTrueValue();
            	} else {
            		value = storageField.getFalseValue();
            	}
            }
            Link link = field.getLink();
            if (link != null) {
                Html html = null;
                html = link.draw(execContext, theme);
                html.setContent(value);
                td.addChild(html);
            } else {
                td.setContent(value);
            }
        } else {
            td.setContent(value);
        }
        
        /**
         * Add the value and label to execContext so they can be retrieved by presentationForm
         */
		addToExecContext(execContext, row_map_name, buildName(storageField), value, getPresentation_name(formField, storageField), (String)dataMap.get(TOOLTIP));
        return htmls;
	}

    private Html[] displayViewForText(IExecContext execContext, String row_map_name, Map<String, Object> dataMap, CommonFormFields formField,
            CommonStorageField storageField, String value, Theme theme) {
    	
    	if (formField.getSnippet_ref() != null) {
    		String snippet = execContext.getString(formField.getSnippet_ref());
    		snippet = replaceMarkersForSnippet(snippet, dataMap);
    		Html html = new HtmlSpan();
    		html.setContent(snippet);
    		Html[] trs;
   			trs = new HtmlSpan[1];
   			trs[0] = html;
    		return trs;
    	}
		HtmlTr[] trs;
		String label_position = getLabel_position(formField, storageField);
		if (DrawDBHTMLHelper.isPositionAbove(label_position)) {
			trs = new HtmlTr[2];
		} else {
			trs = new HtmlTr[1];
		}
		HtmlTr tr = new HtmlTr(theme);
		HtmlTd td = tr.addTd(theme);
		td.setContent((String)dataMap.get(LABEL));
		td.setClazz(theme.getValue(ThemeConst.VIEW_LABEL.toString()));
        td.setTitle((String)dataMap.get(TOOLTIP));
		trs[0] = tr;
		
		if (DrawDBHTMLHelper.isPositionAbove(label_position)) {
			tr = new HtmlTr(theme);
			td = tr.addTd(theme);
			td.setContent((String)dataMap.get(VALUE));
			td.setClazz(theme.getValue(ThemeConst.VIEW_CONTENT.toString()));
			td.setHeight((String)dataMap.get(HEIGHT));
	        td.setTitle((String)dataMap.get(TOOLTIP));
			trs[1] = tr;
		} else {
			td = tr.addTd(theme);
			td.setContent((String)dataMap.get(VALUE));
			td.setClazz(theme.getValue(ThemeConst.VIEW_CONTENT.toString()));
			td.setAlign("left");
			td.setHeight((String)dataMap.get(HEIGHT));
	        td.setTitle((String)dataMap.get(TOOLTIP));
		}
        /**
         * Add the value and label to execContext so they can be retrieved by presentationForm
         */
		addToExecContext(execContext, row_map_name, buildName(storageField), value, getPresentation_name(formField, storageField), (String)dataMap.get(TOOLTIP));
		return trs;
	}

    private Html[] displayViewForBinary(IExecContext execContext, String row_map_name, Map<String, Object> dataMap, CommonFormFields formField,
            CommonStorageField storageField, String value, Theme theme) {
    	
    	if (formField.getSnippet_ref() != null) {
    		String snippet = execContext.getString(formField.getSnippet_ref());
    		snippet = replaceMarkersForSnippet(snippet, dataMap);
    		Html html = new HtmlSpan();
    		html.setContent(snippet);
    		Html[] trs;
   			trs = new HtmlSpan[1];
   			trs[0] = html;
    		return trs;
    	}
		HtmlTr[] trs;
		String label_position = getLabel_position(formField, storageField);
		if (DrawDBHTMLHelper.isPositionAbove(label_position)) {
			trs = new HtmlTr[2];
		} else {
			trs = new HtmlTr[1];
		}
		HtmlTr tr = new HtmlTr(theme);
		HtmlTd td = tr.addTd(theme);
		td.setContent((String)dataMap.get(LABEL));
		td.setClazz(theme.getValue(ThemeConst.VIEW_LABEL.toString()));
        td.setTitle((String)dataMap.get(TOOLTIP));
		trs[0] = tr;
		
		String value2 = (String)dataMap.get(VALUE);
		if (value2.equals("0")) {
			value2 = "No";
		} else if (value2.equals("1")){
			value2 = "Yes";
		}
		if (DrawDBHTMLHelper.isPositionAbove(label_position)) {
			tr = new HtmlTr(theme);
			td = tr.addTd(theme);
			td.setContent(value2);
			td.setClazz(theme.getValue(ThemeConst.VIEW_CONTENT.toString()));
			td.setHeight((String)dataMap.get(HEIGHT));
	        td.setTitle((String)dataMap.get(TOOLTIP));
			trs[1] = tr;
		} else {
			td = tr.addTd(theme);
			td.setContent(value2);
			td.setClazz(theme.getValue(ThemeConst.VIEW_CONTENT.toString()));
			td.setAlign("left");
			td.setHeight((String)dataMap.get(HEIGHT));
	        td.setTitle((String)dataMap.get(TOOLTIP));
		}
        /**
         * Add the value and label to execContext so they can be retrieved by presentationForm
         */
		addToExecContext(execContext, row_map_name, buildName(storageField), value, getPresentation_name(formField, storageField), (String)dataMap.get(TOOLTIP));
		return trs;
	}

    private Html[] displayAddForText(IExecContext execContext, String row_map_name, CommonFormFields formField,
            CommonStorageField storageField, String value, Theme theme, boolean includeRequiredMarker) {
        String tooltip = execContext.replace(getTooltip(formField, storageField));
		HtmlTr[] trs;
		String label_position = getLabel_position(formField, storageField);
		if (DrawDBHTMLHelper.isPositionAbove(label_position)) {
			trs = new HtmlTr[2];
		} else {
			trs = new HtmlTr[1];
		}
		HtmlTr tr = new HtmlTr(theme);
		HtmlTd td = tr.addTd(theme);
		if (includeRequiredMarker) {
			td.setContent(DrawHtmlField.buildPresentationName(getPresentation_name(formField, storageField), storageField.isMandatory(),storageField.isUnique()));
		} else {
			td.setContent(getPresentation_name(formField, storageField));
		}
		
		td.setClazz(theme.getValue(ThemeConst.VIEW_LABEL.toString()));
        td.setTitle(tooltip);
		trs[0] = tr;
		
		HtmlInput input = new HtmlInput(theme);
		input.setType("text");
		input.setName(buildName(storageField));
        input.setSize(getWidth("" + storageField.getPresentation_width(), formField.getWidth()));
		if (storageField.getLength() > 0) {
			input.setMaxlength("" + storageField.getLength());
		}
		input.setValue(value);
        if (storageField.isMandatory()) {
        	input.setRequired("true");
        }
        if (!StringUtils.isBlank(storageField.getPattern())) {
        	input.setPattern(storageField.getPattern());
        }
        if (!StringUtils.isBlank(storageField.getPlaceholder())) {
        	input.setPlaceholder(storageField.getPlaceholder());
        }
        if (!StringUtils.isBlank(storageField.getTooltip())) {
        	input.setTitle(storageField.getTooltip(execContext));
        }
		
		addEvents(input, formField, storageField);
		addAttributes(input, formField);
		if (formField.getCss() != null) {
			String clazz = input.get(HtmlEnum.clazz.getAttributeName());
			input.setClazz(clazz + " " + formField.getCss());
		}
		
		if (DrawDBHTMLHelper.isPositionAbove(label_position)) {
			tr = new HtmlTr(theme);
			td = tr.addTd(theme);
			trs[1] = tr;
		} else {
			td = tr.addTd(theme);
		}
        td.setTitle(tooltip);
		td.setAlign("left");
		td.setClazz(theme.getValue(ThemeConst.VIEW_CONTENT.toString()));
		td.setHeight(getHeight(formField, storageField));
		td.addChild(input);
        /**
         * Add the value and label to execContext so they can be retrieved by presentationForm
         */
		addToExecContext(execContext, row_map_name, buildName(storageField), value, getPresentation_name(formField, storageField), tooltip);
		return trs;
	}
	
    private Html[] displayAddForBinary(IExecContext execContext, String row_map_name, CommonFormFields formField,
    		BinaryHtml storageField, String value, Theme theme, boolean includeRequiredMarker) {
        String tooltip = execContext.replace(getTooltip(formField, storageField));
		HtmlTr[] trs;
		String label_position = getLabel_position(formField, storageField);
		if (DrawDBHTMLHelper.isPositionAbove(label_position)) {
			trs = new HtmlTr[2];
		} else {
			trs = new HtmlTr[1];
		}
		HtmlTr tr = new HtmlTr(theme);
		boolean isTrue = storageField.isTrue(value);
		String booleanValue;
		if (isTrue) {
			booleanValue = storageField.getTrueValue();
		} else {
			booleanValue = storageField.getFalseValue();
		}
		String name = buildName(storageField);
		HtmlInput input = new HtmlInput(theme);
		input.setType("hidden");
		input.setId(name);
		input.setName(name);
		input.setValue(booleanValue);

		HtmlTd td = tr.addTd(theme);
		td.addChild(input);
		if (includeRequiredMarker) {
			td.setContent(DrawHtmlField.buildPresentationName(getPresentation_name(formField, storageField), storageField.isMandatory(),storageField.isUnique()));
		} else {
			td.setContent(getPresentation_name(formField, storageField));
		}
		
		td.setClazz(theme.getValue(ThemeConst.VIEW_LABEL.toString()));
        td.setTitle(tooltip);
		trs[0] = tr;
		
		input = new HtmlInput(theme);
		input.setType("checkbox");
		input.setId("actor." + name);
		input.setName("actor." + name);
		input.setValue(value);
		if (isTrue) {
			input.setChecked("checked");
		}
		input.setOnChange("toggleTrueFalse('" + name + "','"+ storageField.getFalseValue() + "','" + storageField.getTrueValue() + "');");
		input.setContent(" " + getPresentation_name(formField, storageField));
		addEvents(input, formField, storageField);
		addAttributes(input, formField);
		if (formField.getCss() != null) {
			String clazz = input.get(HtmlEnum.clazz.getAttributeName());
			input.setClazz(clazz + " " + formField.getCss());
		}
		
		if (DrawDBHTMLHelper.isPositionAbove(label_position)) {
			tr = new HtmlTr(theme);
			td = tr.addTd(theme);
			trs[1] = tr;
		} else {
			td = tr.addTd(theme);
		}
        td.setTitle(tooltip);
		td.setAlign("left");
		td.setClazz(theme.getValue(ThemeConst.VIEW_CONTENT.toString()));
		td.setHeight(getHeight(formField, storageField));
		td.addChild(input);
        /**
         * Add the value and label to execContext so they can be retrieved by presentationForm
         */
		addToExecContext(execContext, row_map_name, buildName(storageField), value, getPresentation_name(formField, storageField), tooltip);
		return trs;
	}
	
	private Html drawTextAreaForView(Map<String, Object> dataMap, Theme theme) {
		HtmlTextArea textArea = new HtmlTextArea(theme);
		textArea.setClazz(theme.getValue(ThemeConst.VIEW_CONTENT.toString()));
		textArea.setName((String)dataMap.get(NAME));
		textArea.setCols((String)dataMap.get(COLS));
		textArea.setRows((String)dataMap.get(ROWS));
		textArea.setContent((String)dataMap.get(VALUE));
		textArea.setReadonly("true");
		// textArea.setDisabled("true");
		return textArea;
	}
	private Html drawTextAreaForAdd(Theme theme, String name, String value, String cols, String rows) {
		HtmlTextArea textArea = new HtmlTextArea(theme);
		textArea.setClazz(theme.getValue(ThemeConst.VIEW_CONTENT.toString()));
		textArea.setName(name);
		textArea.setCols(cols);
		textArea.setRows(rows);
		textArea.setContent(value);
		return textArea;
	}
	
	/**
	 * Select - radio and check boxes
	 * @param execContext
	 * @param formField
	 * @param storageField
	 * @param value
	 * @param theme
	 * @param includeRequiredMarker - true = draw marker for mandatory and unique
	 * @return
	 */
    private Html[] displayAddForSelect(IExecContext execContext, String row_map_name, CommonFormFields formField, SelectHtml storageField,
            String value, Theme theme, boolean includeRequiredMarker) {
        String tooltip = execContext.replace(getTooltip(formField, storageField));
		if (formField.getPopulatorXml() == null) {
			throw new IllegalArgumentException("Error:" + this.getClass().getSimpleName()
					+ " - The xml populator has not been set.");
		}
		XMLObject root = new XMLObject().mapXMLCharToXMLObject(formField.getPopulatorXml());
		HtmlTd rootTd = new HtmlTd(theme);
		if (formField.getAlign() != null) {
			rootTd.setAlign(formField.getAlign());
		} else {
			rootTd.setAlign("left");
		}
        rootTd.setTitle(tooltip);
		HtmlTable table = rootTd.addTable(theme);
		Html tr = table.addTr();

		int count = 0;
		for (XMLObject option : root.getChildren()) {
			
			HtmlTd td = new HtmlTd();
			td.setHeight(getHeight(formField, storageField));
			td.setClazz(theme.getValue(ThemeConst.VIEW_LABEL.toString()));
	        td.setTitle(tooltip);
			String name = buildName(storageField);
			String id = name + "." + count++;
			td.setOnClick("toggleCheck('" + id +"');");
			HtmlInput input = td.addInput(theme);
			input.setOnClick("toggleCheck('" + id +"');");
			input.setId(id);
			if (storageField.isMultiSelect()) {
				input.setType("checkbox");
			} else {
				input.setType("radio");
			}
			input.setName(buildName(storageField));
			// set value
			if (option.getAttributeCount() > 0) {
				input.setValue(option.getAttributeAsString(0));
			}
			// set content
			if (option.getAttributeCount() > 1) {
				HtmlDiv div = input.addDiv(theme);
				input.setContent(option.getAttributeAsString(1));
			}
			// set title
			if (option.getAttributeCount() > 2) {
				input.setTitle(option.getAttributeAsString(2));
				td.setTitle(option.getAttributeAsString(2));
			}
			tr.addChild(td);
            if (DrawDBHTMLHelper.isDirectionDown(getDirection(formField, storageField))) {
            	tr = table.addTr();
            }
			
		}
		String labelPosition =getLabel_position(formField, storageField);
		HtmlTd td = DrawHtmlField.buildDisplayHeader(theme, tooltip, getPresentation_name(formField, storageField), false, false);
		Html[] html = DrawDBHTMLHelper.buildDisplay(theme,labelPosition,td,rootTd);
        /**
         * Add the value and label to execContext so they can be retrieved by presentationForm
         */
		addToExecContext(execContext, row_map_name, buildName(storageField), value, getPresentation_name(formField, storageField), tooltip);
		return html;
	}
	

	private void addEvents(Html html, CommonFormFields formField, CommonStorageField storageField) {
		if (formField.getOnchange() != null) {
			html.put(HtmlEnum.onchange.getAttributeName(), formField.getOnchange());
		}
		if (formField.getOnclick() != null) {
			html.put(HtmlEnum.onclick.getAttributeName(), formField.getOnclick());
		}
		if (formField.getOnselect() != null) {
			html.put(HtmlEnum.onselect.getAttributeName(), formField.getOnselect());
		}
		if (formField.getOnfocus() != null) {
			html.put(HtmlEnum.onfocus.getAttributeName(), formField.getOnfocus());
		}
	}
	
	/**
	 * Builds an identifier name for the parent and child so it matches the
	 * table and field.
	 * 
	 * @param parent
	 * @param child
	 * @return
	 */
	private String buildName(CommonStorageField storageField)
	{
        CommonStorageField parent = (CommonStorageField) storageField.getParent();
        if (parent.getAlias() != null) {
            return parent.getAlias() + Table.TABLE_FIELD_SEPERATOR + storageField.getName();
        } else {
            return parent.getName() + Table.TABLE_FIELD_SEPERATOR + storageField.getName();
        }
	}

	private Map<String, Object> buildDataMap(IExecContext execContext, CommonFormFields formField, CommonStorageField storageField, String value) {
		Map <String,Object> map = new HashMap<String, Object> ();
    	String presentationName = DrawHtmlField.buildPresentationName(getPresentation_name(formField, storageField), storageField.isMandatory(),storageField.isUnique());
    	String tooltip = execContext.replace(getTooltip(formField, storageField));
		String height = getHeight(formField, storageField);
		String width = getWidth(formField, storageField);
	    map.put(LABEL, presentationName);
		map.put(VALUE, value);
		map.put(TOOLTIP, tooltip);
		map.put(NAME, buildName(storageField));
		map.put(HEIGHT, height);
		map.put(WIDTH, width);
		map.put(ROWS, height);
		map.put(COLS, width);
		return map;
	}
	private Map<String, Object> buildDataMapForList(IExecContext execContext, CommonFormFields formField, CommonStorageField storageField, String value) {
		Map <String,Object> map = new HashMap<String, Object> ();
    	String presentationName = DrawHtmlField.buildPresentationName(getPresentation_name(formField, storageField), storageField.isMandatory(),storageField.isUnique());
    	String tooltip = execContext.replace(getTooltip(formField, storageField));
		String height = getHeight(formField, storageField);
		String width = null;
		if (storageField instanceof TextAreaHtml) {
			width = getWidthForList(formField, storageField);
		} else {
			width = getWidthForList(formField, storageField);
		}
	    map.put(LABEL, presentationName);
		map.put(VALUE, value);
		map.put(TOOLTIP, tooltip);
		map.put(NAME, buildName(storageField));
		map.put(HEIGHT, height);
		map.put(WIDTH, width);
		map.put(ROWS, height);
		map.put(COLS, width);
		return map;
	}

	private String replaceMarkersForSnippet(String snippet, Map<String, Object> map) {
		return StrSubstitutor.replace(snippet, map);
	}
	
	public Html buildTextAreaInputForAddAndEdit(IExecContext execContext, String row_map_name, CommonFormFields formField,
			CommonStorageField storageField, String value, Theme theme) {

		String tooltip = execContext.replace(getTooltip(formField, storageField));

		HtmlTextArea textArea = new HtmlTextArea(theme);
		textArea.setClazz(theme.getValue(ThemeConst.VIEW_CONTENT.toString()));
		textArea.setName(buildName(storageField));
		textArea.setCols(getWidth("" + storageField.getPresentation_width(), formField.getWidth()));
		textArea.setRows(getHeight(formField, storageField));
		textArea.setContent(value);
		textArea.setTitle(tooltip);
		addAttributes(textArea, formField);
        /**
         * Add the value and label to execContext so they can be retrieved by presentationForm
         */
		addToExecContext(execContext, row_map_name, buildName(storageField), value, getPresentation_name(formField, storageField), tooltip);
		return textArea;
	}
	
	public Html buildTextInputForAddAndEdit(IExecContext execContext, CommonFormFields formField,
			CommonStorageField storageField, String value, Theme theme) {

		String tooltip = execContext.replace(getTooltip(formField, storageField));

		HtmlInput input = new HtmlInput(theme);
		input.setType("text");
		input.setName(buildName(storageField));
		input.setSize(getWidth("" + storageField.getPresentation_width(), formField.getWidth()));
		if (storageField.getLength() > 0) {
			input.setMaxlength("" + storageField.getLength());
		}
		input.setValue(value);
		input.setTitle(tooltip);
		addAttributes(input, formField);
		return input;

	}
	
	public static void addAttributes(Html html, CommonFormFields formField) {
		if (formField.getAttributes() != null) {
			List<XMLAttribute> atts = formField.getAttributes().getAttributes();
			if (atts != null) {
				for (XMLAttribute att : atts) {
					html.put(att.getKey(), (String)att.getValue());
				}
			}
		}
	}
	
	public HtmlTd buildTdLabel(Theme theme, boolean isMandatory, boolean isUnique, String label, String tooltip) {
		HtmlTd td = new HtmlTd(theme);
		td.setContent(DrawHtmlField.buildPresentationName(label, isMandatory, isUnique));
		td.setClazz(theme.getValue(ThemeConst.VIEW_LABEL.toString()));
        td.setTitle(tooltip);
        return td;

	}
	
	private void addStorageAttributes(HtmlTextArea htmlTextArea, CommonStorageField storageField, IExecContext execContext) {
        if (storageField.isMandatory()) {
        	htmlTextArea.setRequired("true");
        }
        if (!StringUtils.isBlank(storageField.getPattern())) {
        	htmlTextArea.setPattern(storageField.getPattern());
        }
        if (!StringUtils.isBlank(storageField.getPlaceholder())) {
        	htmlTextArea.setPlaceholder(storageField.getPlaceholder());
        }
        if (!StringUtils.isBlank(storageField.getTooltip())) {
        	htmlTextArea.setTitle(storageField.getTooltip(execContext));
        }
	}        
	
    public static void addToExecContext(IExecContext execContext, String row_map_name, String key, Object value, String label, String tooltip) {
    	// logger.debug(String.format("addToExecContext(%s, %s, %s, %s)", key, value, label, tooltip));
    	if (value != null) {
    		execContext.put(row_map_name + ":value_" + key, value);
    	}
    	if (label != null) {
    		execContext.put(row_map_name + ":label_" + key, label);
    	}
    	if (tooltip != null) {
    		execContext.put(row_map_name + ":tooltip_" + key, tooltip);
    	}
    }

}
