package br.com.leonardoferreira;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Map;

class NoArgsConstructorUsingAccessorsConverter implements Converter {

    private final Constructor<?> constructor;

    private final Map<String, Accessors> inputFields;

    private final Map<String, Accessors> outputFields;

    private NoArgsConstructorUsingAccessorsConverter(final Constructor<?> constructor,
                                                     final Map<String, Accessors> inputFields,
                                                     final Map<String, Accessors> outputFields) {
        this.constructor = constructor;
        this.inputFields = inputFields;
        this.outputFields = outputFields;
    }

    public static NoArgsConstructorUsingAccessorsConverter from(final Method method) {
        final Class<?> inputClass = method.getParameterTypes()[0];
        final Class<?> outputClass = method.getReturnType();

        final Constructor<?> constructor = Try.sneakyThrow(outputClass::getConstructor);
        constructor.setAccessible(true);

        return new NoArgsConstructorUsingAccessorsConverter(
                constructor,
                ReflectionUtils.findAllFieldsWithAccessors(inputClass),
                ReflectionUtils.findAllFieldsWithAccessors(outputClass)
        );
    }

    public Object convert(final Class<?> returnType, final Object... args) {
        final Object input = args[0];
        final Object output = Try.sneakyThrow(constructor::newInstance);

        outputFields.forEach((fieldName, outputAccessors) -> {
            final Accessors inputAccessors = inputFields.get(fieldName);
            if (inputAccessors != null) {
                final Object value = inputAccessors.invokeGetter(input);
                outputAccessors.invokeSetter(output, value);
            }
        });

        return output;
    }

}