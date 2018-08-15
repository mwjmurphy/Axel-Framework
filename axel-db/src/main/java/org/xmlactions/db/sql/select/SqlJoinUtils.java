package org.xmlactions.db.sql.select;


import java.util.List;


import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.xmlactions.db.actions.FK;
import org.xmlactions.db.actions.Table;


public class SqlJoinUtils
{

	private static final Logger log = LoggerFactory.getLogger(SqlJoinUtils.class);

    /**
     * 
     * @param leadTable
     * @param iSqlTable
     * @param table
     * @param fk
     * @param switchTables
     *            - if set we use the fk table name / alias
     */
    public static void addFkJoinClause(List<String> addedTables, Table leadTable, ISqlTable iSqlTable, Table table,
            FK fk, boolean switchTables)
	{
		String tableName;
        if (alreadyAdded(addedTables, table.getName())) {
            // this table already added so use the fk
            if (StringUtils.isNotEmpty(fk.getForeign_table_alias())) {
                tableName = fk.getForeign_table() + " " + fk.getForeign_table_alias();
            } else {
                tableName = fk.getForeign_table();
            }
		} else {
            if (StringUtils.isNotEmpty(table.getAlias())) {
                tableName = table.getName() + " " + table.getAlias();
            } else {
                tableName = table.getName();
            }
		}

        //if (leadTable != null && leadTable.getName().equals(table.getName())) {
        //	if (StringUtils.isNotEmpty(fk.getForeign_table_as())) {
        //        tableName = fk.getForeign_table() + " " + fk.getForeign_table_as();
        //	} else {
        //		tableName = fk.getForeign_table();
        //	}
        //} else {
        //    if (switchTables == true) {
        //        if (StringUtils.isNotEmpty(iSqlTable.getTableAlias())) {
        //            tableName = iSqlTable.getTableName() + " " + iSqlTable.getTableAlias();
        //        } else {
        //            tableName = iSqlTable.getTableName();
        //        }
        //    } else {
        //        if (StringUtils.isNotEmpty(table.getAs())) {
        //            tableName = table.getName() + " " + table.getAs();
        //        } else {
        //            tableName = table.getName();
        //        }
        //    }
        //}

        // String t1Name;
        // if(table.getAs() != null) {
        // t1Name = table.getAs();
        // } else {
        // t1Name = table.getName();
        // }

    	SqlJoinBase sqlJoin = iSqlTable.getJoin(iSqlTable.getTableName());
		if (sqlJoin != null) {
			// ===
			// add onto an existing join
			// ===
			//log.debug("existing:" + iSqlTable.getTableName() + ", " + table.getName() + ", "
			//		+ ((Table) fk.getParent()).getName() + ", " + fk.getName() + ", " + fk.getForeign_table() + ", "
			//		+ fk.getForeign_key());
            String on = tableName + "." + fk.getName() + " = " + fk.getForeign_table() + "." + fk.getForeign_key();
        	if (StringUtils.isNotEmpty(fk.getWhere() )) {
        		on += " and " + fk.getWhere();
        	}
        	sqlJoin.addOnValue(on);
 
			sqlJoin.addOnValue(on);
			// if the fk table is not in the list of tables then add it to this fkJoin.
			SqlJoinBase fkJoin = iSqlTable.getJoin(fk.getForeign_table());
			if (fkJoin == null) {
                sqlJoin.setTableName(fk.getForeign_table());
			}
		} else {
			// ===
			// add a new join
			// ===
			if (fk.isMandatory()) {
				sqlJoin = new SqlJoin();
			} else {
				sqlJoin = new SqlLeftJoin();
			}

			//log.debug("     new: " + iSqlTable.getTableName() + ", " + table.getName() + ", "
			//		+ ((Table) fk.getParent()).getName() + ", " + fk.getName() + ", " + fk.getForeign_table() + ", "
			//		+ fk.getForeign_key());
			
            //sqlJoin.addTableName(fk.getForeign_table());
			//sqlJoin.addOnValue(table.getName() + "." + fk.getName() + " = " + fk.getForeign_table() + "."
			//		+ fk.getForeign_key());
            sqlJoin.setTableName(tableName);
            log.debug("adding table to join:" + tableName);
            addedTables.add(tableName);
        		
    		String on;
        	if (fk.getForeign_table_alias() != null) {
                on = tableName + "." + fk.getName() + " = " + fk.getForeign_table_alias() + "." + fk.getForeign_key();
        	} else {
                on = tableName + "." + fk.getName() + " = " + fk.getForeign_table() + "." + fk.getForeign_key();
        	}
        	if (StringUtils.isNotEmpty(fk.getWhere() )) {
        		on += " and " + fk.getWhere();
        	}
        	sqlJoin.addOnValue(on);

			iSqlTable.getJoins().add(sqlJoin);
		}
	}

    private static boolean alreadyAdded(List<String> addedTables, String tableName) {
        for (String name : addedTables) {
            if (name.equals(tableName)) {
                return true;
            }
        }
        return false;
    }

}
