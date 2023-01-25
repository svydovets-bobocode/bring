package com.bobocode.svydovets.annotation.bean.factory;

import com.bobocode.svydovets.annotation.exception.NoSuchBeanException;
import com.bobocode.svydovets.annotation.exception.NoUniqueBeanException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class AnnotationBeanFactory implements BeanFactory {

    protected final Map<String, Object> rootContextMap = new ConcurrentHashMap<>();

    @Override
    public <T> T getBean(Class<T> beanType) {
        Map<String, T> matchingBeans = getAllBeans(beanType);
        if (matchingBeans.size() > 1) {
            throw new NoUniqueBeanException();
        }
        return matchingBeans.values().stream()
                .findAny()
                .orElseThrow(NoSuchBeanException::new);
    }

    @Override
    public <T> T getBean(String beanName, Class<T> beanType) {
        return rootContextMap.entrySet().stream()
                .filter(beanEntry -> beanName.equals(beanEntry.getKey()))
                .findAny()
                .map(Map.Entry::getValue)
                .map(beanType::cast)
                .orElseThrow(NoSuchBeanException::new);
    }

    @Override
    public <T> Map<String, T> getAllBeans(Class<T> beanType) {
        return rootContextMap.entrySet().stream()
                .filter(entry -> beanType.isAssignableFrom(entry.getValue().getClass()))
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> beanType.cast(entry.getValue())));
    }
}
