package com.bobocode.svydovets.annotation.context;

import com.bobocode.svydovets.annotation.context.AnnotationApplicationContext;
import com.bobocode.svydovets.annotation.context.bean_with_same_name.Bean1;
import com.bobocode.svydovets.annotation.context.bean_with_same_name.Bean2;
import com.bobocode.svydovets.annotation.context.no_default_constructor.BeanWithNoDefaultConstructor;
import com.bobocode.svydovets.annotation.exception.BeanException;
import com.bobocode.svydovets.annotation.exception.NoSuchBeanException;
import com.bobocode.svydovets.annotation.exception.NoUniqueBeanException;
import com.bobocode.svydovets.autowiring.success.SuccessCustomService;
import com.bobocode.svydovets.autowiring.success.SuccessMessageServiceImpl;
import com.bobocode.svydovets.autowiring.success.SuccessPrinterServiceImpl;
import org.junit.jupiter.api.*;

import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.*;


@TestClassOrder(ClassOrderer.OrderAnnotation.class)
@DisplayName("RegisterBean test")
class RegisterBeanTest {

    @Test
    @Order(1)
    @DisplayName("Throw exception if no default constructor for bean")
    void throwsExceptionIfNoDefaultConstructor() {
        BeanException beanException = assertThrows(BeanException.class,
                () -> new AnnotationApplicationContext("com.bobocode.svydovets.annotation.context.no_default_constructor"));

        assertThat(beanException.getMessage(), containsString("No default constructor for " + BeanWithNoDefaultConstructor.class.getName()));
    }

    @Test
    @Order(2)
    @DisplayName("Throw exception if bans have same name")
    void throwsExceptionIfBeansHaveSameName() {
        BeanException beanException = assertThrows(BeanException.class,
                () -> new AnnotationApplicationContext("com.bobocode.svydovets.annotation.context.bean_with_same_name"));

        assertThat(beanException.getMessage(), containsString("Could not register object [" + Bean2.class.getName()));
        assertThat(beanException.getMessage(), containsString("under bean name 'bean1': there is already object [" + Bean1.class.getName()));
    }

}
