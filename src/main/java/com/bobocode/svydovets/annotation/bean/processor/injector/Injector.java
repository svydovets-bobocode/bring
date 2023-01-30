package com.bobocode.svydovets.annotation.bean.processor.injector;

import java.lang.reflect.AccessibleObject;

public interface Injector<T extends AccessibleObject> {

    void injectDependency(Object beanObject);
}
