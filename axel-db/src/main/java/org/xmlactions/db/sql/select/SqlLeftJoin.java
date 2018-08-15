package org.xmlactions.db.sql.select;

import java.util.List;



public class SqlLeftJoin extends SqlJoinBase
{
	public String buildJoinClause(List<String> tablesAlreadyInList)
	{
		return buildJoinClause(tablesAlreadyInList, "left join");
	}

}
