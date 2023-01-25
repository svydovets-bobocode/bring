package com.bobocode.svydovets.annotation.annotations;

import com.bobocode.svydovets.annotation.bean.processor.AutoSvydovetsBeanPostProcessor;
import com.bobocode.svydovets.annotation.exception.NoUniqueBeanException;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Base annotation to perform injection of some dependency. Can be set on fields, setters and constructors.
 * <p>
 * Get`s processed by {@link AutoSvydovetsBeanPostProcessor}. To avoid @{@link NoUniqueBeanException} should be
 * used among with either {@link Qualifier} or {@link Primary} annotations.
 *
 * @see AutoSvydovetsBeanPostProcessor
 * @see Qualifier
 * @see Primary
 * @see NoUniqueBeanException
 */
@Target({ElementType.CONSTRUCTOR, ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoSvydovets {

}
