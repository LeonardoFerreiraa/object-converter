package br.com.leonardoferreira.handler;

import org.apiguardian.api.API;

@API(status = API.Status.INTERNAL)
public interface MethodHandler {

    Object invoke(Object... argv) throws Throwable;

}
