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

package org.xmlactions.mapping.xml_to_bean;


import java.lang.reflect.InvocationTargetException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;


import org.apache.commons.beanutils.BeanUtils;
import org.xmlactions.common.xml.XMLObject;
import org.xmlactions.mapping.KeyValue;
import org.xmlactions.mapping.MappingConstants;

public class PopulatorSqlDate extends AbstractPopulateClassFromXml {

    // yyyy-MM-dd HH:mm:ss.SSS

    public void performAction(List<KeyValue> keyvalues, Object object, String propertyName, Object value, XMLObject xo)
            throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, SecurityException,
            IllegalArgumentException, NoSuchFieldException {

        String format = getKeyValue(keyvalues, MappingConstants.TIME_FORMAT);
        Date date = new Date(buildTime(format, "" + value));
        BeanUtils.setProperty(object, propertyName, date);
	}

    private long buildTime(String format, String date) {
        try {
            return new SimpleDateFormat(format).parse(date).getTime();
        } catch (Exception ex) {
            throw new IllegalArgumentException("The [" + format + "] is incorrect for [" + date + "]", ex);
        }
    }
	

}
