package com.bobocode.svydovets.autowiring;

import com.bobocode.svydovets.annotation.context.AnnotationApplicationContext;
import com.bobocode.svydovets.annotation.exception.BeanException;
import com.bobocode.svydovets.annotation.exception.NoSuchBeanException;
import com.bobocode.svydovets.annotation.exception.NoUniqueBeanException;
import com.bobocode.svydovets.autowiring.beanqualifier.success.SuccessBarServiceBeanQualifier;
import com.bobocode.svydovets.autowiring.collection.success.SuccessCollectionService;
import com.bobocode.svydovets.autowiring.configuration.AutoSvydovetsClientBean;
import com.bobocode.svydovets.autowiring.configuration.FooBarService;
import com.bobocode.svydovets.autowiring.configuration.FooService;
import com.bobocode.svydovets.autowiring.configuration.TestConfig;
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

    public static final String CONFIGURATION_PACKAGE = "com.bobocode.svydovets.autowiring.configuration";

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
            SuccessMessageServiceImpl messageService = applicationContext.getBean(SuccessMessageServiceImpl.class);
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
        @DisplayName("Successful retrieve bean with @Primary annotation")
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

        @Test
        @Order(4)
        @DisplayName("Field injection with list of beans is successfully autowired")
        void autowiringListToFieldSetCorrectly() {
            AnnotationApplicationContext applicationContext = new AnnotationApplicationContext("com.bobocode.svydovets.autowiring.collection.success");
            SuccessCollectionService bean = applicationContext.getBean(SuccessCollectionService.class);
            assertEquals(1, bean.printBeanSize());
        }

        @Test
        @Order(5)
        @DisplayName("Exception is thrown if collection is raw used")
        void autowiringListToFieldIfCollectionIsRawUsed() {
            BeanException beanException = assertThrows(BeanException.class, () ->
                    new AnnotationApplicationContext("com.bobocode.svydovets.autowiring.collection.invalid"));
            String message = beanException.getMessage();
            assertEquals(message, "@AutoSvydovets collection List should not be raw used for field 'beans'");
        }
    }

    @Nested
    @Order(3)
    @DisplayName("3. Configuration beans creation test")
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    class ConfigurationBeansCreationTest {

        @Test
        @Order(1)
        @DisplayName("@Configuration marked class is successfully created as a bean")
        void autowiringFieldIsSetCorrectly() {
            AnnotationApplicationContext applicationContext = new AnnotationApplicationContext(CONFIGURATION_PACKAGE);
            assertNotNull(applicationContext.getBean(TestConfig.class));
            assertNotNull(applicationContext.getBean("testConfig", TestConfig.class));
        }

        @Test
        @Order(2)
        @DisplayName("@Bean marked method returns correctly configured bean")
        void beanIsCreatedAndStoredInContext() {
            AnnotationApplicationContext applicationContext = new AnnotationApplicationContext(CONFIGURATION_PACKAGE);
            FooService bean = applicationContext.getBean(FooService.class);
            assertNotNull(bean);
            assertEquals("FooPrimary", bean.getMessage());
        }

        @Test
        @Order(3)
        @DisplayName("@Bean marked method returns correctly configured bean with inter-bean dependency")
        void interBeanDependencyIsHandledCorrectly() {
            AnnotationApplicationContext applicationContext = new AnnotationApplicationContext(CONFIGURATION_PACKAGE);
            FooBarService fooBarService = applicationContext.getBean(FooBarService.class);
            FooService fooService = applicationContext.getBean(FooService.class);
            assertNotNull(fooBarService);
            assertNotNull(fooService);
            assertEquals("FooBar", fooBarService.getMessage());
        }

        @Test
        @Order(4)
        @DisplayName("@Bean is correctly created using @AutoSvydovets dependency")
        void autoSvydovetsCorrectlyProcessedWithBeanMethods() {
            AnnotationApplicationContext applicationContext = new AnnotationApplicationContext(CONFIGURATION_PACKAGE);
            var autoSvydovetsClientBean = applicationContext.getBean(AutoSvydovetsClientBean.class);
            assertNotNull(autoSvydovetsClientBean);

            //todo: throws NullPointerException in such case
            //String dependencyCallResult = autoSvydovetsClientBean.callAutoSvydovetsDependency();
            //assertEquals("AutoSvydovetsDependency", dependencyCallResult);
        }

        @Test
        @Order(3)
        @DisplayName("method marked as @Bean and @Primary should be injected when there are more then one bean with the same type")
        void primaryWorksCorrectlyWhenThereAreTwoBeansWithTheSameType() {
            AnnotationApplicationContext applicationContext = new AnnotationApplicationContext(CONFIGURATION_PACKAGE);
            FooService fooService = applicationContext.getBean(FooService.class);
            assertNotNull(fooService);
            assertEquals("FooPrimary", fooService.getMessage());
        }

    }

    @Nested
    @Order(4)
    @DisplayName("4. Configuration beans qualifier")
    @TestMethodOrder(MethodOrderer.OrderAnnotation.class)
    class ConfigurationBeansQualifierTest {

        public static final String SUCCESS_CONFIGURATION_BEANQUALIFIER_PACKAGE = "com.bobocode.svydovets.autowiring.beanqualifier.success";
        public static final String INVALID_CONFIGURATION_BEANQUALIFIER_PACKAGE = "com.bobocode.svydovets.autowiring.beanqualifier.invalid";

        @Test
        @Order(1)
        @DisplayName("@Configuration marked class is successfully created as a beans with component @Qualifier")
        void autowiringWithQualifierIsSetCorrectly() {
            AnnotationApplicationContext applicationContext = new AnnotationApplicationContext(SUCCESS_CONFIGURATION_BEANQUALIFIER_PACKAGE);
            SuccessBarServiceBeanQualifier serviceBeanQualifier = applicationContext.getBean(SuccessBarServiceBeanQualifier.class);
            assertNotNull(serviceBeanQualifier);
            assertEquals("Foo1", serviceBeanQualifier.getMessage());
        }

        @Test
        @Order(2)
        @DisplayName("Failed context creation two beans without component @Qualifier")
        void failedContextCreationWithTwoBeansWithoutComponentQualifier() {
            NoUniqueBeanException noUniqueBeanException = assertThrows(NoUniqueBeanException.class,
                    () -> new AnnotationApplicationContext(INVALID_CONFIGURATION_BEANQUALIFIER_PACKAGE));

            assertThat(noUniqueBeanException.getMessage(), containsString("No qualifying bean of type [com.bobocode.svydovets.autowiring.beanqualifier.invalid.InvalidFooServiceBeanQualifier]: expected single matching bean but found 2: [fooService1, fooService2]"));
        }
    }
}
