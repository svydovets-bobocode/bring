package com.bobocode.svydovets.beans;

import com.bobocode.svydovets.bring.annotations.Component;
import com.bobocode.svydovets.bring.annotations.Scope;
import com.bobocode.svydovets.bring.register.BeanScope;

@Component
@Scope(BeanScope.PROTOTYPE)
public class MessageService implements CustomService {

    private String hello = "Hello";

    public String getMessage() {
        return hello;
    }

}
