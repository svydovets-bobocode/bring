package com.bobocode.svydovets.annotation.context;

import com.bobocode.svydovets.annotation.annotations.PostConstruct;
import com.bobocode.svydovets.annotation.exception.PostConstructProcessingException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;

/**
 * The PostConstructHandler class provides a process for handling the `PostConstruct` annotation.
 *
 * The class provides a static method `processPostConstruct` that takes an Object as an input and
 * processes all the methods annotated with the `PostConstruct` annotation.
 *
 * The class ensures that the methods annotated with the `PostConstruct` annotation should be without
 * parameters and not static.
 *
 * The class is a utility class and couldn't be initialised
 */
public final class PostConstructHandler {

    private PostConstructHandler() {
        throw new UnsupportedOperationException("This utility class and cannot be instantiated");
    }

    /**
     * Processes all the methods annotated with the `PostConstruct` annotation in the provided object
     *
     * @param bean the Object to process the `PostConstruct` annotated methods.
     */
    static void processPostConstruct(Object bean) {
        List<Method> postConstructAnnotatedMethods =
                Arrays.stream(bean.getClass().getDeclaredMethods())
                        .filter(method -> method.isAnnotationPresent(PostConstruct.class))
                        .toList();

        checkPossibilityToProcess(postConstructAnnotatedMethods);

        postConstructAnnotatedMethods.forEach(method -> invokeMethod(method, bean));
    }

    private static void checkPossibilityToProcess(List<Method> postConstructAnnotatedMethods) {
        if (postConstructAnnotatedMethods.stream().anyMatch(method -> method.getParameters().length > 0)) {
            throw new PostConstructProcessingException(
                    "Method annotated with PostConstruct should be without parameters");
        }

        if (postConstructAnnotatedMethods.stream().anyMatch(method -> Modifier.isStatic(method.getModifiers()))) {
            throw new PostConstructProcessingException("Method annotated with PostConstruct can not be static");
        }
    }

    @SuppressWarnings("java:S3011")
    private static void invokeMethod(Method method, Object bean) {
        try {
            method.setAccessible(true);
            method.invoke(bean);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new PostConstructProcessingException(
                    "Exception happened during PostConstruct annotation processing", e);
        }
    }
}
