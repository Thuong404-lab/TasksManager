package com.taskmanager.config;

import java.sql.Driver;
import java.sql.DriverManager;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DatabaseConnectionProviderTest {

    @Test
    void registersSqlServerDriver() throws Exception {
        new DatabaseConnectionProvider();
        Driver driver = DriverManager.getDriver("jdbc:sqlserver://127.0.0.1:1433");
        assertEquals("SQLServerDriver", driver.getClass().getSimpleName());
    }
}
