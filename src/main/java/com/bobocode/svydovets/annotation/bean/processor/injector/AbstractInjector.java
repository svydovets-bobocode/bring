package com.bobocode.svydovets.annotation.bean.processor.injector;

import com.bobocode.svydovets.annotation.annotations.AutoSvydovets;
import com.bobocode.svydovets.annotation.annotations.Qualifier;
import com.bobocode.svydovets.annotation.bean.factory.BeanFactory;

import java.lang.reflect.AccessibleObject;
import java.util.Map;

public abstract class AbstractInjector<T extends AccessibleObject> implements Injector<T> {
    protected final BeanFactory beanFactory;

    protected AbstractInjector(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }


    @Override
    public void injectDependency(Object beanObject) {
        getAccessibleObjects(beanObject)
                .forEach((key, value) -> {
                    Object dependency = getDependency(value, key);
                    injectDependency(value, beanObject, dependency);
                });
    }

    /**
     * @param accessibleObject
     * @param injectType
     * @return bean that will be used for injection in another bean
     */
    protected Object getDependency(T accessibleObject, Class<?> injectType) {
        if (accessibleObject.isAnnotationPresent(Qualifier.class)) {
            String qualifierValue = accessibleObject.getAnnotation(Qualifier.class).value();
            return beanFactory.getBean(qualifierValue, injectType);
        } else {
            return beanFactory.getBean(injectType);
        }
    }

    /**
     * Checks if {@link AutoSvydovets} annotation is present ot top of the object
     * @param accessibleObject
     * @return true or false
     */
    protected boolean isAutoSvydovetsPresent(AccessibleObject accessibleObject) {
        return accessibleObject.isAnnotationPresent(AutoSvydovets.class);
    }

    /**
     * @param beanObject
     * @return a Map of the class type and its declared fields that are annotated with @AutoSvydovets
     */
    protected abstract Map<Class<?>, T> getAccessibleObjects(Object beanObject);

    /**
     * Overloaded method that provide final dependency injection based on {@link Injector} type
     * @param accessibleObject
     * @param beanObject
     * @param dependencyParams
     */
    protected abstract void injectDependency(T accessibleObject, Object beanObject, Object dependencyParams);
}
