
package org.xmlactions.common.theme;


import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import junit.framework.TestCase;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xmlactions.common.theme.Theme;

public class ThemeTest extends TestCase
{

	private static Logger logger = LoggerFactory.getLogger(ThemeTest.class);

	public void testGetThemeMap()
	{

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("riostl.table", "bgcolor=\"white\" class=\"table\" style=\"color:#ff0000;\"");
		map.put("riostl.tr", "bgcolor=\"white\" class=\"row\" style=\"color:#ff0000;\"");
		map.put("riostl.th", "bgcolor=\"grey\" class=\"header\" style=\"color:#ff0000;\"");
		map.put("riostl.td", "bgcolor=\"red\" class=\"cell\" style=\"color:#ff0000;\"");

		map.put("anothertheme.table", "bgcolor=\"white\" class=\"table\" style=\"color:#ff0000;\"");
		map.put("anothertheme.tr", "bgcolor=\"white\" class=\"row\" style=\"color:#ff0000;\"");
		map.put("anothertheme.th", "bgcolor=\"grey\" class=\"header\" style=\"color:#ff0000;\"");
		map.put("anothertheme.td", "bgcolor=\"red\" class=\"cell\" style=\"color:#ff0000;\"");
		Theme theme = new Theme(map);
		
		Theme riostl = theme.getTheme("riostl");
		assertNotNull("Missing theme property for 'riostl.table'", riostl.getValue("table"));

		assertNotNull("Missing theme property for 'riostl.td'", riostl.getValue("td"));

		Theme anotherTheme = theme.getTheme("anothertheme");
		assertNotNull("Missing theme property for 'anothertheme.td'", anotherTheme.getValue("td"));
		assertNull("Missing theme property for 'anothertheme.nothtere'", anotherTheme.getValue("nothtere"));

	}

	public void testGetThemeProperties()
	{

		Properties props = new Properties();
		props.put("riostl.table", "bgcolor=\"white\" class=\"table\" style=\"color:#ff0000;\"");
		props.put("riostl.tr", "bgcolor=\"white\" class=\"row\" style=\"color:#ff0000;\"");
		props.put("riostl.th", "bgcolor=\"grey\" class=\"header\" style=\"color:#ff0000;\"");
		props.put("riostl.td", "bgcolor=\"red\" class=\"cell\" style=\"color:#ff0000;\"");

		props.put("anothertheme.table", "bgcolor=\"white\" class=\"table\" style=\"color:#ff0000;\"");
		props.put("anothertheme.tr", "bgcolor=\"white\" class=\"row\" style=\"color:#ff0000;\"");
		props.put("anothertheme.th", "bgcolor=\"grey\" class=\"header\" style=\"color:#ff0000;\"");
		props.put("anothertheme.td", "bgcolor=\"red\" class=\"cell\" style=\"color:#ff0000;\"");
		Theme theme = new Theme(props);
		
		Theme riostl = theme.getTheme("riostl");
		assertNotNull("Missing theme property for 'riostl.table'", riostl.getValue("table"));

		assertNotNull("Missing theme property for 'riostl.td'", riostl.getValue("td"));

		Theme anotherTheme = theme.getTheme("anothertheme");
		assertNotNull("Missing theme property for 'anothertheme.td'", anotherTheme.getValue("td"));

		assertNull("Missing theme property for 'anothertheme.nothtere'", anotherTheme.getValue("nothtere"));

	}
}
