package com.bobocode.svydovets.annotation.register;

import java.util.Map;

//todo: refactor it to remove getSingletons, they should be only in BeanFactory
//single responsibility
public interface BeanRegistry {

    //todo: it stays
    void registerBean(String beanName, Object bean);

    <T> T getSingleton(String name, Class<T> beanType);

    <T> T getSingleton(Class<T> beanType);

    <T> Map<String, T> getAllSingletons(Class<T> beanType);

    //todo: it stays
    Map<String, Object> getRootContext();
}
