package com.bobocode.svydovets.bpp;

import com.bobocode.svydovets.bring.context.AnnotationApplicationContext;
import com.bobocode.svydovets.bring.exception.BeanException;
import com.bobocode.svydovets.bpp.success_change_field.BppComponent1WithFieldAnnotation;
import com.bobocode.svydovets.bpp.success_change_field.BppComponent2WithoutFieldAnnotation;
import org.junit.jupiter.api.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@TestClassOrder(ClassOrderer.OrderAnnotation.class)
@DisplayName("BeanPostProcessorTest test")
class BeanPostProcessorTest {

    @Nested
    @Order(1)
    @DisplayName("1. Context with null return bpp test")
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    class BppReturnsNullTest {
        @Test
        @Order(1)
        @DisplayName("Throws BeanException when registering bean with null bpp")
        void throwsBeanexceptionWhenRegisteringBeanWithNullBpp() {
            BeanException beanException = assertThrows(BeanException.class, () -> new AnnotationApplicationContext("com.bobocode.svydovets.bpp.returns_null"));
            assertEquals("Bean instance must not be null. (Check BeanPostProcessor not to return null)", beanException.getMessage());
        }
    }

    @Nested
    @Order(2)
    @DisplayName("2. Bean post processor success test")
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    class BeanPostProcessorSuccessTest {

        @Test
        @Order(1)
        @DisplayName("Field marked with annotation returns correctly value")
        void bppIsHandledCorrectly() {
            AnnotationApplicationContext applicationContext = new AnnotationApplicationContext("com.bobocode.svydovets.bpp.success_change_field");
            BppComponent1WithFieldAnnotation bean1 = applicationContext.getBean(BppComponent1WithFieldAnnotation.class);
            BppComponent2WithoutFieldAnnotation bean2 = applicationContext.getBean(BppComponent2WithoutFieldAnnotation.class);
            assertNull(bean1.getString());
            assertEquals("not null value",bean2.getString());
        }
    }
}
