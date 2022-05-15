package br.com.leonardoferreira;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.function.Function;

interface Converter {

    Object convert(Class<?> returnType, Object... args);

    static Converter converterFor(Method method, final Map<Class<?>, Function<Object, Object>> typeAdapters) {
        final Class<?> returnType = method.getReturnType();
        for (final Constructor<?> constructor : returnType.getConstructors()) {
            if (constructor.getParameterCount() == 0) {
                return NoArgsConstructorUsingAccessorsConverter.from(method, typeAdapters);
            }
        }

        throw new IllegalArgumentException("can not create a converter for this method");
    }

}