package org.example.trangdangnhap.util;

import java.sql.Connection;

public class DBConnectionTest {
    public static void main(String[] args) {
        try (Connection conn = DBConnection.getConnection()) {
            System.out.println("Kết nối thành công: " + (conn != null && !conn.isClosed()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


