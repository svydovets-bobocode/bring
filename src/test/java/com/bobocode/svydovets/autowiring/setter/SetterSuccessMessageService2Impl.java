package com.bobocode.svydovets.autowiring.setter;

import com.bobocode.svydovets.bring.annotations.AutoSvydovets;
import com.bobocode.svydovets.bring.annotations.Component;

import java.util.List;

@Component
public class SetterSuccessMessageService2Impl implements SetterSuccessMessageService {

    private SetterSuccessPrinterServiceImpl printerService;
    private List<String> strings;

    public void setPrinterService(SetterSuccessPrinterServiceImpl printerService) {
        this.printerService = printerService;
    }

    public SetterSuccessPrinterServiceImpl getPrinterService() {
        return printerService;
    }

    @AutoSvydovets
    public void setTwoParams(SetterSuccessPrinterServiceImpl printerService, List<String> strings) {
        this.printerService = printerService;
        this.strings = strings;
    }

    public List<String> getStrings() {
        return strings;
    }

    @Override
    public String getMessage() {
        return "Hello2";
    }
}
