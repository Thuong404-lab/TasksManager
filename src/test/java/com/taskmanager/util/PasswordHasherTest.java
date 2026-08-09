package com.taskmanager.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PasswordHasherTest {
    @Test
    void hashesAndChecksBcryptPassword() {
        String hash = PasswordHasher.hash("password123");
        assertTrue(PasswordHasher.matches("password123", hash));
        assertFalse(PasswordHasher.matches("wrong-password", hash));
        assertFalse(PasswordHasher.needsUpgrade(hash));
    }

    @Test
    void supportsLegacyMd5DuringMigration() {
        assertTrue(PasswordHasher.matches("admin123", "0192023a7bbd73250516f069df18b500"));
        assertTrue(PasswordHasher.needsUpgrade("0192023a7bbd73250516f069df18b500"));
    }
}
