package com.bobocode.svydovets.annotation.bean.processor;

import javax.annotation.Nullable;

public interface BeanPostProcessor {

    @Nullable
    default Object postProcessBeforeInitialization(Object bean, String beanName) {
      return bean;
    }

    @Nullable
    default Object postProcessAfterInitialization(Object bean, String beanName) {
      return bean;
    }
}
