package com.bobocode.svydovets.autowiring.collection.invalid;

import com.bobocode.svydovets.annotation.annotations.AutoSvydovets;
import com.bobocode.svydovets.annotation.annotations.Component;

import java.util.List;

@Component
public class InvalidCollectionService {

    @AutoSvydovets
    private List beans;

    public int printBeanSize() {
        return beans.size();
    }

}
