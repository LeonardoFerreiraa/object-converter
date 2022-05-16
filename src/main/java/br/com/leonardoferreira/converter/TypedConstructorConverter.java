package br.com.leonardoferreira.converter;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import br.com.leonardoferreira.domain.Attribute;
import br.com.leonardoferreira.domain.InputAttributes;
import br.com.leonardoferreira.domain.ObjectConverterOptions;
import br.com.leonardoferreira.domain.TypeAdapter;
import br.com.leonardoferreira.domain.TypeAdapters;
import br.com.leonardoferreira.util.Pair;
import br.com.leonardoferreira.util.Try;

class TypedConstructorConverter implements Converter {

    private final Constructor<?> constructor;

    private final List<Pair<Attribute, TypeAdapter>> fields;

    public TypedConstructorConverter(final Constructor<?> constructor, final List<Pair<Attribute, TypeAdapter>> fields) {
        this.constructor = constructor;
        this.fields = fields;
    }

    public static Converter from(final Constructor<?> constructor, final Method method, final ObjectConverterOptions options) {
        final TypeAdapters typeAdapters = options.getTypeAdapters();
        final InputAttributes inputAttributes = InputAttributes.from(method, options);

        final List<Pair<Attribute, TypeAdapter>> fields = Arrays.stream(constructor.getParameters())
                .map(it -> {
                    if (!it.isNamePresent()) {
                        throw new IllegalStateException("should be compiled with -parameter flag");
                    }

                    final Attribute inputAttribute = inputAttributes.get(it.getName());
                    if (inputAttribute == null) {
                        return null;
                    }

                    final TypeAdapter adapter = typeAdapters.adapterFor(inputAttribute.getType(), it.getType());
                    return Pair.of(inputAttribute, adapter);
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        if (!constructor.isAccessible()) {
            if (options.isForceAccessible()) {
                constructor.setAccessible(true);
            } else {
                throw new IllegalStateException("constructor is not acessible");
            }
        }

        return new TypedConstructorConverter(constructor, fields);
    }

    @Override
    public Object convert(final Object... args) {
        final Object input = args[0];

        final Object[] parameters = fields.stream()
                .map(it -> {
                    final Attribute attribute = it.getFirst();
                    final TypeAdapter adapter = it.getSecond();

                    final Object value = attribute.retrieveValue(input);
                    return adapter.adapt(value);
                })
                .toArray();

        return Try.sneakyThrow(() -> constructor.newInstance(parameters));
    }

}
