package br.com.leonardoferreira.domain;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import br.com.leonardoferreira.util.Try;
import org.apiguardian.api.API;

@API(status = API.Status.INTERNAL)
public class Attribute {

    private final Field field;

    private final Method getter;

    private final Method setter;

    private final boolean readOnlyField;

    private Attribute(final Field field, final Method getter, final Method setter, final boolean readOnlyField) {
        this.field = field;
        this.getter = getter;
        this.setter = setter;
        this.readOnlyField = readOnlyField;
    }

    public static Attribute from(final Field field, final Method getter, final Method setter, final ObjectConverterOptions options) {
        final boolean readOnlyField = Modifier.isFinal(field.getModifiers());

        if (getter == null || setter == null) {
            if (!field.isAccessible()) {
                if (options.isForceAccessible()) {
                    field.setAccessible(true);
                } else {
                    throw new IllegalStateException("field is not accessible");
                }
            }
        }

        return new Attribute(field, getter, setter, readOnlyField);
    }

    public Class<?> getType() {
        return field.getType();
    }

    public Object retrieveValue(final Object obj) {
        if (getter == null) {
            return Try.sneakyThrow(() -> field.get(obj));
        }

        return Try.sneakyThrow(() -> getter.invoke(obj));
    }

    public void putValue(final Object obj, final Object value) {
        if (readOnlyField) {
            throw new IllegalStateException("field is readonly");
        }

        if (setter == null) {
            Try.sneakyThrow(() -> field.set(obj, value));
        } else {
            Try.sneakyThrow(() -> setter.invoke(obj, value));
        }
    }

}
