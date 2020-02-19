package utils;

import java.lang.reflect.Constructor;

public class ClassManipulatorUtil {

    private Class<?> clazz;

    public ClassManipulatorUtil(Class<?> clazz) {
        this.clazz = clazz;
    }

    public ConstructorManipulatorUtil getConstructorDefault() {
        try {
            Constructor<?> constructorDefault = this.clazz.getDeclaredConstructor();
            return new ConstructorManipulatorUtil(constructorDefault);
        } catch (NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public ObjectManipulatorUtil createInstace() {
        Object instance = getConstructorDefault().invoke();
        return new ObjectManipulatorUtil(instance);
    }

}
