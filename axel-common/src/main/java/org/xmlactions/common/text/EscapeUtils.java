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
