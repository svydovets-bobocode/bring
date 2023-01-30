package com.bobocode.svydovets.annotation.util;

import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.util.Set;

public class ReflectionUtils {

    private ReflectionUtils() {
    }

    public static Set<Class<?>> scan(Class<? extends Annotation> targetAnnotation, String... packages) {
        Reflections reflections = new Reflections((Object) packages);
        return reflections.getTypesAnnotatedWith(targetAnnotation);
    }
}
