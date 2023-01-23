package com.bobocode.svydovets.annotation.bean.processor;

import javax.annotation.Nullable;

//todo: provide JavaDocs
public interface BeanPostProcessor {

    void process();

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
