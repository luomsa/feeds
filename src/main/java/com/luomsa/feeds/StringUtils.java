package com.luomsa.feeds;

public class StringUtils {
    public static String toSlug(String title) {
        var slug = title.toLowerCase();
        slug = slug.replaceAll("[^\\p{Alnum}\\s-]", "");
        slug = slug.replaceAll("[\\s-]+", "-");
        slug = slug.replaceAll("^-|-$", "");
        return slug;
    }
}
