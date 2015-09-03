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

package org.xmlactions.web.conceal;

import org.xmlactions.action.config.IExecContext;

public class CreateUserParams {

    public CreateUserParams(IExecContext execContext) {
    	String value = execContext.getString("persistence:" + IExecContext.SELECTED_THEME_NAME);
    	if (value == null) {
    		value = execContext.getString(IExecContext.DEFAULT_THEME_NAME);
    	}
    	execContext.put(IExecContext.SELECTED_THEME_NAME, value);
    	execContext.put("persistence:" + IExecContext.SELECTED_THEME_NAME, value);
    }
    
}
