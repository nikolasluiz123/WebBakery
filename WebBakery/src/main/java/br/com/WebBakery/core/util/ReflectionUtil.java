package br.com.WebBakery.core.util;

public class ReflectionUtil {

    public ClassManipulatorUtil reflectClass(Class<?> clazz) {
       return new ClassManipulatorUtil(clazz);
    }

}
