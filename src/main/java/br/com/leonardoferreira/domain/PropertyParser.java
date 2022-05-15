package br.com.leonardoferreira.domain;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import br.com.leonardoferreira.Converting;
import br.com.leonardoferreira.util.Pair;
import br.com.leonardoferreira.util.ReflectionUtils;
import org.apiguardian.api.API;

@API(status = API.Status.INTERNAL)
public class PropertyParser {

    private final Attribute inputAttribute;

    private final Attribute outputAttribute;

    private final TypeAdapter typeAdapter;

    public PropertyParser(final Attribute inputAttribute, final Attribute outputAttribute, final TypeAdapter typeAdapter) {
        this.inputAttribute = inputAttribute;
        this.outputAttribute = outputAttribute;
        this.typeAdapter = typeAdapter;
    }

    public static List<PropertyParser> from(final Method method, final TypeAdapters typeAdapters) {
        final Class<?> inputClass = method.getParameterTypes()[0];
        final Map<String, Attribute> inputFields = ReflectionUtils.findAllAttributes(inputClass);
        Optional.ofNullable(method.getAnnotation(Converting.class))
                .map(Converting::properties)
                .map(Arrays::stream)
                .ifPresent(properties -> properties.forEach(property -> inputFields.put(property.to(), inputFields.get(property.from()))));

        final Class<?> outputClass = method.getReturnType();
        final Map<String, Attribute> outputFields = ReflectionUtils.findAllAttributes(outputClass);

        return Pair.stream(outputFields)
                .map(pair -> createPropertyConverter(pair, inputFields, typeAdapters))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private static PropertyParser createPropertyConverter(final Pair<String, Attribute> field,
                                                          final Map<String, Attribute> inputFields,
                                                          final TypeAdapters typeAdapters) {
        final Attribute inputAttribute = inputFields.get(field.getFirst());
        if (inputAttribute == null) {
            return null;
        }

        final Attribute outputAttribute = field.getSecond();
        final TypeAdapter typeAdapter = typeAdapters.adapterFor(inputAttribute.getType(), outputAttribute.getType());

        return new PropertyParser(inputAttribute, outputAttribute, typeAdapter);
    }

    public void parse(final Object input, final Object output) {
        final Object value = inputAttribute.retrieveValue(input);
        final Object parsedValue = typeAdapter.adapt(value);
        outputAttribute.putValue(output, parsedValue);
    }

}
