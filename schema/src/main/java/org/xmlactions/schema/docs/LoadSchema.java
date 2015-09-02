package org.xmlactions.schema.docs;

import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.lang.text.StrSubstitutor;
import org.xmlactions.common.io.ResourceUtils;

public class LoadSchema {
	
//	protected static String loadSchema(String schemaName) {
//		try {
//			String data = ResourceUtils.loadFile(schemaName);
//			return data;
//		} catch (Exception ex) {
//			throw new IllegalArgumentException(ex.getMessage(), ex);
//		}
//	}

	protected static String loadSchema(String propsFilename, String schemaName) {
		try {
			InputStream inputStream = ResourceUtils.getInputStream(propsFilename);
			Properties props = new Properties();
			props.load(inputStream);
			return loadSchema(props, schemaName);
		} catch (Exception ex) {
			throw new IllegalArgumentException(ex.getMessage(), ex);
		}
	}

	protected static String loadSchema(Properties props, String schemaName) {
		try {
			String data = ResourceUtils.loadFile(schemaName);
			data = StrSubstitutor.replace(data, props);
			return data;
		} catch (Exception ex) {
			throw new IllegalArgumentException(ex.getMessage(), ex);
		}
	}

}
