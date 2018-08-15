
package org.xmlactions.db.sql.oracle;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Ignore;
import org.junit.Test;
import org.xmlactions.db.actions.Table;
import org.xmlactions.db.actions.UtilsTestHelper;
import org.xmlactions.db.sql.oracle.OracleSelectQuery;
import org.xmlactions.db.sql.select.ISqlSelectBuildQuery;
import org.xmlactions.db.sql.select.SqlSelectInputs;
import org.xmlactions.db.sql.select.SqlTable;

import junit.framework.TestCase;

public class TestOracleSelectQuery {

	@Test
    public void testSelect() throws IOException {

        SqlSelectInputs select = new SqlSelectInputs("db1");

        Table table = new Table();
        table.setName("tb1");
        SqlTable sqlTable = new SqlTable(table);
        sqlTable.addField("f1", "tb1_f1");
        sqlTable.addField("f2", "tb1_f2");
        sqlTable.addWhereClause("tb1.f1=tb2.f2");
        select.addSqlTable(sqlTable);

        table = new Table();
        table.setName("tb2");
        sqlTable = new SqlTable(table);
        sqlTable.addField("f1", "tb2_f1");
        sqlTable.addField("f2", "tb2_f2");
        sqlTable.addField("f3", "tb2_f3");
        select.addSqlTable(sqlTable);

        ISqlSelectBuildQuery build = new OracleSelectQuery();

        String selectSql = build.buildSelectQuery(UtilsTestHelper.getExecContext(), select, null);
        String expectedResult =
        	"\n" +
        	"  select\n" +
        	" tb1.f1 as \"tb1.tb1_f1\",\n" +
        	" tb1.f2 as \"tb1.tb1_f2\",\n" +
        	" tb2.f1 as \"tb2.tb2_f1\",\n" +
        	" tb2.f2 as \"tb2.tb2_f2\",\n" +
        	" tb2.f3 as \"tb2.tb2_f3\"\n" +
        	" from tb1 tb1\n" +
        	" where tb1.f1=tb2.f2";
        assertEquals(expectedResult, selectSql);
    }

    @Ignore	// Not sure what the problem, will look at it later.
    public void testSelectReal() throws IOException {
        SqlSelectInputs select = new SqlSelectInputs();

        Table table = new Table();
        table.setName("tdfi_ersmessage");
        SqlTable sqlTable = new SqlTable(table);
        sqlTable.addField("TDFI_ERSMESSAGEID", "ERSMESSAGEID");
        sqlTable.addField("VESSELID");
        sqlTable.addField("ORIGINID");
        sqlTable.addField("OPSTYPE");
        sqlTable.addField("OPSNUMBER");
        sqlTable.addField("STATUSID");
        //table.addField("CREATEDATE", "CD", "to_char(%s, 'DD/MM/RRRR HH24:MI.SS')");
        sqlTable.addField("CREATEDATE", "CD", "Must get function from database using function_ref");
        select.addSqlTable(sqlTable);

        sqlTable.addWhereClause("ORIGINID=192775277");
        sqlTable.addWhereClause("OPSTYPE='QUE'");

        select.addOrderByClause("OPSNUMBER");

        select.setLimitFrom("5");
        select.setLimitTo("10");

        ISqlSelectBuildQuery build = new OracleSelectQuery();
        String selectSql = build.buildSelectQuery(UtilsTestHelper.getExecContext(), select, null);
        assertEquals("SSS", selectSql);

    }

    @Test
    public void testSelectReal2() throws IOException {
        SqlSelectInputs select = new SqlSelectInputs();

        Table table = new Table();
        table.setName("tdfi_ersmessage");

        SqlTable sqlTable = new SqlTable(table);
        sqlTable.addField("TDFI_ERSMESSAGEID", "ERSMESSAGEID");
        sqlTable.addField("VESSELID");
        sqlTable.addField("ORIGINID");
        sqlTable.addField("OPSTYPE");
        sqlTable.addField("OPSNUMBER");

        // table.addWhereClause("ORIGINID=192775277");

        select.addSqlTable(sqlTable);

        table = new Table();
        table.setName("tdfi_erssendhistory");
        sqlTable = new SqlTable(table);
        sqlTable.addField("TDFI_ERSSENDHISTORYID", "ERSHISTORYID");
        sqlTable.addField("ERRORDETAILS");
        sqlTable.addWhereClause("tdfi_erssendhistory.TDFI_ERSMESSAGEID=tdfi_ersmessage.TDFI_ERSMESSAGEID");

        select.addSqlTable(sqlTable);

        select.addOrderByClause("OPSNUMBER");

        ISqlSelectBuildQuery build = new OracleSelectQuery();
        String selectSql = build.buildSelectQuery(UtilsTestHelper.getExecContext(), select, null);
        String expectedResult =
        	"\n" +
        	"  select\n" +
        	" tdfi_ersmessage.TDFI_ERSMESSAGEID as \"tdfi_ersmessage.ERSMESSAGEID\",\n" +
        	" tdfi_ersmessage.VESSELID as \"tdfi_ersmessage.VESSELID\",\n" +
        	" tdfi_ersmessage.ORIGINID as \"tdfi_ersmessage.ORIGINID\",\n" +
        	" tdfi_ersmessage.OPSTYPE as \"tdfi_ersmessage.OPSTYPE\",\n" +
        	" tdfi_ersmessage.OPSNUMBER as \"tdfi_ersmessage.OPSNUMBER\",\n" +
        	" tdfi_erssendhistory.TDFI_ERSSENDHISTORYID as \"tdfi_erssendhistory.ERSHISTORYID\",\n" +
        	" tdfi_erssendhistory.ERRORDETAILS as \"tdfi_erssendhistory.ERRORDETAILS\"\n" +
        	" from tdfi_ersmessage tdfi_ersmessage\n" +
        	" where tdfi_erssendhistory.TDFI_ERSMESSAGEID=tdfi_ersmessage.TDFI_ERSMESSAGEID\n" +
        	" order by OPSNUMBER";
        assertEquals(expectedResult, selectSql);

    }
}
