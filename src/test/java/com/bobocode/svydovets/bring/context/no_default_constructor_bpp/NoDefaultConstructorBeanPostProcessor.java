package com.bobocode.svydovets.bring.context.no_default_constructor_bpp;

import com.bobocode.svydovets.bring.bean.processor.BeanPostProcessor;

public class NoDefaultConstructorBeanPostProcessor implements BeanPostProcessor {
    public NoDefaultConstructorBeanPostProcessor(int i) {
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        return null;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        return null;
    }
}
