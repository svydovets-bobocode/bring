package com.bobocode.svydovets.annotation.bean.processor;

import com.bobocode.svydovets.annotation.annotations.AutoSvydovets;
import com.bobocode.svydovets.annotation.annotations.Qualifier;
import com.bobocode.svydovets.annotation.bean.factory.BeanFactory;
import com.bobocode.svydovets.annotation.exception.BeanException;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
            injectIntoSetters(beanObject);
            injectIntoFields(beanObject);
        }
    }
    private void injectIntoSetters(Object beanObject) {
        for (var method : beanObject.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(AutoSvydovets.class)
                    && isSetter(method)) {
                var dependencyParams = getDependencyParams(method);
                invokeSetter(method, beanObject, dependencyParams);
            }
        }
    }

    private boolean isSetter(Method method) {
        return method.getName().startsWith("set")
                && method.getParameterTypes().length == 1;
    }

    private Object getDependencyParams(Method method) {
        Class<?> injectedType = method.getParameterTypes()[0];
        if (method.isAnnotationPresent(Qualifier.class)) {
            String qualifierValue = method.getAnnotation(Qualifier.class).value();
            return beanFactory.getBean(qualifierValue, injectedType);
        } else {
            return beanFactory.getBean(injectedType);
        }
    }

    private void invokeSetter(Method method, Object beanObject, Object dependencyParams) {
        try {
            method.invoke(beanObject, dependencyParams);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new BeanException(e.getMessage(), e);
        }
    }

    private void injectIntoFields(Object beanObject) {
        for (var field : beanObject.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(AutoSvydovets.class)) {
                var dependency = getDependencyForField(field);
                initField(beanObject, field, dependency);
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
        if (field.isAnnotationPresent(Qualifier.class)) {
            String qualifierValue = field.getAnnotation(Qualifier.class).value();
            return beanFactory.getBean(qualifierValue, field.getType());
        } else {
            return beanFactory.getBean(field.getType());
        }
    }

}
