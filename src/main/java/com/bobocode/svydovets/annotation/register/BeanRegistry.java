package com.bobocode.svydovets.annotation.register;

import java.util.Map;

public interface BeanRegistry {

  void register(String beanName, Object bean);

  <T> T getSingleton(Class<T> beanType, String name);

  <T> Map<String, T> getAllSingletons(Class<T> beanType);

  Map<String, Object> getRootContext();
}
