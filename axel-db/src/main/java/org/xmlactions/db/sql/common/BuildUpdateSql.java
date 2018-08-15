package org.xmlactions.db.sql.common;



import java.util.ArrayList;
import java.util.List;


import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.db.DBSQL;
import org.xmlactions.db.actions.PK;
import org.xmlactions.db.actions.Table;
import org.xmlactions.db.sql.select.ISelectInputs;
import org.xmlactions.db.sql.select.ISqlTable;
import org.xmlactions.db.sql.select.SqlField;

public class BuildUpdateSql {

	private static final Logger log = LoggerFactory.getLogger(BuildUpdateSql.class);
	
	public ISqlTable [] buildUpdateSqls(IExecContext execContext, ISelectInputs iSelect) {
		
        StringBuilder sb = new StringBuilder();
	
		for (ISqlTable sqlTable : iSelect.getSqlTables()) {
			if (sqlTable.getFields().size() > 0) {
				buildUpdateSql(execContext, iSelect, sqlTable);
			}
		}
        return iSelect.getSqlTables();
    }

	public String buildUpdateSql(IExecContext execContext, ISelectInputs iSelect, ISqlTable sqlTable) {
        StringBuilder sb = new StringBuilder();
        sb.append("update ");
   		sb.append(sqlTable.getTableName() + " " + sqlTable.getTableAliasOrName());
        boolean addedField = addFieldsForUpdate(sb, sqlTable, iSelect);
		buildWhereClauses(execContext, sb, sqlTable,  iSelect);
		if (addedField == true) {
			sqlTable.setUpdateSql(sb.toString());
		} else {
			log.debug(sqlTable.getTableAliasOrName() + " No Fields To Update");
		}
        return sb.toString();
    }

	/**
	 * @deprecated use buildUpdateSql(IExecContext execContext, ISelectInputs iSelect, ISqlTable sqlTable)
	 * @param execContext
	 * @param iSelect
	 * @return
	 */
	public String buildUpdateSql(IExecContext execContext, ISelectInputs iSelect) {
        StringBuilder sb = new StringBuilder();
        sb.append("update ");
        addTableNames(sb, iSelect);
        addFieldsForUpdate(sb, iSelect);
		addWhereClauses(sb, iSelect);
        return sb.toString();
    }

	/**
	 * @deprecated
	 * @param sb
	 * @param iSelect
	 */
    private void addTableNames(StringBuilder sb, ISelectInputs iSelect) {
    	boolean addComma = false;
        for (ISqlTable iTable : iSelect.getSqlTables()) {
        	if (addComma == true) {
        		sb.append(", ");
        	}
       		sb.append(iTable.getTableName() + " " + iTable.getTableAliasOrName());
        }
    }
	

    private boolean addFieldsForUpdate(StringBuilder sb, ISqlTable sqlTable, ISelectInputs iSelect) {

    	boolean addedSet = false;
    	boolean addedField = false;
    	
    	List<SqlField> params = new ArrayList<SqlField>();
    	sqlTable.setParams(params);
        
    	SqlField pkSqlField = null;
        
    	PK pk = sqlTable.getTable().getPk();
        
    	if (pk != null || pk != null) {
        	pkSqlField = sqlTable.getFieldByName(pk.getName());
        	if (pkSqlField == null) {
        		// throw new IllegalArgumentException("Unable to perform sql update for [" + sqlTable.getTableName() + "]. Missing PK value for [" + pk.getName() + "]");
        		// we don't need to have a pk field.
        	}
        }
        if (sqlTable.getFields().size() > 0) {
        	for (SqlField sqlField : sqlTable.getFields()) {
        		if (sqlField.equals(pkSqlField)) {
        			if (log.isDebugEnabled()) {
        				log.debug("Don't update a PK field.");
        			}
        			continue;
        		}
        		if (addedSet == false) {
        			sb.append("\nset ");
        			addedSet = true;
        		}
        		String tableName = sqlTable.getTableAliasOrName();
        		String fieldName = Table.getFieldName(sqlField.getFieldName());
        		if (fieldName == null) {
        			fieldName = sqlField.getFieldName();
        		}
        		if (addedField == true) {
        			sb.append(",");
        		} else {
        			addedField = true;
        		}
        		String sqlName = tableName + Table.TABLE_FIELD_SEPERATOR + fieldName;
        		if (sqlField.getFunction_ref() != null) {
        			String field = SqlCommon.replaceForSqlFunction(iSelect.getDatabase(), iSelect.getDbSpecificName(), sqlField.getFunction_ref(), sqlName);
        			sb.append("\n " + field + "");
        		} else {
        			sb.append("\n " + sqlName);
        		}
                sb.append("=?");
        		params.add(sqlField);
        		//if (sqlField.getValue() instanceof String) {
        		//	sb.append("='" + DBSQL.escCharacters((String)sqlField.getValue()) + "'");
        		//} else {
        		//	sb.append("="+sqlField.getValue());
        		//}
        	}
        }
        return addedField;
    }

    /**
     * @deprecated
     * @param sb
     * @param iSelect
     */
    private void addFieldsForUpdate(StringBuilder sb, ISelectInputs iSelect) {

    	boolean addedField = false;
    	boolean addedSet = false;
    	
    	List<SqlField> params = new ArrayList<SqlField>();
    	// iSelect.setParams(params);
        
    	
        for (ISqlTable iTable : iSelect.getSqlTables()) {
            if (iTable.getFields().size() > 0) {
                for (SqlField sqlField : iTable.getFields()) {
                	if (addedSet == false) {
                		sb.append("\nset ");
                		addedSet = true;
                	}
                	String tableName = iTable.getTableAliasOrName();
                	String fieldName = Table.getFieldName(sqlField.getFieldName());
                	if (fieldName == null) {
                		fieldName = sqlField.getFieldName();
                	}
                    if (addedField == true) {
                        sb.append(",");
                    } else {
                    	addedField = true;
                    }
                    String sqlName = tableName + Table.TABLE_FIELD_SEPERATOR + fieldName;
                    if (sqlField.getFunction_ref() != null) {
                    	String field = SqlCommon.replaceForSqlFunction(iSelect.getDatabase(), iSelect.getDbSpecificName(), sqlField.getFunction_ref(), sqlName);
                        sb.append("\n " + field + "");
                    } else {
                        sb.append("\n " + sqlName);
                    }
                    sb.append("=?");
                    params.add(sqlField);
                    //if (sqlField.getValue() instanceof String) {
                    //	sb.append("='" + DBSQL.escCharacters((String)sqlField.getValue()) + "'");
                    //} else {
                    //	sb.append("="+sqlField.getValue());
                    //}
                }
            }
        }
    }
    
    /**
     * @deprecated - we'll build these dynamically
     * @param sb
     * @param iSelect
     */
	private void addWhereClauses(StringBuilder sb, ISelectInputs iSelect) {
        boolean addedWhere = false;
		for (String whereClause : iSelect.getWhereClauses()) {
			if (addedWhere == false) {
				sb.append("\n where " + whereClause);
				addedWhere = true;
			} else {
				sb.append(" and " + whereClause);
			}
		}
        for (ISqlTable iTable : iSelect.getSqlTables()) {
            for (String whereClause : iTable.getWhereClauses()) {
                if (addedWhere == false) {
                    sb.append("\n where " + whereClause);
                    addedWhere = true;
                } else {
                    sb.append(" and " + whereClause);
                }
            }
        }
		if (addedWhere == false) {
    		throw new IllegalArgumentException("Unable to perform sql update for [" + iSelect.getLeadTable() + "]. Unable to construct a suitable where clause");
		}
    }


	private void buildWhereClauses(IExecContext execContext, StringBuilder sb, ISqlTable sqlTable, ISelectInputs iSelect) {
		PK pk = sqlTable.getTable().getPk();
		boolean addedWhere = false;
		SqlField pkSqlField = null;
		if (pk != null) {
        	pkSqlField = sqlTable.getFieldByAliasOrName(pk.getName());
		}
		if (pkSqlField != null) {
        	Object pkValue = pkSqlField.getValue();
        	if (pkValue == null) {
        		throw new IllegalArgumentException("Unable to perform sql update for [" + sqlTable.getTableName() + "]. Missing PK value for [" + pk.getName() + "]");
        	}
            for (String whereClause : sqlTable.getWhereClauses()) {
                if (addedWhere == false) {
                    sb.append("\n where " + whereClause);
                    addedWhere = true;
                } else {
                    sb.append(" and " + whereClause);
                }
            }
            if (addedWhere == false) {
            	sb.append("\n where " + pk.getName() + " = " + pkValue);
                addedWhere = true;
            } else {
            	sb.append(" and " + pk.getName() + " = " + pkValue);
            }
        } else {
			for (String whereClause : iSelect.getWhereClauses()) {
				if (addedWhere == false) {
					sb.append("\n where " + whereClause);
					addedWhere = true;
				} else {
					sb.append(" and " + whereClause);
				}
			}
        }
		if (addedWhere == false) {
    		throw new IllegalArgumentException("Unable to perform sql update for [" + sqlTable.getTableName() + "]. Unable to construct a suitable where clause");
		}
	}
}
