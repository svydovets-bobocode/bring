package com.bobocode.svydovets.autowiring.setter;

import com.bobocode.svydovets.annotation.annotations.AutoSvydovets;
import com.bobocode.svydovets.annotation.annotations.Component;

@Component
public class SetterSuccessMessageService1Impl implements SetterSuccessMessageService {

    private SetterSuccessPrinterServiceImpl printerService;

    @AutoSvydovets
    public void setPrinterService(SetterSuccessPrinterServiceImpl printerService) {
        this.printerService = printerService;
    }

    public SetterSuccessPrinterServiceImpl getPrinterService() {
        return printerService;
    }

    @Override
    public String getMessage() {
        return "Hello1";
    }
}
