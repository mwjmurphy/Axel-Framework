
package org.xmlactions.pager.drawing.html;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.xmlactions.db.actions.Table;
import org.xmlactions.pager.drawing.IDrawField;
import org.xmlactions.pager.env.EnvironmentAccess;


public class DrawHTMLHelperTest
{
	
	@Before
	public void beforeMethod() {
		org.junit.Assume.assumeTrue(EnvironmentAccess.runDatabaseTests());
	}

	@Test
	public void testBuildName()
	{

		Table parent = new Table();
		parent.setName("tb_parent");
		PKHtml child = new PKHtml();
		child.setName("child");
		parent.setPk(child);
		child.setParent(parent);
		assertEquals("tb_parent.child", DrawDBHTMLHelper.buildName(child));

	}

	@Test
	public void testBuildNameWithUniqueId()
	{

		Table parent = new Table();
		parent.setName("tb_parent");
		PKHtml child = new PKHtml();
		child.setName("child");
		parent.setPk(child);
		child.setParent(parent);
		assertEquals("uniqueId.tb_parent.child", DrawDBHTMLHelper.buildName(child, "uniqueId"));
		assertEquals("tb_parent.child", DrawDBHTMLHelper.removeUniqueId("uniqueId.tb_parent.child", "uniqueId"));

		assertEquals("tb_parent.child", DrawDBHTMLHelper.buildName(child, null));
		assertEquals(".tb_parent.child", DrawDBHTMLHelper.buildName(child, ""));
		assertEquals("tb_parent.child", DrawDBHTMLHelper.removeUniqueId("tb_parent.child", null));
		assertEquals("tb_parent.child", DrawDBHTMLHelper.removeUniqueId(".tb_parent.child", ""));
	}

	@Test
	public void testGetFieldName()
	{

		assertEquals("child", DrawDBHTMLHelper.getFieldName("tb_parent.child"));

	}

	@Test
    public void testBuildNameWithAlias() {


        Table parent = new Table();
        parent.setName("tb_parent");
        parent.setAlias("alias");
        PKHtml child = new PKHtml();
        child.setName("child");
        parent.setPk(child);
        child.setParent(parent);
        assertEquals("alias.child", DrawDBHTMLHelper.buildName(child));
        assertEquals("alias.child", parent.buildTableAndFieldName("child"));
        Table table = (Table) child.getParent();
        assertNotNull(table);

        IDrawField drawHtml = child;
        //table = (Table) drawHtml.getParent();
        //assertNotNull(table);

    }

}
