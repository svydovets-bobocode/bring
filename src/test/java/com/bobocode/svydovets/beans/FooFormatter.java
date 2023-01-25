package com.bobocode.svydovets.beans;

import com.bobocode.svydovets.annotation.annotations.Component;

@Component
public class FooFormatter implements Formatter {
    @Override
    public String format() {
        return "foo";
    }
}
