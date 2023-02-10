package com.bobocode.svydovets.beans;

import com.bobocode.svydovets.bring.annotations.AutoSvydovets;
import com.bobocode.svydovets.bring.annotations.Component;
import com.bobocode.svydovets.bring.annotations.Qualifier;

@Component
public class FooService {

    @AutoSvydovets
    @Qualifier("fooFormatter")
    private Formatter formatter;
}
