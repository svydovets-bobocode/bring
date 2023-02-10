package com.bobocode.svydovets.bring.bean.processor;

import java.util.Map;

/**
 * Interface that allows custom linear processing of all beans in the
 * provided context. Processing logic should be provider by implementing
 * {@link #processBeans(Map)}.
 * <p>
 * Also, it provides several default methods, that will be common for
 * different types of processors.
 *
 * @see AutoSvydovetsBeanProcessor
 */
public interface BeanProcessor {

    void processBeans(Map<String, Object> rootContext);

}
