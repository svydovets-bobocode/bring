package com.bobocode.svydovets.beans;

import com.bobocode.svydovets.bring.annotations.AutoSvydovets;
import com.bobocode.svydovets.bring.annotations.Component;

@Component
public class PrinterService implements CustomService {

    @AutoSvydovets
    private MessageService messageService;

    public String print() {
        return messageService.getMessage();
    }

}
