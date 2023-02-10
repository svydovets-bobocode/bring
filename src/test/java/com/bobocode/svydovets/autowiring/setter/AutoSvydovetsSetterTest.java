package com.bobocode.svydovets.autowiring.setter;

import com.bobocode.svydovets.bring.context.AnnotationApplicationContext;
import com.bobocode.svydovets.autowiring.setter.invalid.NonSetterMethod;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;


@DisplayName("AutoSvydovets setter qualifier tests")
class AutoSvydovetsSetterTest {

    private AnnotationApplicationContext applicationContext;

    @BeforeEach
    void setUp() {
        applicationContext = new AnnotationApplicationContext("com.bobocode.svydovets.autowiring.setter");
    }

    @Test
    @Order(1)
    @DisplayName("Bean is correctly set from the context by it`s type and qualified name")
    void setBeanByTypeSettsCorrectBeanByQualifierIntoSetterShouldReturnSameBeanFromContext() {
        SetterSuccessPrinterServiceImpl printerService = applicationContext.getBean(SetterSuccessPrinterServiceImpl.class);

        SetterSuccessMessageService1Impl messageService = applicationContext.getBean(SetterSuccessMessageService1Impl.class);
        assertEquals(printerService.getMessageService(), messageService);
    }

    @Test
    @Order(2)
    @DisplayName("Bean is not set from the context. No annotation on the setter")
    void noAnnotationOnTheSetterShouldReturnNullInGetter() {
        SetterSuccessMessageService2Impl messageService2 = applicationContext.getBean(SetterSuccessMessageService2Impl.class);

        assertNull(messageService2.getPrinterService());
    }

    @Test
    @Order(3)
    @DisplayName("Bean with two setter params")
    void setterWithTwoParamsShouldReturnNullInGetter() {
        SetterSuccessMessageService2Impl messageService2 = applicationContext.getBean(SetterSuccessMessageService2Impl.class);

        assertNull(messageService2.getStrings());
    }

    @Test
    @Order(4)
    @DisplayName("Bean is correctly set from the context by it`s type")
    void setBeanByTypeSettsCorrectBeanIntoSetterShouldReturnSameBeanFromContext() {
        SetterSuccessMessageService1Impl messageService = applicationContext.getBean(SetterSuccessMessageService1Impl.class);

        SetterSuccessPrinterServiceImpl printerService = applicationContext.getBean(SetterSuccessPrinterServiceImpl.class);

        assertEquals(messageService.getPrinterService(), printerService);
    }

    @Test
    @Order(5)
    @DisplayName("Dependency will not be injected if method name is not started with 'set'")
    void dependencyIsNotInjectedIntoNonSetterMethod() {
        applicationContext = new AnnotationApplicationContext("com.bobocode.svydovets.autowiring.setter.invalid");
        NonSetterMethod bean = applicationContext.getBean(NonSetterMethod.class);
        assertNull(bean.getDependency());
    }
}
