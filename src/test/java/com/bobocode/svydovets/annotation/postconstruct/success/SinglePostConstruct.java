package com.bobocode.svydovets.annotation.postconstruct.success;

import com.bobocode.svydovets.annotation.annotations.Component;
import com.bobocode.svydovets.annotation.annotations.PostConstruct;

@Component
public class SinglePostConstruct {
    @PostConstruct
    public void doSomething() {
        Foo.doSmthForSingle();
    }
}
