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
