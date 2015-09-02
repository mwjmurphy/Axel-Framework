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

package org.xmlactions.action;

public class ActionWebConst {
	
    /**
     * HttpRequest parameters are stored in execContext as a Named Map using
     * this identifying string.
     */
    public final static String REQUEST = "request";


	/**
	 * Builds the key prepending the HttpRequest named map name in the
	 * execContext.
	 * 
	 * @param key
	 *            the key name for the HttpRequest parameter
	 * @return the full key name that includes the HttpRequest named map.
	 */
	public static String buildRequestKey(String key)
	{

		return (REQUEST + ":" + key);
	}


}
