package com.bobocode.svydovets.annotation.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//todo: add Java docs
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Bean {

    //todo: refactor it
    /**
     * The name of this bean, or if several names, a primary bean name plus aliases.
     * <p>If left unspecified, the name of the bean is the name of the annotated method.
     * If specified, the method name is ignored.
     * <p>The bean name and aliases may also be configured via the {@link #value}
     * attribute if no other attributes are declared.
     * @see #value
     */
    String value() default "";
}
