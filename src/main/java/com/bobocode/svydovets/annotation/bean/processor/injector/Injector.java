package com.bobocode.svydovets.annotation.bean.processor.injector;

import java.lang.reflect.AccessibleObject;

/**
 * Interface uses for all dependency injectors.
 * @param <T>
 */
public interface Injector<T extends AccessibleObject> {

    /**
     * Injects bean into dependency
     * @param beanObject
     */
    void injectDependency(Object beanObject);
}
