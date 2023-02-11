package com.bobocode.svydovets.annotation.util;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;

class StringUtilsTest {

    @Test
    void isBlankWithNull() {
        assertThat(StringUtils.isBlank(null)).isTrue();
    }

    @Test
     void isBlankWithBlankString() {
        assertThat(StringUtils.isBlank("")).isTrue();
    }

    @Test
    void isBlankWithStringContainingOnlySpaces() {
        assertThat(StringUtils.isBlank("   ")).isTrue();
    }

    @Test
    void isBlankWithNonBlankString() {
        assertThat(StringUtils.isBlank("not blank")).isFalse();
    }

}

