package com.bobocode.svydovets.annotation.context;

import com.bobocode.svydovets.annotation.exception.BeanException;
import com.bobocode.svydovets.annotation.exception.UnprocessableScanningBeanLocationException;
import com.bobocode.svydovets.beans.BarFormatter;
import com.bobocode.svydovets.beans.Car;
import com.bobocode.svydovets.beans.FooFormatter;
import com.bobocode.svydovets.beans.FooService;
import com.bobocode.svydovets.beans.MessageService;
import com.bobocode.svydovets.beans.PrinterService;
import com.bobocode.svydovets.beans.subpackage.SubpackageComponent;
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
    public void shouldFindClassesAnnotatedWithComponentAnnotation() {
        var result = applicationContext.scan("com.bobocode.svydovets.beans");
        assertNotNull(result);
        assertEquals(7, result.size());
        assertTrue(result.contains(Car.class));
        assertTrue(result.contains(PrinterService.class));
        assertTrue(result.contains(MessageService.class));
        assertTrue(result.contains(BarFormatter.class));
        assertTrue(result.contains(FooService.class));
        assertTrue(result.contains(FooFormatter.class));
        assertTrue(result.contains(SubpackageComponent.class));
    }

    @Test
    public void shouldFindClassesAnnotatedWithComponentAnnotationInSubpackages() {
        var result = applicationContext.scan("com.bobocode.svydovets.beans");
        assertNotNull(result);
        assertTrue(result.contains(SubpackageComponent.class));
    }

    @Test
    public void shouldNotFailIfProvidedPackageDoesNotExist() {
        var result = applicationContext.scan("foo.bar");
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    public void shouldThrowIfPackagesArgumentIsNull() {
        String[] packages = null;
        UnprocessableScanningBeanLocationException thrown = assertThrows(UnprocessableScanningBeanLocationException.class,
                () -> applicationContext.scan(packages));
        assertEquals("Packages to scan argument can not be null", thrown.getMessage());
    }

    @Test
    public void shouldThrowIfItemInPackagesArgumentIsNull() {
        String packageName = null;
        UnprocessableScanningBeanLocationException thrown = assertThrows(UnprocessableScanningBeanLocationException.class,
                () -> applicationContext.scan(packageName));
        assertEquals("Package to scan argument can not be null", thrown.getMessage());
    }

    @Test
    public void shouldThrowIfItemInPackagesArgumentIsBlank() {
        UnprocessableScanningBeanLocationException thrown = assertThrows(UnprocessableScanningBeanLocationException.class,
                () -> applicationContext.scan(""));
        assertEquals("Package to scan can not be empty", thrown.getMessage());
    }

    @Test
    public void shouldRegisterBeanSuccessfully() {
        var result = applicationContext.getBean(Car.class);
        assertNotNull(result);
        assertEquals(Car.class, result.getClass());
    }

    @Test
    public void shouldThrowIfRegisterAlreadyExistingBean() {
        assertThrows(BeanException.class, () -> applicationContext.register(Car.class));
    }

    @Test
    public void shouldThrowIfRegisterBeanWithoutDefaultConstructor() {
        assertThrows(BeanException.class,
                () -> applicationContext = new AnnotationApplicationContext("com.bobocode.svydovets.invalidbeans.defaultconstructor"));
    }

    @Test
    public void shouldThrowIfRegisterBeanFromAbstractClass() {
        assertThrows(BeanException.class,
                () -> applicationContext = new AnnotationApplicationContext("com.bobocode.svydovets.invalidbeans.abstractbean"));
    }
}
