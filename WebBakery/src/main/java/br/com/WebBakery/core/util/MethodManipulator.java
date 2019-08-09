package br.com.WebBakery.core.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class MethodManipulator {

    private Object instance;
    private Method method;
    private Class<?>[] parametersTypes;

    public MethodManipulator(Object instance, Method method) {
        this.instance = instance;
        this.method = method;
    }

    public MethodManipulator(Object instance, Method method, Class<?>[] parametersTypes) {
        this.instance = instance;
        this.method = method;
        this.parametersTypes = parametersTypes;
    }

    public Object invoke() {
        try {
            return method.invoke(instance);
        } catch (IllegalAccessException | IllegalArgumentException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getTargetException());
        }
    }

    public Object invokeWhithParameters() {
        try {
            List<Object> params = new ArrayList<>();
            Stream.of(method.getParameters())
                    .forEach(p -> params.add(parametersTypes.getClass().equals(p.getType())));

            return method.invoke(instance, params.toArray());
        } catch (IllegalAccessException | IllegalArgumentException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getTargetException());
        }
    }

    public Method getMethod() {
        return this.method;
    }

}
