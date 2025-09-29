package config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private String url = "jdbc:postgresql://localhost:5432/bankAppV2";
    private String username = "postgres";
    private String pass = "Wissam0908";

    public static DBConnection instance = null;
    private Connection connection = null;

    public static DBConnection getInstance() {
        if (instance == null)
            instance = new DBConnection();
        return instance;
    }

    private DBConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url, username, pass);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
