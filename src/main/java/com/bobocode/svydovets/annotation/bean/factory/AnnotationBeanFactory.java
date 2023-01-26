package com.bobocode.svydovets.annotation.bean.factory;

import com.bobocode.svydovets.annotation.context.AnnotationApplicationContext;
import com.bobocode.svydovets.annotation.exception.NoSuchBeanException;
import com.bobocode.svydovets.annotation.exception.NoUniqueBeanException;
import com.bobocode.svydovets.annotation.register.BeanDefinition;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * {@link BeanFactory} that is a part of {@link AnnotationApplicationContext} structure.
 *
 * @see BeanFactory
 * @see AnnotationApplicationContext
 */
public class AnnotationBeanFactory implements BeanFactory {

    protected final Map<String, Object> rootContextMap = new ConcurrentHashMap<>();
    protected final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

    @Override
    public <T> T getBean(Class<T> beanType) {
        Map<String, T> matchingBeans = getAllBeans(beanType);
        if (matchingBeans.size() > 1) {
            var bean = checkForPrimary(matchingBeans);
            if (Objects.nonNull(bean)) {
                return bean;
            }
            String foundBeans = String.join(", ", matchingBeans.keySet());
            throw new NoUniqueBeanException(beanType.getName(), matchingBeans.size(), foundBeans);
        }
        return matchingBeans.values().stream()
                .findAny()
                .orElseThrow(() -> new NoSuchBeanException(beanType.getName()));
    }

    private <T> T checkForPrimary(Map<String, T> matchingBeans) {
        T result = null;
        for (String beanName : matchingBeans.keySet()) {
            var beanDefinition = beanDefinitionMap.get(beanName);
            if (beanDefinition.isPrimary()) {
                result = matchingBeans.get(beanName);
            }
        }
        return result;
    }

    @Override
    public <T> T getBean(String beanName, Class<T> beanType) {
        return rootContextMap.entrySet().stream()
                .filter(beanEntry -> beanName.equals(beanEntry.getKey()))
                .findAny()
                .map(Map.Entry::getValue)
                .map(beanType::cast)
                .orElseThrow(() -> new NoSuchBeanException(beanType.getName()));
    }

    @Override
    public <T> Map<String, T> getAllBeans(Class<T> beanType) {
        return rootContextMap.entrySet().stream()
                .filter(entry -> beanType.isAssignableFrom(entry.getValue().getClass()))
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> beanType.cast(entry.getValue())));
    }
}
