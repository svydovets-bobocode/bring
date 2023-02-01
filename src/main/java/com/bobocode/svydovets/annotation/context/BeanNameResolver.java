package com.bobocode.svydovets.annotation.context;

import java.lang.reflect.Method;

/**
 * Provides name resolving capabilities for class-based and method-based Bring beans.
 */
public interface BeanNameResolver {

    String resolveBeanName(Class<?> type);

    String resolveBeanName(Method method);

}
