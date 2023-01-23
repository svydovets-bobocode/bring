package com.bobocode.svydovets.annotation.register;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

//todo: we decided that bean registry only needs registerBean() and getRootContext()
public class DefaultBeanRegistry implements BeanRegistry {

    private static final Map<String, Object> rootContextMap = new ConcurrentHashMap<>();

    public DefaultBeanRegistry() {
    }

    @Override
    public void registerBean(String beanName, Object bean) {
        Object oldObject = rootContextMap.get(beanName);
        if (oldObject != null) {
            throw new IllegalStateException(
                    "Could not register object [" + oldObject + "] under bean name '" + beanName
                            + "': there is already object [" + oldObject + "] bound");
        }
        rootContextMap.put(beanName, bean);
    }

    @Override
    public Map<String, Object> getRootContext() {
        return rootContextMap;
    }

    //todo:remove it from parent interface and perform refactoring
    @Override
    public <T> T getSingleton(String beanName, Class<T> beanType) {
        return beanType.cast(rootContextMap.get(beanName));
    }

    @Override
    public <T> T getSingleton(Class<T> beanType) {
        Map<String, T> allBeans = getAllSingletons(beanType);
        if (allBeans.size() > 1) {
            throw new RuntimeException("not unique bean");
        }
        return allBeans.values().stream().findAny().orElseThrow();
    }

    @Override
    public <T> Map<String, T> getAllSingletons(Class<T> beanType) {
        return rootContextMap.entrySet().stream()
                .filter(entry -> beanType.isAssignableFrom(entry.getValue().getClass()))
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> beanType.cast(entry.getValue())));
    }

}
