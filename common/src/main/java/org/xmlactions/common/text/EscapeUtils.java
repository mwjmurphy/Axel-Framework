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

package org.xmlactions.common.text;

public class EscapeUtils {

    public static String escapeUrl(String input) {
        String output = escapeUrlCharacters(input);
        return output;
    }

    private static String escapeUrlCharacters(String inputString) {
        char[] input = (inputString + " ").toCharArray();
        StringBuilder sb = new StringBuilder();
        boolean skip = false;
        for (int i = 0; i < input.length - 1; i++) {
            if (input[i] == '%') {
                char p = input[i + 1];
                if (!(p >= '0' && p <= '9')) {
                    sb.append("%25");
                    skip = true;
                }
            }
            if (skip == false) {
                sb.append(input[i]);
            } else {
                skip = false;
            }
        }
        return sb.toString();
    }
}
