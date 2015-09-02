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

package org.xmlactions.common.collections;


import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Map.Entry;

import org.xmlactions.common.io.ResourceUtils;


public class MapUtils {

    public static Map<String, Object> loadMapFromProperties(String propertiesResourceName) throws IOException {

        InputStream is = ResourceUtils.getInputStream(propertiesResourceName);
        return loadMapFromProperties(is);
    }

    public static Map<String, Object> loadMapFromProperties(URL propertiesResource) throws IOException {

        InputStream is = propertiesResource.openStream();
        return loadMapFromProperties(is);
    }

    public static Map<String, Object> loadMapFromProperties(InputStream is) throws IOException {

        try {
            Properties props = new Properties();
            props.load(is);
            return propertiesToMap(props);
        } finally {
            is.close();
        }
    }

    public static Map<String, Object> propertiesToMap(Properties props) {
        Map<String, Object> map = new HashMap<String, Object>();
        for (Entry entry : props.entrySet()) {
            map.put((String) entry.getKey(), entry.getValue());
        }
        return map;
    }


}
