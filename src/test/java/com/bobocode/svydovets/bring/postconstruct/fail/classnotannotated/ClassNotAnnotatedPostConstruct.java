package com.bobocode.svydovets.bring.postconstruct.fail.classnotannotated;

import com.bobocode.svydovets.bring.annotations.PostConstruct;
import com.bobocode.svydovets.bring.postconstruct.success.Foo;

public class ClassNotAnnotatedPostConstruct {
    @PostConstruct
    public void doSomething() {
        Foo.doSmthForClassNotAnnotated();
    }
}
