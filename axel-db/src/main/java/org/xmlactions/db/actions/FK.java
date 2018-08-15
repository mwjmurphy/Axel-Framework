
package org.xmlactions.db.actions;


import org.apache.commons.lang.StringUtils;
import org.xmlactions.action.config.IExecContext;


public class FK extends Fields
{

	private String foreign_table;

	private String foreign_key;

	/**
	 * Needed when we want to query the same foreign table more than once with
	 * multiple foreign_key values and where clauses.
	 */
	private String foreign_table_alias;

	/**
	 * A where is used to select the data returned from a select. <br/>
	 * Do not provide the 'where' syntax for the where clause. Instead only
	 * provide the conditions of the where clause. <br/>
	 * example:<br/>
	 * tb1.id=tb2.id<br/>
	 * or<br/>
	 * tb1.name like 'fred'<br/>
	 * or<br/>
	 * tb1.id=tb2.id and tb1.name like 'fred'<br/>
	 */
	private String where;

	public String execute(IExecContext execContext) throws Exception
	{

		// TODO Auto-generated method stub
		return null;
	}

	public String toString(int indent)
	{

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < indent; i++) {
			sb.append(' ');
		}
		sb.append("FK:" + getName());
		return sb.toString();
	}

	public void setForeign_table(String foreign_table)
	{

		this.foreign_table = foreign_table;
	}

	public String getForeign_table()
	{

		return foreign_table;
	}

	public void setForeign_key(String foreign_key)
	{

		this.foreign_key = foreign_key;
	}

	public String getForeign_key()
	{

		return foreign_key;
	}

	public String validate(String value)
	{

		String error = null;
		if (StringUtils.isEmpty(value) && this.isMandatory()) {
			error = "Missing Value";
		}
		return buildErrorString(error);
	}

	public String getForeign_table_alias() {
		return foreign_table_alias;
	}

	public void setForeign_table_alias(String foreignTableAlias) {
		foreign_table_alias = foreignTableAlias;
	}
	
	/**
	 * @return the alias if its set else the name
	 */
	public String getForeignTableAliasOrName() {
		if (getForeign_table_alias() == null) {
			return getForeign_table();
		}
		return getForeign_table_alias();
	}

	public String getWhere() {
		return where;
	}

	public void setWhere(String where) {
		this.where = where;
	}

}

