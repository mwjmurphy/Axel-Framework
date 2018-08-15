
package org.xmlactions.db.sql.select;


import java.util.List;
import java.util.Map;

import org.xmlactions.db.actions.Database;
import org.xmlactions.db.actions.Table;
import org.xmlactions.db.sql.select.ISqlTable;


public interface ISelectInputs {

    public String getDatabaseName();

    public ISqlTable[] getSqlTables();

    public void setSqlTables(List<ISqlTable> selTables);

	public ISqlTable getSqlTable(String name);

    public String[] getWhereClauses();

    public String[] getOrderByClauses();

    public String[] getGroupByClauses();

	public String getLimitFrom();

    public String getLimitTo();
    
    public Table getLeadTable();
    
    public Database getDatabase();
    
    public String getDbSpecificName();
    
    /**
     * Replace any ?xxx with parameters for the sql call.
     * @param store the params here.
     * @return the converted sql
     */
    public String replaceWhereWithParams(String sql, List <SqlField> sqlParams);
    

}
