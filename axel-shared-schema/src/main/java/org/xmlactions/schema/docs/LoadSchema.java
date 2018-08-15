package org.xmlactions.schema.docs;

import org.xmlactions.common.io.ResourceUtils;

public class LoadSchema {
	
	protected static String loadSchema(String schemaName) {
		try {
			String data = ResourceUtils.loadFile(schemaName);
			return data;
		} catch (Exception ex) {
			throw new IllegalArgumentException(ex.getMessage(), ex);
		}
	}

}
