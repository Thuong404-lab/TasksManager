package com.taskmanager.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class InputValidatorTest {
    @Test
    void acceptsValidInputs() {
        assertNull(InputValidator.validateUser("student01", "password1", "Nguyen Van A", "a@example.com", "user", true));
        assertNull(InputValidator.validatePriority("Cao", "#DC3545"));
        assertNull(InputValidator.validateTask("Hoan thanh bao cao", "2026-08-10", "doing"));
    }

    @Test
    void rejectsInvalidInputs() {
        assertNotNull(InputValidator.validateUser("x", "123", "A", "bad", "owner", true));
        assertNotNull(InputValidator.validatePriority("Cao", "red;display:none"));
        assertNotNull(InputValidator.validateTask("", "not-a-date", "unknown"));
        assertNull(InputValidator.parsePositiveInt("0"));
        assertNull(InputValidator.parsePositiveInt("abc"));
        assertEquals("#6C757D", InputValidator.safeColor("red;display:none"));
    }
}
