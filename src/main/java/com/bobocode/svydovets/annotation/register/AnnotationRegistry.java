package com.bobocode.svydovets.annotation.register;

import com.bobocode.svydovets.annotation.context.AnnotationApplicationContext;

import java.util.Set;

/**
 * Provides contract to scan and register bean as a part of {@link AnnotationApplicationContext} structure.
 * It should perform scanning and registering beans to the Context map, provided by implementation.
 *
 * @see AnnotationApplicationContext
 */
public interface AnnotationRegistry {

    /**
     * Performs scanning of the given packages and returns set of found classes.
     *
     * @param packages package names to scan
     * @return set of classes (empty if no classes were found or invalid package name)
     */
    Set<Class<?>> scan(String... packages);

    /**
     * Registers provided class to some application context. Context is provided by the implementation.
     *
     * @param componentClasses classes to register, beans will be instantiated
     * @throws IllegalStateException if class was already registered in the context
     */
    void register(Class<?>... componentClasses);
}
