package com.bobocode.svydovets.annotation.context;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.bobocode.svydovets.beans.Car;
import com.bobocode.svydovets.support.ApplicationContext;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AnnotationApplicationContextTest {

  private static ApplicationContext applicationContext;

  @BeforeAll
  static void setUp() {
    applicationContext = new AnnotationApplicationContext("com.bobocode.svydovets");
  }

  @Test
  void test_whenGetBeanWithType_thenVerifyObjectBeanCreatedSuccessfully() {
    Car expected = new Car();

    Car bean = applicationContext.getBean(Car.class);

    assertBean(expected, bean);
  }

  @Test
  void test_whenGetBeanWithTypeAndComponentName_thenVerifyObjectBeanCreatedSuccessfully() {
    Car expected = new Car();

    Car bean = applicationContext.getBean(Car.class, "car_bean");

    assertBean(expected, bean);
  }

  private static void assertBean(Car expected, Car bean) {
    assertNotNull(bean);
    assertEquals(bean.getClass(), expected.getClass());
    assertEquals(bean.getName(), expected.getName());
  }

}