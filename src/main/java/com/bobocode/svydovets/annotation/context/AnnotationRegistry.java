package com.bobocode.svydovets.annotation.context;

import java.util.Set;

//todo: provide JavaDocs
public interface AnnotationRegistry {

    Set<Class<?>> scan(String... packages);
}
