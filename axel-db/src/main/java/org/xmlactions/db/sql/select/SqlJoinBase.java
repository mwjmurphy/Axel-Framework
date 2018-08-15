package org.xmlactions.db.sql.select;

import java.util.ArrayList;
import java.util.List;


public abstract class SqlJoinBase
{
    private String tableName;
    private String tableAlias;
    private String otherTableName;
    private String otherTableAlias;

	private List<String> onValues = new ArrayList<String>();

	public abstract String buildJoinClause(List<String> tablesAlreadyInList);

	/**
	 * joinType is the text syntax for the join type.<br/>
	 * Example1: "left join"<br/>
	 * Example2: "join"<br/>
	 * 
	 * @param joinType
	 * @return
	 */
	protected String buildJoinClause(List<String> tablesAlreadyInList, String joinType)
	{
		StringBuilder sb = new StringBuilder();
		sb.append(" " + joinType + " (");
		boolean moreThanOne = false;
		if (! alreadyInList(tablesAlreadyInList, getTableAlias())) {
	        sb.append(getTableName());
	        if (getTableAlias() != null) {
	            sb.append(' ');
	            sb.append(getTableAlias());
	        }
			sb.append(")\n   on ");
			for (String onValue : getOnValues()) {
	            if (moreThanOne) {
	                sb.append("\n   and ");
	            }
				sb.append(onValue);
	            moreThanOne = true;
			}
		} else {
	        sb.append(getOtherTableName());
	        if (getOtherTableAlias() != null) {
	            sb.append(' ');
	            sb.append(getOtherTableAlias());
	        }
			sb.append(")\n   on ");
			for (String onValue : getOnValues()) {
	            if (moreThanOne) {
	                sb.append("\n   and ");
	            }
				sb.append(onValue);
	            moreThanOne = true;
			}
		}
		return sb.toString();
	}

	/**
	 * Builds the and clause as extensions for the join clause.
	 * @return
	 */
	public String buildAndClause()
	{
		StringBuilder sb = new StringBuilder();
		boolean moreThanOne = false;
		for (String onValue : getOnValues()) {
            if (moreThanOne == false) {
                sb.append("   and ");
            }
			sb.append(onValue);
            moreThanOne = true;
		}
		return sb.toString();
	}

	public List<String> getOnValues()
	{
		return onValues;
	}

	/**
	 * The onValue must contain the correct syntax to match the object types.<br/>
	 * Example1: tb_name.name='mike'<br/>
	 * Example1: tb_name.id=tb_address.name_id
	 * 
	 * @param onValue
	 */
	public void addOnValue(String onValue)
	{
		getOnValues().add(onValue);
	}

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableAlias(String tableAlias) {
        this.tableAlias = tableAlias;
    }

    public String getTableAlias() {
        if (tableAlias == null) {
            return getTableName();
        }
        return tableAlias;
    }

    public void setOtherTableName(String otherTableName) {
        this.otherTableName = otherTableName;
    }

    public String getOtherTableName() {
        return otherTableName;
    }

    public void setOtherTableAlias(String otherTableAlias) {
        this.otherTableAlias = otherTableAlias;
    }

    public String getOtherTableAlias() {
        return otherTableAlias;
    }
    
    public String toString() {
    	StringBuilder sb = new StringBuilder();
    	sb.append(tableName + "-" + otherTableName);
    	return sb.toString();
    }
    
    /**
     * Change if a table is already in a list.
     * @param tablesAlreadyInList
     * @param tableName
     * @return true if tableName is already in tablesAlreadyInList
     */
    public boolean alreadyInList(List<String> tablesAlreadyInList, String tableName) {
    	for (String existingTableName : tablesAlreadyInList) {
    		if (existingTableName.equalsIgnoreCase(tableName)) {
    			return true;
    		}
    	}
    	return false;
    }

}
