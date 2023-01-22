package com.bobocode.svydovets.annotation.bean.factory;

import com.bobocode.svydovets.annotation.annotations.Component;
import com.bobocode.svydovets.annotation.bean.processor.BeanPostProcessor;
import com.bobocode.svydovets.annotation.bean.processor.DefaultBeanPostProcessor;
import com.bobocode.svydovets.annotation.register.BeanRegistry;
import com.bobocode.svydovets.annotation.register.DefaultBeanRegistry;
import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.Set;

public class DefaultBeanFactoryImpl implements BeanFactory {

  private final BeanRegistry beanRegistry;
  private final BeanPostProcessor defaultBeanPostProcessor;

  public DefaultBeanFactoryImpl() {
    this.beanRegistry = new DefaultBeanRegistry();
    this.defaultBeanPostProcessor = new DefaultBeanPostProcessor();
  }

  @Override
  public Map<String, Object> createBeans(Set<Class<?>> beanTypes) {
    for (Class<?> beanType : beanTypes) {
      try {
        Constructor<?> constructor = beanType.getConstructor();
        Object beanObject = constructor.newInstance();
        String beanName = resolveBeanName(beanType);
        beanRegistry.register(beanName, beanObject);
        postProcessBeans();
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
    return null;
  }

  private void postProcessBeans() {
    defaultBeanPostProcessor.process();
  }

  private String resolveBeanName(Class<?> beanType) {
    String beanName = beanType.getAnnotation(Component.class).value();
    return beanName.isEmpty() ? beanType.getSimpleName() : beanName;
  }

  @Override
  public <T> T getBean(Class<T> beanType) {
    Map<String, T> allBeans = getAllBeans(beanType);
    if (!allBeans.isEmpty() && allBeans.size() > 1) {
      throw new RuntimeException("not unique bean");
    }
    return allBeans.values().stream().findAny().orElseThrow();
  }

  @Override
  public <T> T getBean(Class<T> beanType, String name) {
    return beanRegistry.getSingleton(beanType, name);
  }

  @Override
  public <T> Map<String, T> getAllBeans(Class<T> beanType) {
    return beanRegistry.getAllSingletons(beanType);
  }

}
