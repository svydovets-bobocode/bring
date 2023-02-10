package com.bobocode.svydovets.autowiring.collection.success;

import com.bobocode.svydovets.bring.annotations.AutoSvydovets;
import com.bobocode.svydovets.bring.annotations.Component;

import java.util.List;

@Component
public class SuccessCollectionService {

    @AutoSvydovets
    private List<SampleBean> beans;

    public int printBeanSize() {
        return beans.size();
    }

}
