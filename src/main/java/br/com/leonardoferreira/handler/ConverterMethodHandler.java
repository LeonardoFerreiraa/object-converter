package br.com.leonardoferreira.handler;

import br.com.leonardoferreira.converter.Converter;
import org.apiguardian.api.API;

@API(status = API.Status.INTERNAL)
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
