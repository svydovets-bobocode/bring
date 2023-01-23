package com.bobocode.svydovets.support;

import com.bobocode.svydovets.annotation.bean.factory.BeanFactory;

//todo: provide JavaDocs (optionally)
public abstract class AbstractApplicationContext implements ApplicationContext {

    protected abstract BeanFactory getBeanFactory();

}
