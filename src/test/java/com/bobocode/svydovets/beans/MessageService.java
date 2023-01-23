package com.bobocode.svydovets.beans;

import com.bobocode.svydovets.annotation.annotations.Component;

@Component
public class MessageService implements CustomService {

    public String getMessage() {
        return "Hello";
    }

}
