package com.bobocode.svydovets.annotation.bean.processor;

import java.util.Map;

//todo: provide JavaDocs
public interface BeanPostProcessor {

    void processBeans(Map<String, Object> rootContext);

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
