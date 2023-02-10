package com.bobocode.svydovets.bring.bean.processor.injector;

import com.bobocode.svydovets.bring.bean.factory.BeanFactory;
import com.bobocode.svydovets.bring.exception.BeanException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class SetterInjector extends AbstractInjector<Method> {

    public SetterInjector(BeanFactory beanFactory) {
        super(beanFactory);
    }

    @Override
    protected void injectDependency(Method method, Object beanObject, Object dependency) {
        try {
            method.invoke(beanObject, dependency);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new BeanException(e.getMessage(), e);
        }
    }

    @Override
    protected Map<Class<?>, Method> getAccessibleObjects(Object beanObject) {
        return Arrays.stream(beanObject.getClass().getDeclaredMethods())
                .filter(this::isAutoSvydovetsPresent)
                .filter(this::isSetter)
                .collect(Collectors.toMap(method -> method.getParameterTypes()[0], method -> method));
    }

    private boolean isSetter(Method method) {
        return method.getName().startsWith("set")
                && method.getParameterTypes().length == 1;
    }
}

