package com.bobocode.svydovets.annotation.bean.processor;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * Interface that allows custom linear processing of all beans in the
 * provided context. Processing logic should be provider by implementing
 * {@link #processBeans(Map)}.
 * <p>
 * Also, it provides several default methods, that will be common for
 * different types of processors.
 *
 * @see AutoSvydovetsBeanPostProcessor
 */
public interface BeanPostProcessor {

    void processBeans(Map<String, Object> rootContext);

    default void initField(Object beanObject, Field field, Object dependency) {
        try {
            field.setAccessible(true);
            field.set(beanObject, dependency);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

//    todo: think about before/after initialization
//    @Nullable
//    default Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
//      return bean;
//    }
//
//    @Nullable
//    default Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
//      return bean;
//    }
}
