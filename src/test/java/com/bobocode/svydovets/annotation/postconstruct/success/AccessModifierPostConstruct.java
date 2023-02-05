package com.bobocode.svydovets.annotation.postconstruct.success;

import com.bobocode.svydovets.annotation.annotations.Component;
import com.bobocode.svydovets.annotation.annotations.PostConstruct;

@Component
public class AccessModifierPostConstruct {
    @PostConstruct
    public void doSomething1() {
        Foo.doSmthForAccessModifier1();
    }

    @PostConstruct
    void doSomething2() {
        Foo.doSmthForAccessModifier2();
    }

    @PostConstruct
    protected void doSomething3() {
        Foo.doSmthForAccessModifier3();
    }

    @PostConstruct
    private void doSomething4() {
        Foo.doSmthForAccessModifier4();
    }

    @PostConstruct
    final void doSomething5() {
        Foo.doSmthForAccessModifier5();
    }
}
