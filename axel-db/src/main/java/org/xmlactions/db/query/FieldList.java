package org.xmlactions.db.query;

import java.util.List;


public class FieldList {

    private List<Field> fields;
    private List<Where> whereClauses;
    private List<Order> orderBy;
    
    private String limit_from;
    private String limit_rows;

    private Query query;

    public void setQuery(Query query) {
        this.query = query;
    }

    public Query getQuery() {
        return query;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

    public List<Field> getFields() {
        return fields;
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

	public String getLimit_from() {
		return limit_from;
	}

	public void setLimit_from(String limit_from) {
		this.limit_from = limit_from;
	}

	public String getLimit_rows() {
		return limit_rows;
	}

	public void setLimit_rows(String limit_rows) {
		this.limit_rows = limit_rows;
	}

}
