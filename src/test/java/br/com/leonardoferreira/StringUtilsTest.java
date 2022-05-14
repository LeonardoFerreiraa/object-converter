package br.com.leonardoferreira;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class StringUtilsTest {

    @ParameterizedTest
    @CsvSource({
            "test, Test",
            "test123, Test123",
            "te, Te",
            "t, T"
    })
    void shouldCapitalizeSuccessfullyTest(final String input, final String expectedResult) {
        final String result = StringUtils.capitalize(input);
        Assertions.assertEquals(expectedResult, result);
    }

    @Test
    void shouldNotCapitalizeNullTest() {
        Assertions.assertNull(StringUtils.capitalize(null));
    }

    @Test
    void shouldNotCapitalizeEmptyTest() {
        Assertions.assertEquals("", StringUtils.capitalize(""));
    }


}