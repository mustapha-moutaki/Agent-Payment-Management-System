package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DBConnection {
    private static Connection connection;

    private DBConnection() {}

    public static Connection getConnection() {
        if (connection == null) {
            try {
                // Load properties file
                Properties props = new Properties();
                FileInputStream fis = new FileInputStream("src/resources/database/config.properties");
                props.load(fis);

                // Get values
                String url = props.getProperty("db.url");// the same thing in the config.
                String user = props.getProperty("db.user");
                String password = props.getProperty("db.password");

                // Create connection
                connection = DriverManager.getConnection(url, user, password);
                System.out.println("Connected to database success!");

            } catch (SQLException e) {
                System.out.println("Database connection failed!");
                e.printStackTrace();
            } catch (IOException e) {
                System.out.println("Failed to read config.properties file!");
                e.printStackTrace();
            }
        }
        return connection;
    }
}
