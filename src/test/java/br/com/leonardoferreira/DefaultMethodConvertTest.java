package br.com.leonardoferreira;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DefaultMethodConvertTest {

    @Test
    void shouldCallDefaultMethodTest() {
        final MyConverter myConverter = ObjectConverter.create(MyConverter.class);
        final FirstRecord firstRecord = new FirstRecord("test");

        final SecondRecord second = myConverter.firstToSecond(firstRecord);
        Assertions.assertEquals(firstRecord.getProperty(), second.getProperty());

        final SecondRecord secondUppercase = myConverter.firstToSecondUppercase(firstRecord);
        Assertions.assertEquals(firstRecord.getProperty().toUpperCase(), secondUppercase.getProperty());
    }

    interface MyConverter {

        SecondRecord firstToSecond(FirstRecord firstRecord);

        default SecondRecord firstToSecondUppercase(final FirstRecord firstRecord) {
            final SecondRecord secondRecord = new SecondRecord();
            secondRecord.setProperty(firstRecord.getProperty().toUpperCase());
            return secondRecord;
        }

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

        private String property;

        public String getProperty() {
            return property;
        }

        public void setProperty(final String property) {
            this.property = property;
        }
    }

}
