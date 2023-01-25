package com.bobocode.svydovets.annotation.bean.processor;

import java.lang.reflect.Field;
import java.util.Map;

//todo: provide JavaDocs
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

    //todo: think about before/after initialization
//  public interface BeanPostProcessor {
//    @Nullable
//    default Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
//      return bean;
//    }
//
//    @Nullable
//    default Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
//      return bean;
//    }
//  }
}
