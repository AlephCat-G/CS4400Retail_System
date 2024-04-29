package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnector {

    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/drone_dispatch";
    private static final String DATABASE_USER = "root";
    private static final String DATABASE_PASSWORD = "965979Hl!";

    public static Connection getConnection() throws SQLException {
        Properties connectionProps = new Properties();
        connectionProps.put("user", DATABASE_USER);
        connectionProps.put("password", DATABASE_PASSWORD);

        // Set additional properties
        connectionProps.put("useSSL", "false");
        connectionProps.put("allowPublicKeyRetrieval", "true");

        // Register MySQL JDBC driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // Connect to the database
        Connection conn = DriverManager.getConnection(DATABASE_URL, connectionProps);
        System.out.println("Connection to MySQL database successful!");

        return conn;
    }

    public static void main(String[] args) {
        try {
            Connection connection = DatabaseConnector.getConnection();
            if (connection != null) {
                System.out.println("Connection successful!");
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Connection failed: " + e.getMessage());
        }
    }

}
