package com.bobocode.svydovets.annotation.register;

import lombok.Builder;

@Builder
public class BeanDefinition {
    private Class<?> beanClass;

    private String beanName;

    private String scope;

    private boolean isLazy;

    private boolean isPrimary;

    private String qualifier;
}
