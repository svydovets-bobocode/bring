package com.bobocode.svydovets.bring.postconstruct.success;

import com.bobocode.svydovets.bring.annotations.Component;
import com.bobocode.svydovets.bring.annotations.PostConstruct;

@Component
public class SinglePostConstruct {
    @PostConstruct
    public void doSomething() {
        Foo.doSmthForSingle();
    }
}
