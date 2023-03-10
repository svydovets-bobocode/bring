package com.bobocode.svydovets.annotation.bean.processor.injector;

import com.bobocode.svydovets.annotation.bean.factory.BeanFactory;
import com.bobocode.svydovets.annotation.exception.BeanException;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static java.lang.String.format;

/**
 * Injector's implementation for dependency injections in constructors
 * marked {@link com.bobocode.svydovets.annotation.annotations.AutoSvydovets}
 */
@Slf4j
public class ConstructorInjector extends AbstractInjector<Field> {

    public ConstructorInjector(BeanFactory beanFactory) {
        super(beanFactory);
    }

    @Override
    protected Map<Class<?>, Field> getAccessibleObjects(Object beanObject) {
        log.trace("Constructor injection processing on bean {}", beanObject.getClass());
        Map<Class<?>, Field> accessibleObjects = new HashMap<>();
        Arrays.stream(beanObject.getClass().getConstructors())
                .filter(this::isAutoSvydovetsPresent)
                .findFirst()
                .ifPresent(constructor -> {
                    Class<?>[] parameterTypes = constructor.getParameterTypes();
                    for (int i = 0; i < constructor.getParameterCount(); i++) {
                        Class<?> parameterType = parameterTypes[i];
                        var field = retrieveFieldForParameterType(beanObject, parameterType);
                        accessibleObjects.put(parameterType, field);
                    }
                });
        return accessibleObjects;
    }

    private Field retrieveFieldForParameterType(Object beanObject, Class<?> parameterType) {
        return Arrays.stream(beanObject.getClass().getDeclaredFields())
                .filter(field -> field.getType().isAssignableFrom(parameterType))
                .findFirst()
                .orElseThrow(() -> new BeanException(
                        format("No field found for constructor-passed parameter type %s", parameterType.getName())));
    }

    @Override
    @SuppressWarnings("java:S3011")
    protected void injectDependency(Field field, Object beanObject, Object dependency) {
        try {
            field.setAccessible(true);
            field.set(beanObject, dependency);
        } catch (IllegalAccessException e) {
            throw new BeanException(e.getMessage(), e);
        }
    }
}
