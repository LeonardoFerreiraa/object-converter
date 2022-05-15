package br.com.leonardoferreira;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Map;

import br.com.leonardoferreira.converter.Converter;
import br.com.leonardoferreira.domain.TypeAdapter;
import br.com.leonardoferreira.domain.TypeAdapters;
import br.com.leonardoferreira.handler.ConverterMethodHandler;
import br.com.leonardoferreira.handler.DefaultMethodHandler;
import br.com.leonardoferreira.handler.MethodHandler;
import br.com.leonardoferreira.util.Pair;
import org.apiguardian.api.API;

@API(status = API.Status.STABLE, since = "1.0.0")
public class ObjectConverter {

    private static final TypeAdapters DEFAULT_TYPE_ADAPTERS = TypeAdapters.from(
            new TypeAdapter(String.class, String::valueOf),
            new TypeAdapter(Integer.class, it -> Integer.parseInt(String.valueOf(it))),
            new TypeAdapter(Long.class, it -> Long.parseLong(String.valueOf(it))),
            new TypeAdapter(Double.class, it -> Double.parseDouble(String.valueOf(it))),
            new TypeAdapter(Float.class, it -> Float.parseFloat(String.valueOf(it))),
            new TypeAdapter(BigDecimal.class, it -> new BigDecimal(String.valueOf(it)))
    );

    private ObjectConverter() throws IllegalAccessException {
        throw new IllegalAccessException();
    }

    public static <T> T create(final Class<T> target) {
        final Map<Method, MethodHandler> handlers = Arrays.stream(target.getMethods())
                .map(method ->
                        Pair.of(
                                method,
                                method.isDefault() ? DefaultMethodHandler.from(method) : new ConverterMethodHandler(Converter.converterFor(method, DEFAULT_TYPE_ADAPTERS))
                        )
                )
                .collect(Pair.toMap());

        final Object instance = Proxy.newProxyInstance(
                ObjectConverter.class.getClassLoader(),
                new Class[]{target},
                new ObjectConverterInvocationHandler(target, handlers)
        );

        handlers.forEach((key, value) -> {
            if (value instanceof DefaultMethodHandler) {
                ((DefaultMethodHandler) value).bindTo(instance);
            }
        });

        return target.cast(instance);
    }

}
