package br.com.leonardoferreira;

import java.util.function.Function;

public class TypeAdapter<T> {

    private final Class<T> clazz;

    private final Function<Object, T> adapterFunction;

    public TypeAdapter(final Class<T> clazz, final Function<Object, T> adapterFunction) {
        this.clazz = clazz;
        this.adapterFunction = adapterFunction;
    }

    public boolean isAssignableFrom(final Class<?> other) {
        return clazz.isAssignableFrom(other);
    }

    public Object adapt(final Object value) {
        return adapterFunction.apply(value);
    }

}
