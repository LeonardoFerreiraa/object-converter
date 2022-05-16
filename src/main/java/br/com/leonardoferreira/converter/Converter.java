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

        final Constructor<?>[] constructors = returnType.getConstructors();
        if (constructors.length != 1) {
            throw new IllegalStateException("unable to find appropriate constructor");
        }

        final Constructor<?> constructor = constructors[0];
        if (constructor.getParameterCount() == 0) {
            return NoArgsConstructorConverter.from(method, typeAdapters);
        }

        return TypedConstructorConverter.from(constructor, method, typeAdapters);
    }

}