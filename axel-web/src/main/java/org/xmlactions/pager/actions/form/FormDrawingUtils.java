
package org.xmlactions.pager.actions.form;


import org.apache.commons.lang.StringUtils;
import org.xmlactions.pager.actions.form.templates.HtmlDiv;
import org.xmlactions.pager.actions.form.templates.HtmlInput;
import org.xmlactions.pager.actions.form.templates.HtmlTable;
import org.xmlactions.pager.actions.form.templates.HtmlTh;
import org.xmlactions.pager.actions.form.templates.HtmlTr;


public class FormDrawingUtils
{

	/**
	 * Starts a drawing frame (table) , adding width, any hidden fields and title.
	 * 
	 * @param form
	 * @return
	 */
    public static HtmlTable startFrame(FormDrawing form)
	{
    	return startFrame(form, 1);
	}

	/**
	 * Starts a drawing frame (table) , adding width, any hidden fields and title.
	 * 
	 * @param form
	 * @return
	 */
    public static HtmlTable startFrame(FormDrawing form, int colspan)
	{

		HtmlTable table = new HtmlTable();
        table.setId(form.getId());
		// ===
		// build table with headers
		// ===
		if (form.isVisible()) {
            table.setClazz(form.getTheme().getValue(ThemeConst.TABLE.toString()));
		} else {
            table.setClazz(form.getTheme().getValue(ThemeConst.TABLE.toString(), "hide"));
		}
		if (StringUtils.isNotEmpty(form.getWidth())) {
			table.setWidth(form.getWidth());
		}

		// ===
		// Add Any Hidden Fields
		// ===
		for (HtmlInput input : form.getHiddenFields()) {
			table.addChild(input);
		}
		// ===
		// Add Frame Title
		// ===
		if (StringUtils.isNotEmpty(form.getTitle())) {
			HtmlTr tr = table.addTr();
			HtmlTh th = tr.addTh();
			th.setColspan("" + colspan);
			tr.setClazz(form.getTheme().getValue(ThemeConst.BAR.toString()));
			th.setClazz(form.getTheme().getValue(ThemeConst.HEADER.toString()));
			th.setContent(form.getTitle());
		}
		return table;

	}
}
