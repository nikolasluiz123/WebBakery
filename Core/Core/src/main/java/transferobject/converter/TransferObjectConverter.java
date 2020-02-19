package transferobject.converter;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import model.abstractclasses.AbstractBaseModel;
import transferobject.abstractclasses.AbstractBaseTO;
import transferobject.annotations.TOEntity;
import utils.ObjectManipulatorUtil;
import utils.ReflectionUtil;

/**
 * 
 * @author TECBMNLS
 * @created 2 de ago de 2019 15:09:16
 *
 */
public class TransferObjectConverter {

    private ReflectionUtil reflectionUtil;

    public TransferObjectConverter() {
        this.reflectionUtil = new ReflectionUtil();
    }

    public void getTOFromModel(AbstractBaseModel model, AbstractBaseTO to) throws Exception {
        ObjectManipulatorUtil toInstance = reflectionUtil.reflectClass(to.getClass()).createInstace();
        ObjectManipulatorUtil modelInstance = reflectionUtil.reflectClass(model.getClass()).createInstace();

        Field[] toDeclaredFields = toInstance.getDeclaredFields();
        Field[] modelDeclaredFields = modelInstance.getDeclaredFields();

        for (Field toField : toDeclaredFields) {
            if (toField.isAnnotationPresent(TOEntity.class)) {
                TOEntity toEntity = toField.getAnnotation(TOEntity.class);
                for (Field modelField : modelDeclaredFields) {
                    if (modelField.getName().equals(toEntity.fieldName())) {

                        String setMethodName = methodName(toField, "set");
                        String getMethodName = methodName(modelField, "get");
                        
                        Method get = modelInstance.getInstantDeclaredMethod(getMethodName);
                        Method set = toInstance.getInstantDeclaredMethod(setMethodName, toField.getType());  

                        if (set != null && get != null) {
                            if (AbstractBaseTO.class.isAssignableFrom(toField.getType())) {
                                AbstractBaseModel nestedModel = (AbstractBaseModel) get
                                        .invoke(model);

                                if (nestedModel != null) {
                                    AbstractBaseTO nestedTO = (AbstractBaseTO) reflectionUtil
                                                                               .reflectClass(toField.getType())
                                                                               .createInstace()
                                                                               .getInstance();
                                    set.invoke(to, nestedTO);
                                    getTOFromModel(nestedModel, nestedTO);
                                } else {
                                    Object obj = null;
                                    set.invoke(to, obj);
                                }
                            } else {
                                Object value = get.invoke(model);
                                set.invoke(to, value);
                            }
                        }
                        break;
                    }
                }
            }
        }
    }

    public void getModelFromTO(AbstractBaseTO to, AbstractBaseModel model) throws Exception {
        ObjectManipulatorUtil toInstance = reflectionUtil.reflectClass(to.getClass()).createInstace();
        ObjectManipulatorUtil modelInstance = reflectionUtil.reflectClass(model.getClass()).createInstace();

        Field[] toDeclaredFields = toInstance.getDeclaredFields();
        Field[] modelDeclaredFields = modelInstance.getDeclaredFields();

        for (Field toField : toDeclaredFields) {
            if (toField.isAnnotationPresent(TOEntity.class)) {
                TOEntity toEntity = toField.getAnnotation(TOEntity.class);
                for (Field modelField : modelDeclaredFields) {
                    if (modelField.getName().equals(toEntity.fieldName())) {

                        String setMethodName = methodName(modelField, "set");
                        String getMethodName = methodName(toField, "get");
                        
                        Method get = toInstance.getInstantDeclaredMethod(getMethodName);
                        Method set = modelInstance.getInstantDeclaredMethod(setMethodName, modelField.getType());        
                                
                        if (set != null && get != null) {
                            if (AbstractBaseModel.class.isAssignableFrom(modelField.getType())) {
                                AbstractBaseTO nestedTO = (AbstractBaseTO) get.invoke(to);

                                if (nestedTO != null) {
                                    AbstractBaseModel nestedModel = (AbstractBaseModel) reflectionUtil
                                                                               .reflectClass(modelField.getType())
                                                                               .createInstace()
                                                                               .getInstance();
                                    set.invoke(model, nestedModel);
                                    getModelFromTO(nestedTO, nestedModel);
                                } else {
                                    Object obj = null;
                                    set.invoke(model, obj);
                                }
                            } else {
                                Object value = get.invoke(to);
                                set.invoke(model, value);
                            }
                        }
                        break;
                    }
                }
            }
        }
    }
    

    private String methodName(Field toField, String prefix) {
        return prefix + toField.getName().substring(0, 1).toUpperCase()
                + toField.getName().substring(1);
    }
}
