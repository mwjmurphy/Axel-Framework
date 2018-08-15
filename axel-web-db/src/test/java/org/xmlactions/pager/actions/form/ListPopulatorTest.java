package org.xmlactions.pager.actions.form;

import static org.junit.Assert.assertTrue;

import org.junit.Ignore;
import org.junit.Test;

public class ListPopulatorTest {

	@Ignore
	@Test
	public void testTypeSql() {
		ListPopulator lp = new ListPopulator();
		lp.setType(ListPopulator.types.sql_ref.toString());
		assertTrue(lp.isSqlType());
	}
}
