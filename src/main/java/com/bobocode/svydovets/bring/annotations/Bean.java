package com.bobocode.svydovets.bring.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//todo: integrate it with @Primary
//todo: integrate it with @Qualifier
/**
 * Used among with {@link Configuration} in order to create beans by method-based approach.
 *
 * @see Configuration
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Bean {

    /**
     * The name of this bean. If left unspecified, the name of the bean is the name of the annotated method.
     * If specified, the method name is ignored.
     */
    String value() default "";
}
