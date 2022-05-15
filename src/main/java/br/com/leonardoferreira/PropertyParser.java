package br.com.leonardoferreira;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.function.Function;

class PropertyParser {

    private final Method getter;

    private final Method setter;

    private final Function<Object, Object> typeAdapter;

    public PropertyParser(final Method getter,
                          final Method setter,
                          final Function<Object, Object> typeAdapter) {
        this.getter = getter;
        this.setter = setter;
        this.typeAdapter = typeAdapter;
    }

    public static Map<String, PropertyParser> from(final Method method,
                                                   final Map<Class<?>, Function<Object, Object>> typeAdapters) {
        final Class<?> inputClass = method.getParameterTypes()[0];
        final Map<String, Accessors> inputFields = ReflectionUtils.findAllFieldsWithAccessors(inputClass);

        final Class<?> outputClass = method.getReturnType();
        final Map<String, Accessors> outputFields = ReflectionUtils.findAllFieldsWithAccessors(outputClass);

        return Pair.stream(outputFields)
                .map(pair -> createPropertyConverter(pair, inputFields, typeAdapters))
                .collect(Pair.toMap());
    }

    private static Pair<String, PropertyParser> createPropertyConverter(final Pair<String, Accessors> field,
                                                                        final Map<String, Accessors> inputFields,
                                                                        final Map<Class<?>, Function<Object, Object>> typeAdapters) {
        final Accessors inputAccessors = inputFields.get(field.getFirst());
        final Method getter = inputAccessors.getGetter();
        final Method setter = field.getSecond().getSetter();
        final Function<Object, Object> typeAdapter = createTypeAdapter(field.getSecond().getType(), inputAccessors.getType(), typeAdapters);

        return Pair.of(
                field.getFirst(),
                new PropertyParser(getter, setter, typeAdapter)
        );
    }

    private static Function<Object, Object> createTypeAdapter(final Class<?> outputType,
                                                              final Class<?> inputType,
                                                              final Map<Class<?>, Function<Object, Object>> typeAdapters) {
        if (outputType == inputType) {
            return Function.identity();
        }

        for (final Map.Entry<Class<?>, Function<Object, Object>> it : typeAdapters.entrySet()) {
            if (it.getKey().isAssignableFrom(outputType)) {
                return it.getValue();
            }
        }

        throw new IllegalArgumentException("unable to find a adapter for " + outputType + " -> " + inputType);
    }

    public void convert(final Object input, final Object output) {
        final Object value = Try.sneakyThrow(() -> getter.invoke(input));
        final Object parsedValue = typeAdapter.apply(value);
        Try.sneakyThrow(() -> setter.invoke(output, parsedValue));
    }

}
