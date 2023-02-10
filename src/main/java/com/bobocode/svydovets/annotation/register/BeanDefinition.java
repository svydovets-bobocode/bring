package com.bobocode.svydovets.annotation.register;

import lombok.Builder;
import lombok.Data;

/**
 * The class represents a definition of a bean in a Bring-Svydovets application.
 *
 * A `BeanDefinition` object contains meta information about bean
 */
@Builder
@Data
public class BeanDefinition {
    private Class<?> beanClass;

    private String beanName;

    private BeanScope scope;

    private boolean isPrimary;
}
