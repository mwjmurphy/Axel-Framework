
package org.xmlactions.db.sql.mysql;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;








import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.text.StrSubstitutor;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.db.DBSQL;
import org.xmlactions.db.actions.Table;
import org.xmlactions.db.sql.common.BuildInsertSql;
import org.xmlactions.db.sql.common.BuildSaveSql;
import org.xmlactions.db.sql.common.BuildUpdateSql;
import org.xmlactions.db.sql.common.SqlCommon;
import org.xmlactions.db.sql.select.ISelectInputs;
import org.xmlactions.db.sql.select.ISqlSelectBuildQuery;
import org.xmlactions.db.sql.select.ISqlTable;
import org.xmlactions.db.sql.select.SqlField;
import org.xmlactions.db.sql.select.SqlJoinBase;

public class MySqlSelectQuery implements ISqlSelectBuildQuery {

	private String manualSql = null;
	
    public MySqlSelectQuery() {
        
    }
    
	public ISqlTable[] buildUpdateSqls(IExecContext execContext, ISelectInputs iSelect) {
    	return new BuildUpdateSql().buildUpdateSqls(execContext, iSelect);
	}
	
	public String buildUpdateSql(IExecContext execContext, ISelectInputs iSelect) {
    	return new BuildUpdateSql().buildUpdateSql(execContext, iSelect);
    }
    
    public String buildInsertSql(IExecContext execContext, ISelectInputs iSelect) {
    	return new BuildInsertSql().buildInsertSql(execContext, iSelect);
    }
    
    public ISqlTable[] buildInsertSqls(IExecContext execContext, ISelectInputs iSelect) {
    	return new BuildInsertSql().buildInsertSqls(execContext, iSelect);
    }
    
    public ISqlTable[] buildSaveSqls(IExecContext execContext, ISelectInputs iSelect) {
    	return new BuildSaveSql().buildSaveSqls(execContext, iSelect);
    }
    
    /**
     * sqlParams can be null
     */
    public String buildSelectQuery(IExecContext execContext, ISelectInputs iSelect, List<SqlField> sqlParams) {
        StringBuilder sb = new StringBuilder();
        if (iSelect.getLimitFrom() != null) {
        	if (StringUtils.isNotEmpty(getSql())) {
	            sb.append("select * ");
	            sb.append("\n from (");
	            sb.append(buildInnerSelect(iSelect, true));
				sb.append("\n) t");
				sb.append(addLimits(iSelect));
        	} else {
	            sb.append("select ");
				addSelectFromTablesAndFieldAlias(sb, iSelect);
	            sb.append("\n from (");
	            sb.append(buildInnerSelect(iSelect, true));
				sb.append("\n) t");
				sb.append(addLimits(iSelect));
        	}
        } else {
            sb.append(buildInnerSelect(iSelect, false));
        }
        String sql = StrSubstitutor.replace(sb.toString(), execContext);
        if (sqlParams != null) {
        	return iSelect.replaceWhereWithParams(sql, sqlParams);
        } else {
        	return sql;
        }
    }
    
    public String buildInnerSelect(ISelectInputs iSelect, boolean inside) {
        StringBuilder sb = new StringBuilder();
        if (StringUtils.isNotEmpty(getSql())) {
            sb.append("\n" + getSql());
    		addWhereClauses(sb, iSelect);
            addGroupByClauses(sb, iSelect);
            addOrderByClauses(sb, iSelect);
        } else {
            sb.append("\n  select");
            addSelectFromTablesAndFieldNames(sb, iSelect, inside);
    		addJoinClauses(sb, iSelect);
    		addWhereClauses(sb, iSelect);
            addGroupByClauses(sb, iSelect);
            addOrderByClauses(sb, iSelect);
        }
        return sb.toString();
    }

    private void addSelectFromTablesAndFieldAlias(StringBuilder sb, ISelectInputs iSelect) {
        boolean addedField = false;
        // ===
        // Get the list of fields we want to query
        // in table_fieldalias format
        for (ISqlTable iTable : iSelect.getSqlTables()) {
            if (iTable.getFields().size() > 0) {
                for (SqlField sqlField : iTable.getFields()) {
                    if (addedField == true) {
                        sb.append(",");
                    }
                	if (StringUtils.isNotBlank(sqlField.getSql())) {
	                    //sb.append("\n " + sqlField.getAliasOrFieldName());
	                    //sb.append(" as \"" + sqlField.getAliasOrFieldName() + "\"");
	                    String tableName = Table.getTableName(sqlField.getFieldName());
	                    if (tableName == null) {
		                    sb.append("\n " + sqlField.getAliasOrFieldName() + " as \"" + sqlField.getAliasOrFieldName() + "\"");
	                    } else {
		                    String fieldAlias = Table.getFieldName(sqlField.getAliasOrFieldName());
		                    if (fieldAlias == null) {
		                    	fieldAlias = sqlField.getAliasOrFieldName();
		                    }
		                    sb.append("\n " + tableName + Table.TABLE_FIELD_AS_SEPERATOR + fieldAlias);
		                    sb.append(" as \"" + tableName + Table.TABLE_FIELD_SEPERATOR + fieldAlias + "\"");
	                    }
                	} else {
	                	String tableName = Table.getTableName(sqlField.getFieldName());
	                	if (tableName == null) {
	                		tableName = iTable.getTableName();
	                	}
	                    String fieldAlias = Table.getFieldName(sqlField.getAliasOrFieldName());
	                    if (fieldAlias == null) {
	                    	fieldAlias = sqlField.getAliasOrFieldName();
	                    }
	                    sb.append("\n " + tableName + Table.TABLE_FIELD_AS_SEPERATOR + fieldAlias);
	                    sb.append(" as \"" + tableName + Table.TABLE_FIELD_SEPERATOR + fieldAlias + "\"");
                	}
                    addedField = true;
                }
            }
        }
    }

    private void addSelectFromTablesAndFieldNames(StringBuilder sb, ISelectInputs iSelect, boolean inside) {
        boolean addedField = false;
        for (ISqlTable iTable : iSelect.getSqlTables()) {
            if (iTable.getFields().size() > 0) {
                for (SqlField sqlField : iTable.getFields()) {
                    if (addedField == true) {
                        sb.append(",");
                    }
                	if (StringUtils.isBlank(sqlField.getSql())) {
	                	String tableName = Table.getTableName(sqlField.getFieldName());
	                	if (tableName == null) {
	                		tableName = iTable.getTableName();
	                	}
	                	String fieldName = Table.getFieldName(sqlField.getFieldName());
	                	if (fieldName == null) {
	                		fieldName = sqlField.getFieldName();
	                	}
	                    String sqlName = tableName + Table.TABLE_FIELD_SEPERATOR + fieldName;
	                    if (sqlField.getFunction_ref() != null) {
	                    	String field = SqlCommon.replaceForSqlFunction(iSelect.getDatabase(), iSelect.getDbSpecificName(), sqlField.getFunction_ref(), sqlName);
	                        sb.append("\n " + field + "");
	                    } else if (sqlField.getCommonStorageField() != null
	                            && sqlField.getCommonStorageField().getFunction_ref() != null) {
	                        String field = SqlCommon.replaceForSqlFunction(iSelect.getDatabase(),
	                                iSelect.getDbSpecificName(),
	                                sqlField.getCommonStorageField().getFunction_ref(),
	                                sqlName);
	                        sb.append("\n " + field + "");
	                    } else {
	                        sb.append("\n " + sqlName);
	                    }
	                    String fieldAlias;
	                    if (Table.isTableAndFieldName(sqlField.getAliasOrFieldName())) {
	                    	fieldAlias = Table.getFieldName(sqlField.getAliasOrFieldName());
	                    } else {
	                    	fieldAlias = sqlField.getAliasOrFieldName();
	                    }
	                    if (inside) {
	                    	sb.append(" as " + tableName + Table.TABLE_FIELD_AS_SEPERATOR + fieldAlias);
	                    } else {
	                        sb.append(" as \"" + tableName + Table.TABLE_FIELD_SEPERATOR + fieldAlias + "\"");
	                    }
                	} else {
                    	sb.append("\n " + sqlField.getSql());
                		String as;
	                    if (Table.isTableAndFieldName(sqlField.getAliasOrFieldName())) {
		                    if (inside) {
		                    	sb.append(" as " + Table.getTableName(sqlField.getAliasOrFieldName()) + Table.TABLE_FIELD_AS_SEPERATOR + Table.getFieldName(sqlField.getAliasOrFieldName()));
		                    } else {
		                    	sb.append(" as \"" + Table.getTableName(sqlField.getAliasOrFieldName()) + Table.TABLE_FIELD_SEPERATOR + Table.getFieldName(sqlField.getAliasOrFieldName()) + "\"");
		                    }
	                    } else {
		                    if (inside) {
		                    	sb.append(" as " + sqlField.getAliasOrFieldName() + "");
		                    } else {
		                    	sb.append(" as \"" + sqlField.getAliasOrFieldName() + "\"");
		                    }
	                    }
                		
                	}
                    addedField = true;
                }
            }
        }
        if (addedField == false) {
            sb.append(" *");
        }
        sb.append("\n from ");
        ISqlTable firstTable = iSelect.getSqlTables()[0];
        sb.append(firstTable.getTableName());
        if (firstTable.getTableAlias() != null) {
            sb.append(" " + firstTable.getTableAlias());
        }

    }
    
	private void addJoinClauses(StringBuilder sb, ISelectInputs iSelect)
	{
		List<String> tablesAlreadyInList = new ArrayList<String>();

		for (ISqlTable iTable : iSelect.getSqlTables()) {
			boolean moreThanOne = false;
			SqlJoinBase firstJoin = null;
			for (SqlJoinBase join : iTable.getJoins()) {
				if (moreThanOne == true &&
						alreadyInList(tablesAlreadyInList, iTable, join.getTableAlias(), join.getOtherTableAlias()) == true) {
					sb.append("\n " + join.buildAndClause());
				} else {
					boolean added = false;
					if (join.getTableAlias().equals(iTable.getTableAliasOrName())) {
						if (alreadyInList(tablesAlreadyInList,iTable.getTableAliasOrName())) {
							sb.append("\n " + join.buildAndClause());
							added = true;
						}
					}
					if (added == false) {
						sb.append("\n " + join.buildJoinClause(tablesAlreadyInList));
					}
					firstJoin = join;
				}
				moreThanOne = true;
			}
			if (firstJoin != null) {
				tablesAlreadyInList.add(firstJoin.getTableAlias());
				tablesAlreadyInList.add(firstJoin.getOtherTableAlias());
			}
		}
	}

	private boolean alreadyInList(List<String> tablesAlreadyInList, ISqlTable iTable, String tableAlias, String otherTableAlias) {
		
		if (iTable.getTableAliasOrName().equals(tableAlias)) {
			if (alreadyInList(tablesAlreadyInList,otherTableAlias)) {
				return true;
			}
		} else {
			if (alreadyInList(tablesAlreadyInList,tableAlias)) {
				return true;
			}
		}
		return false;
	}

	private boolean alreadyInList(List<String> tablesAlreadyInList, String tableNameOrAlias) {
		
		for (String tableInList : tablesAlreadyInList) {
			if (tableInList.equals(tableNameOrAlias)) {
				return true;
			}
		}
		return false;
	}
	
	
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
    }

	private void addOrderByClauses(StringBuilder sb, ISelectInputs iSelect)
	{
        boolean addedOrderBy = false;
        for (String orderBy : iSelect.getOrderByClauses()) {
            if (addedOrderBy == false) {
                sb.append("\n order by " + orderBy);
                addedOrderBy = true;
            } else {
                sb.append(" ," + orderBy);
            }
        }
    }

	private void addGroupByClauses(StringBuilder sb, ISelectInputs iSelect)
	{
        boolean addedGroupBy = false;
        for (String groupBy : iSelect.getGroupByClauses()) {
            if (addedGroupBy == false) {
                sb.append("\n group by " + groupBy);
                addedGroupBy = true;
            } else {
                sb.append(" ," + groupBy);
            }
        }
    }

	private String addLimits(ISelectInputs iSelect)
	{

		String limit = "";
		long from = 0, to = 0;
		if (iSelect.getLimitFrom() != null) {
			from = Long.parseLong(iSelect.getLimitFrom());
		}
		if (iSelect.getLimitTo() != null) {
			to = Long.parseLong(iSelect.getLimitTo());
		}
		// int offset, rows;
		if (iSelect.getLimitFrom() != null) {
			if (iSelect.getLimitTo() != null) {
				limit = " limit " + (to - (from - 1)) + " offset " + (from - 1);
			} else {
				limit = "\n limit " + from;
			}
		} else if (iSelect.getLimitTo() != null) {
			limit = "\n limit " + to;
		}
		return limit;
	}

	public String getSql() {
		return manualSql;	
	}

	/**
	 * Can be used to create a manual sql in place of the generated sql.
	 * <p>
	 * same functionality applies as with the generated sql, you can set the
	 * whereClause and the orderBy and also the from and to limits.
	 * </p>
	 */
	public void setSql(String sql) {
		manualSql = sql;
	}


}
