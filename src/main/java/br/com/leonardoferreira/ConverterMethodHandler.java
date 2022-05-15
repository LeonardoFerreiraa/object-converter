package br.com.leonardoferreira;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.function.Function;

public class ConverterMethodHandler implements MethodHandler {

    private final Class<?> returnType;

    private final Converter converter;

    public ConverterMethodHandler(final Class<?> returnType, final Converter converter) {
        this.returnType = returnType;
        this.converter = converter;
    }

    public static MethodHandler from(final Method method, final Map<Class<?>, Function<Object, Object>> typeAdapters) {
        final Converter converter = Converter.converterFor(method, typeAdapters);
        return new ConverterMethodHandler(method.getReturnType(), converter);
    }

    @Override
    public Object invoke(final Object... argv) throws Throwable {
        return converter.convert(returnType, argv);
    }

}
