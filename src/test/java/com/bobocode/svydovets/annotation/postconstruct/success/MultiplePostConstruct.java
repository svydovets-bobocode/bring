package com.bobocode.svydovets.annotation.postconstruct.success;

import com.bobocode.svydovets.annotation.annotations.Component;
import com.bobocode.svydovets.annotation.annotations.PostConstruct;

@Component
public class MultiplePostConstruct {
    @PostConstruct
    public void doSomething1() {
        Foo.doSmthForMultiple1();
    }

    @PostConstruct
    public void doSomething2() {
        Foo.doSmthForMultiple2();
    }
}
