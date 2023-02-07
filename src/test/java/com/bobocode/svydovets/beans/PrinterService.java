package com.bobocode.svydovets.beans;

import com.bobocode.svydovets.annotation.annotations.AutoSvydovets;
import com.bobocode.svydovets.annotation.annotations.Component;

@Component
public class PrinterService implements CustomService {

    @AutoSvydovets
    private MessageService messageService;

    public String print() {
        return messageService.getMessage();
    }

}
