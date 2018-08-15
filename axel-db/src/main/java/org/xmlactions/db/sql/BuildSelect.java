package org.xmlactions.db.sql;


import java.util.ArrayList;
import java.util.List;


import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.xmlactions.db.actions.CommonStorageField;
import org.xmlactions.db.actions.Database;
import org.xmlactions.db.actions.FK;
import org.xmlactions.db.actions.Storage;
import org.xmlactions.db.actions.Table;
import org.xmlactions.db.sql.select.ISelectInputs;
import org.xmlactions.db.sql.select.ISqlTable;
import org.xmlactions.db.sql.select.SqlField;
import org.xmlactions.db.sql.select.SqlJoin;
import org.xmlactions.db.sql.select.SqlJoinBase;
import org.xmlactions.db.sql.select.SqlLeftJoin;
import org.xmlactions.db.sql.select.SqlSelectInputs;
import org.xmlactions.db.sql.select.SqlTable;


public class BuildSelect
{

	private static final Logger log = LoggerFactory.getLogger(BuildSelect.class);
	private Storage storage;

	private String databaseName;

	private String tableName;

    private List<SqlField> sqlFields;

	private List<CommonStorageField> commonStorageFields;

	private List<ISqlTable> sqlTables = new ArrayList<ISqlTable>();
	
	private String dbSpecificName;

	/**
	 * 
	 * @param storage
	 * @param databaseName
	 * @param tableName
	 * @param sqlFields
	 * @param dbSpecificName
	 * @return
	 */
	public static SqlSelectInputs buildSelect(Storage storage, String databaseName, String tableName,
            List<SqlField> sqlFields, String dbSpecificName)
	{

		BuildSelect buildSelect = new BuildSelect();
		buildSelect.setStorage(storage);
		buildSelect.setDatabaseName(databaseName);
		buildSelect.setTableName(tableName);
        buildSelect.setSqlFields(sqlFields);
        buildSelect.setDbSpecificName(dbSpecificName);
		return buildSelect.buildSelect();

	}

	private SqlSelectInputs buildSelect()
	{

		SqlSelectInputs sqlSelectInputs = new SqlSelectInputs(getDatabaseName());
		sqlSelectInputs.setDatabase( getStorage().getDatabase(getDatabaseName()));
		sqlSelectInputs.setLeadTable(sqlSelectInputs.getDatabase().getTable(getTableName()));

        if (getSqlFields() != null) {
            setCommonStorageFields(sqlSelectInputs.getLeadTable().buildStorageFieldsList(getSqlFields()));
		} else {
			setCommonStorageFields(sqlSelectInputs.getLeadTable().getFields());
			sqlFields = buildSqlFields(getCommonStorageFields());
		}
		for (SqlField sqlField : sqlFields) {
			addTableAndField(sqlField);
		}
		for (ISqlTable sqlTable : getSqlTables() ) {
			sqlSelectInputs.addSqlTable(sqlTable);
		}
        sqlTables = includeTablePaths(sqlTables, sqlSelectInputs, commonStorageFields);
        sqlTables = resolveTableLinks(sqlTables, sqlSelectInputs, commonStorageFields);
        sqlSelectInputs.setSqlTables(sqlTables);
        sqlSelectInputs.setDbSpecificName(dbSpecificName);
		return sqlSelectInputs;
	}


	private void addTableName(List<String> tableNames, String tableName)
	{

		for (String table : tableNames) {
			if (table.equalsIgnoreCase(tableName)) {
				return;
			}
		}
		tableNames.add(tableName);
	}

	private void addTableAndField(SqlField sqlField)
	{
		CommonStorageField commonStorageField = sqlField.getCommonStorageField();
		ISqlTable sqlTable;
		if (commonStorageField != null) {
			sqlTable = findSqlTable(((Table) commonStorageField.getParent()).getName());
		} else {
			if (getSqlTables().size() > 0) {
				sqlTable = getSqlTables().get(0); 
			} else {
				throw new IllegalArgumentException("No SqlTables have been set. Unable to add field [" + sqlField.getFieldName() + "] sql [" + sqlField.getSql() + "]");
			}
		}
		
		if (sqlTable == null) {
			Table table = (Table) commonStorageField.getParent();
			sqlTable = new SqlTable(table);
			getSqlTables().add(sqlTable);
		}
        sqlTable.addField(sqlField);
	}

	private ISqlTable findSqlTable(String tableName)
	{

		for (ISqlTable sqlTable : getSqlTables()) {
			if (sqlTable.getTableName().equalsIgnoreCase(tableName)) {
				return sqlTable;
			}
		}
		return null;
	}

	private List<ISqlTable> getSqlTables()
	{

		return sqlTables;
	}

	public void setStorage(Storage storage)
	{

		this.storage = storage;
	}

	public Storage getStorage()
	{

		return storage;
	}

	public void setDatabaseName(String databaseName)
	{

		this.databaseName = databaseName;
	}

	public String getDatabaseName()
	{

		return databaseName;
	}

    public void setSqlFields(List<SqlField> sqlFields)
	{

        this.sqlFields = sqlFields;
	}

    public List<SqlField> getSqlFields()
	{

		return sqlFields;
	}

	public void setTableName(String tableName)
	{

		this.tableName = tableName;
	}

	public String getTableName()
	{

		return tableName;
	}

	public void setCommonStorageFields(List<CommonStorageField> commonStorageFields)
	{

		this.commonStorageFields = commonStorageFields;
	}

	public List<CommonStorageField> getCommonStorageFields()
	{

		return commonStorageFields;
	}

	private List<SqlField> buildSqlFields(List<CommonStorageField> commonStorageFields) {
		List<SqlField> sqlFields = new ArrayList<SqlField> ();
		for (CommonStorageField field : commonStorageFields) {
			sqlFields.add(new SqlField(field));
		}
		return sqlFields;
	}
	
    private List<ISqlTable> includeTablePaths(List<ISqlTable> sqlTables, SqlSelectInputs sqlSelectInputs,
            List<CommonStorageField> commonStorageFields) {
    	
    	for (int i1 = 0 ; i1 < sqlTables.size()-1; i1++) {
    		ISqlTable t1 = sqlTables.get(i1);
    		for (int i2 = i1+1 ; i2 < sqlTables.size(); i2++) {
        		ISqlTable t2 = sqlTables.get(i2);
        		findTablePath(sqlSelectInputs.getDatabase(), sqlTables, t1, t2);
    		}
    	}
        return sqlTables;
	}
    private void findTablePath(Database database, List<ISqlTable> sqlTables, ISqlTable sqlTable1, ISqlTable sqlTable2) {
    	log.debug("   path:" + sqlTable1.getTableName() + "-" + sqlTable2.getTableName());
    	Table table = database.findTablePath(sqlTable1.getTableName(), sqlTable2.getTableName());
    	if (table != null){
    		log.debug("   tp:" + table.getName());
    		if (isNewTable(table.getName(), sqlTables)) {
	        	SqlTable sqlTable = new SqlTable(table);
	        	for (CommonStorageField field : table.getFields()) {
	        		if (field instanceof FK) {
	        			FK fk = (FK) field;
	        			if (fk.getForeign_table().equalsIgnoreCase(sqlTable1.getTableName())) {
	                        SqlJoinBase sqlJoin = addJoin(database, sqlTable, fk);
	                        log.debug("   join:" + sqlJoin.getTableAlias() + " on " + sqlJoin.getOnValues().get(0));
	        			}
	        			else if (fk.getForeign_table().equalsIgnoreCase(sqlTable2.getTableName())) {
	                        SqlJoinBase sqlJoin = addJoin(database, sqlTable, fk);
	                        log.debug("   join:" + sqlJoin.getTableAlias() + " on " + sqlJoin.getOnValues().get(0));
	        			}
	        		}
	        	}
	        	sqlTables.add(sqlTable);
    		}
    	}
    }
    
    private boolean isNewTable(String tableName, List<ISqlTable> sqlTables) {
    	for (ISqlTable sqlTable : sqlTables) {
    		if (tableName.equalsIgnoreCase(sqlTable.getTableName())) {
    			return false;
    		}
    	}
    	return true;
    }
    private List<ISqlTable>  resolveTableLinks(List<ISqlTable> sqlTables, SqlSelectInputs sqlSelectInputs,
            List<CommonStorageField> commonStorageFields) {
        for (ISqlTable sqlTable : sqlTables) {
            log.debug("resolve:" + sqlTable.getTableName());
            resolveTableLinks(sqlTables, sqlSelectInputs, commonStorageFields, sqlTable);
        }
        sqlTables = orderTableLinks(sqlTables, sqlSelectInputs, commonStorageFields);
        removeDuplicateJoins(sqlTables);
        correctJoinTables(sqlTables, sqlSelectInputs);
        return sqlTables;
	}

    /**
     * Fix the joins so the tables are not repeated in the join ("xxx") table name
     * @param sqlTables
     * @param sqlSelectInputs
     */
    private void correctJoinTables(List<ISqlTable> sqlTables, SqlSelectInputs sqlSelectInputs) {
    	List<String> tableOrder = new ArrayList<String>();
    	sqlSelectInputs.getLeadTable();
    	int count = 0;
    	for (ISqlTable sqlTable : sqlTables) {
    		if (sqlTable.getJoins() != null) {
    	    	for (SqlJoinBase sqlJoin : sqlTable.getJoins()) {
	    			if (count == 0) {
	    				tableOrder.add(sqlJoin.getTableName());
	    				tableOrder.add(sqlJoin.getOtherTableName());
	    				count+=2;
	    			}
	    			else {
	    				correctJoinTableOrder(tableOrder, sqlJoin);
	    				tableOrder.add(sqlJoin.getTableName());
	    				count++;
	    			}
    	    	}
    		}
    	}
	}
    
    /**
     * Using the table order, figure if the table joins need to be swopped.
     * @param tableOrder
     * @param sqlJoin
     */
    private void correctJoinTableOrder(List<String> tableOrder, SqlJoinBase sqlJoin) {
    	String firstTableName = sqlJoin.getTableName();
    	String firstTableAlias = sqlJoin.getTableAlias();
    	String secondTableAlias = sqlJoin.getOtherTableAlias();
    	if (StringUtils.isNotEmpty(firstTableAlias) && ! firstTableAlias.equals(firstTableName)) {
			if (log.isDebugEnabled()) {
				log.debug("DONT SWOP JOIN TABLE ORDER because we have a" +
						" firstTableName[" + firstTableName  + "] firstTableAlias[" + firstTableAlias  + "]");
			}
			return;
    	}
    	for (String tableName : tableOrder) {
    		if (tableName.equalsIgnoreCase(firstTableName)) {
    			if (log.isDebugEnabled()) {
    				log.debug("\n" +
    						"SWOP JOIN TABLE ORDER" +
    						"\nfirstTableName[" + firstTableName + "] firstTableAlias[" + firstTableAlias  + "]" +
    						" with secondTableName[" + sqlJoin.getOtherTableName()+"] secondTableAlias[" + secondTableAlias +"]");
    			}
    			swopJoinTableOrder(sqlJoin);
    			return;
    		}
    	}
    }
    
    /**
     * Swop the tables so the join will use the 2nd and not the first table.
     * @param sqlJoin
     */
    private void swopJoinTableOrder(SqlJoinBase sqlJoin) {
    	String t1Name = sqlJoin.getTableName();
    	String t1Alias = sqlJoin.getTableAlias();
    	sqlJoin.setTableName(sqlJoin.getOtherTableName());
    	sqlJoin.setTableAlias(sqlJoin.getOtherTableAlias());
    	sqlJoin.setOtherTableName(t1Name);
    	sqlJoin.setOtherTableAlias(t1Alias);
    }
    
    private void resolveTableLinks(List<ISqlTable> sqlTables, SqlSelectInputs sqlSelectInputs,
            List<CommonStorageField> commonStorageFields, ISqlTable table) {
        for (ISqlTable sqlTable : sqlTables) {
            if (!sqlTable.getTableName().equals(table.getTableName())) {
                // log.debug("     on:" + sqlTable.getTableName());
                resolveTableLinks(sqlTables, sqlSelectInputs, commonStorageFields, table, sqlTable);
            }
        }
    }

    private void resolveTableLinks(List<ISqlTable> sqlTables, SqlSelectInputs sqlSelectInputs,
            List<CommonStorageField> commonStorageFields, ISqlTable table1, ISqlTable table2) {
        Database database = sqlSelectInputs.getDatabase();
        Table table = database.getTable(table1.getTableName());
        for (CommonStorageField field : table.getFields()) {
            if (field instanceof FK) {
                FK fk = (FK) field;
                if (fk.getForeign_table().equals(table2.getTableName())
                        || fk.getForeign_table().equals(table2.getTableAlias())) {
                    SqlJoinBase sqlJoin = addJoin(database, table1, fk);
                    log.debug("   join:" + sqlJoin.getTableName() + " on " + sqlJoin.getOnValues().get(0));
                }
            }
        }
    }
    
    private SqlJoinBase addJoin(Database database, ISqlTable table1, FK fk) {
    	SqlJoinBase sqlJoin;

		if (fk.isMandatory()) {
			sqlJoin = new SqlJoin();
		} else {
			sqlJoin = new SqlLeftJoin();
		}

		Table fkTable = database.getTable(fk.getForeign_table()); 
        sqlJoin.setTableName(fkTable.getName());
        if (StringUtils.isEmpty(fk.getForeign_table_alias())) {
        	sqlJoin.setTableAlias(fkTable.getAlias());
        } else {
        	sqlJoin.setTableAlias(fk.getForeign_table_alias());
        }

        sqlJoin.setOtherTableName(table1.getTableName());
        sqlJoin.setOtherTableAlias(table1.getTableAliasOrName());
    		
		String on;
        on = Table.buildTableAndFieldName(table1.getTableAliasOrName(), fk.getName()) + " = " +
        	Table.buildTableAndFieldName(sqlJoin.getTableAlias(), fk.getForeign_key());
    	if (StringUtils.isNotEmpty(fk.getWhere() )) {
    		on += " and " + fk.getWhere();
    	}
    	sqlJoin.addOnValue(on);

    	boolean added = table1.addUniqueJoin(sqlJoin);
    	if (log.isDebugEnabled()) {
    		if (added == true) {
    			log.debug("join added [" + on + "]");
    		} else {
    			log.debug("join not added [" + on + "]");
    		}
    	}
		
		return sqlJoin;
    	
    }

    private List<ISqlTable> orderTableLinks(List<ISqlTable> sqlTables, SqlSelectInputs sqlSelectInputs,
            List<CommonStorageField> commonStorageFields) {
    	List<ISqlTable> orderedTables = new ArrayList<ISqlTable>();
    	
    	// 1st put them all into the order list
        for (ISqlTable sqlTable : sqlTables) {
        	orderedTables.add(sqlTable);
        }
        
        // 2nd move all the ones that dont have a join to the end
        for (ISqlTable sqlTable : sqlTables) {
        	if (sqlTable.getJoins().size() == 0) {
        		orderedTables.remove(sqlTable);
        		orderedTables.add(sqlTable);
        	}
        }
    	// 3rd put them back into the original list
    	sqlTables = new ArrayList<ISqlTable>();
        for (ISqlTable sqlTable : orderedTables) {
        	sqlTables.add(sqlTable);
        }
       	// 4th order the ones that have a join, using references from one to the other.
        for (ISqlTable sqlTable : orderedTables) {
        	if (sqlTable.getJoins().size() > 0) {
        		for (SqlJoinBase join : sqlTable.getJoins()) {
                    log.debug("   sort:" + sqlTable.getTableName() + " on " + join.getTableName());
                    orderTables(sqlTables, sqlTable, join.getTableName());
        		}
        	}
        }
        
        for (ISqlTable sqlTable : sqlTables) {
            log.debug("ordered:" + sqlTable.getTableName());
        }
        
        return sqlTables;
	}
    
	private void orderTables(List<ISqlTable> sqlTables, ISqlTable sqlTable1, String tableName) {
		int first = sqlTables.indexOf(sqlTable1);
		ISqlTable sqlTable2 = null;
		for (ISqlTable sqlTable : sqlTables) {
			if (sqlTable.getTableName().equalsIgnoreCase(tableName)) {
				sqlTable2 = sqlTable;
            } else {
                for (SqlField sqlField : sqlTable.getFields()) {
                    String fieldTableName = Table.getTableName(sqlField.getFieldName());
                    if (fieldTableName != null && fieldTableName.equalsIgnoreCase(tableName)) {
                        sqlTable2 = sqlTable;
                        break;
                    }
                }
			}
            if (sqlTable2 != null) {
                break;
            }
		}
		if (sqlTable2 == null) {
			throw new IllegalArgumentException(tableName + " is not contained in List");
		}
		int second = sqlTables.indexOf(sqlTable2);
		if (second < first) {
            log.debug("replace:" + sqlTable1.getTableName() + "[" + first + "] with " + sqlTable2.getTableName() + "["
                    + second + "]");
			sqlTables.remove(first);
			sqlTables.remove(second);
			sqlTables.add(second, sqlTable1);
			sqlTables.add(first, sqlTable2);
		}
	}


    private void removeDuplicateJoins(List<ISqlTable> sqlTables) {
        for (ISqlTable sqlTable : sqlTables) {
            removeDuplicateJoins(sqlTables, sqlTable);
        }
    }

    private void removeDuplicateJoins(List<ISqlTable> sqlTables, ISqlTable curTable) {
        for (ISqlTable sqlTable : sqlTables) {
            if (sqlTable.getTableName() != curTable.getTableName()) {
                removeDuplicateJoins(curTable, sqlTable);
            }
        }
    }

    private void removeDuplicateJoins(ISqlTable curTable, ISqlTable otherTable) {
        if (curTable.getJoins() == null || otherTable.getJoins() == null) {
            return;
        }
        for (SqlJoinBase curJoin :curTable.getJoins()) {
            for (int index = otherTable.getJoins().size() - 1; index >= 0; index--) {
                SqlJoinBase otherJoin = otherTable.getJoins().get(index);
                if (matchJoins(curJoin, otherJoin)) {
                    if (matchTables(curTable, otherTable, curJoin)) {
                        for (String onValue : otherJoin.getOnValues()) {
                            curJoin.getOnValues().add(onValue);
                        }
                        otherTable.getJoins().remove(otherJoin);
                        log.debug("move join from:" + otherTable.getTableName() + " to " + curTable.getTableName());
                    }
                }
            }
        }
    }

    private boolean matchJoins(SqlJoinBase curJoin, SqlJoinBase otherJoin) {
        if ((curJoin.getOtherTableName().equalsIgnoreCase(otherJoin.getTableName()))
                && (curJoin.getTableName().equalsIgnoreCase(otherJoin.getOtherTableName()))) {
            return true;
        }
        return false;
    }

    private boolean matchTables(ISqlTable curTable, ISqlTable otherTable, SqlJoinBase join) {
        return matchTables(curTable.getTableName(), otherTable.getTableName(), join);
    }

    private boolean matchTables(String t1Name, String t2Name, SqlJoinBase join) {
        String j1Name = join.getTableName();
        String j2Name = join.getOtherTableName();
        if ( t1Name.equalsIgnoreCase(j1Name) && t2Name.equalsIgnoreCase(j2Name)) {
            return true;
        }
        if ( t1Name.equalsIgnoreCase(j2Name) && t2Name.equalsIgnoreCase(j1Name)) {
            return true;
        }
        return false;
    }

	public void setDbSpecificName(String dbSpecificName) {
		this.dbSpecificName = dbSpecificName;
	}

	public String getDbSpecificName() {
		return dbSpecificName;
	}


}
