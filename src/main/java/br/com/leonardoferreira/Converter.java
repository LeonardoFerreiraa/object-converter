package br.com.leonardoferreira;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

interface Converter {

    Object convert(Class<?> returnType, Object... args);

    static Converter converterFor(Method method) {
        final Class<?> returnType = method.getReturnType();
        for (final Constructor<?> constructor : returnType.getConstructors()) {
            if (constructor.getParameterCount() == 0) {
                return NoArgsConstructorUsingAccessorsConverter.from(method);
            }
        }

        throw new IllegalArgumentException("can not create a converter for this method");
    }

}