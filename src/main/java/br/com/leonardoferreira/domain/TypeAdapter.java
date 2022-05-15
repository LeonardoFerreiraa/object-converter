package br.com.leonardoferreira.domain;

import java.util.function.Function;

import org.apiguardian.api.API;

@API(status = API.Status.INTERNAL)
public class TypeAdapter {

    private final Class<?> clazz;

    private final Function<Object, Object> adapterFunction;

    public TypeAdapter(final Class<?> clazz, final Function<Object, Object> adapterFunction) {
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
