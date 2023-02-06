package com.bobocode.svydovets.annotation.postconstruct.fail.classnotannotated;

import com.bobocode.svydovets.annotation.annotations.PostConstruct;
import com.bobocode.svydovets.annotation.postconstruct.success.Foo;

public class ClassNotAnnotatedPostConstruct {
    @PostConstruct
    public void doSomething() {
        Foo.doSmthForClassNotAnnotated();
    }
}
