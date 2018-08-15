
package org.xmlactions.db.sql.select;


import java.util.List;

import org.xmlactions.db.actions.Table;



public interface ISqlTable {

    public String getTableName();

    public String getTableAlias();

    /**
	 * @return the alias if its set else the name
	 */
	public String getTableAliasOrName();

	public void addField(SqlField field);

	public SqlField getFieldByName(String sqlFieldName);
	public SqlField getFieldByAlias(String sqlAliasName);
	public SqlField getFieldByAliasOrName(String sqlAliasOrName);
	
    public List<SqlField> getFields();
    
    public void setFields(List<SqlField> sqlFields);

	public List<SqlJoinBase> getJoins();

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
	public SqlJoinBase getJoin(String tableName);
	
	/**
	 * Adds a join to a table if it's not already there.
	 * @return true if added, else false if not added
	 */
	public boolean addUniqueJoin(SqlJoinBase join);

    public List<String> getWhereClauses();
    
    /**
     * For inserts we must be able to manage child tables.  These would have a PK
     * that it's parent requires before saving the parent.
     * @param child
     */
    public void addChild(ISqlTable child);

    /**
     * For inserts we must be able to manage child tables.  These would have a PK
     * that it's parent requires before saving the parent.
     * @param child
     */
    public List<ISqlTable> getChildren();
    
    /**
     * table that this SqlTable references.
     */
    public Table getTable();
    /**
     * table that this SqlTable references.
     */
    public void setTable(Table table);
    
    public String getInsertSql();
    public void setInsertSql(String sql);

    public String getUpdateSql();
    public void setUpdateSql(String sql);

    public List<SqlField> getParams();
    
    public void setParams(List<SqlField> params);
    

}
