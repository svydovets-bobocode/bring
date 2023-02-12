package com.bobocode.svydovets.annotation.context;

import com.bobocode.svydovets.annotation.exception.BeanException;
import com.bobocode.svydovets.annotation.exception.UnprocessableScanningBeanLocationException;
import com.bobocode.svydovets.beans.Account;
import com.bobocode.svydovets.beans.AdminAccount;
import com.bobocode.svydovets.beans.BarFormatter;
import com.bobocode.svydovets.beans.Car;
import com.bobocode.svydovets.beans.FooFormatter;
import com.bobocode.svydovets.beans.FooService;
import com.bobocode.svydovets.beans.MessageService;
import com.bobocode.svydovets.beans.PrinterService;
import com.bobocode.svydovets.beans.SimpleValueBean;
import com.bobocode.svydovets.beans.SuperAdminAccount;
import com.bobocode.svydovets.beans.subpackage.SubpackageComponent;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


class AnnotationApplicationContextTest {

    private static AnnotationApplicationContext applicationContext;

    @BeforeAll
    public static void setUp() {
        applicationContext = new AnnotationApplicationContext();
    }

    @Test
    void shouldFindClassesAnnotatedWithComponentAnnotation() {
        var result = applicationContext.scan("com.bobocode.svydovets.beans");
        assertNotNull(result);
        assertEquals(11, result.size());
        assertTrue(result.contains(Car.class));
        assertTrue(result.contains(PrinterService.class));
        assertTrue(result.contains(MessageService.class));
        assertTrue(result.contains(BarFormatter.class));
        assertTrue(result.contains(FooService.class));
        assertTrue(result.contains(FooFormatter.class));
        assertTrue(result.contains(SubpackageComponent.class));
    }

    @Test
    void shouldFindClassesAnnotatedWithComponentAnnotationInSubpackages() {
        var result = applicationContext.scan("com.bobocode.svydovets.beans");
        assertNotNull(result);
        assertTrue(result.contains(SubpackageComponent.class));
    }

    @Test
    void shouldNotFailIfProvidedPackageDoesNotExist() {
        var result = applicationContext.scan("foo.bar");
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void shouldThrowIfPackagesArgumentIsNull() {
        String[] packages = null;
        UnprocessableScanningBeanLocationException thrown = assertThrows(UnprocessableScanningBeanLocationException.class,
                () -> applicationContext.scan(packages));
        assertEquals("Packages to scan argument can not be null", thrown.getMessage());
    }

    @Test
    void shouldThrowIfItemInPackagesArgumentIsNull() {
        String packageName = null;
        UnprocessableScanningBeanLocationException thrown = assertThrows(UnprocessableScanningBeanLocationException.class,
                () -> applicationContext.scan(packageName));
        assertEquals("Package to scan argument can not be null or empty", thrown.getMessage());
    }

    @Test
    void shouldThrowIfItemInPackagesArgumentIsEmpty() {
        UnprocessableScanningBeanLocationException thrown = assertThrows(UnprocessableScanningBeanLocationException.class,
                () -> applicationContext.scan(""));
        assertEquals("Package to scan argument can not be null or empty", thrown.getMessage());
    }

    @Test
    void shouldThrowIfItemInPackagesArgumentIsBlank() {
        UnprocessableScanningBeanLocationException thrown = assertThrows(UnprocessableScanningBeanLocationException.class,
                () -> applicationContext.scan(" "));
        assertEquals("Package to scan argument can not be null or empty", thrown.getMessage());
    }

    @Test
    void shouldRegisterBeanSuccessfully() {
        applicationContext = new AnnotationApplicationContext("com.bobocode.svydovets.beans");
        var result = applicationContext.getBean(Car.class);
        assertNotNull(result);
        assertEquals(Car.class, result.getClass());
    }

    @Test
    void shouldThrowIfRegisterAlreadyExistingBean() {
        applicationContext = new AnnotationApplicationContext("com.bobocode.svydovets.beans");
        assertThrows(BeanException.class, () -> applicationContext.register(Car.class));
    }

    @Test
    void shouldThrowIfRegisterBeanWithoutDefaultConstructor() {
        assertThrows(BeanException.class,
                () -> applicationContext = new AnnotationApplicationContext("com.bobocode.svydovets.invalidbeans.defaultconstructor"));
    }

    @Test
    void shouldThrowIfRegisterBeanFromAbstractClass() {
        assertThrows(BeanException.class,
                () -> applicationContext = new AnnotationApplicationContext("com.bobocode.svydovets.invalidbeans.abstractbean"));
    }

    @Test
    void verifySimpleValueAnnotationIsSet() {
        applicationContext = new AnnotationApplicationContext("com.bobocode.svydovets.beans");
        var valueBean = applicationContext.getBean(SimpleValueBean.class);
        assertNotNull(valueBean);
        assertEquals("simpleAccountId", valueBean.accountId);
    }

    @Test
    void verifyValueAnnotationIsSet() {
        applicationContext = new AnnotationApplicationContext("com.bobocode.svydovets.beans");
        var accountBean = applicationContext.getBean(Account.class);
        assertNotNull(accountBean);
        assertEquals("testValue", accountBean.accountId);
    }

    @Test
    void verifyLongValueAnnotationIsSet() {
        applicationContext = new AnnotationApplicationContext("com.bobocode.svydovets.beans");
        var accountBean = applicationContext.getBean(AdminAccount.class);
        assertNotNull(accountBean);
        assertEquals(123L, accountBean.accountId);
    }

    @Test
    void verifyIntegerValueAnnotationIsSet() {
        applicationContext = new AnnotationApplicationContext("com.bobocode.svydovets.beans");
        var accountBean = applicationContext.getBean(AdminAccount.class);
        assertNotNull(accountBean);
        assertEquals(2023, accountBean.year);
    }

    @Test
    void verifyDoubleValueAnnotationIsSet() {
        applicationContext = new AnnotationApplicationContext("com.bobocode.svydovets.beans");
        var accountBean = applicationContext.getBean(AdminAccount.class);
        assertNotNull(accountBean);
        assertEquals(9999.99, accountBean.adminSalary);
    }

    @Test
    void verifyListValueAnnotationIsSet() {
        applicationContext = new AnnotationApplicationContext("com.bobocode.svydovets.beans");
        var accountBean = applicationContext.getBean(SuperAdminAccount.class);
        assertNotNull(accountBean);
        assertEquals(List.of("User", "Admin", "SuperAdmin"), accountBean.roles);
    }
    @Test
    void shouldThrowIfValueTypeIsNotCompatible() {
        assertThrows(BeanException.class,
                () -> applicationContext = new AnnotationApplicationContext("com.bobocode.svydovets.properties.invalidvalue"));
    }
}
