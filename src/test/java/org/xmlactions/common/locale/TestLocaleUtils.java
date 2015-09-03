
package org.xmlactions.common.locale;

import junit.framework.TestCase;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmlactions.common.locale.LocaleUtils;

public class TestLocaleUtils extends TestCase
{

	private static final Logger log = LoggerFactory.getLogger(TestLocaleUtils.class);

	public void testGetLocaleSetting()
	{

		String x = LocaleUtils.getLocalizedString("config/lang/rio-common", "project");
		assertEquals("rio-common", x);
	}
}
