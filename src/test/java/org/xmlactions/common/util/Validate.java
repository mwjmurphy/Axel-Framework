package org.xmlactions.common.util;

import java.io.InvalidObjectException;

public class Validate {

	public static void notNull(Object object, String msg) throws InvalidObjectException
	{
		if (object == null)
		{
			throw new InvalidObjectException(msg);
		}
	}
}
