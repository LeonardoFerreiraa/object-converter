package br.com.leonardoferreira;

import java.math.BigDecimal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TypedConstructorConverterTest {

    @Test
    void shouldConvertSuccessfullyTest() {
        final MyConverter converter = ObjectConverter.create(MyConverter.class);
        final FirstRecord first = new FirstRecord(
                "1",
                "str 2",
                "str 3",
                2,
                3L,
                4.4d,
                5.5f,
                new BigDecimal("6.6")
        );
        final SecondRecord second = converter.firstToSecond(first);

        Assertions.assertAll(
                () -> Assertions.assertEquals(first.getStringProp(), second.getStringProp()),
                () -> Assertions.assertEquals(first.getStringProp2(), second.getSecondStringProp()),
                () -> Assertions.assertEquals(first.getStringProp3(), second.getStringProp3()),
                () -> Assertions.assertEquals(first.getIntegerProp(), second.getIntegerProp()),
                () -> Assertions.assertEquals(first.getLongProp(), second.getLongProp()),
                () -> Assertions.assertEquals(first.getDoubleProp(), second.getDoubleProp()),
                () -> Assertions.assertEquals(first.getFloatProp(), second.getFloatProp()),
                () -> Assertions.assertEquals(first.getBigDecimalProp(), second.getBigDecimalProp())
        );
    }

    interface MyConverter {

        @Converting(properties = {
                @ConvertingProperty(from = "stringProp2", to = "secondStringProp")
        })
        SecondRecord firstToSecond(FirstRecord first);

    }

    public static class FirstRecord {

        private final String stringProp;

        private final String stringProp2;

        private final String stringProp3;

        private final Integer integerProp;

        private final Long longProp;

        private final Double doubleProp;

        private final Float floatProp;

        private final BigDecimal bigDecimalProp;

        public FirstRecord(final String stringProp,
                           final String stringProp2,
                           final String stringProp3,
                           final Integer integerProp,
                           final Long longProp,
                           final Double doubleProp,
                           final Float floatProp,
                           final BigDecimal bigDecimalProp) {
            this.stringProp = stringProp;
            this.stringProp2 = stringProp2;
            this.stringProp3 = stringProp3;
            this.integerProp = integerProp;
            this.longProp = longProp;
            this.doubleProp = doubleProp;
            this.floatProp = floatProp;
            this.bigDecimalProp = bigDecimalProp;
        }

        public String getStringProp() {
            return stringProp;
        }

        public Integer getIntegerProp() {
            return integerProp;
        }

        public Long getLongProp() {
            return longProp;
        }

        public Double getDoubleProp() {
            return doubleProp;
        }

        public Float getFloatProp() {
            return floatProp;
        }

        public BigDecimal getBigDecimalProp() {
            return bigDecimalProp;
        }

        public String getStringProp2() {
            return stringProp2;
        }

        public String getStringProp3() {
            return stringProp3;
        }
    }

    public static class SecondRecord {

        private final String stringProp;

        private final String secondStringProp;

        private final String stringProp3;

        private final Integer integerProp;

        private final Long longProp;

        private final Double doubleProp;

        private final Float floatProp;

        private final BigDecimal bigDecimalProp;

        public SecondRecord(final Integer integerProp,
                            final BigDecimal bigDecimalProp,
                            final String stringProp3,
                            final String stringProp,
                            final Float floatProp,
                            final String secondStringProp,
                            final Double doubleProp,
                            final Long longProp) {
            this.stringProp = stringProp;
            this.secondStringProp = secondStringProp;
            this.stringProp3 = stringProp3;
            this.integerProp = integerProp;
            this.longProp = longProp;
            this.doubleProp = doubleProp;
            this.floatProp = floatProp;
            this.bigDecimalProp = bigDecimalProp;
        }

        public String getStringProp() {
            return stringProp;
        }

        public Integer getIntegerProp() {
            return integerProp;
        }

        public Long getLongProp() {
            return longProp;
        }

        public Double getDoubleProp() {
            return doubleProp;
        }

        public Float getFloatProp() {
            return floatProp;
        }

        public BigDecimal getBigDecimalProp() {
            return bigDecimalProp;
        }

        public String getSecondStringProp() {
            return secondStringProp;
        }

        public String getStringProp3() {
            return stringProp3;
        }

    }

}
