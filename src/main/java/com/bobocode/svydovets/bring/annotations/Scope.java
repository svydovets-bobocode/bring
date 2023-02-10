package com.bobocode.svydovets.bring.annotations;

import com.bobocode.svydovets.bring.register.BeanScope;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *  When used as a type-level annotation in conjunction with
 *  * {@link Component @Component},
 *  * {@code @Scope} indicates the name of a scope to use for instances of
 *  * the annotated type.
 *  *
 *  * <p>When used as a method-level annotation in conjunction with
 *  * {@link Bean @Bean}, {@code @Scope} indicates the name of a scope to use
 *  * for the instance returned from the method.
 *
 *  *
 *  * <p>In this context, <em>scope</em> means the lifecycle of an instance,
 *  * such as {@code SINGLETON}, {@code PROTOTYPE}, constants available in the {@link BeanScope}.
 *  *
 */

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Scope {
    BeanScope value() default BeanScope.SINGLETON;
}
