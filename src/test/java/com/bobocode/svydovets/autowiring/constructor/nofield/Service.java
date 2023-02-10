package com.bobocode.svydovets.autowiring.constructor.nofield;

import com.bobocode.svydovets.bring.annotations.AutoSvydovets;
import com.bobocode.svydovets.bring.annotations.Component;
import com.bobocode.svydovets.autowiring.constructor.success.BarDependency;
import com.bobocode.svydovets.autowiring.constructor.success.FooDependency;

@Component
public class Service {

    private BarDependency barDependency;

    public Service() {
    }

    @AutoSvydovets
    public Service(FooDependency fooDependency, BarDependency barDependency) {
        this.barDependency = barDependency;
    }

    public String getMessage() {
        return barDependency.getMessage();
    }
}
