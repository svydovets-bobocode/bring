package com.bobocode.svydovets.bpp.returns_null;

import com.bobocode.svydovets.bring.bean.processor.BeanPostProcessor;

public class ReturnBeforeBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }
}
