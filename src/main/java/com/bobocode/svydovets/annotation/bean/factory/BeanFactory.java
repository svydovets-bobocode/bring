package com.bobocode.svydovets.annotation.bean.factory;

import java.util.Map;
import java.util.Set;

public interface BeanFactory {

  Map<String, Object> createBeans(Set<Class<?>> beanTypes);

  <T> T getBean(Class<T> beanType);

  <T> T getBean(Class<T> beanType, String name);

  <T> Map<String, T> getAllBeans(Class<T> beanType);
}
