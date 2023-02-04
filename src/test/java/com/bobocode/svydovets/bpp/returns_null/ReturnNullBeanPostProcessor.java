package com.bobocode.svydovets.bpp.returns_null;

import com.bobocode.svydovets.annotation.bean.processor.BeanPostProcessor;

public class ReturnNullBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        return null;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        return null;
    }
}
