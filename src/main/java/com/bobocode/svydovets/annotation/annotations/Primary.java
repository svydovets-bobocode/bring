package com.bobocode.svydovets.annotation.annotations;

import com.bobocode.svydovets.annotation.exception.NoUniqueBeanException;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used among with {@link AutoSvydovets} in order to qualify that marked bean class should be used
 * as a primary autowiring candidate, in order to avoid {@link NoUniqueBeanException}.
 *
 * @see AutoSvydovets
 * @see Qualifier
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Primary {
}
