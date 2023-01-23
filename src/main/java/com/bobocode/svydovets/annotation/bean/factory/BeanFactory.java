package com.bobocode.svydovets.annotation.bean.factory;

import java.util.Map;

//todo: provide JavaDocs
public interface BeanFactory {

    //todo: implement correctly that methods without using of BeanRegistry
    <T> T getBean(Class<T> beanType);

    <T> T getBean(Class<T> beanType, String name);

    <T> Map<String, T> getAllBeans(Class<T> beanType);

}
