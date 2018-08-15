package org.xmlactions.db.query;

import java.util.List;

public class SqlQuery {

    private String sql_query;
    private String sql_ref;
    private List<Where> whereClauses;
    private List<Order> orderBy;
    private List<Group> groupBy;


    public SqlQuery() {

    }

    public void setSql_query(String sql_query) {
        this.sql_query = sql_query;
    }

    public String getSql_query() {
        return sql_query;
    }

	public void setSql_ref(String sql_ref) {
		this.sql_ref = sql_ref;
	}

	public String getSql_ref() {
		return sql_ref;
	}
	
    public void setWhereClauses(List<Where> whereClauses) {
        this.whereClauses = whereClauses;
    }

    public List<Where> getWhereClauses() {
        return whereClauses;
    }

    public void setOrderBy(List<Order> orderBy) {
        this.orderBy = orderBy;
    }

    public List<Order> getOrderBy() {
        return orderBy;
    }

	public List<Group> getGroupBy() {
		return groupBy;
	}

	public void setGroupBy(List<Group> groupBy) {
		this.groupBy = groupBy;
	}	
}
