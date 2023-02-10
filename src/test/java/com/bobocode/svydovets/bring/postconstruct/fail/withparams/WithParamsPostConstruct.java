package com.bobocode.svydovets.bring.postconstruct.fail.withparams;

import com.bobocode.svydovets.bring.annotations.Component;
import com.bobocode.svydovets.bring.annotations.PostConstruct;
import com.bobocode.svydovets.bring.postconstruct.success.Foo;

@Component
public class WithParamsPostConstruct {
    @PostConstruct
    public void doSomething(String s) {
        Foo.doSmthForWithParams();
    }
}
