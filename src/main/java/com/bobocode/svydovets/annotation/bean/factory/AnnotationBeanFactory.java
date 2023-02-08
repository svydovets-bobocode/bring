package com.bobocode.svydovets.annotation.bean.factory;

import com.bobocode.svydovets.annotation.context.AnnotationApplicationContext;
import com.bobocode.svydovets.annotation.exception.NoSuchBeanException;
import com.bobocode.svydovets.annotation.exception.NoUniqueBeanException;
import com.bobocode.svydovets.annotation.register.BeanDefinition;
import com.bobocode.svydovets.annotation.register.BeanScope;
import com.bobocode.svydovets.annotation.util.ObjectCloneUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.stream.Collectors.toMap;

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
            var bean = checkForPrimary(matchingBeans, beanType);

            if (bean != null) {
                return bean;
            }

            String foundBeans = String.join(", ", matchingBeans.keySet());
            throw new NoUniqueBeanException(beanType.getName(), matchingBeans.size(), foundBeans);
        }
        return matchingBeans.values().stream()
                .findAny()
                .orElseThrow(() -> new NoSuchBeanException(beanType.getName()));
    }

    private <T> T checkForPrimary(Map<String, T> matchingBeans, Class<T> beanType) {
        T result = null;
        int primaryAnnotationCount = 0;
        for (String beanName : matchingBeans.keySet()) {
            var beanDefinition = beanDefinitionMap.get(beanName);
            if (beanDefinition.isPrimary()) {
                primaryAnnotationCount++;
                if (primaryAnnotationCount > 1) {
                    throw new NoUniqueBeanException(
                            beanType.getName(),
                            matchingBeans.keySet().toString()
                    );
                }
                result = matchingBeans.get(beanName);
            }
        }
        return result;
    }

    @Override
    public <T> T getBean(String beanName, Class<T> beanType) {
        return Optional.ofNullable(getBeanForScope(beanName, rootContextMap.get(beanName)))
                .map(beanType::cast)
                .orElseThrow(() -> new NoSuchBeanException("No such bean with type " + beanType.getName()));
    }

    @Override
    public <T> Map<String, T> getAllBeans(Class<T> beanType) {
        return rootContextMap.entrySet().stream()
                .filter(entry -> beanType.isAssignableFrom(entry.getValue().getClass()))
                .collect(toMap(Map.Entry::getKey, entry -> getBeanForScope(entry, beanType)));
    }

    @Override
    public <T> List<T> getBeansByType(Class<T> beanType) {
        return getAllBeans(beanType).values()
                .stream().toList();
    }

    public BeanDefinition getBeanDefinition(String beanName) {
        return beanDefinitionMap.get(beanName);
    }

    public Set<String> getBeanDefinitionNames() {
        return beanDefinitionMap.keySet();
    }

    private  <T> T getBeanForScope(Map.Entry<String, Object> entry, Class<T> beanType) {
        var beanDefinition = beanDefinitionMap.get(entry.getKey());
        if (beanDefinition.getScope().equals(BeanScope.PROTOTYPE)) {
            return ObjectCloneUtils.deepCopy(
                    beanType.cast(entry.getValue()), (Class<? extends T>) beanDefinition.getBeanClass()
            );
        }
        return beanType.cast(entry.getValue());
    }

    private <T> T getBeanForScope(String beanName, T bean) {
        var beanDefinition = Optional.ofNullable(beanDefinitionMap.get(beanName))
                .orElseThrow(() -> new NoSuchBeanException("No such bean with type " + beanName));
        if (beanDefinition.getScope().equals(BeanScope.PROTOTYPE)) {
            return ObjectCloneUtils.deepCopy(bean, (Class<? extends T>) beanDefinition.getBeanClass());
        }
        return bean;
    }

}
