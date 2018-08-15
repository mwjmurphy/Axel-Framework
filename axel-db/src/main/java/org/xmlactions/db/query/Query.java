package org.xmlactions.db.query;

import java.util.List;

public class Query {

    private SqlQuery sql;
    private FieldList fieldList;
    private List<Query> subQueries;
    private String table_name;
    private List<Requires> requires;
    
    public void setFieldList(FieldList fieldList) {
        this.fieldList = fieldList;
    }

    public FieldList getFieldList() {
        return fieldList;
    }

    public void setSubQueries(List<Query> subQueries) {
        this.subQueries = subQueries;
    }

    public List<Query> getSubQueries() {
        return subQueries;
    }

    public void setTable_name(String table_name) {
        this.table_name = table_name;
    }

    public String getTable_name() {
        return table_name;
    }

	public void setRequires(List<Requires> requires) {
		this.requires = requires;
	}

	public List<Requires> getRequires() {
		return requires;
	}

    public void setSql(SqlQuery sql) {
        this.sql = sql;
    }

    public SqlQuery getSql() {
        return sql;
    }


}
