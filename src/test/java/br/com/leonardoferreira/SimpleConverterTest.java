package br.com.leonardoferreira;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SimpleConverterTest {

    @Test
    void shouldDoASimpleConversionTest() {
        final FirstToSecondConverter converter = ObjectConverter.create(FirstToSecondConverter.class);

        final FirstObject first = new FirstObject(
                "first",
                "second",
                3
        );

        final SecondObject second = converter.convert(first);

        Assertions.assertAll(
                () -> Assertions.assertEquals(first.getFirst(), second.getFirst()),
                () -> Assertions.assertEquals(first.getSecond(), second.getSecond()),
                () -> Assertions.assertEquals(first.getThird(), second.getThird())
        );
    }

    public interface FirstToSecondConverter {
        SecondObject convert(FirstObject firstObject);
    }

    public static class FirstObject {

        private String first;

        private String second;

        private int third;

        public FirstObject(final String first, final String second, final int third) {
            this.first = first;
            this.second = second;
            this.third = third;
        }

        public String getFirst() {
            return first;
        }

        public void setFirst(final String first) {
            this.first = first;
        }

        public String getSecond() {
            return second;
        }

        public void setSecond(final String second) {
            this.second = second;
        }

        public int getThird() {
            return third;
        }

        public void setThird(final int third) {
            this.third = third;
        }
    }

    public static class SecondObject {

        private String first;

        private String second;

        private int third;

        public String getFirst() {
            return first;
        }

        public void setFirst(final String first) {
            this.first = first;
        }

        public String getSecond() {
            return second;
        }

        public void setSecond(final String second) {
            this.second = second;
        }

        public int getThird() {
            return third;
        }

        public void setThird(final int third) {
            this.third = third;
        }
    }

}
