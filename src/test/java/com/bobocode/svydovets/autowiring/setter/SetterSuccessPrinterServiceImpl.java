package com.bobocode.svydovets.autowiring.setter;

import com.bobocode.svydovets.annotation.annotations.AutoSvydovets;
import com.bobocode.svydovets.annotation.annotations.Component;
import com.bobocode.svydovets.annotation.annotations.Qualifier;

@Component("printer-bean")
public class SetterSuccessPrinterServiceImpl {
    private SetterSuccessMessageService messageService;

    @AutoSvydovets
    @Qualifier("setterSuccessMessageService1Impl")
    public void setMessageService(SetterSuccessMessageService messageService) {
        this.messageService = messageService;
    }

    public String print() {
        return messageService.getMessage();
    }

    public SetterSuccessMessageService getMessageService() {
        return messageService;
    }
}
