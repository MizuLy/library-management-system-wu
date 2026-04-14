package config;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {
    private static String URL = "jdbc:mysql://localhost:3306/LMSJAVA";
    private static String USER = "mizu";
    private static String PASSWORD = "password";
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