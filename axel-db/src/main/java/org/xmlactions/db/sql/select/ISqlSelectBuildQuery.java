
package org.xmlactions.db.sql.select;

import java.util.List;

import org.xmlactions.action.config.IExecContext;


public interface ISqlSelectBuildQuery {
	
    /**
     * sqlParams can be null. If null then the ? in where clauses will not be set as parameters 
     */
    public String buildSelectQuery(IExecContext execContext, ISelectInputs iSelect, List<SqlField> sqlParams);
    public String buildUpdateSql(IExecContext execContext, ISelectInputs iSelect);
    public ISqlTable[] buildUpdateSqls(IExecContext execContext, ISelectInputs iSelect);
    public String buildInsertSql(IExecContext execContext, ISelectInputs iSelect);
    public ISqlTable[] buildInsertSqls(IExecContext execContext, ISelectInputs iSelect);
    public ISqlTable[] buildSaveSqls(IExecContext execContext, ISelectInputs iSelect);
    
    /**
     * If we want to use a manual sql query
     * @return the sql
     */
    public String getSql();

    /**
     * If we want to use a manual sql query
     * @param the sql
     */
    public void setSql(String sql);
}
