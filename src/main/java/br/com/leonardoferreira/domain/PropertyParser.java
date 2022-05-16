package br.com.leonardoferreira.domain;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

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
        final InputAttributes inputAttributes = InputAttributes.from(method);

        final Class<?> outputClass = method.getReturnType();
        final Map<String, Attribute> outputFields = ReflectionUtils.findAllAttributes(outputClass);

        return Pair.stream(outputFields)
                .map(pair -> createPropertyConverter(pair, inputAttributes, typeAdapters))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    private static PropertyParser createPropertyConverter(final Pair<String, Attribute> field,
                                                          final InputAttributes inputAttributes,
                                                          final TypeAdapters typeAdapters) {
        final Attribute inputAttribute = inputAttributes.get(field.getFirst());
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
