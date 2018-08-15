package org.xmlactions.db.sql.common;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.xmlactions.action.config.IExecContext;
import org.xmlactions.db.ConstantsDB;
import org.xmlactions.db.DBSQL;
import org.xmlactions.db.actions.CommonStorageField;
import org.xmlactions.db.actions.FK;
import org.xmlactions.db.actions.PK;
import org.xmlactions.db.actions.Table;
import org.xmlactions.db.sql.select.ISelectInputs;
import org.xmlactions.db.sql.select.ISqlTable;
import org.xmlactions.db.sql.select.SqlField;

public class BuildInsertSql {

	private static final Logger log = LoggerFactory.getLogger(BuildInsertSql.class);
	
	public ISqlTable [] buildInsertSqls(IExecContext execContext, ISelectInputs iSelect) {
		
        StringBuilder sb = new StringBuilder();
	
        iSelect.setSqlTables(sortDependency(iSelect));
		for (ISqlTable sqlTable : iSelect.getSqlTables()) {
			buildInsertSql(execContext, iSelect, sqlTable);
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
                sb.append(tableName + Table.TABLE_FIELD_SEPERATOR + fieldName);
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
    	    		ISqlTable foundTable = findFKReferenceTable(selectInputs, fk);
    	    		if (foundTable != null) {
    	    			if (! alreadyInTable(sqlTable, field)) {
	    	    			SqlField newField = new SqlField(field.getName(),field.getAlias());
	    	    			newField.setValue("${" + fk.getForeign_table() + Table.TABLE_FIELD_SEPERATOR + fk.getForeign_key() + "}");
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
}
