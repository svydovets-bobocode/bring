package com.bobocode.svydovets.annotation.bean.factory;

import com.bobocode.svydovets.annotation.register.BeanDefinition;
import com.bobocode.svydovets.annotation.register.BeanScope;
import com.bobocode.svydovets.beans.Car;
import com.bobocode.svydovets.beans.CustomService;
import com.bobocode.svydovets.beans.MessageService;
import com.bobocode.svydovets.beans.PrinterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class AnnotationBeanFactoryTest {

    private final AnnotationBeanFactory annotationBeanFactory = new AnnotationBeanFactory();

    @BeforeEach
    void setUp() {
        initContext(annotationBeanFactory.rootContextMap);
        initBeanDefinition(annotationBeanFactory.beanDefinitionMap);
    }

    @Test
    void getSimpleBeanByTypeReturnsCorrectObject() {
        Car expected = new Car();
        Car bean = annotationBeanFactory.getBean(Car.class);
        assertBean(expected, bean);
    }

    @Test
    void getSimpleBeanByExplicitNameReturnsCorrectObject() {
        Car expected = new Car();
        Car bean = annotationBeanFactory.getBean("car", Car.class);
        assertBean(expected, bean);
    }

    private void assertBean(Car expected, Car bean) {
        assertNotNull(bean);
        assertEquals(bean.getClass(), expected.getClass());
        assertEquals(bean.getName(), expected.getName());
    }

    @Test
    void getBeanByTypeReturnsCorrectBean() {
        MessageService messageService = annotationBeanFactory.getBean(MessageService.class);
        assertEquals("Hello", messageService.sayMessage());
    }

    @Test
    void getBeanByTypeWhenNoBeanPresentInContext() {
        assertThrows(RuntimeException.class, () -> annotationBeanFactory.getBean(List.class));
    }

    @Test
    void getBeanByTypeWhenNoUniqueBeanPresentInContext() {
        assertThrows(RuntimeException.class, () -> annotationBeanFactory.getBean(CustomService.class));
    }

    @Test
    void getBeanByByNameReturnsCorrectBean() {
        MessageService messageService = annotationBeanFactory.getBean("messageService", MessageService.class);
        assertEquals("Hello", messageService.sayMessage());
    }

    @Test
    void getBeanByByNameAndSuperclassReturnsCorrectBean() {
        CustomService messageService = annotationBeanFactory.getBean("messageService", CustomService.class);
        assertEquals("Hello", ((MessageService) messageService).sayMessage());
    }

    @Test
    void getBeanByNonPresentName() {
        assertThrows(RuntimeException.class, () -> annotationBeanFactory.getBean("customService", CustomService.class));
    }

    @Test
    void getBeanAnnotatedWithPrototypeScope() {
        CustomService firstBean = annotationBeanFactory.getBean("messageService", CustomService.class);
        CustomService secondBean = annotationBeanFactory.getBean("messageService", CustomService.class);
        assertFalse(firstBean == secondBean);
    }

    @Test
    void getAllBeansReturnsCorrectMap() {
        Map<String, CustomService> customServices = annotationBeanFactory.getAllBeans(CustomService.class);

        assertEquals(2, customServices.size());

        assertTrue(customServices.containsKey("messageService"));
        assertTrue(customServices.containsKey("printerService"));

        assertTrue(customServices.values().stream()
                .anyMatch(bean -> bean.getClass().isAssignableFrom(MessageService.class)));
        assertTrue(customServices.values().stream()
                .anyMatch(bean -> bean.getClass().isAssignableFrom(PrinterService.class)));
    }

    private void initContext(Map<String, Object> rootContextMap) {
        rootContextMap.put("car", new Car());
        rootContextMap.put("messageService", new MessageService());
        rootContextMap.put("printerService", new PrinterService());
    }

    private void initBeanDefinition(Map<String, BeanDefinition> beanDefinitionMap) {
        beanDefinitionMap.put("car", BeanDefinition.builder()
                .beanName("car")
                .beanClass(Car.class)
                .isPrimary(false)
                .scope(BeanScope.SINGLETON)
                .build());
        beanDefinitionMap.put("messageService", BeanDefinition.builder()
                .beanName("messageService")
                .beanClass(MessageService.class)
                .isPrimary(false)
                .scope(BeanScope.PROTOTYPE)
                .build());
        beanDefinitionMap.put("printerService", BeanDefinition.builder()
                .beanName("printerService")
                .beanClass(PrinterService.class)
                .isPrimary(false)
                .scope(BeanScope.SINGLETON)
                .build());
    }
}