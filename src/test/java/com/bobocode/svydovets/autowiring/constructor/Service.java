package com.bobocode.svydovets.autowiring.constructor;

import com.bobocode.svydovets.annotation.annotations.AutoSvydovets;
import com.bobocode.svydovets.annotation.annotations.Component;

@Component
public class Service {

    private FooDependency fooDependency;

    private BarDependency barDependency;

    public Service() {
    }

    @AutoSvydovets
    public Service(FooDependency fooDependency, BarDependency barDependency) {
        this.fooDependency = fooDependency;
        this.barDependency = barDependency;
    }

    public String getMessage() {
        return fooDependency.getMessage() + barDependency.getMessage();
    }
}
