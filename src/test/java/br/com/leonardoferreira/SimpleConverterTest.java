package br.com.leonardoferreira;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SimpleConverterTest {

    @Test
    void shouldDoASimpleConversionTest() {
        final FirstToSecondConverter converter = ObjectConverter.create(FirstToSecondConverter.class);

        final FirstRecord first = new FirstRecord(
                "first",
                "second",
                3
        );

        final SecondRecord second = converter.convert(first);

        Assertions.assertAll(
                () -> Assertions.assertEquals(first.getFirst(), second.getFirst()),
                () -> Assertions.assertEquals(first.getSecond(), second.getSecond()),
                () -> Assertions.assertEquals(first.getThird(), second.getThird())
        );
    }

    public interface FirstToSecondConverter {
        SecondRecord convert(FirstRecord firstRecord);
    }

    public static class FirstRecord {

        private String first;

        private String second;

        private int third;

        public FirstRecord(final String first, final String second, final int third) {
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

    public static class SecondRecord {

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
