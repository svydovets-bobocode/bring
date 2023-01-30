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


    public void injectDependency(Object beanObject) {
        getAccessibleObjects(beanObject)
                .forEach((key, value) -> {
                    Object dependency = getDependency(value, key);
                    injectDependency(value, beanObject, dependency);
                });
    }

    protected Object getDependency(T accessibleObject, Class<?> injectType) {
        if (accessibleObject.isAnnotationPresent(Qualifier.class)) {
            String qualifierValue = accessibleObject.getAnnotation(Qualifier.class).value();
            return beanFactory.getBean(qualifierValue, injectType);
        } else {
            return beanFactory.getBean(injectType);
        }
    }

    protected boolean isAutoSvydovetsPresent(AccessibleObject accessibleObject) {
        return accessibleObject.isAnnotationPresent(AutoSvydovets.class);
    }

    protected abstract Map<Class<?>, T> getAccessibleObjects(Object beanObject);

    protected abstract void injectDependency(T accessibleObject, Object beanObject, Object dependencyParams);
}
