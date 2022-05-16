package br.com.leonardoferreira.util;

import java.math.BigDecimal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ReflectionUtilsTest {

    @Test
    void shouldBeBasicTypeTest() {
        Assertions.assertTrue(ReflectionUtils.isBasicType(int.class));
        Assertions.assertTrue(ReflectionUtils.isBasicType(long.class));
        Assertions.assertTrue(ReflectionUtils.isBasicType(float.class));
        Assertions.assertTrue(ReflectionUtils.isBasicType(char.class));
        Assertions.assertTrue(ReflectionUtils.isBasicType(double.class));
        Assertions.assertTrue(ReflectionUtils.isBasicType(short.class));
        Assertions.assertTrue(ReflectionUtils.isBasicType(byte.class));
        Assertions.assertTrue(ReflectionUtils.isBasicType(boolean.class));
        Assertions.assertTrue(ReflectionUtils.isBasicType(Integer.class));
        Assertions.assertTrue(ReflectionUtils.isBasicType(Long.class));
        Assertions.assertTrue(ReflectionUtils.isBasicType(Float.class));
        Assertions.assertTrue(ReflectionUtils.isBasicType(Character.class));
        Assertions.assertTrue(ReflectionUtils.isBasicType(String.class));
        Assertions.assertTrue(ReflectionUtils.isBasicType(Double.class));
        Assertions.assertTrue(ReflectionUtils.isBasicType(Short.class));
        Assertions.assertTrue(ReflectionUtils.isBasicType(Byte.class));
        Assertions.assertTrue(ReflectionUtils.isBasicType(Boolean.class));
        Assertions.assertTrue(ReflectionUtils.isBasicType(BigDecimal.class));
    }

}