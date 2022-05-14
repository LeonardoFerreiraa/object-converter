package br.com.leonardoferreira;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CommonObjectMethodsTest {

    @Test
    void shouldInvokeEqualsSuccessfullyTest() {
        final FirstConverter firstConverterInstance1 = ObjectConverter.create(FirstConverter.class);
        final FirstConverter firstConverterInstance2 = ObjectConverter.create(FirstConverter.class);

        Assertions.assertTrue(firstConverterInstance1.equals(firstConverterInstance2));

        final SecondConverter secondConverter = ObjectConverter.create(SecondConverter.class);
        Assertions.assertFalse(firstConverterInstance1.equals(secondConverter));
    }

    @Test
    void shouldInvokeToStringSuccessfullyTest() {
        final FirstConverter firstConverter = ObjectConverter.create(FirstConverter.class);
        final SecondConverter secondConverter = ObjectConverter.create(SecondConverter.class);

        Assertions.assertEquals("ObjectConverterInvocationHandler(target=FirstConverter)", firstConverter.toString());
        Assertions.assertEquals("ObjectConverterInvocationHandler(target=SecondConverter)", secondConverter.toString());
    }

    @Test
    void shouldInvokeHashCodeSuccessfullyTest() {
        final FirstConverter firstConverter = ObjectConverter.create(FirstConverter.class);
        final SecondConverter secondConverter = ObjectConverter.create(SecondConverter.class);

        Assertions.assertEquals(FirstConverter.class.hashCode(), firstConverter.hashCode());
        Assertions.assertEquals(SecondConverter.class.hashCode(), secondConverter.hashCode());
    }


    interface FirstConverter {
    }

    interface SecondConverter {
    }

}
