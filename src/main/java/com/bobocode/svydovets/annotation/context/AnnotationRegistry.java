package com.bobocode.svydovets.annotation.context;

import java.util.Set;

public interface AnnotationRegistry {

  Set<Class<?>> scan(String... packages);
}
