
package org.xmlactions.pager.drawing.html;


import org.apache.commons.lang.StringUtils;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.common.theme.Theme;
import org.xmlactions.db.actions.CommonStorageField;
import org.xmlactions.db.actions.Table;
import org.xmlactions.pager.actions.form.BaseFormAction;
import org.xmlactions.pager.actions.form.ThemeConst;
import org.xmlactions.pager.actions.form.templates.Html;
import org.xmlactions.pager.actions.form.templates.HtmlA;
import org.xmlactions.pager.actions.form.templates.HtmlInput;
import org.xmlactions.pager.actions.form.templates.HtmlTable;
import org.xmlactions.pager.actions.form.templates.HtmlTd;
import org.xmlactions.pager.actions.form.templates.HtmlTh;
import org.xmlactions.pager.actions.form.templates.HtmlTr;


public class DrawHTMLHelperDB extends DrawHTMLHelper
{

	/**
	 * Builds an identifier name for the parent and child so it matches the
	 * table and field.
	 * 
	 * @param parent
	 * @param child
	 * @return
	 */
	public static String buildName(CommonStorageField child)
	{
        CommonStorageField parent = (CommonStorageField) child.getParent();
        if (StringUtils.isNotEmpty(parent.getAlias())) {
            return parent.getAlias() + Table.TABLE_FIELD_SEPERATOR + child.getName();
        } else {
            return parent.getName() + Table.TABLE_FIELD_SEPERATOR + child.getName();
        }
	}

	/**
	 * Builds a unique identifier name for the parent and child so it matches
	 * the table and field with a unique Id.
	 * <p>
	 * The purpose of the unique Id is to allow for the same field to be used
	 * more than once on the same page.
	 * </p>
	 * 
	 * @param child
	 * @param uniqueId
	 * @return
	 */
	public static String buildName(CommonStorageField child, String uniqueId)
	{

		if (uniqueId != null)
			return (uniqueId + "." + ((CommonStorageField) child.getParent()).getName() + "." + child.getName());
		else
            return buildName(child);
	}

}
