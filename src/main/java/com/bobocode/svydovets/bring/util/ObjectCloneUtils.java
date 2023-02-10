package com.bobocode.svydovets.bring.util;

import com.bobocode.svydovets.bring.exception.BeanException;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import static com.bobocode.svydovets.bring.exception.BeanException.CAN_NOT_CREATE_A_COPY_OF_BEAN;

public class ObjectCloneUtils {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private ObjectCloneUtils() {
    }

    public static <T> T deepCopy(T inputObject, Class<? extends T> beanType)  {
        objectMapper.setVisibility(objectMapper.getSerializationConfig().getDefaultVisibilityChecker()
                .withFieldVisibility(JsonAutoDetect.Visibility.ANY)
                .withGetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withSetterVisibility(JsonAutoDetect.Visibility.NONE)
                .withCreatorVisibility(JsonAutoDetect.Visibility.NONE));
        String objectJson;
        try {
            objectJson = objectMapper.writeValueAsString(inputObject);
            return objectMapper.readValue(objectJson, beanType);
        } catch (JsonProcessingException e) {
            throw new BeanException(CAN_NOT_CREATE_A_COPY_OF_BEAN);
        }
    }
}
