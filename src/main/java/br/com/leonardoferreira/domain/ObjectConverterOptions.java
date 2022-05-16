package br.com.leonardoferreira.domain;

public class ObjectConverterOptions {

    private final boolean forceAccessible;

    private final TypeAdapters typeAdapters;

    public ObjectConverterOptions(final boolean forceAccessible, final TypeAdapters typeAdapters) {
        this.forceAccessible = forceAccessible;
        this.typeAdapters = typeAdapters;
    }

    public boolean isForceAccessible() {
        return forceAccessible;
    }

    public TypeAdapters getTypeAdapters() {
        return typeAdapters;
    }
}
