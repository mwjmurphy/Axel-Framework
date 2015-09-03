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

package org.xmlactions.pager.actions;


import org.slf4j.Logger; import org.slf4j.LoggerFactory;
import org.xmlactions.action.actions.BaseAction;
import org.xmlactions.action.config.IExecContext;

public class DebugAction extends BaseAction {

    private static Logger log = LoggerFactory.getLogger(DebugAction.class);
    
    private static final int LEVEL_UNKNOWN = 0, 
                             LEVEL_DEBUG = 1,
                             LEVEL_INFO = 2,
                             LEVEL_WARN = 3,
                             LEVEL_ERROR = 4,
                             LEVEL_NONE = 5;
                             


    private String level;

    public String execute(IExecContext execContext) {
        
        String debugLevel = execContext.getString(IExecContext.DEBUG_LEVEL);
        if (debugLevel != null) {
            return processDebug(execContext, convertLevelToInt(debugLevel));
        }
        return "";
    }

    private String processDebug(IExecContext execContext, int configDebugLevel) {
        int debugLevel = convertLevelToInt(getLevel());
        if (configDebugLevel == LEVEL_NONE || debugLevel == LEVEL_NONE) {
            return "";
        }
        if (debugLevel >= configDebugLevel) {
            return processDebug(execContext);
        }
        return "";
    }

    private String processDebug(IExecContext execContext) {
        String value = getContent();
        value = execContext.replace(value);
        return value;
    }

    private int convertLevelToInt(String debugLevel) {
        if ("debug".equalsIgnoreCase(debugLevel)) {
            return LEVEL_DEBUG;
        } else if ("info".equalsIgnoreCase(debugLevel)) {
            return LEVEL_INFO;
        } else if ("warn".equalsIgnoreCase(debugLevel)) {
            return LEVEL_WARN;
        } else if ("error".equalsIgnoreCase(debugLevel)) {
            return LEVEL_ERROR;
        } else if ("none".equalsIgnoreCase(debugLevel)) {
            return LEVEL_NONE;
        } else {
            return LEVEL_UNKNOWN;
        }
    }
    public String toString() {

        return "echo [" + getContent() + "]";
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getLevel() {
        return level;
    }

}
