package org.xmlactions.db.sql.select;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class TestSqlSelectInputs {

	@Test
	public void testSqlNumbers() {

		List<SqlField> sqlParams = new ArrayList<SqlField>();
		String sql = "select * from t where t.id = ?1234";
		SqlSelectInputs sqlSelectInputs = new SqlSelectInputs();
		String newSql = sqlSelectInputs.replaceWhereWithParams(sql, sqlParams);
		assertEquals(1, sqlParams.size());
		assertEquals("select * from t where t.id = ?", newSql);
		assertEquals("p1", sqlParams.get(0).getFieldName());
		assertEquals("1234", sqlParams.get(0).getValue());

		sqlParams = new ArrayList<SqlField>();
		sql = "select * from t where t.id = ?  1234  tea ";
		newSql = sqlSelectInputs.replaceWhereWithParams(sql, sqlParams);
		assertEquals(1, sqlParams.size());
		assertEquals("select * from t where t.id = ?  tea ", newSql);
		assertEquals("p1", sqlParams.get(0).getFieldName());
		assertEquals("1234", sqlParams.get(0).getValue());

		sqlParams = new ArrayList<SqlField>();
		sql = "select * from t where t.id = ?  1234 \n\r\t ";
		newSql = sqlSelectInputs.replaceWhereWithParams(sql, sqlParams);
		assertEquals(1, sqlParams.size());
		assertEquals("select * from t where t.id = ? \n\r\t ", newSql);
		assertEquals("p1", sqlParams.get(0).getFieldName());
		assertEquals("1234", sqlParams.get(0).getValue());
	}

	@Test
	public void testSqlStrings() {

		List<SqlField> sqlParams = new ArrayList<SqlField>();
		String sql = "select * from t where t.id = ?\"Fred Flinstone\"";
		SqlSelectInputs sqlSelectInputs = new SqlSelectInputs();
		String newSql = sqlSelectInputs.replaceWhereWithParams(sql, sqlParams);
		assertEquals(1, sqlParams.size());
		assertEquals("select * from t where t.id = ?", newSql);
		assertEquals("p1", sqlParams.get(0).getFieldName());
		assertEquals("Fred Flinstone", sqlParams.get(0).getValue());

		sqlParams = new ArrayList<SqlField>();
		sql = "select * from t where t.id = ?\"Fred ' Flinstone\"";
		sqlSelectInputs = new SqlSelectInputs();
		newSql = sqlSelectInputs.replaceWhereWithParams(sql, sqlParams);
		assertEquals(1, sqlParams.size());
		assertEquals("select * from t where t.id = ?", newSql);
		assertEquals("p1", sqlParams.get(0).getFieldName());
		assertEquals("Fred ' Flinstone", sqlParams.get(0).getValue());

		sqlParams = new ArrayList<SqlField>();
		sql = "select * from t where t.id = ?'Fred \" Flinstone'";
		sqlSelectInputs = new SqlSelectInputs();
		newSql = sqlSelectInputs.replaceWhereWithParams(sql, sqlParams);
		assertEquals(1, sqlParams.size());
		assertEquals("select * from t where t.id = ?", newSql);
		assertEquals("p1", sqlParams.get(0).getFieldName());
		assertEquals("Fred \" Flinstone", sqlParams.get(0).getValue());

		sqlParams = new ArrayList<SqlField>();
		sql = "select * from t where t.id = ? 'Fred \" Flinstone' ";
		sqlSelectInputs = new SqlSelectInputs();
		newSql = sqlSelectInputs.replaceWhereWithParams(sql, sqlParams);
		assertEquals(1, sqlParams.size());
		assertEquals("select * from t where t.id = ?", newSql);
		assertEquals("p1", sqlParams.get(0).getFieldName());
		assertEquals("Fred \" Flinstone", sqlParams.get(0).getValue());

		sqlParams = new ArrayList<SqlField>();
		sql = "select * from t where t.id = ?'Fred \" Flinstone' more stuff here";
		sqlSelectInputs = new SqlSelectInputs();
		newSql = sqlSelectInputs.replaceWhereWithParams(sql, sqlParams);
		assertEquals(1, sqlParams.size());
		assertEquals("select * from t where t.id = ? more stuff here", newSql);
		assertEquals("p1", sqlParams.get(0).getFieldName());
		assertEquals("Fred \" Flinstone", sqlParams.get(0).getValue());

	}

	@Test
	public void testSql2Strings() {

		List<SqlField> sqlParams = new ArrayList<SqlField>();
		String sql = "select * from t where t.id = ?\"Fred Flinstone\" AND t.name=?'Wilma Flinstone'";
		SqlSelectInputs sqlSelectInputs = new SqlSelectInputs();
		String newSql = sqlSelectInputs.replaceWhereWithParams(sql, sqlParams);
		assertEquals(2, sqlParams.size());
		assertEquals("select * from t where t.id = ? AND t.name=?", newSql);
		assertEquals("p1", sqlParams.get(0).getFieldName());
		assertEquals("p2", sqlParams.get(1).getFieldName());
		assertEquals("Fred Flinstone", sqlParams.get(0).getValue());
		assertEquals("Wilma Flinstone", sqlParams.get(1).getValue());

	}

	@Test
	public void testSqlQuoteStrings() {

		List<SqlField> sqlParams = new ArrayList<SqlField>();
		String sql = "select * from t where t.x='fred?flinstone' and t.id = ?\"Fred Flinstone\" AND t.name=?'Wilma Flinstone'";
		SqlSelectInputs sqlSelectInputs = new SqlSelectInputs();
		String newSql = sqlSelectInputs.replaceWhereWithParams(sql, sqlParams);
		assertEquals(2, sqlParams.size());
		assertEquals("select * from t where t.x='fred?flinstone' and t.id = ? AND t.name=?", newSql);
		assertEquals("p1", sqlParams.get(0).getFieldName());
		assertEquals("p2", sqlParams.get(1).getFieldName());
		assertEquals("Fred Flinstone", sqlParams.get(0).getValue());
		assertEquals("Wilma Flinstone", sqlParams.get(1).getValue());

	}

}
