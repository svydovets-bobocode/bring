package com.bobocode.svydovets.annotation.register;

import lombok.Builder;
import lombok.Getter;

/**
 * The class represents a definition of a bean in a Bring-Svydovets application.
 * <p>
 * A `BeanDefinition` object contains meta information about bean
 */
@Builder
public class BeanDefinition {
    @Getter
    private Class<?> beanClass;

    private String beanName;

    @Getter
    private BeanScope scope;

    @Getter
    private boolean isPrimary;
}
