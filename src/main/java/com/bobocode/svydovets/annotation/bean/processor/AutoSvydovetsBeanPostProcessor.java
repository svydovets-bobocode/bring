package com.bobocode.svydovets.annotation.bean.processor;

import com.bobocode.svydovets.annotation.annotations.AutoSvydovets;
import com.bobocode.svydovets.annotation.annotations.Qualifier;
import com.bobocode.svydovets.annotation.bean.factory.BeanFactory;


import java.lang.reflect.Field;
import java.util.Map;

//todo: provide JavaDocs
//todo: perform refactoring
public class AutoSvydovetsBeanPostProcessor implements BeanPostProcessor {

    private final BeanFactory beanFactory;

    public AutoSvydovetsBeanPostProcessor(BeanFactory beanFactory) {
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
            throw new RuntimeException(e);
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
