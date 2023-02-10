package com.bobocode.svydovets.bring.bean.processor.injector;

import java.lang.reflect.AccessibleObject;

public interface Injector<T extends AccessibleObject> {

    void injectDependency(Object beanObject);
}
