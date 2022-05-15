package br.com.leonardoferreira;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.function.Function;

class NoArgsConstructorUsingAccessorsConverter implements Converter {

    private final Constructor<?> constructor;

    private final Map<String, PropertyParser> propertyConverters;

    public NoArgsConstructorUsingAccessorsConverter(final Constructor<?> constructor,
                                                    final Map<String, PropertyParser> propertyConverters) {
        this.constructor = constructor;
        this.propertyConverters = propertyConverters;
    }

    public static NoArgsConstructorUsingAccessorsConverter from(final Method method,
                                                                final Map<Class<?>, Function<Object, Object>> typeAdapters) {
        final Class<?> outputClass = method.getReturnType();

        final Constructor<?> constructor = Try.sneakyThrow(outputClass::getConstructor);
        constructor.setAccessible(true);

        return new NoArgsConstructorUsingAccessorsConverter(
                constructor,
                PropertyParser.from(method, typeAdapters)
        );
    }

    public Object convert(final Class<?> returnType, final Object... args) {
        final Object input = args[0];
        final Object output = Try.sneakyThrow(constructor::newInstance);

        propertyConverters.forEach((fieldName, converter) -> converter.convert(input, output));

        return output;
    }

}