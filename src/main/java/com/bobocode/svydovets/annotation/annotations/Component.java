package com.bobocode.svydovets.annotation.annotations;

import com.bobocode.svydovets.annotation.context.AnnotationApplicationContext;
import com.bobocode.svydovets.annotation.context.BeanNameResolver;
import com.bobocode.svydovets.annotation.register.AnnotationRegistry;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Base annotation to mark class as a Bring bean. If class is marked by it - context will
 * create a bean and put in into it`s map on the stage of package scanning.
 *
 * @see AnnotationApplicationContext
 * @see AnnotationRegistry
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Component {

    /**
     * Explicit name of the Bring bean. If no value is provided - name will be resolved by {@link BeanNameResolver}.
     *
     * @see BeanNameResolver
     */
    String value() default "";
}
