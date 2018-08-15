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
