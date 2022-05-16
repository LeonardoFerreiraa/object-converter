package br.com.leonardoferreira.converter;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.List;

import br.com.leonardoferreira.domain.ObjectConverterOptions;
import br.com.leonardoferreira.domain.PropertyParser;
import br.com.leonardoferreira.util.Try;

class NoArgsConstructorConverter implements Converter {

    private final Constructor<?> constructor;

    private final List<PropertyParser> propertyParsers;

    private NoArgsConstructorConverter(final Constructor<?> constructor,
                                       final List<PropertyParser> propertyParsers) {
        this.constructor = constructor;
        this.propertyParsers = propertyParsers;
    }

    public static NoArgsConstructorConverter from(final Method method, final ObjectConverterOptions options) {
        final Class<?> outputClass = method.getReturnType();

        final Constructor<?> constructor = Try.sneakyThrow(() -> outputClass.getConstructor());
        if (!constructor.isAccessible()) {
            if (options.isForceAccessible()) {
                constructor.setAccessible(true);
            } else {
                throw new IllegalStateException("constructor is not accessible");
            }
        }

        return new NoArgsConstructorConverter(
                constructor,
                PropertyParser.from(method, options)
        );
    }

    public Object convert(final Object... args) {
        final Object input = args[0];
        final Object output = Try.sneakyThrow(() -> constructor.newInstance());

        propertyParsers.forEach(parser -> parser.parse(input, output));

        return output;
    }

}