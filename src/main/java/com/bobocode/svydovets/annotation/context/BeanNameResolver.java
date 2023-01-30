package com.bobocode.svydovets.annotation.context;

import java.lang.reflect.Method;

//todo: add JavaDocs
public interface BeanNameResolver {

    String resolveBeanName(Class<?> type);

    String resolveBeanName(Method method);

}
