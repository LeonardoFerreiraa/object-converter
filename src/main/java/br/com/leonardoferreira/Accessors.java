package br.com.leonardoferreira;

import java.lang.reflect.Method;

class Accessors {

    private final Class<?> type;

    private final Method getter;

    private final Method setter;

    public Accessors(final Class<?> type,
                     final Method getter,
                     final Method setter) {
        this.type = type;
        this.getter = getter;
        this.setter = setter;
    }

    public Class<?> getType() {
        return type;
    }

    public Method getGetter() {
        return getter;
    }

    public Method getSetter() {
        return setter;
    }
}