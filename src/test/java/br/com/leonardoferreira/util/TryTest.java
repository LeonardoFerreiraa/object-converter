package br.com.leonardoferreira.util;

import java.io.IOException;

import br.com.leonardoferreira.util.Try;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;


class TryTest {

    @Nested
    class OrNullTest {

        @Test
        void shouldReturnSupplierValueTest() {
            final String result = Try.orNull(() -> "test");
            Assertions.assertEquals("test", result);
        }

        @Test
        void shouldReturnNullWhenSupplierThrowsTest() {
            final String result = Try.orNull(() -> {
                throw new IllegalArgumentException();
            });
            Assertions.assertNull(result);
        }

    }

    @Nested
    class SneakyThrowTest {

        @Test
        void shouldReturnValueTest() {
            final String result = Try.sneakyThrow(() -> "test");
            Assertions.assertEquals("test", result);
        }

        @Test
        void shouldThrowTest() {
            Assertions.assertThrows(IOException.class, () ->
                    Try.sneakyThrow(() -> {
                        throw new IOException();
                    })
            );
        }

    }

}