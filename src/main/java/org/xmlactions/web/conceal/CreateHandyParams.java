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

import java.sql.Date;


import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.xmlactions.action.config.IExecContext;

public class CreateHandyParams {

    private static final String XSD_DATE_FMT = "yyyy-MM-dd";
    private static final String XSD_TIME_FMT = "HH:mm";
    private static final String XSD_LONG_TIME_FMT = "HH:mm:ss.SSS";
    private static final String XSD_DATE_TIME_FMT = "yyyy-MM-dd HH:mm";
    
    private static final String
        FORMATTER_DATE = "formatter.date",
        FORMATTER_TIME = "formatter.time",
        FORMATTER_DATE_TIME = "formatter.date_time",
        FORMATTER_LONG_TIME = "formatter.long_time";

    private IExecContext execContext;
    
    public static final String
        CURRENT_DATE = "current.date",
        CURRENT_TIME = "current.time",
        CURRENT_DATE_TIME = "current.date_time",
        CURRENT_LONG_TIME = "current.long_time";
    
    public CreateHandyParams(IExecContext execContext) {
        this.execContext = execContext;
        addDate();
        addTime();
        addDateTime();
        addLongTime();
    }
    
    private void addDate() {
        String formatter = execContext.getString(FORMATTER_DATE);
        if (StringUtils.isEmpty(formatter)) {
            formatter = XSD_DATE_FMT;
        }
        Date date = new Date(System.currentTimeMillis());
        String d = DateFormatUtils.format(date, formatter);
        execContext.put(CURRENT_DATE, d);
    }

    private void addTime() {
        String formatter = execContext.getString(FORMATTER_TIME);
        if (StringUtils.isEmpty(formatter)) {
            formatter = XSD_TIME_FMT;
        }
        Date date = new Date(System.currentTimeMillis());
        String t = DateFormatUtils.format(date, formatter);
        execContext.put(CURRENT_TIME, t);
    }

    private void addDateTime() {
        String formatter = execContext.getString(FORMATTER_DATE_TIME);
        if (StringUtils.isEmpty(formatter)) {
            formatter = XSD_DATE_TIME_FMT;
        }
        Date date = new Date(System.currentTimeMillis());
        String t = DateFormatUtils.format(date, formatter);
        execContext.put(CURRENT_DATE_TIME, t);
    }

    private void addLongTime() {
        String formatter = execContext.getString(FORMATTER_LONG_TIME);
        if (StringUtils.isEmpty(formatter)) {
            formatter = XSD_LONG_TIME_FMT;
        }
        Date date = new Date(System.currentTimeMillis());
        String t = DateFormatUtils.format(date, formatter);
        execContext.put(CURRENT_LONG_TIME, t);
    }
}
