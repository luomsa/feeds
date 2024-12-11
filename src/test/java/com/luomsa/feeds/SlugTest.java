package com.luomsa.feeds;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SlugTest {
    @Test
    void shouldReturnStringWithHyphens() {
        var slug = StringUtils.toSlug("this is the title 123");
        assertEquals("this-is-the-title-123", slug);
    }
    @Test
    void shouldIgnoreNonAlphabetsAndNonNumbers() {
        var slug = StringUtils.toSlug("this is the title öä ?? 😊😊");
        assertEquals("this-is-the-title", slug);
    }
}
