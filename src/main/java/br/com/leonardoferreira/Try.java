package br.com.leonardoferreira;

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
    interface SupplierThatThrows<T> {
        T get() throws Throwable;
    }

}