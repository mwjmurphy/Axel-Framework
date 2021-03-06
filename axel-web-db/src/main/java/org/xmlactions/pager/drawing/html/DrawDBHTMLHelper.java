
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


public class DrawDBHTMLHelper
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
	public static String removeUniqueId(String key, String uniqueId)
	{

		if (uniqueId == null)
			return key;
		int index = key.indexOf('.');
		if (index == uniqueId.length()) {
			return key.substring(uniqueId.length() + 1);
		}
		throw new IllegalArgumentException("A Matching Unique Id [" + uniqueId + "] was not found in [" + key + "]");
	}

	/**
	 * Returns the field name part of the string which is the part after the '.'
	 * 
	 * @param tableAndFieldName
	 *            the table and field name separated with the '.'
	 * @return the field part of the name or null of the field part of the name
	 *         is not found.
	 */
	public static String getFieldName(String tableAndFieldName)
	{

		int index = tableAndFieldName.lastIndexOf('.');
		if (index >= 0) {
			return (tableAndFieldName.substring(index + 1));
		}
		return null;
	}

	public static HtmlTr[] buildDisplay(Theme theme, String position, HtmlTd header, HtmlTd field)
	{
		HtmlTr[] trs;
		if (isPositionAbove(position)) {
			trs = new HtmlTr[2];
			HtmlTr tr = new HtmlTr(theme);
			tr.addChild(header);
			trs[0] = tr;
			tr = new HtmlTr(theme);
			tr.addChild(field);
			trs[1] = tr;
		} else {
			trs = new HtmlTr[1];
			HtmlTr tr = new HtmlTr(theme);
			tr.addChild(header);
			tr.addChild(field);
			trs[0] = tr;
		}
		return (trs);
	}

	public static HtmlTr[] buildDisplay(Theme theme, String position, HtmlTh header, Html field)
	{
		HtmlTd td = new HtmlTd(theme);
		td.addChild(field);		
		HtmlTr[] trs;
		if (isPositionAbove(position)) {
			trs = new HtmlTr[2];
			HtmlTr tr = new HtmlTr(theme);
			tr.addChild(header);
			trs[0] = tr;
			tr = new HtmlTr(theme);
			tr.addChild(td);
			trs[1] = tr;
		} else {
			trs = new HtmlTr[1];
			HtmlTr tr = new HtmlTr(theme);
			tr.addChild(header);
			tr.addChild(td);
			trs[0] = tr;
		}
		return (trs);
	}

	/**
	 * Check if the position is 'above' the field
	 * 
	 * @param position
	 * @return true it position is 'above' the field
	 */
	public static boolean isPositionAbove(String position)
	{

		if (position != null && position.equals("above")) {
			return true;
		}
		return false;
	}

	/**
	 * Check if the position is 'left' of the field
	 * 
	 * @param position
	 * @return true it position is 'left' of the field
	 */
	public static boolean isPositionLeft(String position)
	{

		if (position != null && position.equals("left")) {
			return true;
		}
		return false;

	}

	/**
     * Check if the direction is 'down' as opposed to 'across'.
     * <p>
     * Used when drawing a presentation of checkboxes or radio boxes.
     * </p>
     * 
     * @param direction
     * @return true it direction is 'down'
     */
    public static boolean isDirectionDown(String direction)
    {

        if (direction != null && direction.equals("down")) {
            return true;
        }
        return false;
    }

    /**
     * Check if the direction is 'across' as opposed to 'down'
     * <p>
     * Used when drawing a presentation of checkboxes or radio boxes.
     * </p>
     * 
     * @param direction
     * @return true it direction is 'across'
     */
    public static boolean isDirectionAcross(String direction)
    {

        if (direction != null && direction.equals("across")) {
            return true;
        }
        return false;

    }

	/**
	 * Builds the links and buttons for display at the bottom of a form.
	 * 
	 * @param execContext
	 * @param baseAction
	 * @param theme
	 * @param align
	 * @return
	 */
	public static HtmlTr buildLinksAndButtons(IExecContext execContext, BaseFormAction baseAction, Theme theme,
			String align)
	{
		return buildLinksAndButtons(execContext, baseAction, theme, align, null);
	}

	/**
	 * Builds the links and buttons for display at the bottom of a form.
	 * 
	 * @param execContext
	 * @param baseAction
	 * @param theme
	 * @param align
	 * @param formSubmit
	 * @return
	 */
	public static HtmlTr buildLinksAndButtons(IExecContext execContext, BaseFormAction baseAction, Theme theme,
			String align, HtmlInput formSubmit)
	{

		HtmlTr tr = new HtmlTr();
		tr.setClazz(theme.getValue(ThemeConst.ROW.toString()));
		HtmlTd td = tr.addTd();
		if (baseAction.getLinks().size() > 0 || formSubmit != null) {
			td.setAlign(align);
			HtmlTable table = td.addTable();
			HtmlTr tr2 = table.addTr();
			tr2.setClazz(theme.getValue(ThemeConst.ROW.toString()));
			Html[] As = BaseFormAction.buildLinks(execContext, baseAction.getLinks(), theme);
			for (Html a : As) {
				td = tr2.addTd(theme);
				td.addChild(a);
			}
			HtmlInput[] inputs = BaseFormAction.buildButtons(execContext, baseAction.getButtons(), theme);
			for (HtmlInput input : inputs) {
				td = tr2.addTd(theme);
				td.addChild(input);
			}

			if (formSubmit != null) {
				td = tr2.addTd(theme);
				td.addChild(formSubmit);
			}
		}
		return tr;
	}


	/**
	 * add the links and buttons to the execContext. These can then be inserted into
	 * a page using the link name;
	 * 
	 * @param execContext
	 * @param baseAction
	 * @param theme
	 * @param align
	 * @return
	 */
	public static void addLinksAndButtonsToExecContext(IExecContext execContext, BaseFormAction baseAction, Theme theme,
			String align)
	{
		addLinksAndButtonsToExecContext(execContext, baseAction, theme, align, null);
	}

	/**
	 * add the links and buttons to the execContext. These can then be inserted into
	 * a page using the link name;
	 * 
	 * @param execContext
	 * @param baseAction
	 * @param theme
	 * @param align
	 * @param formSubmit
	 * @return
	 */
	public static void addLinksAndButtonsToExecContext(IExecContext execContext, BaseFormAction baseAction, Theme theme,
			String align, HtmlInput formSubmit)
	{
		if (baseAction.getLinks().size() > 0 || formSubmit != null) {
			Html[] As = BaseFormAction.addLinksToExecContext(execContext, baseAction.getLinks(), theme);
			HtmlInput[] inputs = BaseFormAction.addButtonsToExecContext(execContext, baseAction.getButtons(), theme);
		}
	}


}
