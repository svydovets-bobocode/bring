package com.bobocode.svydovets.annotation.bean.processor;

import com.bobocode.svydovets.annotation.annotations.AutoSvydovets;
import com.bobocode.svydovets.annotation.bean.factory.AnnotationBeanFactory;
import com.bobocode.svydovets.annotation.bean.factory.BeanFactory;


import java.lang.reflect.Field;
import java.util.Map;

//todo: provide JavaDocs
//todo: perform refactoring
public class AutoSvydovetsBeanPostProcessor implements BeanPostProcessor {

    private BeanFactory beanFactory;

    public AutoSvydovetsBeanPostProcessor() {
        this.beanFactory = new AnnotationBeanFactory();
    }

    @Override
    public void processBeans(Map<String, Object> rootContext) {
        for (var entrySet : rootContext.entrySet()) {
            Object beanObject = entrySet.getValue();
            Class<?> beanType = beanObject.getClass();
            for (var field : beanType.getDeclaredFields()) {
                if (field.isAnnotationPresent(AutoSvydovets.class)) {
                    var dependency = beanFactory.getBean(field.getType());
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

}
