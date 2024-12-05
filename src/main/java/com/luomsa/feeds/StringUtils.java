package com.luomsa.feeds;

public class StringUtils {
    public static String toSlug(String title) {
        var chars = title.toCharArray();
        var builder = new StringBuilder();
        for (char aChar : chars) {
            if (aChar == ' ') builder.append("-");
            else if (aChar >= 97 && aChar <= 122) {
                builder.append(aChar);
            }
        }
        return builder.toString();
    }
}
