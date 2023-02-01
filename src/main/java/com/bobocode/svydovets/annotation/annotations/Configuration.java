package com.bobocode.svydovets.annotation.annotations;

import com.bobocode.svydovets.annotation.context.BeanNameResolver;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used to mark class as a special configuration bean, in which it is able to declare
 * method-configured beans, marked by {@link Bean} annotation.
 * <p>
 * Marked classes are also created and stored in context, as {@link Component} ones.
 * <p>
 * Example:
 * <pre class="code">
 * &#064;Configuration
 * public class TestConfig {
 *
 *     &#064;AutoSvydovets
 *     private AutoSvydovetsDependency autoSvydovetsDependency;
 *
 *     &#064;Bean
 *     public FooService fooService() {
 *         FooService fooService = new FooService();
 *         fooService.setMessage("Foo");
 *         return fooService;
 *     }
 *
 *     &#064;Bean
 *     public FooBarService fooBarService() {
 *         FooBarService fooBarService = new FooBarService(fooService());
 *         fooBarService.setMessage("Bar");
 *         return fooBarService;
 *     }
 * }
 * </pre>
 *
 * @see Bean
 * @see Component
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Configuration {

    /**
     * Explicit name of the Bring bean. If no value is provided - name will be resolved by {@link BeanNameResolver}.
     *
     * @see BeanNameResolver
     */
    String value() default "";

    /**
     * Specify whether {@code @Bean} methods should get proxied in order to enforce
     * bean lifecycle behavior, e.g. to return shared singleton bean instances even
     * in case of direct {@code @Bean} method calls in user code.
     */
    boolean proxyBeanMethods() default true;
}
