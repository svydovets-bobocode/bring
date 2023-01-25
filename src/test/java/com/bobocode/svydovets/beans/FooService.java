package com.bobocode.svydovets.beans;

import com.bobocode.svydovets.annotation.annotations.AutoSvydovets;
import com.bobocode.svydovets.annotation.annotations.Component;
import com.bobocode.svydovets.annotation.annotations.Qualifier;

@Component
public class FooService {

    @AutoSvydovets
    @Qualifier("fooFormatter")
    private Formatter formatter;
}
