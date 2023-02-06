package com.bobocode.svydovets.annotation.postconstruct.fail.withparams;

import com.bobocode.svydovets.annotation.annotations.Component;
import com.bobocode.svydovets.annotation.annotations.PostConstruct;
import com.bobocode.svydovets.annotation.postconstruct.success.Foo;

@Component
public class WithParamsPostConstruct {
    @PostConstruct
    public void doSomething(String s) {
        Foo.doSmthForWithParams();
    }
}
