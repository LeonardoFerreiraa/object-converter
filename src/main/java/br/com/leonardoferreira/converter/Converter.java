package br.com.leonardoferreira.converter;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import br.com.leonardoferreira.domain.TypeAdapters;
import org.apiguardian.api.API;

@API(status = API.Status.INTERNAL)
public interface Converter {

    Object convert(Object... args);

    static Converter converterFor(final Method method, final TypeAdapters typeAdapters) {
        final Class<?> returnType = method.getReturnType();
        for (final Constructor<?> constructor : returnType.getConstructors()) {
            if (constructor.getParameterCount() == 0) {
                return NoArgsConstructorUsingAccessorsConverter.from(method, typeAdapters);
            }
        }

        throw new IllegalArgumentException("can not create a converter for this method");
    }

}