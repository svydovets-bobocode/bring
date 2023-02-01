package com.bobocode.svydovets.autowiring.collection;

import com.bobocode.svydovets.annotation.annotations.AutoSvydovets;
import com.bobocode.svydovets.annotation.annotations.Component;

import java.util.List;

@Component
public class SuccessCollectionService {

    @AutoSvydovets
    private List<SampleBean> beans;

    public int printBeanSize() {
        return beans.size();
    }

}