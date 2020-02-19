package utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.apache.commons.lang3.ArrayUtils;

public class ObjectManipulatorUtil {

    private Object instance;

    public ObjectManipulatorUtil(Object instance) {
        this.instance = instance;
    }

    public MethodManipulator getMethod(String methodName) {
        try {
            Method method = instance.getClass().getDeclaredMethod(methodName);
            return new MethodManipulator(instance, method);
        } catch (NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public Method getInstantDeclaredMethod(String methodName) {
        try {
            return instance.getClass().getDeclaredMethod(methodName);
        } catch (NoSuchMethodException e) {
            try {
                return instance.getClass().getSuperclass().getDeclaredMethod(methodName);
            } catch (NoSuchMethodException | SecurityException e1) {
                try {
                    return instance.getClass().getSuperclass().getSuperclass()
                            .getDeclaredMethod(methodName);
                } catch (NoSuchMethodException | SecurityException e2) {
                    e1.printStackTrace();
                    throw new RuntimeException(e1);
                }
            }
        }
    }

    public MethodManipulator getDeclaredMethod(String methodName, Class<?>... parametersTypes) {
        Method methodSelected;
        try {
            methodSelected = this.instance.getClass().getDeclaredMethod(methodName,
                                                                        parametersTypes);
        } catch (NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

        return new MethodManipulator(instance, methodSelected, parametersTypes);
    }

    public Method getInstantDeclaredMethod(String methodName, Class<?>... parametersTypes) {
        try {
            return this.instance.getClass().getMethod(methodName, parametersTypes);
        } catch (NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public Field[] getDeclaredFields() {
        Class<?> instanceClass = instance.getClass();

        Field[] declaredFields = instanceClass.getDeclaredFields();
        Field[] declaredFieldsSuperClass = instanceClass.getSuperclass().getDeclaredFields();
        Field[] declaredFieldsSuperSuperClass = instanceClass.getSuperclass().getSuperclass()
                .getDeclaredFields();
        Field[] allDeclaredFields = null;
        Field[] fullGambiarra = null;
        if (instanceClass.getSuperclass().getSuperclass() != Object.class) {
            allDeclaredFields = ArrayUtils.addAll(declaredFields, declaredFieldsSuperClass);
            fullGambiarra = ArrayUtils.addAll(allDeclaredFields, declaredFieldsSuperSuperClass);
            return fullGambiarra;
        } else {
            allDeclaredFields = ArrayUtils.addAll(declaredFields, declaredFieldsSuperClass);
            return allDeclaredFields;
        }
    }

    public Object getInstance() {
        return this.instance;
    }

}
