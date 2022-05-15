package br.com.leonardoferreira;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

class PropertyParser {

    private final Attribute inputAttribute;

    private final Attribute outputAttribute;

    private final Function<Object, Object> typeAdapter;

    public PropertyParser(final Attribute inputAttribute,
                          final Attribute outputAttribute,
                          final Function<Object, Object> typeAdapter) {
        this.inputAttribute = inputAttribute;
        this.outputAttribute = outputAttribute;
        this.typeAdapter = typeAdapter;
    }

    public static List<PropertyParser> from(final Method method,
                                            final Map<Class<?>, Function<Object, Object>> typeAdapters) {
        final Class<?> inputClass = method.getParameterTypes()[0];
        final Map<String, Attribute> inputFields = ReflectionUtils.findAllAttributes(inputClass);
        Optional.ofNullable(method.getAnnotation(Converting.class))
                .map(Converting::properties)
                .map(Arrays::stream)
                .ifPresent(properties ->
                        properties.forEach(property -> inputFields.put(property.to(), inputFields.get(property.from())))
                );

        final Class<?> outputClass = method.getReturnType();
        final Map<String, Attribute> outputFields = ReflectionUtils.findAllAttributes(outputClass);

        return Pair.stream(outputFields)
                .map(pair -> createPropertyConverter(pair, inputFields, typeAdapters))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private static PropertyParser createPropertyConverter(final Pair<String, Attribute> field,
                                                          final Map<String, Attribute> inputFields,
                                                          final Map<Class<?>, Function<Object, Object>> typeAdapters) {
        final Attribute inputAttribute = inputFields.get(field.getFirst());
        if (inputAttribute == null) {
            return null;
        }

        final Attribute outputAttribute = field.getSecond();
        final Function<Object, Object> typeAdapter = retrieveTypeAdapter(outputAttribute.getType(), inputAttribute.getType(), typeAdapters);

        return new PropertyParser(inputAttribute, outputAttribute, typeAdapter);
    }

    private static Function<Object, Object> retrieveTypeAdapter(final Class<?> outputType,
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

    public void parse(final Object input, final Object output) {
        final Object value = inputAttribute.retrieveValue(input);
        final Object parsedValue = typeAdapter.apply(value);
        outputAttribute.putValue(output, parsedValue);
    }

}
