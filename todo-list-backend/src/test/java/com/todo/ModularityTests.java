package com.todo;

import org.junit.jupiter.api.Test;
import org.springframework.modulith.core.ApplicationModules;

class ModularityTests {

    @Test
    void verifyModularStructure() {
        ApplicationModules.of(TodoApplication.class).verify();
    }
}

