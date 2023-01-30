package com.bobocode.svydovets.annotation.context;

import com.bobocode.svydovets.annotation.annotations.Bean;
import com.bobocode.svydovets.annotation.annotations.Component;
import com.bobocode.svydovets.annotation.annotations.Configuration;
import com.bobocode.svydovets.annotation.exception.BeanException;

import com.bobocode.svydovets.beans.FooService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;

public class DefaultBeanNameResolverTest {

    private static BeanNameResolver beanNameResolver;

    @BeforeAll
    public static void setUp() {
        beanNameResolver = new DefaultBeanNameResolver();
    }

    @Test
    public void resolveComponentExplicitName() {
        String resolvedBeanName = beanNameResolver.resolveBeanName(ComponentMarkedExplicitName.class);
        String expectedName = ComponentMarkedExplicitName.class.getAnnotation(Component.class).value();
        assertEquals(expectedName, resolvedBeanName);
    }

    @Test
    public void resolveComponentClassName() {
        String resolvedBeanName = beanNameResolver.resolveBeanName(ComponentMarked.class);
        assertEquals("componentMarked", resolvedBeanName);
    }

    @Test
    public void resolveConfigurationExplicitName() {
        String resolvedBeanName = beanNameResolver.resolveBeanName(ConfigurationMarkedExplicitName.class);
        String expectedName = ConfigurationMarkedExplicitName.class.getAnnotation(Configuration.class).value();
        assertEquals(expectedName, resolvedBeanName);
    }

    @Test
    public void resolveConfigurationClassName() {
        String resolvedBeanName = beanNameResolver.resolveBeanName(ConfigurationMarked.class);
        assertEquals("configurationMarked", resolvedBeanName);
    }

    @Test
    public void resolveNonBeanName() {
        BeanException beanException =
                assertThrows(BeanException.class, () -> beanNameResolver.resolveBeanName(NonBean.class));

        assertThat(beanException.getMessage(), containsString("Provided type " + NonBean.class.getName()));
        assertThat(beanException.getMessage(), containsString("is neither marked as @Component nor @Configuration"));
    }

    @Test
    public void resolveBeanMethodExplicitName() throws NoSuchMethodException {
        Method fooServiceMethod = ConfigurationMarkedExplicitName.class.getMethod("fooService");
        String resolvedBeanName = beanNameResolver.resolveBeanName(fooServiceMethod);
        String expectedName = fooServiceMethod.getAnnotation(Bean.class).value();
        assertEquals(expectedName, resolvedBeanName);
    }

    @Test
    public void resolveBeanMethodName() throws NoSuchMethodException {
        Method fooServiceMethod = ConfigurationMarked.class.getMethod("fooService");
        String resolvedBeanName = beanNameResolver.resolveBeanName(fooServiceMethod);
        assertEquals("fooService", resolvedBeanName);
    }

    @Component("componentExplicit")
    private static class ComponentMarkedExplicitName {
    }

    @Component
    private static class ComponentMarked {
    }

    @Configuration("configurationExplicit")
    private static class ConfigurationMarkedExplicitName {
        @Bean("explicitFooService")
        public FooService fooService() {
            return new FooService();
        }
    }

    @Configuration
    private static class ConfigurationMarked {
        @Bean
        public FooService fooService() {
            return new FooService();
        }
    }

    private static class NonBean {
    }

}
