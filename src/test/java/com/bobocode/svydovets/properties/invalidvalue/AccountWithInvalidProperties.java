package com.bobocode.svydovets.properties.invalidvalue;

import com.bobocode.svydovets.annotation.annotations.Component;
import com.bobocode.svydovets.annotation.annotations.Value;

@Component
public class AccountWithInvalidProperties {
    @Value("{badProperty}")
    public Long badProperty;
}
