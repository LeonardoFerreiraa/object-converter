package br.com.leonardoferreira;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class Attribute {

    private final Field field;

    private final Method getter;

    private final Method setter;

    public Attribute(final Field field, final Method getter, final Method setter) {
        this.field = field;
        this.getter = getter;
        this.setter = setter;
    }

    public Class<?> getType() {
        return field.getType();
    }

    public Object retrieveValue(final Object obj) {
        return Try.sneakyThrow(() -> getter.invoke(obj));
    }

    public void putValue(final Object obj, final Object value) {
        Try.sneakyThrow(() -> setter.invoke(obj, value));
    }

}
