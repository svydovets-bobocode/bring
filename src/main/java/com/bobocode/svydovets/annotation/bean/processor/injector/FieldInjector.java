package com.bobocode.svydovets.annotation.bean.processor.injector;

import com.bobocode.svydovets.annotation.bean.factory.BeanFactory;
import com.bobocode.svydovets.annotation.exception.BeanException;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class FieldInjector extends AbstractInjector<Field> {

    public FieldInjector(BeanFactory beanFactory) {
        super(beanFactory);
    }

    @Override
    protected Map<Class<?>, Field> getAccessibleObjects(Object beanObject) {
        return Arrays.stream(beanObject.getClass().getDeclaredFields())
                .filter(this::isAutoSvydovetsPresent)
                .collect(Collectors.toMap(Field::getType, field -> field));
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
