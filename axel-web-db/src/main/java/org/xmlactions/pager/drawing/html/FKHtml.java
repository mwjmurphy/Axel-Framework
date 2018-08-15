
package org.xmlactions.pager.drawing.html;

import org.xmlactions.action.config.IExecContext;
import org.xmlactions.common.theme.Theme;
import org.xmlactions.db.actions.FK;
import org.xmlactions.pager.actions.form.CommonFormFields;
import org.xmlactions.pager.actions.form.Field;
import org.xmlactions.pager.actions.form.templates.Html;
import org.xmlactions.pager.actions.form.templates.HtmlTr;
import org.xmlactions.pager.drawing.IDrawField;



public class FKHtml extends FK implements IDrawField
{

    private Field field;

    public Field getHtmlField() {
        return field;
    }

    public void setHtmlField(Field field) {
        this.field = field;
    }

	public HtmlTr[] displayForAdd(String value, Theme theme)
	{

		// TODO Auto-generated method stub
		return null;
	}

    public Html displayForList(IExecContext execContext, Field field, String value, Theme theme)
	{

		// TODO Auto-generated method stub
		return null;
	}

	public HtmlTr displayForSearch(String value, Theme theme)
	{

		// TODO Auto-generated method stub
		return null;
	}

	public HtmlTr[] displayForUpdate(String value, Theme theme)
	{

		// TODO Auto-generated method stub
		return null;
	}

	public HtmlTr[] displayForView(CommonFormFields callingAction, String value, Theme theme)
	{

		// TODO Auto-generated method stub
		return null;
	}

	public Html buildAddHtml(String value, Theme theme) {
		return null;
	}

	public Html buildUpdateHtml(String value, Theme theme)
	{
		return null;
	}

	public Html buildViewHtml(String value, Theme theme) {
		return null;
	}

	public Html[] displayForView(CommonFormFields callingAction, Field field,
			String value, Theme theme) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public String toString() {
		return (String.format("DB FK name:%s FKTable:%s FKTableAlias:%s FKKey:%s FKWhere:%s", this.getName(), this.getForeign_table(), this.getForeign_table_alias(), this.getForeign_key(), this.getWhere()));
	}

}
