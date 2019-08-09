package br.com.WebBakery.core.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class ConstructorManipulatorUtil {

    private Constructor<?> constructor;

    public ConstructorManipulatorUtil(Constructor<?> constructor) {
        this.constructor = constructor;
    }

    public Object invoke() {
        try {
            return this.constructor.newInstance();
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
            throw new RuntimeException("Erro no construtor!", e.getTargetException());
        }
    }
    
}
