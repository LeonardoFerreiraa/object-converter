package br.com.leonardoferreira.handler;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.apiguardian.api.API;

@API(status = API.Status.INTERNAL)
public class DefaultMethodHandler implements MethodHandler {

    private MethodHandle handle;

    private DefaultMethodHandler(final MethodHandle handle) {
        this.handle = handle;
    }

    public static DefaultMethodHandler from(final Method defaultMethod) {
        try {
            final Class<?> declaringClass = defaultMethod.getDeclaringClass();
            final Field field = MethodHandles.Lookup.class.getDeclaredField("IMPL_LOOKUP");
            field.setAccessible(true);
            final MethodHandles.Lookup lookup = (MethodHandles.Lookup) field.get(null);

            final MethodHandle methodHandle = lookup.unreflectSpecial(defaultMethod, declaringClass);
            return new DefaultMethodHandler(methodHandle);
        } catch (final NoSuchFieldException | IllegalAccessException ex) {
            throw new IllegalStateException(ex);
        }
    }

    public void bindTo(final Object proxy) {
        handle = handle.bindTo(proxy);
    }

    @Override
    public Object invoke(final Object[] argv) throws Throwable {
        return handle.invokeWithArguments(argv);
    }

}
