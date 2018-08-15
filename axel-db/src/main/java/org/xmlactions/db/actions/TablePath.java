package org.xmlactions.db.actions;

import org.xmlactions.action.actions.BaseAction;
import org.xmlactions.action.config.IExecContext;

public class TablePath extends BaseAction {

	private String table_a, table_b;

	public void setTable_a(String table_a) {
		this.table_a = table_a;
	}

	public String getTable_a() {
		return table_a;
	}

	public void setTable_b(String table_b) {
		this.table_b = table_b;
	}

	public String getTable_b() {
		return table_b;
	}

	@Override
	public String execute(IExecContext execContext) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}
	
}
