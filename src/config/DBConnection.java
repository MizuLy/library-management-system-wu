package config;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    private static String URL = "jdbc:mysql://localhost:3306/test01";
    private static String USER = "root";
    private static String PASSWORD = "root";
    private static Connection con;

    public static Connection getConnection() {
        try {
            con = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            System.out.println("Database error!");
        }
        return con;
    }
}