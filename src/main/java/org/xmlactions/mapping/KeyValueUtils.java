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