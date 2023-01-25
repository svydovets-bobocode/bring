package com.bobocode.svydovets.annotation.register;

import java.util.Set;

//todo: provide JavaDocs
public interface AnnotationRegistry {

    Set<Class<?>> scan(String... packages);

    void register(Class<?>... componentClasses);
}
