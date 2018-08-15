package org.xmlactions.pager.drawing.html;

import org.xmlactions.common.theme.Theme;
import org.xmlactions.db.actions.CommonStorageField;
import org.xmlactions.pager.actions.form.ThemeConst;
import org.xmlactions.pager.actions.form.templates.HtmlInput;
import org.xmlactions.pager.freeware.FreewareConstants;


public class DrawInputFieldUtils {
	
	public static HtmlInput buildInputForText(CommonStorageField commonStorageField, String value, Theme theme) {
		HtmlInput input = new HtmlInput(theme);
		input.setType("text");
		input.setValue(value);
		setCommonAttributes(commonStorageField, input);
		return input;
	}
	
	public static HtmlInput buildInputForView(CommonStorageField commonStorageField, String value, Theme theme) {
		HtmlInput input = new HtmlInput(theme);
		input.setType("text");
		input.setValue(value);
		input.setReadonly("true");
		setCommonAttributes(commonStorageField, input);
		return input;
	}
	
	public static HtmlInput buildInputForDate(CommonStorageField commonStorageField, String value, Theme theme) {
		HtmlInput input = new HtmlInput(theme);
		input.setType("text");
		input.setClazz(FreewareConstants.tcal.toString() + " " + theme.getValue(ThemeConst.INPUT_TEXT.toString()));
		input.setValue(value);
		setCommonAttributes(commonStorageField, input);
		return input;
	}
	
	public static HtmlInput buildInputForTime(CommonStorageField commonStorageField, String value, Theme theme) {
		HtmlInput input = new HtmlInput(theme);
		input.setType("text");
		input.setClazz(FreewareConstants.time.toString() + " " + theme.getValue(ThemeConst.INPUT_TEXT.toString()));
		input.setValue(value);
		setCommonAttributes(commonStorageField, input);
		return input;
	}
	
	private static void setCommonAttributes(CommonStorageField commonStorageField, HtmlInput input){
		input.setName(DrawDBHTMLHelper.buildName(commonStorageField));
		input.setSize("" + commonStorageField.getPresentation_width());
		if (commonStorageField.getLength() > 0) {
			input.setMaxlength("" + commonStorageField.getLength());
		}
		if (commonStorageField.isEditable()== false || commonStorageField.isUnique() == true) {
			input.setReadonly("true");
		}
	}
}
