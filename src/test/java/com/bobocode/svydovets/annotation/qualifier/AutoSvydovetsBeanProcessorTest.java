package com.bobocode.svydovets.annotation.qualifier;

import com.bobocode.svydovets.annotation.annotations.AutoSvydovets;
import com.bobocode.svydovets.annotation.annotations.Qualifier;
import com.bobocode.svydovets.annotation.bean.factory.BeanFactory;
import com.bobocode.svydovets.annotation.bean.processor.AutoSvydovetsBeanProcessor;
import com.bobocode.svydovets.annotation.bean.processor.injector.FieldInjector;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class AutoSvydovetsBeanProcessorTest {

    @Mock
    private BeanFactory mockBeanFactory;

    private AutoSvydovetsBeanProcessor postProcessor;
    private Map<String, Object> rootContext;

    @SneakyThrows
    @BeforeEach
    public void setUp() {
        // Initialize the mock
        MockitoAnnotations.openMocks(this).close();

        postProcessor = new AutoSvydovetsBeanProcessor(List.of(new FieldInjector(mockBeanFactory)));
        rootContext = new HashMap<>();
    }

    @Test
    void testProcessBeansWithQualifier() {
        // Create a mock dependency
        TestDependency mockDependency = new TestDependency();

        // Configure the mock to return the mock dependency when getBean is called
        when(mockBeanFactory.getBean(eq("testDependency"), any(Class.class))).thenReturn(mockDependency);

        // Add a test bean to the root context
        TestBeanWithQualifier testBean = new TestBeanWithQualifier();
        rootContext.put("testBean", testBean);

        // Process the beans
        postProcessor.processBeans(rootContext);

        // Assert that the dependency was injected into the test bean
        assertNotNull(testBean.getDependency());
        assertEquals("test", testBean.getDependency().getValue());
    }

    private static class TestBeanWithQualifier {
        @AutoSvydovets
        @Qualifier("testDependency")
        private TestDependency dependency;

        public TestDependency getDependency() {
            return dependency;
        }
    }

    private static class TestDependency {
        private String value = "test";

        public String getValue() {
            return value;
        }
    }
}
