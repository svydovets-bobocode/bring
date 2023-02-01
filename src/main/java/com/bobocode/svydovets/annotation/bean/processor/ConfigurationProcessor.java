package com.bobocode.svydovets.annotation.bean.processor;

import com.bobocode.svydovets.annotation.bean.factory.BeanFactory;

import java.util.Map;

//todo: provide JavaDocs
public class ConfigurationProcessor implements BeanProcessor {

    private final BeanFactory beanFactory;

    public ConfigurationProcessor(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public void processBeans(Map<String, Object> rootContext) {

    }
}
