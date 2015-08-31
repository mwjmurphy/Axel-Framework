/*
 * Copyright (C) Mike Murphy 2003-2015 <mike.murphy@xmlactions.org><mwjmurphy@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

/*
 * Copyright (C) 2003, Mike Murphy <mike.murphy@xmlactions.org><mwjmurphy@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or 
 * implied. See the License for the specific language governing 
 * permissions and limitations under the License.
 */

package org.xmlactions.action.utils;

import org.apache.commons.lang.Validate;
import org.xmlactions.action.config.IExecContext;

/**
 * Provides String utilities for ExecContext.
 * 
 * @author mike.murphy
 *
 */
public class StringUtils {
	
	private static final String seperatorChar = ":";

	/**
	 * Perform a String.replaceAll
	 * <p>
	 * The replacementPattern format is "replace:value:regex:replacement"
	 * <br/>"replace" replacement instruction
	 * <br/>"value" the string to perform the replacement on
	 * <br/>"regex" the expression for the replacement
	 * <br/>"replacement" the replacement value
	 * </p>
	 * 
	 * @return the value after the replacement.
	 * 
	 */
	public static String replace(IExecContext execContext, String entryPattern) {
		
			String parts[] = entryPattern.split(seperatorChar);
			Validate.isTrue(parts.length==4, "Invalid entry string for replace. It must match this pattern [replace:value:regex:replacement]");
			String value = execContext.getStringQuietly(parts[1]);
			String regex = execContext.getStringQuietly(parts[2]);
			String replacement = execContext.getStringQuietly(parts[3]);
			
			String result = value.replaceAll(regex, replacement);
			return result;

	}
}
