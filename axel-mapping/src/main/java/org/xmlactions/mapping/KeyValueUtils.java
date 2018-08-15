package org.xmlactions.mapping;

import java.util.List;

public class KeyValueUtils {

    public String getKeyValue(List<KeyValue> keyvalues, String key) {
        for (KeyValue keyvalue : keyvalues) {
            if (key.equals(keyvalue.getKey())) {
                return keyvalue.getValue();
            }
        }
        return null;
    }

}
