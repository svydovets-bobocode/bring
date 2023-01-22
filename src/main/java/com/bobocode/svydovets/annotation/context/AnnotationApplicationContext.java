package com.bobocode.svydovets.annotation.context;

import com.bobocode.svydovets.annotation.annotations.Component;
import com.bobocode.svydovets.annotation.bean.factory.BeanFactory;
import com.bobocode.svydovets.annotation.bean.factory.DefaultBeanFactoryImpl;
import com.bobocode.svydovets.support.AbstractApplicationContext;
import java.util.Map;
import java.util.Set;
import org.reflections.Reflections;

public class AnnotationApplicationContext extends AbstractApplicationContext implements
    AnnotationRegistry {

  private final BeanFactory defaultBeanFactory;

  public AnnotationApplicationContext(String... packages) {
    this.defaultBeanFactory = new DefaultBeanFactoryImpl();
    Set<Class<?>> beanClasses = this.scan(packages);
    getBeanFactory().createBeans(beanClasses);
  }

  @Override
  public <T> T getBean(Class<T> beanType) {
    return getBeanFactory().getBean(beanType);
  }

  @Override
  public <T> T getBean(Class<T> beanType, String beanName) {
    return getBeanFactory().getBean(beanType, beanName);
  }

  @Override
  public <T> Map<String, T> getAllBeans(Class<T> beanType) {
    return getBeanFactory().getAllBeans(beanType);
  }

  @Override
  public Set<Class<?>> scan(String... packages) {
    Reflections reflections = new Reflections((Object) packages);
    return reflections.getTypesAnnotatedWith(Component.class);
  }

  @Override
  protected BeanFactory getBeanFactory() {
    return defaultBeanFactory;
  }
}
