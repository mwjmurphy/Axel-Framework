package org.xmlactions.db.actions;

import org.xmlactions.action.actions.BaseAction;
import org.xmlactions.action.config.IExecContext;

public class Function extends BaseAction {

    private String name;
    private String sql;

    public String execute(IExecContext execContext) throws Exception {

        return null;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getSql() {
        return sql;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
