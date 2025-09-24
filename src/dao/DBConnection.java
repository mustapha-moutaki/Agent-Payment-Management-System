package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static Connection connection;

    // Private constructor to prevent creating new instances
    private DBConnection() {}

    public static Connection getConnection() {
        if (connection == null) {
            try {

                String url = "jdbc:mysql://localhost:3307/java";
                String user = "user";
                String password = "user";

                connection = DriverManager.getConnection(url, user, password);
                System.out.println("Connected to database!");

            } catch (SQLException e) {
                System.out.println("Failed to connect to database:");
                e.printStackTrace();
            }
        }
        return connection;
    }
}
