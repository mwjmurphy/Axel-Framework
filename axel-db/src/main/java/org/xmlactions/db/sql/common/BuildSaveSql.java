package org.xmlactions.db.sql.common;



import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.common.collections.PropertyKeyValue;
import org.xmlactions.db.ConstantsDB;
import org.xmlactions.db.actions.CommonStorageField;
import org.xmlactions.db.actions.Database;
import org.xmlactions.db.actions.FK;
import org.xmlactions.db.actions.PK;
import org.xmlactions.db.actions.Table;
import org.xmlactions.db.sql.select.ISelectInputs;
import org.xmlactions.db.sql.select.ISqlTable;
import org.xmlactions.db.sql.select.SqlField;
import org.xmlactions.db.sql.select.VersionNumberConcurrency;

public class BuildSaveSql {

	private static final Logger log = LoggerFactory.getLogger(BuildSaveSql.class);
	
	public ISqlTable [] buildSaveSqls(IExecContext execContext, ISelectInputs iSelect) {
		
        StringBuilder sb = new StringBuilder();
	
        iSelect.setSqlTables(sortDependency(iSelect));
		for (ISqlTable sqlTable : iSelect.getSqlTables()) {
			boolean havePkValue = false;
			PK pk = sqlTable.getTable().getPk();
			String pkName = Table.buildTableAndFieldName(sqlTable.getTableName(), pk.getName());
			for (SqlField sqlField : sqlTable.getFields())
			{
				String fieldName = Table.getFieldName(sqlField.getFieldName());
				if (pk != null && fieldName != null && fieldName.equals(pk.getName())) {
					if (StringUtils.isNotBlank((String)sqlField.getValue())) {
						havePkValue = true;
						break;
					}
				}
				// if we have a PK and the pk has a value then we want to update not insert
				log.debug(fieldName + " pk:" + pk.getName());
			}
			if (havePkValue) {
				log.debug("UPDATE we have a pk value");
				buildUpdateSql(execContext, iSelect, sqlTable);
			} else {
				log.debug("INSERT we dont a pk value");
				buildInsertSql(execContext, iSelect, sqlTable);
			}
		}
        return iSelect.getSqlTables();
    }

	/**
	 * @deprecated use buildInsertSql(IExecContext execContext, ISelectInputs iSelect, ISqlTable sqlTable)
	 * @param execContext
	 * @param iSelect
	 * @return
	 */
	public String buildInsertSql(IExecContext execContext, ISelectInputs iSelect) {
        StringBuilder sb = new StringBuilder();
        sb.append("insert into ");
        addTableNames(sb, iSelect);
        addFieldsForInsert(sb, iSelect);
        addValuesForInsert(sb, iSelect);
        return sb.toString();
    }

	private void buildInsertSql(IExecContext execContext, ISelectInputs iSelect, ISqlTable sqlTable) {
        StringBuilder sb = new StringBuilder();
        sb.append("insert into ");
        sb.append(sqlTable.getTableName());
        addFieldsForInsert(sb, sqlTable, iSelect);
        addValuesForInsert(sb, sqlTable, iSelect);
        sqlTable.setInsertSql(sb.toString());
    }

	private String buildUpdateSql(IExecContext execContext, ISelectInputs iSelect, ISqlTable sqlTable) {
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
	

    private void addTableNames(StringBuilder sb, ISelectInputs iSelect) {
    	boolean addComma = false;
        for (ISqlTable iTable : iSelect.getSqlTables()) {
        	if (addComma == true) {
        		sb.append(", ");
        	}
       		sb.append(iTable.getTableName());
        }
    }
	

    private void addFieldsForInsert(StringBuilder sb, ISqlTable sqlTable, ISelectInputs iSelect) {
        boolean addedField = false;
        sb.append("\n(");
        if (sqlTable.getFields().size() > 0) {
            if (hasPkCreate(sqlTable.getTable())) {
        		// M.M. 23-Jul-2012
                //sb.append(sqlTable.getTable().getName() + Table.TABLE_FIELD_SEPERATOR
                //        + sqlTable.getTable().getPk().getName());
                // addedField = true;
                List<SqlField> sqlFields = sqlTable.getFields();
                SqlField pkSqlField = new SqlField(sqlTable.getTable().getPk());
                pkSqlField.setValue("${" + ConstantsDB.SQL_INSERT_PK_VALUE + "}");
                sqlFields.add(0,pkSqlField);
                sqlTable.setFields(sqlFields);
            }
            for (SqlField sqlField : sqlTable.getFields()) {
               	String tableName = sqlTable.getTableName();
               	String fieldName = Table.getFieldName(sqlField.getFieldName());
              	if (fieldName == null) {
            		fieldName = sqlField.getFieldName();
             	}
                if (addedField == true) {
                    sb.append(", ");
                } else {
                	addedField = true;
                }
                //sb.append(tableName + Table.TABLE_FIELD_SEPERATOR + fieldName);
                sb.append(fieldName);
            }
        }
        sb.append(')');
    }

    /**
     * @param sb
     * @param sqlTable
     * @param iSelect
     */
    private void addValuesForInsert(StringBuilder sb, ISqlTable sqlTable, ISelectInputs iSelect) {
    	List<SqlField> params = new ArrayList<SqlField>();
    	sqlTable.setParams(params);
    	boolean addedField = false;
    	sb.append("\n values (");
    	if (sqlTable.getFields().size() > 0) {
    		for (SqlField sqlField : sqlTable.getFields()) {
    			String tableName = Table.getTableName(sqlField.getFieldName());
    			if (tableName == null) {
    				tableName = sqlTable.getTableName();
    			}
    			String fieldName = Table.getFieldName(sqlField.getFieldName());
    			if (fieldName == null) {
    				fieldName = sqlField.getFieldName();
    			}
    			if (addedField == true) {
    				sb.append(", ");
    			} else {
    				addedField = true;
    			}
            	params.add(sqlField);
            	sb.append("?");
    		}
    	}
    	sb.append(')');
    }

    /**
     * @deprecated use addFieldsForInsert(StringBuilder sb, ISqlTable sqlTable, ISelectInputs iSelect)
     * @param sb
     * @param iSelect
     */
    private void addFieldsForInsert(StringBuilder sb, ISelectInputs iSelect) {
        boolean addedField = false;
        sb.append("\n(");
        for (ISqlTable iTable : iSelect.getSqlTables()) {
            if (hasPkCreate(iSelect.getLeadTable())) {
                String pkCreateSql = iSelect.getLeadTable()
                        .getPk()
                        .getPkCreateSql(iSelect.getDatabase(), iSelect.getDbSpecificName());
            }
            if (iTable.getFields().size() > 0) {
                if (hasPkCreate(iSelect.getLeadTable())) {
                    String pkCreateSql = iSelect.getLeadTable()
                            .getPk()
                            .getPkCreateSql(iSelect.getDatabase(), iSelect.getDbSpecificName());
                    sb.append(iSelect.getLeadTable().getName() + Table.TABLE_FIELD_SEPERATOR
                            + iSelect.getLeadTable().getPk().getName());
                    addedField = true;
                }
                for (SqlField sqlField : iTable.getFields()) {
                	String tableName = iTable.getTableName();
                	String fieldName = Table.getFieldName(sqlField.getFieldName());
                	if (fieldName == null) {
                		fieldName = sqlField.getFieldName();
                	}
                    if (addedField == true) {
                        sb.append(", ");
                    } else {
                    	addedField = true;
                    }
                    sb.append(tableName + Table.TABLE_FIELD_SEPERATOR + fieldName);
                }
            }
        }
        sb.append(')');
    }

    
    /**
     * @deprecated use addValuesForInsert(StringBuilder sb, ISqlTable sqlTable, ISelectInputs iSelect)
     * @param sb
     * @param iSelect
     */
    private void addValuesForInsert(StringBuilder sb, ISelectInputs iSelect) {
    	List<SqlField> params = new ArrayList<SqlField>();
    	// iSelect.setParams(params);
        boolean addedField = false;
        sb.append("\n values (");
        for (ISqlTable iTable : iSelect.getSqlTables()) {
            if (iTable.getFields().size() > 0) {
                if (hasPkCreate(iSelect.getLeadTable())) {
                    sb.append("${" + ConstantsDB.SQL_INSERT_PK_VALUE + "}");
                    addedField = true;
                }
                for (SqlField sqlField : iTable.getFields()) {
                	String tableName = Table.getTableName(sqlField.getFieldName());
                	if (tableName == null) {
                		tableName = iTable.getTableName();
                	}
                	String fieldName = Table.getFieldName(sqlField.getFieldName());
                	if (fieldName == null) {
                		fieldName = sqlField.getFieldName();
                	}
                    if (addedField == true) {
                        sb.append(", ");
                    } else {
                    	addedField = true;
                    }
                	params.add(sqlField);
                	sb.append("?");
                	
                    //if (sqlField.getValue() instanceof String) {
                    //	sb.append('\'' + DBSQL.escCharacters((String)sqlField.getValue()) + '\'');
                    //} else {
                    //	sb.append(sqlField.getValue());
                    //}
                }
            }
        }
        sb.append(')');
    }
	

    private boolean hasPkCreate(Table table) {
        PK pk = table.getPk();
        if (pk != null && StringUtils.isNotEmpty(pk.getPk_ref())) {
            return true;
        }
        return false;
    }
    
    private String buildShortTableFieldName(ISqlTable sqlTable, SqlField sqlField) {
    	StringBuilder  name = new StringBuilder();
    	name.append(sqlTable.getTableAliasOrName());
    	name.append(Table.TABLE_FIELD_SEPERATOR);
    	name.append(sqlField.getAliasOrFieldName());
    	return name.toString();
    	
    }

    
    private List<ISqlTable> sortDependency(ISelectInputs selectInputs) {
    	List<ISqlTable> sortedTables = new ArrayList<ISqlTable>();
    	for (ISqlTable sqlTable : selectInputs.getSqlTables()) {
    		if (doesSqlTableAlreadExist(sortedTables, sqlTable) == false) {
    			sortedTables.add(sqlTable);
    		}
    		Table table = selectInputs.getDatabase().getTable(sqlTable.getTableName());
			if (log.isDebugEnabled()) {
				log.debug("sort sqlTable [" + sqlTable.getTableName() + "]");
				log.debug("     fieldCount [" + table.getFields().size() + "]");
			}
    		for (CommonStorageField field : table.getFields()) {
    			if (field instanceof FK) {
    				FK fk = (FK)field;
    				if (log.isDebugEnabled()) {
    					log.debug("     fk table [" + fk.getForeign_table() + "] fk.field [" + fk.getForeign_key() + "]");
    				}
    				ISqlTable foundTable = null;
    				SqlField foundField = findFKReferenceField(selectInputs, fk);
    				if (foundField != null) {
    					foundTable = findFKReferenceTable(selectInputs, fk);
    				}
    	    		if (foundTable != null) {
    	    			if (! alreadyInTable(sqlTable, field)) {
	    	    			SqlField newField = new SqlField(field.getName(),field.getAlias());
	    	    			//newField.setValue("${" + fk.getForeign_table() + Table.TABLE_FIELD_SEPERATOR + fk.getForeign_key() + "}");
	    	    			String name = buildShortTableFieldName(foundTable, foundField);
	    	    			newField.setValue(name);
	    	    			sqlTable.addField(newField);
	    	    			sqlTable.addChild(sqlTable);
	    	    			sortedTables.remove(foundTable);
	    	    			sortedTables.add(foundTable);
	    	    			if (log.isDebugEnabled()) {
	    	    				log.debug("     found matching sqlTable [" + sqlTable.getTableName() + "] for FK [" + fk.getForeign_table() + "]");
	    	    			}
    	    			}
    	    		}
    			}
    		}
    	}
    	return sortedTables;
    }
    
    private boolean alreadyInTable(ISqlTable sqlTable, CommonStorageField field) {
    	for (SqlField sqlField : sqlTable.getFields()) {
    		boolean result = equals(sqlField.getFieldName(), field.getName());
    		if (result == false) {
    			result = equals(sqlField.getFieldAlias(), field.getAlias());
    		}
    		if (result == true) {
    			return true;
    		}
    	}
    	return false;
    }
    
    private boolean equals(String n1, String n2) {
    	if (n1 != null && n2 != null) {
    		String name1;
    		if (Table.isTableAndFieldName(n1)) {
    			name1 = Table.getFieldName(n1);
    		} else {
    			name1  = n1;
    		}
    		String name2;
    		if (Table.isTableAndFieldName(n2)) {
    			name2 = Table.getFieldName(n2);
    		} else {
    			name2  = n2;
    		}
    		return name1.equals(name2);
    	}
    	return false;
    }

    
    private boolean doesSqlTableAlreadExist(List<ISqlTable> sortedTables, ISqlTable newSqlTable) {
    	for (ISqlTable sqlTable : sortedTables) {
    		if (newSqlTable.getTableName().equals(sqlTable.getTableName())) {
    			return true;
    		}
    	}
    	return false;
    }
    	
    private ISqlTable findFKReferenceTable(ISelectInputs selectInputs, FK fk) {
    	for (ISqlTable sqlTable : selectInputs.getSqlTables()) {
    		if (sqlTable.getTableName().equals(fk.getForeign_table())) {
    			return(sqlTable);
    		}
    	}
    	return null;
    }
    
    private SqlField findFKReferenceField(ISelectInputs selectInputs, FK fk) {
    	for (ISqlTable sqlTable : selectInputs.getSqlTables()) {
    		if (sqlTable.getTableName().equals(fk.getForeign_table())) {
    			SqlField sqlField = findField(sqlTable, fk);
    			return sqlField;
    		}
    	}
    	return null;
    }
    
    private SqlField findField(ISqlTable sqlTable, FK fk) {
    	for (SqlField field : sqlTable.getFields()) {
    		if (fk.getForeign_key().equals(field.getFieldName()) || fk.getForeign_key().equals(field.getFieldAlias())) {
    			return field;
    		}
    	}
    	return null;	// not found
    }
    
	private void buildWhereClauses(IExecContext execContext, StringBuilder sb, ISqlTable sqlTable, ISelectInputs iSelect) {
		
		Object obj = execContext.get(ConstantsDB.ENFORCE_CONCURRENCY);
		if (obj != null && (Boolean)obj == false) {
			// skip concurrency
		} else {
			PropertyKeyValue versionNumber = VersionNumberConcurrency.getVersionNumber(execContext, sqlTable);
			if (versionNumber != null) {
				sqlTable.getWhereClauses().add(versionNumber.getKey() + "=" + versionNumber.getValue());
			}
		}

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
