package br.com.leonardoferreira;

import java.math.BigDecimal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class SameNameDifferentTypesConverterTest {

    @Test
    void shouldDoAConversionWhenPropertiesHasTheSameNameAndDifferentTypesTest() {
        final MyConverter converter = ObjectConverter.create(MyConverter.class);

        final FirstRecord first = new FirstRecord(
               "test",
                1,
                2L,
                3.3,
                4.4f,
                new BigDecimal("5.5")
        );

        final SecondRecord second = converter.convertFirstToSecond(first);

        Assertions.assertAll(
                () -> Assertions.assertEquals("test", second.getStringProp()),
                () -> Assertions.assertEquals("1", second.getIntegerProp()),
                () -> Assertions.assertEquals("2", second.getLongProp()),
                () -> Assertions.assertEquals("3.3", second.getDoubleProp()),
                () -> Assertions.assertEquals("4.4", second.getFloatProp()),
                () -> Assertions.assertEquals("5.5", second.getBigDecimalProp())
        );

        final FirstRecord firstConverted = converter.convertSecondToFirst(second);
        Assertions.assertAll(
                () -> Assertions.assertEquals(first.getStringProp(), firstConverted.getStringProp()),
                () -> Assertions.assertEquals(first.getIntegerProp(), firstConverted.getIntegerProp()),
                () -> Assertions.assertEquals(first.getLongProp(), firstConverted.getLongProp()),
                () -> Assertions.assertEquals(first.getDoubleProp(), firstConverted.getDoubleProp()),
                () -> Assertions.assertEquals(first.getFloatProp(), firstConverted.getFloatProp()),
                () -> Assertions.assertEquals(first.getBigDecimalProp(), firstConverted.getBigDecimalProp())
        );
    }

    public interface MyConverter {

        SecondRecord convertFirstToSecond(FirstRecord firstRecord);

        FirstRecord convertSecondToFirst(SecondRecord secondRecord);

    }

    public static class FirstRecord {

        private String stringProp;

        private Integer integerProp;

        private Long longProp;

        private Double doubleProp;

        private Float floatProp;

        private BigDecimal bigDecimalProp;

        public FirstRecord() {
        }

        public FirstRecord(final String stringProp,
                           final Integer integerProp,
                           final Long longProp,
                           final Double doubleProp,
                           final Float floatProp,
                           final BigDecimal bigDecimalProp) {
            this.stringProp = stringProp;
            this.integerProp = integerProp;
            this.longProp = longProp;
            this.doubleProp = doubleProp;
            this.floatProp = floatProp;
            this.bigDecimalProp = bigDecimalProp;
        }

        public String getStringProp() {
            return stringProp;
        }

        public void setStringProp(final String stringProp) {
            this.stringProp = stringProp;
        }

        public Integer getIntegerProp() {
            return integerProp;
        }

        public void setIntegerProp(final Integer integerProp) {
            this.integerProp = integerProp;
        }

        public Long getLongProp() {
            return longProp;
        }

        public void setLongProp(final Long longProp) {
            this.longProp = longProp;
        }

        public Double getDoubleProp() {
            return doubleProp;
        }

        public void setDoubleProp(final Double doubleProp) {
            this.doubleProp = doubleProp;
        }

        public Float getFloatProp() {
            return floatProp;
        }

        public void setFloatProp(final Float floatProp) {
            this.floatProp = floatProp;
        }

        public BigDecimal getBigDecimalProp() {
            return bigDecimalProp;
        }

        public void setBigDecimalProp(final BigDecimal bigDecimalProp) {
            this.bigDecimalProp = bigDecimalProp;
        }
    }

    public static class SecondRecord {

        private String stringProp;

        private String integerProp;

        private String longProp;

        private String doubleProp;

        private String floatProp;

        private String bigDecimalProp;

        public SecondRecord() {
        }

        public SecondRecord(final String stringProp,
                            final String integerProp,
                            final String longProp,
                            final String doubleProp,
                            final String floatProp,
                            final String bigDecimalProp) {
            this.stringProp = stringProp;
            this.integerProp = integerProp;
            this.longProp = longProp;
            this.doubleProp = doubleProp;
            this.floatProp = floatProp;
            this.bigDecimalProp = bigDecimalProp;
        }

        public String getStringProp() {
            return stringProp;
        }

        public void setStringProp(final String stringProp) {
            this.stringProp = stringProp;
        }

        public String getIntegerProp() {
            return integerProp;
        }

        public void setIntegerProp(final String integerProp) {
            this.integerProp = integerProp;
        }

        public String getLongProp() {
            return longProp;
        }

        public void setLongProp(final String longProp) {
            this.longProp = longProp;
        }

        public String getDoubleProp() {
            return doubleProp;
        }

        public void setDoubleProp(final String doubleProp) {
            this.doubleProp = doubleProp;
        }

        public String getFloatProp() {
            return floatProp;
        }

        public void setFloatProp(final String floatProp) {
            this.floatProp = floatProp;
        }

        public String getBigDecimalProp() {
            return bigDecimalProp;
        }

        public void setBigDecimalProp(final String bigDecimalProp) {
            this.bigDecimalProp = bigDecimalProp;
        }

    }

}
