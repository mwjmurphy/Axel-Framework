
package org.xmlactions.pager.actions.form;


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.xmlactions.common.io.ResourceUtils;
import org.xmlactions.common.theme.Theme;
import org.xmlactions.pager.actions.form.FileUpload;

import junit.framework.TestCase;

public class FileUploadTest extends TestCase
{

	public void testCreate() throws IOException
	{

		Properties props = new Properties();
		InputStream is = ResourceUtils.getInputStream("/themes/riostl.properties");
		props.load(is);
		Theme theme = new Theme(props);
		
		FileUpload fileUpload = new FileUpload();
		fileUpload.setPath("path");
		fileUpload.setTheme_name("riostl");
		fileUpload.setTheme(theme);
		assertEquals("path", fileUpload.getPath());
		assertNotNull(fileUpload.getTheme(null));
	}
}
