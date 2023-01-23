package com.bobocode.svydovets.support;

import java.util.Map;

//todo(): provide JavaDocs
public interface ApplicationContext {

    <T> T getBean(Class<T> beanType);

    <T> T getBean(String name, Class<T> beanType);

    <T> Map<String, T> getAllBeans(Class<T> beanType);
}
