package com.bobocode.svydovets.annotation.bean.processor;

import com.bobocode.svydovets.annotation.exception.BeanException;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * Class uses for finding all implementations of {@link BeanPostProcessor}
 */
public class BeanPostProcessorScanner {

     /**
     * Scan packages to find classes implemented
     * {@link BeanPostProcessor} and returns list of BeanPostProcessor
     * instantiated from these classes.
     *
     * @param packages
     * @return list of BeanPostProcessor
     */
    public List<BeanPostProcessor> scan(String... packages) {
        return new Reflections(packages).getSubTypesOf(BeanPostProcessor.class)
                .stream()
                .map(this::instantiate)
                .toList();
    }

    /**
     * Instantiate subclass of BeanPostProcessor with default constructor
     *
     * @param bppClass
     * @return subclass of BeanPostProcessor
     */
    private BeanPostProcessor instantiate(Class<? extends BeanPostProcessor> bppClass) {
        try {
            return bppClass.getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException
                 | InvocationTargetException | NoSuchMethodException e) {
            throw new BeanException("Can't create instance " + bppClass.getName(), e);
        }
    }
}
