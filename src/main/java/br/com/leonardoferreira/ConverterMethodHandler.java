package br.com.leonardoferreira;

public class ConverterMethodHandler implements MethodHandler {

    private final Converter converter;

    public ConverterMethodHandler(final Converter converter) {
        this.converter = converter;
    }

    @Override
    public Object invoke(final Object... argv) throws Throwable {
        return converter.convert(argv);
    }

}
