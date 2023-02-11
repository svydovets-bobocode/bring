package com.bobocode.svydovets.annotation.context;

import com.bobocode.svydovets.annotation.annotations.Bean;
import com.bobocode.svydovets.annotation.annotations.Component;
import com.bobocode.svydovets.annotation.annotations.Configuration;
import com.bobocode.svydovets.annotation.exception.BeanException;
import com.bobocode.svydovets.annotation.util.StringUtils;

import java.lang.reflect.Method;
import java.util.Optional;

public class DefaultBeanNameResolver implements BeanNameResolver {

    @Override
    public String resolveBeanName(Class<?> type) {
        String explicitName = Optional.ofNullable(type.getAnnotation(Component.class))
                .map(Component::value)
                .or(() -> Optional.ofNullable(type.getAnnotation(Configuration.class)).map(Configuration::value))
                .orElseThrow(() -> new BeanException(String.format(
                        "Provided type %s is neither marked as @Component nor @Configuration", type.getName())));
        return StringUtils.isBlank(explicitName) ? resolveTypeId(type) : explicitName;
    }

    private String resolveTypeId(Class<?> type) {
        String className = type.getSimpleName();
        return className.substring(0, 1).toLowerCase() + className.substring(1);
    }

    @Override
    public String resolveBeanName(Method method) {
        String explicitName = Optional.ofNullable(method.getAnnotation(Bean.class))
                .map(Bean::value)
                .orElseThrow(() -> new BeanException(
                        String.format("Provided method %s is not marked as @Bean", method.getName())));
        return StringUtils.isBlank(explicitName) ? method.getName() : explicitName;
    }

}
