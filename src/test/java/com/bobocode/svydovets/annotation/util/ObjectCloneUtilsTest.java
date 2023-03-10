package com.bobocode.svydovets.annotation.util;


import com.bobocode.svydovets.annotation.exception.BeanException;
import com.bobocode.svydovets.beans.Car;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import static com.bobocode.svydovets.annotation.exception.BeanException.CAN_NOT_CREATE_A_COPY_OF_BEAN;
import static net.bytebuddy.matcher.ElementMatchers.any;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ObjectCloneUtilsTest {

    private final ObjectMapper objectMapper = mock(ObjectMapper.class);

    @Test
    void deepCopySuccessWhenInputIsValid() throws JsonProcessingException {
        Car car = new Car();
        String json = "{name: \"BMW\"}";
        when(objectMapper.writeValueAsString(car)).thenReturn(json);
        when(objectMapper.readValue(json, Object.class)).thenReturn(car);

        Car copyResult = ObjectCloneUtils.deepCopy(car, Car.class);

        assertNotEquals(car, copyResult);
    }

    @Test
    void deepCopyThrowsBeanExceptionWhenInputIsInvalid() throws JsonProcessingException {
        doThrow(JsonProcessingException.class).when(objectMapper).writeValueAsString(any());
        Object objectCopy = new Object();
        assertThrows(BeanException.class,
                () -> ObjectCloneUtils.deepCopy(objectCopy, Object.class), CAN_NOT_CREATE_A_COPY_OF_BEAN);
    }
}