package br.com.leonardoferreira;

public interface MethodHandler {

    Object invoke(Object... argv) throws Throwable;

}
