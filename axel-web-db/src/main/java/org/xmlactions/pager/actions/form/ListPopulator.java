package org.xmlactions.pager.actions.form;

import org.xmlactions.action.actions.BaseAction;
import org.xmlactions.action.config.IExecContext;

/*
 * @deprecated used populator_sql or populator_code instead
 */
public class ListPopulator extends BaseAction {

	public enum types {
        sql_ref
	};
	
	/** The type of populator. i.e. sql_ref...*/
	private String type;
	/** The population source ref such as an sql or a static data ref */
	private String ref;

	public String execute(IExecContext execContext) throws Exception {
		return null;
	}

	public void setType(String type) {
		this.type = type;
		throw new IllegalArgumentException("list_populator has been deprecated, use populate_sql instead");
	}

	public String getType() {
		throw new IllegalArgumentException("list_populator has ben deprecated, use populate_sql instead");
		//return type;
	}

	public void setRef(String ref) {
		this.ref = ref;
		throw new IllegalArgumentException("list_populator has ben deprecated, use populate_sql instead");
	}

	public String getRef() {
		//return ref;
		throw new IllegalArgumentException("list_populator has ben deprecated, use populate_sql instead");
	}
	
	public boolean isSqlType() {
		if (types.sql_ref.toString().equals(type)) {
			return true;
		}
		return false;
	}
	

}
