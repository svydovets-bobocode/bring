package com.bobocode.svydovets.annotation.bean.processor.injector;

import com.bobocode.svydovets.annotation.bean.factory.BeanFactory;
import com.bobocode.svydovets.annotation.exception.BeanException;
import com.bobocode.svydovets.annotation.exception.FieldNotFoundException;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ConstructorInjector extends AbstractInjector<Field> {

    public ConstructorInjector(BeanFactory beanFactory) {
        super(beanFactory);
    }

    @Override
    protected Map<Class<?>, Field> getAccessibleObjects(Object beanObject) {
        Map<Class<?>, Field> accessibleObjects = new HashMap<>();
        Arrays.stream(beanObject.getClass().getConstructors())
                .filter(this::isAutoSvydovetsPresent)
                .findFirst()
                .ifPresent(constructor -> {
                    for (int i = 0; i < constructor.getParameterCount(); i++) {
                        try {
                            var field = beanObject.getClass().getField(constructor.getTypeParameters()[i].getName());
                            accessibleObjects.put(constructor.getParameterTypes()[i], field);
                        } catch (NoSuchFieldException e) {
                            throw new FieldNotFoundException(e.getMessage());
                        }
                    }
                });
        return accessibleObjects;
    }

    @Override
    protected void injectDependency(Field field, Object beanObject, Object dependency) {
        try {
            field.setAccessible(true);
            field.set(beanObject, dependency);
        } catch (IllegalAccessException e) {
            throw new BeanException(e.getMessage(), e);
        }
    }
}
