package com.taskmanager.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionProvider {
    private static final String DRIVER_CLASS = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private static final String DEFAULT_URL = "jdbc:sqlserver://127.0.0.1:1433;databaseName=TaskManagerDB;encrypt=false";

    public DatabaseConnectionProvider() {
        loadDriver();
    }

    public Connection getConnection() {
        // Ưu tiên biến môi trường để không đưa thông tin nhạy cảm vào source khi deploy.
        String url = getConfig("TASK_DB_URL", DEFAULT_URL);
        String user = getConfig("TASK_DB_USER", "sa");
        String password = getConfig("TASK_DB_PASSWORD", "12345");
        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException ex) {
            throw new IllegalStateException("Khong the ket noi database. Kiem tra cac bien TASK_DB_*.", ex);
        }
    }

    private String getConfig(String name, String defaultValue) {
        String value = System.getenv(name);
        return value == null || value.isBlank() ? defaultValue : value;
    }

    private void loadDriver() {
        try {
            // Tomcat có thể không tự đăng ký driver nằm trong WEB-INF/lib.
            Class.forName(DRIVER_CLASS);
        } catch (ClassNotFoundException ex) {
            throw new IllegalStateException(
                    "Khong tim thay SQL Server JDBC Driver trong ung dung.", ex);
        }
    }
}
