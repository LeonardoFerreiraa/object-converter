package br.com.leonardoferreira;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AttributeAccessorConvertTest {

    @Test
    void shouldConvertEvenWhenAccessorsIsMissingTest() {
        final MyConverter converter = ObjectConverter.create(MyConverter.class);
        final FirstRecord first = new FirstRecord("test");

        final SecondRecord second = converter.firstToSecond(first);

        Assertions.assertEquals(first.property, second.property);
    }

    interface MyConverter {
        SecondRecord firstToSecond(FirstRecord first);
    }

    public static class FirstRecord {
        private String property;

        public FirstRecord(final String property) {
            this.property = property;
        }
    }

    public static class SecondRecord {
        private String property;
    }

}
