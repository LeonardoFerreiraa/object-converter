package br.com.leonardoferreira;

import java.lang.reflect.Method;

class Accessors {

    private final Method getter;

    private final Method setter;

    public Accessors(final Method getter, final Method setter) {
        this.getter = getter;
        this.setter = setter;
    }

    public Object invokeGetter(final Object obj) {
        return Try.sneakyThrow(() -> getter.invoke(obj));
    }

    public void invokeSetter(final Object obj, final Object value) {
        Try.sneakyThrow(() -> setter.invoke(obj, value));
    }

}