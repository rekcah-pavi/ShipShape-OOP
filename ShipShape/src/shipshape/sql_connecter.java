package shipshape;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class sql_connecter {

    private static final String JDBC_URL = "";
    private static final String USERNAME = "";
    private static final String PASSWORD = "";
    private static final String DATABASE_NAME = "";
    private static Connection connection = null;

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);

            try (Statement statement = connection.createStatement()) {
                String createDatabaseQuery = "CREATE DATABASE IF NOT EXISTS " + DATABASE_NAME;
                statement.executeUpdate(createDatabaseQuery);
            }

            connection.close();
            String databaseUrl = JDBC_URL + "/" + DATABASE_NAME;
            connection = DriverManager.getConnection(databaseUrl, USERNAME, PASSWORD);
        }
        return connection;
    }
}
