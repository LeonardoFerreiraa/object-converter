package br.com.leonardoferreira.util;

import org.apiguardian.api.API;

@API(status = API.Status.INTERNAL)
public class Try {

    public static <T> T orNull(final SupplierThatThrows<T> supplier) {
        try {
            return supplier.get();
        } catch (final Throwable throwable) {
            return null;
        }
    }

    public static <T> T sneakyThrow(final SupplierThatThrows<T> supplier) {
        try {
            return supplier.get();
        } catch (final Throwable throwable) {
            throw sneakyThrow(throwable);
        }
    }

    private static RuntimeException sneakyThrow(final Throwable t) {
        return Try.internalSneakyThrow(t);
    }

    @SuppressWarnings("unchecked")
    private static <T extends Throwable> T internalSneakyThrow(Throwable t) throws T {
        throw (T) t;
    }

    @FunctionalInterface
    public interface SupplierThatThrows<T> {
        T get() throws Throwable;
    }

}
