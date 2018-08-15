
package org.xmlactions.pager.actions.form;

import junit.framework.TestCase;

public class StorageBaseFormActionTest extends TestCase
{

	public void testFirstFound()
	{

		List list1 = new List();
		list1.setStorage_config_ref("da storageconfig_ref 1");
		List list2 = new List();
		list2.setStorage_config_ref("da storageconfig_ref 2");
		List list3 = new List();
		// list3.setStorage_config_ref("da storageconfig_ref 3");
		// list3.setStorage_ref("da storage_ref 3");

		list1.setChild(list2);
		list2.setChild(list3);

		list2.setParent(list1);
		list3.setParent(list2);

		String value = list3.getFirstValueFound("storage_config_ref");
		assertEquals("da storageconfig_ref 2", value);

		value = list2.getFirstValueFound("storage_config_ref");
		assertEquals("da storageconfig_ref 1", value);

		value = list3.getFirstValueFound("storage_config_ref");
		assertEquals("da storageconfig_ref 2", value);
	}
}
