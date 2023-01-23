package com.bobocode.svydovets.annotation.bean.processor;

import com.bobocode.svydovets.annotation.annotations.AutoSvydovets;
import com.bobocode.svydovets.annotation.register.BeanRegistry;
import com.bobocode.svydovets.annotation.register.DefaultBeanRegistry;

import java.lang.reflect.Field;

//todo: provide JavaDocs
//todo: perform refactoring
public class AutoSvydovetsBeanPostProcessor implements BeanPostProcessor {

    private final BeanRegistry beanRegistry;

    public AutoSvydovetsBeanPostProcessor() {
        this.beanRegistry = new DefaultBeanRegistry();
    }

    @Override
    public void process() {
        for (var entrySet : beanRegistry.getRootContext().entrySet()) {
            Object beanObject = entrySet.getValue();
            Class<?> beanType = beanObject.getClass();
            for (var field : beanType.getDeclaredFields()) {
                if (field.isAnnotationPresent(AutoSvydovets.class)) {
                    var dependency = beanRegistry.getSingleton(field.getType());
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
