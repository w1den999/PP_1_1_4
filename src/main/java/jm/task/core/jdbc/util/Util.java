package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String HOST = "jdbc:mysql://localhost:3306/dbtest";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "1997";


    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(HOST, USERNAME, PASSWORD);
    }
}
