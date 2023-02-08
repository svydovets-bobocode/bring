package com.bobocode.svydovets.beans;

import com.bobocode.svydovets.annotation.annotations.Component;
import com.bobocode.svydovets.annotation.annotations.Scope;
import com.bobocode.svydovets.annotation.register.BeanScope;

@Component
@Scope(BeanScope.PROTOTYPE)
public class MessageService implements CustomService {

    private String hello = "Hello";

    public String getMessage() {
        return hello;
    }

}
