
package org.xmlactions.db.sql.select;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.xmlactions.common.text.Text;
import org.xmlactions.db.actions.CommonStorageField;
import org.xmlactions.db.actions.Database;
import org.xmlactions.db.actions.Table;



public class SqlSelectInputs implements ISelectInputs {

    /** databaseName is not mandatory */
    private String databaseName;

    private List<String> orderByClauses = new ArrayList<String>();

    private List<String> whereClauses = new ArrayList<String>();
    private List<String> groupByClauses = new ArrayList<String>();

    private List<ISqlTable> sqlTables = new ArrayList<ISqlTable>();

    private String limitFrom = null, limitTo = null;
    
	private Database database;

	private Table leadTable;
	
	private String dbSpecificName;
    

    public SqlSelectInputs() {
        this.databaseName = null;
    }

    public SqlSelectInputs(String databaseName) {
        this.databaseName = databaseName;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }

    public String[] getOrderByClauses() {
        return orderByClauses.toArray(new String[orderByClauses.size()]);
    }

    public void addOrderByClause(String orderByClause) {
        if (orderByClause != null) {
            this.orderByClauses.add(orderByClause);
        }
    }

    public void addGroupByClause(String groupByClause) {
        if (groupByClause != null) {
            this.groupByClauses.add(groupByClause);
        }
    }

    public ISqlTable[] getSqlTables() {
        return sqlTables.toArray(new ISqlTable[sqlTables.size()]);
    }

    public void addSqlTable(ISqlTable iTable) {
        this.sqlTables.add(iTable);
    }

    public void addSqlTable(int index, ISqlTable iTable) {
        this.sqlTables.add(index, iTable);
    }

	public ISqlTable getSqlTable(String name) {
    	for (ISqlTable table : sqlTables) {
			if (name.equalsIgnoreCase(table.getTableName())) {
    			return table;
    		}
    	}
        return null;
    }

    public String[] getWhereClauses() {
        return whereClauses.toArray(new String[whereClauses.size()]);
    }

    public void addWhereClause(String whereClause) {
        this.whereClauses.add(whereClause);
    }

    public void setLimitFrom(String limitFrom) {
        this.limitFrom = limitFrom;
    }

    public String getLimitFrom() {
        return limitFrom;
    }

    public void setLimitTo(String limitTo) {
        this.limitTo = limitTo;
    }

    public String getLimitTo() {
        return limitTo;
    }

	public void setDatabase(Database database) {
		this.database = database;
	}

	public Database getDatabase() {
		return database;
	}

	public void setLeadTable(Table leadTable) {
		this.leadTable = leadTable;
	}

	public Table getLeadTable() {
		return leadTable;
	}

	public void setSqlTables(List<ISqlTable> sqlTables) {
		this.sqlTables = sqlTables;
	}

	public void setDbSpecificName(String dbSpecificName) {
		this.dbSpecificName = dbSpecificName;
	}

	public String getDbSpecificName() {
		return dbSpecificName;
	}

	public void setGroupByClauses(List<String> groupByClauses) {
		this.groupByClauses = groupByClauses;
	}

	public String [] getGroupByClauses() {
        return groupByClauses.toArray(new String[groupByClauses.size()]);
	}

	public String replaceWhereWithParams(String sql, List<SqlField> sqlParams) {
		// TODO Auto-generated method stub
		int index = 0;
		int count = 1;
		int fromIndex = 0;
		StringBuilder newSql = new StringBuilder();
//		while ((index = sql.indexOf('?', index)) != -1) {
		while ((index = findNextQuestionMark(sql, index)) != -1) {
			newSql.append(sql.substring(fromIndex, index+1));
			StringBuilder sb = new StringBuilder();
			boolean gotChar = false;
			boolean processing = true;
			boolean startedSingleQuoteProcessing = false;
			boolean startedDoubleQuoteProcessing = false;
			while (processing && ++index < sql.length()) {
				char c = sql.charAt(index);
				if (gotChar == false && ! Text.isWhiteSpaceChar(c)) {
					gotChar = true;
				}
				if (c == '\'') {
					if (startedSingleQuoteProcessing == true) {
						startedSingleQuoteProcessing = false;
						index++;
						processing = false;
						//c = ' ';
						continue;
					} else {
						if (startedDoubleQuoteProcessing == false) {
							startedSingleQuoteProcessing = true;
							// c = ' ';
							continue;
						}
					}
				} else if (c == '"') {
					if (startedDoubleQuoteProcessing == true) {
						startedDoubleQuoteProcessing = false;
						index++;
						processing = false;
						// c = ' ';
						continue;
					} else {
						if (startedSingleQuoteProcessing == false) {
							startedDoubleQuoteProcessing = true;
							//c = ' ';
							continue;
						}
					}
				} else if (gotChar == true && (startedDoubleQuoteProcessing==false && startedSingleQuoteProcessing==false) &&  Text.isWhiteSpaceChar(c)) {
					processing = false;
				}
				sb.append(c);
			}
			SqlField sqlField = new SqlField("p" + count++);
			sqlField.setValue(sb.toString() != null ? sb.toString().trim() : sb.toString());
			CommonStorageField commonStorageField = new org.xmlactions.db.actions.Text();
			sqlField.setCommonStorageField(commonStorageField);
			sqlParams.add(sqlField);
			fromIndex = index;
		}
		if (fromIndex < sql.length()-1) {
			newSql.append(sql.substring(fromIndex));
		}
		return newSql.toString();
	}
	
	private int findNextQuestionMark(String sql, int startIndex) {
		boolean startedSingleQuoteProcessing = false;
		boolean startedDoubleQuoteProcessing = false;
		for (int index = startIndex; index < sql.length(); index++) {
			char c = sql.charAt(index);
			if (c == '\'') {
				if (startedSingleQuoteProcessing == true) {
					startedSingleQuoteProcessing = false;
				} else {
					if (startedDoubleQuoteProcessing == false) {
						startedSingleQuoteProcessing = true;
					}
				}
			} else if (c == '"') {
				if (startedDoubleQuoteProcessing == true) {
					startedDoubleQuoteProcessing = false;
				} else {
					if (startedSingleQuoteProcessing == false) {
						startedDoubleQuoteProcessing = true;
					}
				}
			} else if (c == '?' && startedSingleQuoteProcessing==false && startedDoubleQuoteProcessing==false ) {
				return index;
			}
		}
		return -1;
	}
	

}
