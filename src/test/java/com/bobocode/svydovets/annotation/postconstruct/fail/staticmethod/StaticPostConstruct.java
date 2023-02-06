package com.bobocode.svydovets.annotation.postconstruct.fail.staticmethod;

import com.bobocode.svydovets.annotation.annotations.Component;
import com.bobocode.svydovets.annotation.annotations.PostConstruct;
import com.bobocode.svydovets.annotation.postconstruct.success.Foo;

@Component
public class StaticPostConstruct {
    @PostConstruct
    public static void doSomething() {
        Foo.doSmthForStatic();
    }
}
