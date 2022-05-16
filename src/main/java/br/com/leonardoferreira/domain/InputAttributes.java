package br.com.leonardoferreira.domain;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

import br.com.leonardoferreira.Converting;
import br.com.leonardoferreira.util.ReflectionUtils;
import org.apiguardian.api.API;

@API(status = API.Status.INTERNAL)
public class InputAttributes {

    private final Map<String, Attribute> inputAttributes;

    private InputAttributes(final Map<String, Attribute> inputAttributes) {
        this.inputAttributes = inputAttributes;
    }

    public static InputAttributes from(final Method method) {
        final Class<?> inputType = method.getParameterTypes()[0];
        final Map<String, Attribute> inputAttributes = ReflectionUtils.findAllAttributes(inputType);

        Optional.ofNullable(method.getAnnotation(Converting.class))
                .map(Converting::properties)
                .map(Arrays::stream)
                .ifPresent(properties -> properties.forEach(property -> inputAttributes.put(property.to(), inputAttributes.get(property.from()))));

        return new InputAttributes(inputAttributes);
    }

    public Attribute get(final String name) {
        return inputAttributes.get(name);
    }

}
