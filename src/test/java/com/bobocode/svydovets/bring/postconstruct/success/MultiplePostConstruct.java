package com.bobocode.svydovets.bring.postconstruct.success;

import com.bobocode.svydovets.bring.annotations.Component;
import com.bobocode.svydovets.bring.annotations.PostConstruct;

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
