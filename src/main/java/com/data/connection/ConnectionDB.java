package com.data.connection;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class ConnectionDB {
    public static String Driver = "com.mysql.cj.jdbc.Driver";
    public static String URL = "jdbc:mysql://localhost:3306/session16?useSSL=false&serverTimezone=UTC";
    public static String USER = "root";
    public static String PASSWORD = "123456";
    public static Connection openConnection() {
        Connection conn = null;
        try {
            Class.forName(Driver);
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Kết nối thành công");
        } catch (Exception e) {
            e.fillInStackTrace();
        }
        return conn;
    }
    public static void closeConnection(Connection conn, CallableStatement callSt){
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        if (callSt != null) {
            try {
                callSt.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}