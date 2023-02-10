package com.bobocode.svydovets.bring.postconstruct.fail.staticmethod;

import com.bobocode.svydovets.bring.annotations.Component;
import com.bobocode.svydovets.bring.annotations.PostConstruct;
import com.bobocode.svydovets.bring.postconstruct.success.Foo;

@Component
public class StaticPostConstruct {
    @PostConstruct
    public static void doSomething() {
        Foo.doSmthForStatic();
    }
}
