package com.bobocode.svydovets.annotation.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Annotation that can be used on a field, method, or constructor parameter to indicate a specific bean should be
 * autowired.
 *
 * <p>The bean is selected by its qualifier attribute, which is specified through the value parameter of this
 * annotation.
 *
 * <p>If a qualifier value is not specified, the default value is used.
 *
 */
@Target({ElementType.PARAMETER, ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Qualifier {
    String value() default "";
}
