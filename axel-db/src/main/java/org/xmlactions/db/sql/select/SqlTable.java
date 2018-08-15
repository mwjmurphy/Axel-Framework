
package org.xmlactions.db.sql.select;


import java.util.ArrayList;
import java.util.List;

import org.xmlactions.db.actions.Table;



public class SqlTable implements ISqlTable {

	/**
	 * params are used to store the parameter data into a connection.statement.
	 */
	private List<SqlField> params;
	
    private String tableName;
    private String tableAlias;

    private List<SqlField> fieldNames = new ArrayList<SqlField>();

	private List<SqlJoinBase> joins = new ArrayList<SqlJoinBase>();

    private List<String> whereClauses = new ArrayList<String>();
    
    private List<ISqlTable> children = new ArrayList<ISqlTable>();
    
    private Table table;	// table that this SqlTable references.
    
    private String insertSql;

    private String updateSql;

    public SqlTable(Table table) {
    	this.table = table;
        this.tableName = table.getName();
        this.tableAlias = table.getAlias();
    }

    public List<SqlField> getFields() {
        return fieldNames;
    }

    public void setFields(List<SqlField> sqlFields) {
        fieldNames = sqlFields;
    }

    public void addField(String fieldName) {
        this.fieldNames.add(new SqlField(fieldName));
    }

    public void addField(String fieldName, String aliasName) {
        this.fieldNames.add(new SqlField(fieldName, aliasName));
    }

    public void addField(String fieldName, String aliasName, String function_ref) {
        this.fieldNames.add(new SqlField(fieldName, aliasName, function_ref));
    }
    public void addField(SqlField field) {
        this.fieldNames.add(field);
    }


    /**
     * The field name can be the table.name or just the name
     */
    public SqlField getFieldByName(String fieldName) {
    	String f1;
    	if (Table.isTableAndFieldName(fieldName)) {
    		f1 = Table.getFieldName(fieldName);
    	} else {
    		f1 = fieldName;
    	}
       	for (SqlField field : getFields()) {
       		String f2 = field.getFieldName();
       		if (Table.isTableAndFieldName(f2)) {
       			f2 = Table.getFieldName(f2);
       		}
       		if (f2.equals(f1)) {
       			return field;
       		}
    	}
    	return null;
    }

    public SqlField getFieldByAlias(String aliasName) {

    	String f1;
    	if (Table.isTableAndFieldName(aliasName)) {
    		f1 = Table.getFieldName(aliasName);
    	} else {
    		f1 = aliasName;
    	}
       	for (SqlField field : getFields()) {
       		String f2 = field.getFieldAlias();
       		if (Table.isTableAndFieldName(f2)) {
       			f2 = Table.getFieldName(f2);
       		}
       		if (f2.equals(f1)) {
       			return field;
       		}
    	}
    	return null;
    }

    public SqlField getFieldByAliasOrName(String aliasOrName) {

    	SqlField field = getFieldByAlias(aliasOrName);
    	if (field == null) {
    		field = getFieldByName(aliasOrName);
    	}
    	return field;
    }

    public String getTableName() {
        return tableName;
    }
    
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<String> getWhereClauses() {
        return whereClauses;
    }

    public void addWhereClause(String whereClause) {
        this.whereClauses.add(whereClause);
    }


	public void setJoins(List<SqlJoinBase> joins)
	{
		this.joins = joins;
	}

	public void addJoin(SqlJoinBase join)
	{
		getJoins().add(join);
	}

	public List<SqlJoinBase> getJoins()
	{
		return joins;
	}

	/**
	 * Get a join for this table name.
	 * <p>
	 * A Join may use one or more tables.  The first join found that 
	 * contains the table name is returned.
	 * </p>
	 * <code>join tb_name on ...</code>
	 * <code>join tb_name1, tb_name2 on ...</code>
	 * @return the join or null if not found
	 */
	public SqlJoinBase getJoin(String tableName)
	{

		for (SqlJoinBase join : getJoins()) {
            String tbName = join.getTableName();
            if (tbName.equalsIgnoreCase(tableName)) {
                return join;
			}
		}
		return null;
	}

    public void setTableAlias(String tableAlias) {
        this.tableAlias = tableAlias;
    }

    public String getTableAlias() {
        return tableAlias;
    }

    /**
	 * @return the alias if its set else the name
	 */
	public String getTableAliasOrName() {
		if (getTableAlias() == null) {
			return getTableName();
		}
		return getTableAlias();
	}

	public List<ISqlTable> getChildren() {
		return children;
	}

	public void addChild(ISqlTable child) {
		children.add(child);
		
	}

	public void setTable(Table table) {
		this.table = table;
	}

	public Table getTable() {
		return table;
	}

	public void setInsertSql(String insertSql) {
		this.insertSql = insertSql;
	}

	public String getInsertSql() {
		return insertSql;
	}

	public void setUpdateSql(String updateSql) {
		this.updateSql = updateSql;
	}

	public String getUpdateSql() {
		return updateSql;
	}

	public boolean addUniqueJoin(SqlJoinBase newJoin) {
		for (SqlJoinBase join : getJoins()) {
			for (String onValue : join.getOnValues()) {
				for (String newValue : newJoin.getOnValues()) {
					if (onValue.equalsIgnoreCase(newValue)) {
						return true;
					}
				}
			}
		}
		getJoins().add(newJoin);
		return false;
	}

	public List<SqlField> getParams() {
		if (params == null) {
			params= new ArrayList<SqlField>();
		}
		return params;
	}

	public void setParams(List<SqlField> params) {
		this.params = params;
	}
    
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(tableName);
		if (params != null) {
			for (SqlField sqlField: params) {
				sb.append(" - " + sqlField);
			}
		}
		for (SqlJoinBase sqlJoinBase: joins) {
			sb.append(" - " + sqlJoinBase);
		}
		return sb.toString();
	}
}
