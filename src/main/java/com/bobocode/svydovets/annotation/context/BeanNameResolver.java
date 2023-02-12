package com.bobocode.svydovets.annotation.context;

import java.lang.reflect.Method;

/**
 * Provides name resolving capabilities for class-based and method-based Bring beans.
 */
public interface BeanNameResolver {

    /**
     * Resolve bean name for provided class
     * @param type
     * @return Bean name
     */
    String resolveBeanName(Class<?> type);

    /**
     * Resolve bean name for provided method
     * @param method
     * @return Bean name
     */
    String resolveBeanName(Method method);

}
