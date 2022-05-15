package br.com.leonardoferreira.domain;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import org.apiguardian.api.API;

@API(status = API.Status.INTERNAL)
public class TypeAdapters {

    private final List<TypeAdapter> typeAdapters;

    private TypeAdapters(final List<TypeAdapter> typeAdapters) {
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
