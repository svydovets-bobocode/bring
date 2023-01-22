package com.bobocode.svydovets.support;

import java.util.Map;

public interface ApplicationContext {

  <T> T getBean(Class<T> beanType);

  <T> T getBean(Class<T> beanType, String name);

  <T> Map<String, T> getAllBeans(Class<T> beanType);
}
