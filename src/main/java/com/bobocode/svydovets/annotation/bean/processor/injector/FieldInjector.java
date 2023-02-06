package com.bobocode.svydovets.annotation.bean.processor.injector;

import com.bobocode.svydovets.annotation.bean.factory.BeanFactory;
import com.bobocode.svydovets.annotation.exception.BeanException;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.String.format;

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

    @Override
    protected Object getDependency(Field accessibleObject, Class<?> injectType) {
        if (Collection.class.isAssignableFrom(injectType)) {
            Type genericType = accessibleObject.getGenericType();
            if (genericType instanceof ParameterizedType parameterizedType) {
                Type[] types = parameterizedType.getActualTypeArguments();
                if (types.length == 1 && types[0] instanceof Class<?> elementType) {
                    return beanFactory.getBeansByType(elementType);
                }
            }
            throw new BeanException(format("@AutoSvydovets collection %s should not be raw used for field '%s'",
                        injectType.getSimpleName(), accessibleObject.getName()));
        }
        return super.getDependency(accessibleObject, injectType);
    }
}
