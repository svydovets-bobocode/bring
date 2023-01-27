package com.bobocode.svydovets.annotation.bean.processor;

import com.bobocode.svydovets.annotation.annotations.AutoSvydovets;
import com.bobocode.svydovets.annotation.annotations.Qualifier;
import com.bobocode.svydovets.annotation.bean.factory.BeanFactory;
import com.bobocode.svydovets.annotation.exception.BeanException;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Implementation of the {@link BeanProcessor} interface. Executes processing of
 * {@link AutoSvydovets} marked fields in beans. Such fields will be injected as a
 * dependency, retrieved from the context.
 * <p>
 * If no unique bean is present - processor will try to inject bean based on
 * {@link Qualifier} value.
 *
 * @see BeanProcessor
 * @see AutoSvydovets
 * @see Qualifier
 */
public class AutoSvydovetsBeanProcessor implements BeanProcessor {

    private final BeanFactory beanFactory;

    public AutoSvydovetsBeanProcessor(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public void processBeans(Map<String, Object> rootContext) {
        for (var entry : rootContext.entrySet()) {
            Object beanObject = entry.getValue();
            Class<?> beanType = beanObject.getClass();
            for (var field : beanType.getDeclaredFields()) {
                if (field.isAnnotationPresent(AutoSvydovets.class)) {
                    var dependency = getDependencyForField(field);
                    initField(beanObject, field, dependency);
                }
            }
        }
    }

    private void initField(Object beanObject, Field field, Object dependency) {
        try {
            field.setAccessible(true);
            field.set(beanObject, dependency);
        } catch (IllegalAccessException e) {
            throw new BeanException(e.getMessage(), e);
        }
    }

    private Object getDependencyForField(Field field) {
        if (List.class.isAssignableFrom(field.getType())) {
            return getBeansCollection(field);
        }
        if (field.isAnnotationPresent(Qualifier.class)) {
            String qualifierValue = field.getAnnotation(Qualifier.class).value();
            return beanFactory.getBean(qualifierValue, field.getType());
        } else {
            return beanFactory.getBean(field.getType());
        }
    }

    private List<?> getBeansCollection(Field field) {
        List<?> beansByType = Collections.emptyList();
        Type genericType = field.getGenericType();
        if (genericType instanceof ParameterizedType parameterizedType) {
            Type[] types = parameterizedType.getActualTypeArguments();
            if (types.length == 1 && types[0] instanceof Class<?> elementType) {
                beansByType = beanFactory.getBeansByType(elementType);
            }
        }
        return beansByType;
    }
}
