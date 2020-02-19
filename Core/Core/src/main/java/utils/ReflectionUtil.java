package utils;

public class ReflectionUtil {

    public ClassManipulatorUtil reflectClass(Class<?> clazz) {
       return new ClassManipulatorUtil(clazz);
    }

}
