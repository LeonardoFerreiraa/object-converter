package br.com.leonardoferreira;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class UnequalPropertyNameConvertTest {

    @Test
    void shouldParseUnequalNamePropertiesSuccessfullyTest() {
        final MyConverter converter = ObjectConverter.create(MyConverter.class);
        final FirstRecord first = new FirstRecord("test");

        final SecondRecord second = converter.firstToSecond(first);

        Assertions.assertEquals(first.property, second.getYtreporp());
    }

    interface MyConverter {

        @Converting(
                properties = {
                        @ConvertingProperty(from = "property", to = "ytreporp")
                }
        )
        SecondRecord firstToSecond(FirstRecord firstRecord);

    }

    public static class FirstRecord {

        private String property;

        public FirstRecord(final String property) {
            this.property = property;
        }

        public String getProperty() {
            return property;
        }

        public void setProperty(final String property) {
            this.property = property;
        }

    }

    public static class SecondRecord {

        private String ytreporp;

        public String getYtreporp() {
            return ytreporp;
        }

        public void setYtreporp(final String ytreporp) {
            this.ytreporp = ytreporp;
        }
    }

}
