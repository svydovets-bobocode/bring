package com.bobocode.svydovets.autowiring.collection.invalid;

import com.bobocode.svydovets.bring.annotations.AutoSvydovets;
import com.bobocode.svydovets.bring.annotations.Component;

import java.util.List;

@Component
public class InvalidCollectionService {

    @AutoSvydovets
    private List beans;

    public int printBeanSize() {
        return beans.size();
    }

}
