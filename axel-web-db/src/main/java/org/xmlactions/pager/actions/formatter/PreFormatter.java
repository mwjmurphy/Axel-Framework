package org.xmlactions.pager.actions.formatter;

import java.util.ArrayList;

import org.xmlactions.action.actions.BaseAction;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.common.xml.XMLAttribute;
import org.xmlactions.common.xml.XMLObject;
import org.xmlactions.pager.actions.dates.DateFormatter;
import org.xmlactions.pager.actions.form.Field;
import org.xmlactions.pager.actions.form.FieldHide;
import org.xmlactions.pager.actions.form.FieldList;
import org.xmlactions.web.HttpParam;

/**
 * This will pre format all the entries in the result for fields that have a pre_format attribute
 */
public class PreFormatter {

	/**
	 * This will format all the entries in the result for fields that have a pre_format attribute
	 * @param execContext
	 * @param xo
	 * @param fieldList
	 */
	public static void preFormatResult(IExecContext execContext, XMLObject xo, FieldList fieldList) {
		java.util.List<HttpParam> params = new ArrayList<HttpParam>();
		for (BaseAction baseAction : fieldList.getFields()) {
			if (baseAction instanceof Field) {
				if (((Field)baseAction).getPre_format() != null) {
					String key = ((Field)baseAction).getName();
					String preFormat = ((Field)baseAction).getPre_format();
					params.add(new HttpParam(key, preFormat));
				}
			} else if (baseAction instanceof FieldHide) {
				if (((FieldHide)baseAction).getPre_format() != null) {
					String key = ((FieldHide)baseAction).getName();
					String preFormat = ((FieldHide)baseAction).getPre_format();
					params.add(new HttpParam(key, preFormat));
				}
			}
		}
		if (params.size() > 0) {
			for (XMLObject row: xo.getChildren()) {
				for (HttpParam param : params) {
					XMLAttribute attr = row.getXMLAttribute(param.getKey());
					if (attr != null) {
						String value = DateFormatter.formatValue(execContext, attr.getValueAsString(), (String)param.getValue());
						attr.setValue(value);
					}
				}				
			}
		}
	}


}
