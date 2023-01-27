package com.bobocode.svydovets.autowiring;

import com.bobocode.svydovets.annotation.context.AnnotationApplicationContext;
import com.bobocode.svydovets.annotation.exception.NoSuchBeanException;
import com.bobocode.svydovets.annotation.exception.NoUniqueBeanException;
import com.bobocode.svydovets.autowiring.nouniquebean.primaryduplicate.InvalidAnnotationService;
import com.bobocode.svydovets.autowiring.success.AnnotationService;
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
@DisplayName("AnnotationConfigApplicationContext test")
class AnnotationApplicationContextTest {

    @Nested
    @Order(1)
    @DisplayName("1. Beans retrieving test")
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    class BeansRetrievingTest {

        private AnnotationApplicationContext applicationContext;

        @BeforeEach
        void setUp() {
            applicationContext = new AnnotationApplicationContext("com.bobocode.svydovets.autowiring.success");
        }

        @Test
        @Order(1)
        @DisplayName("Bean is correctly retrieved from the context by it`s type")
        void getBeanByTypeReturnsCorrectBean() {
            SuccessMessageServiceImpl messageService = new AnnotationApplicationContext("com.bobocode.svydovets.autowiring.success").getBean(SuccessMessageServiceImpl.class);
            assertEquals("Hello", messageService.getMessage());
        }

        @Test
        @Order(2)
        @DisplayName("NoSuchBeanException is thrown when there is no bean in context")
        void getBeanByTypeWhenNoBeanPresentInContext() {
            assertThrows(NoSuchBeanException.class, () -> applicationContext.getBean(List.class));
        }

        @Test
        @Order(3)
        @DisplayName("NoUniqueBeanException is thrown when there is no unique bean in context")
        void getBeanByTypeWhenNoUniqueBeanPresentInContext() {
            assertThrows(NoUniqueBeanException.class, () -> applicationContext.getBean(SuccessCustomService.class));
        }

        @Test
        @Order(4)
        @DisplayName("Bean is correctly retrieved from the context by it`s name")
        void getBeanByByNameReturnsCorrectBean() {
            SuccessMessageServiceImpl messageService = applicationContext.getBean("successMessageServiceImpl", SuccessMessageServiceImpl.class);
            assertEquals("Hello", messageService.getMessage());
        }

        @Test
        @Order(5)
        @DisplayName("Bean is correctly retrieved from the context by it`s explicitly provided name")
        void getBeanByByExplicitNameReturnsCorrectBean() {
            SuccessPrinterServiceImpl printerService = applicationContext.getBean("printer-bean", SuccessPrinterServiceImpl.class);
            assertNotNull(printerService);
        }

        @Test
        @Order(6)
        @DisplayName("Bean is correctly retrieved by it`s name and superclass")
        void getBeanByByNameAndSuperclassReturnsCorrectBean() {
            SuccessCustomService messageService = applicationContext.getBean("successMessageServiceImpl", SuccessCustomService.class);
            assertEquals("Hello", ((SuccessMessageServiceImpl) messageService).getMessage());
        }

        @Test
        @Order(7)
        @DisplayName("NoSuchBeanException is thrown when retrieving bean by non-existing name")
        void getBeanByNonPresentName() {
            assertThrows(NoSuchBeanException.class, () -> applicationContext.getBean("customService", SuccessCustomService.class));
        }

        @Test
        @Order(8)
        @DisplayName("Get all beans returns correct map of objects")
        void getAllBeansReturnsCorrectMap() {
            Map<String, SuccessCustomService> customServices = applicationContext.getAllBeans(SuccessCustomService.class);

            assertEquals(2, customServices.size());

            assertTrue(customServices.containsKey("successMessageServiceImpl"));
            assertTrue(customServices.containsKey("printer-bean"));

            assertTrue(customServices.values().stream()
                    .anyMatch(bean -> bean.getClass().isAssignableFrom(SuccessMessageServiceImpl.class)));
            assertTrue(customServices.values().stream()
                    .anyMatch(bean -> bean.getClass().isAssignableFrom(SuccessPrinterServiceImpl.class)));
        }

        @Test
        @Order(9)
        @DisplayName("Successful retrive bean with @Primary annotation")
        void getBeanWithPrimaryAnnotation() {
            var primaryBean = applicationContext.getBean(AnnotationService.class);
            assertNotNull(primaryBean);
            assertEquals("PrimaryAnnotationService", primaryBean.getServiceName());
        }
        @Test
        @Order(10)
        @DisplayName("NoUniqueBeanException is thrown when Primary annotation is present on more then one candidate ")
        void getBeanWithDuplicatedPrimaryAnnotation() {
            applicationContext = new AnnotationApplicationContext("com.bobocode.svydovets.autowiring.nouniquebean.primaryduplicate");
            assertThrows(NoUniqueBeanException.class, () -> applicationContext.getBean(InvalidAnnotationService.class));
        }
    }

    @Nested
    @Order(2)
    @DisplayName("2. Autowiring post processing test")
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    class AutowiringPostProcessingTest {

        @Test
        @Order(1)
        @DisplayName("Field is successfully autowired")
        void autowiringFieldIsSetCorrectly() {
            AnnotationApplicationContext applicationContext = new AnnotationApplicationContext("com.bobocode.svydovets.autowiring.success");
            SuccessPrinterServiceImpl printerService = applicationContext.getBean(SuccessPrinterServiceImpl.class);
            SuccessMessageServiceImpl messageService = applicationContext.getBean(SuccessMessageServiceImpl.class);
            assertEquals(messageService.getMessage(), printerService.print());
        }

        @Test
        @Order(2)
        @DisplayName("NoSuchBeanException is thrown if there no bean that can be autowired")
        void autowiringFieldWhenNoSuchBeanPresentInContext() {
            NoSuchBeanException noSuchBeanException = assertThrows(NoSuchBeanException.class,
                    () -> new AnnotationApplicationContext("com.bobocode.svydovets.autowiring.nosuchbean"));

            assertThat(noSuchBeanException.getMessage(), containsString("expected at least 1 bean which qualifies as autowire candidate for this dependency"));
            assertThat(noSuchBeanException.getMessage(), containsString("To resolve the issue: use @Component or @Bean"));
        }

        @Test
        @Order(3)
        @DisplayName("NoUniqueBeanException is thrown if there are several candidates for autowiring")
        void autowiringFieldWhenNoUniqueBeanPresentInContext() {
            NoUniqueBeanException noUniqueBeanException = assertThrows(NoUniqueBeanException.class,
                    () -> new AnnotationApplicationContext("com.bobocode.svydovets.autowiring.nouniquebean"));

            assertThat(noUniqueBeanException.getMessage(), containsString("expected single matching bean but found"));
            assertThat(noUniqueBeanException.getMessage(), containsString("To resolve the issue: use @Qualifier(\"...\")"));
        }
    }

}
