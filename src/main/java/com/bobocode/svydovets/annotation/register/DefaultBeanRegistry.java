package com.bobocode.svydovets.annotation.register;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class DefaultBeanRegistry implements BeanRegistry {

  private static final Map<String, Object> ROOT_CONTEXT = new ConcurrentHashMap<>();

  public DefaultBeanRegistry() {
  }

  @Override
  public void register(String beanName, Object bean) {
    synchronized (ROOT_CONTEXT) {
      Object oldObject = ROOT_CONTEXT.get(beanName);
      if (oldObject != null) {
        throw new IllegalStateException(
            "Could not register object [" + oldObject + "] under bean name '" + beanName
                + "': there is already object [" + oldObject + "] bound");
      } else {
        ROOT_CONTEXT.put(beanName, bean);
      }
    }
  }

  @Override
  public <T> T getSingleton(Class<T> beanType, String beanName) {
    Object beanObject = ROOT_CONTEXT.get(beanName);
    return beanType.cast(beanObject);
  }

  @Override
  public <T> Map<String, T> getAllSingletons(Class<T> beanType) {
    return ROOT_CONTEXT.entrySet().stream()
        .filter(entry -> beanType.isAssignableFrom(entry.getValue().getClass()))
        .collect(Collectors.toMap(Map.Entry::getKey, entry -> beanType.cast(entry.getValue())));
  }

  @Override
  public Map<String, Object> getRootContext() {
    return ROOT_CONTEXT;
  }

}
