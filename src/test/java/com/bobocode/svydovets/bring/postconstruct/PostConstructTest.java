package com.bobocode.svydovets.bring.postconstruct;

import com.bobocode.svydovets.bring.context.AnnotationApplicationContext;
import com.bobocode.svydovets.bring.exception.PostConstructProcessingException;
import com.bobocode.svydovets.bring.postconstruct.success.AccessModifierPostConstruct;
import com.bobocode.svydovets.bring.postconstruct.success.Foo;
import com.bobocode.svydovets.bring.postconstruct.success.MultiplePostConstruct;
import com.bobocode.svydovets.bring.postconstruct.success.SinglePostConstruct;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;

@DisplayName("PostConstruct test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PostConstructTest {

    @Test
    @Order(1)
    @DisplayName("Method annotated with @PostConstruct invoked")
    void codeInMethodAnnotatedWithPostConstructInvoked() {
        try (MockedStatic<Foo> foo = Mockito.mockStatic(Foo.class)) {
            AnnotationApplicationContext applicationContext = new AnnotationApplicationContext(
                    "com.bobocode.svydovets.annotation.postconstruct.success");
            SinglePostConstruct underTest = applicationContext.getBean(SinglePostConstruct.class);
            assertNotNull(underTest);
            foo.verify(Foo::doSmthForSingle, times(1));
        }
    }

    @Test
    @Order(2)
    @DisplayName("Methods annotated with @PostConstruct invoked")
    void codeInMethodsAnnotatedWithPostConstructInvoked() {
        try (MockedStatic<Foo> foo = Mockito.mockStatic(Foo.class)) {
            AnnotationApplicationContext applicationContext = new AnnotationApplicationContext(
                    "com.bobocode.svydovets.annotation.postconstruct.success");
            MultiplePostConstruct underTest = applicationContext.getBean(MultiplePostConstruct.class);
            assertNotNull(underTest);
            foo.verify(Foo::doSmthForMultiple1, times(1));
            foo.verify(Foo::doSmthForMultiple2, times(1));
        }
    }

    @Test
    @Order(3)
    @DisplayName("Methods annotated with @PostConstruct independent of access modifier")
    void codeInMethodsAnnotatedWithPostConstructInvokedIndependentOfAccessModifier() {
        try (MockedStatic<Foo> foo = Mockito.mockStatic(Foo.class)) {
            AnnotationApplicationContext applicationContext = new AnnotationApplicationContext(
                    "com.bobocode.svydovets.annotation.postconstruct.success");
            AccessModifierPostConstruct underTest = applicationContext.getBean(AccessModifierPostConstruct.class);
            assertNotNull(underTest);
            foo.verify(Foo::doSmthForAccessModifier1, times(1));
            foo.verify(Foo::doSmthForAccessModifier2, times(1));
            foo.verify(Foo::doSmthForAccessModifier3, times(1));
            foo.verify(Foo::doSmthForAccessModifier4, times(1));
            foo.verify(Foo::doSmthForAccessModifier5, times(1));
        }
    }

    @Test
    @Order(4)
    @DisplayName("PostConstructProcessingException is thrown if a method annotated with @PostConstruct has parameters")
    void methodAnnotatedWithPostConstructCanNotHaveParameters() {
        PostConstructProcessingException exception = assertThrows(PostConstructProcessingException.class,
                () -> new AnnotationApplicationContext("com.bobocode.svydovets.annotation.postconstruct.fail.withparams"));

        assertThat(exception.getMessage(), containsString("Method annotated with PostConstruct should be without parameters"));
    }

    @Test
    @Order(5)
    @DisplayName("PostConstructProcessingException is thrown if a method annotated with @PostConstruct is static")
    void methodAnnotatedWithPostConstructCanNotBeStatic() {
        PostConstructProcessingException exception = assertThrows(PostConstructProcessingException.class,
                () -> new AnnotationApplicationContext("com.bobocode.svydovets.annotation.postconstruct.fail.staticmethod"));

        assertThat(exception.getMessage(), containsString("Method annotated with PostConstruct can not be static"));
    }

    @Test
    @Order(6)
    @DisplayName("Method annotated with @PostConstruct should not be invoked if a class not annotated with Component or Configuration")
    void methodAnnotatedWithPostConstructCanNotBeInvokedIfClassNotAnnotated() {
        try (MockedStatic<Foo> foo = Mockito.mockStatic(Foo.class)) {
            new AnnotationApplicationContext("com.bobocode.svydovets.annotation.postconstruct.fail.classnotannotated");
            foo.verify(Foo::doSmthForClassNotAnnotated, never());
        }
    }
}
