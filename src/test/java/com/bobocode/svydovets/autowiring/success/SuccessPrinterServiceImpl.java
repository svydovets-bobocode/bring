package com.bobocode.svydovets.autowiring.success;

import com.bobocode.svydovets.bring.annotations.AutoSvydovets;
import com.bobocode.svydovets.bring.annotations.Component;

@Component("printer-bean")
public class SuccessPrinterServiceImpl implements SuccessCustomService {
    @AutoSvydovets
    private SuccessMessageServiceImpl messageService;

    public String print() {
        return messageService.getMessage();
    }
}
