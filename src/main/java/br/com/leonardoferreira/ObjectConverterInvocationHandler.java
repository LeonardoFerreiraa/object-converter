package br.com.leonardoferreira;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;

public class ObjectConverterInvocationHandler implements InvocationHandler {

    private final Class<?> target;

    private final Map<Method, MethodHandler> handlers;

    public ObjectConverterInvocationHandler(final Class<?> target,
                                            final Map<Method, MethodHandler> handlers) {
        this.target = target;
        this.handlers = handlers;
    }

    @Override
    public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
        if (ReflectionUtils.isToStringMethod(method)) {
            return toString();
        }

        if (ReflectionUtils.isEqualsMethod(method)) {
            return equals(args[0]);
        }

        if (ReflectionUtils.isHashCodeMethod(method)) {
            return hashCode();
        }

        final MethodHandler handler = handlers.get(method);
        return handler.invoke(args);
    }

    @Override
    public boolean equals(final Object obj) {
        if (Proxy.isProxyClass(obj.getClass())) {
            final InvocationHandler handler = Proxy.getInvocationHandler(obj);
            if (handler instanceof ObjectConverterInvocationHandler) {
                final ObjectConverterInvocationHandler other = (ObjectConverterInvocationHandler) handler;
                return this.target == other.target;
            }
        }

        return false;
    }

    @Override
    public int hashCode() {
        return target.hashCode();
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + "(target=" + target.getSimpleName() + ")";
    }
}
