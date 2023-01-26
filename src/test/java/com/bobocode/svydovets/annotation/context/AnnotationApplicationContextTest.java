package com.bobocode.svydovets.annotation.context;

import com.bobocode.svydovets.annotation.exception.BeanException;
import com.bobocode.svydovets.beans.BarFormatter;
import com.bobocode.svydovets.beans.Car;
import com.bobocode.svydovets.beans.FooFormatter;
import com.bobocode.svydovets.beans.FooService;
import com.bobocode.svydovets.beans.MessageService;
import com.bobocode.svydovets.beans.PrinterService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


@ExtendWith(MockitoExtension.class)
class AnnotationApplicationContextTest {

    private static AnnotationApplicationContext applicationContext;

    @BeforeAll
    public static void setUp() {
        applicationContext = new AnnotationApplicationContext("com.bobocode.svydovets.beans");
    }

    @Test
    public void scanPackage(){
        var result = applicationContext.scan("com.bobocode.svydovets.beans");
        assertNotNull(result);
        assertEquals(6, result.size());
        assertTrue(result.contains(Car.class));
        assertTrue(result.contains(PrinterService.class));
        assertTrue(result.contains(MessageService.class));
        assertTrue(result.contains(BarFormatter.class));
        assertTrue(result.contains(FooService.class));
        assertTrue(result.contains(FooFormatter.class));
    }

    @Test
    public void registerBeanSuccessful() {
        var result = applicationContext.getBean(Car.class);
        assertNotNull(result);
        assertEquals(Car.class, result.getClass());
    }

    @Test
    public void registerAlreadyExistingBeanThrowsException() {
        assertThrows(BeanException.class, () -> applicationContext.register(Car.class));
    }

    @Test
    public void registerBeanWithoutDefaultConstructorThrowsException() {
        assertThrows(BeanException.class, () -> applicationContext = new AnnotationApplicationContext("com.bobocode.svydovets.invalidbeans.defaultconstructor"));
    }

    @Test
    public void registerBeanFromAbstractClassThrowsException() {
        assertThrows(BeanException.class, () -> applicationContext = new AnnotationApplicationContext("com.bobocode.svydovets.invalidbeans.abstractbean"));
    }

}
