package br.com.leonardoferreira;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class TypeAdapters {

    private final List<TypeAdapter> typeAdapters;

    public TypeAdapters(final List<TypeAdapter> typeAdapters) {
        this.typeAdapters = typeAdapters;
    }

    public static TypeAdapters from(final TypeAdapter... adapters) {
        return new TypeAdapters(Arrays.asList(adapters));
    }

    public TypeAdapter adapterFor(final Class<?> inputType, final Class<?> outputType) {
        if (outputType == inputType) {
            return new TypeAdapter(outputType, Function.identity());
        }

        for (final TypeAdapter typeAdapter : typeAdapters) {
            if (typeAdapter.isAssignableFrom(outputType)) {
                return typeAdapter;
            }
        }

        throw new IllegalArgumentException("unable to find a adapter for " + outputType + " -> " + inputType);
    }

}
