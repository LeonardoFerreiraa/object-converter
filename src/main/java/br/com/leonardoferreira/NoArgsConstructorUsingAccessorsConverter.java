package br.com.leonardoferreira;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.List;

class NoArgsConstructorUsingAccessorsConverter implements Converter {

    private final Constructor<?> constructor;

    private final List<PropertyParser> propertyParsers;

    private NoArgsConstructorUsingAccessorsConverter(final Constructor<?> constructor,
                                                     final List<PropertyParser> propertyParsers) {
        this.constructor = constructor;
        this.propertyParsers = propertyParsers;
    }

    public static NoArgsConstructorUsingAccessorsConverter from(final Method method, final TypeAdapters typeAdapters) {
        final Class<?> outputClass = method.getReturnType();

        final Constructor<?> constructor = Try.sneakyThrow(outputClass::getConstructor);
        constructor.setAccessible(true);

        return new NoArgsConstructorUsingAccessorsConverter(
                constructor,
                PropertyParser.from(method, typeAdapters)
        );
    }

    public Object convert(final Object... args) {
        final Object input = args[0];
        final Object output = Try.sneakyThrow(constructor::newInstance);

        propertyParsers.forEach(parser -> parser.parse(input, output));

        return output;
    }

}