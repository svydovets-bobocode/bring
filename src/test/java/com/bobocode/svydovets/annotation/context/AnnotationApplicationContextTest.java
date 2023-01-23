package com.bobocode.svydovets.annotation.context;

import com.bobocode.svydovets.beans.Car;
import com.bobocode.svydovets.beans.CustomService;
import com.bobocode.svydovets.beans.MessageService;
import com.bobocode.svydovets.beans.PrinterService;
import com.bobocode.svydovets.support.ApplicationContext;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class AnnotationApplicationContextTest {

  private static ApplicationContext applicationContext;

  @BeforeAll
  static void setUp() {
    applicationContext = new AnnotationApplicationContext("com.bobocode.svydovets");
  }

  //todo: refactor test names
  @Test
  void test_whenGetBeanWithType_thenVerifyObjectBeanCreatedSuccessfully() {
    Car expected = new Car();

    Car bean = applicationContext.getBean(Car.class);

    assertBean(expected, bean);
  }

  @Test
  void test_whenGetBeanWithTypeAndComponentName_thenVerifyObjectBeanCreatedSuccessfully() {
    Car expected = new Car();

    Car bean = applicationContext.getBean("car_bean", Car.class);

    assertBean(expected, bean);
  }

  private static void assertBean(Car expected, Car bean) {
    assertNotNull(bean);
    assertEquals(bean.getClass(), expected.getClass());
    assertEquals(bean.getName(), expected.getName());
  }

  @Test
  void test_getBeanByTypeReturnsCorrectBean() {
    MessageService messageService = applicationContext.getBean(MessageService.class);
    assertEquals("Hello", messageService.getMessage());
  }

  @Test
  void getBeanByTypeWhenNoBeanPresentInContext() {
    assertThrows(RuntimeException.class, () -> applicationContext.getBean(List.class));
  }

  @Test
  void getBeanByTypeWhenNoUniqueBeanPresentInContext() {
    assertThrows(RuntimeException.class, () -> applicationContext.getBean(CustomService.class));
  }

  @Test
  void getBeanByByNameReturnsCorrectBean() {
    MessageService messageService = applicationContext.getBean("messageService", MessageService.class);
    assertEquals("Hello", messageService.getMessage());
  }

  @Test
  void getBeanByByNameAndSuperclassReturnsCorrectBean() {
    CustomService messageService = applicationContext.getBean("messageService", CustomService.class);
    assertEquals("Hello", ((MessageService) messageService).getMessage());
  }

//  @Test
  //todo: fix it
  void getBeanByNonPresentName() {
    assertThrows(RuntimeException.class, () -> applicationContext.getBean("customService", CustomService.class));
  }

  @Test
  void getAllBeansReturnsCorrectMap() {
    Map<String, CustomService> customServices = applicationContext.getAllBeans(CustomService.class);

    assertEquals(2, customServices.size());

    assertTrue(customServices.containsKey("messageService"));
    assertTrue(customServices.containsKey("printerService"));

    assertTrue(customServices.values().stream()
            .anyMatch(bean -> bean.getClass().isAssignableFrom(MessageService.class)));
    assertTrue(customServices.values().stream()
            .anyMatch(bean -> bean.getClass().isAssignableFrom(PrinterService.class)));
  }

}
