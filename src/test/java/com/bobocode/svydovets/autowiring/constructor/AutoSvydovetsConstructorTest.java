package com.bobocode.svydovets.autowiring.constructor;

import com.bobocode.svydovets.annotation.context.AnnotationApplicationContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("AutoSvydovets constructor tests")
public class AutoSvydovetsConstructorTest {

    private AnnotationApplicationContext applicationContext;

    @BeforeEach
    void setUp() {
        applicationContext = new AnnotationApplicationContext("com.bobocode.svydovets.autowiring.constructor");
    }

    @Test
    @Order(1)
    @DisplayName("Bean dependencies is correctly set by the constructor-injection")
    void setBeanByTypeSettsCorrectBeanByQualifierIntoSetterShouldReturnSameBeanFromContext() {
        Service constructorInjectionBean = applicationContext.getBean(Service.class);
        assertNotNull(constructorInjectionBean);
        assertEquals("FooBar", constructorInjectionBean.getMessage());
    }
}
