package org.example.trangdangnhap.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class DBConnection {
    // Ví dụ URL: thêm encrypt và trustServerCertificate nếu dùng SSL tự ký
    // jdbc:sqlserver://localhost:1433;databaseName=DBUser;encrypt=false
    private static final String JDBC_URL = "jdbc:sqlserver://localhost:1433;databaseName=DBUser;encrypt=false";
    private static final String JDBC_USER = "sa"; // chỉnh theo SQL Server của bạn
    private static final String JDBC_PASSWORD = "1234"; // chỉnh theo SQL Server của bạn

    static {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("SQL Server JDBC Driver not found", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
    }

    private DBConnection() {
    }
}


