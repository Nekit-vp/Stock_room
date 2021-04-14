package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBWorker {

    private static final String URL = "jdbc:postgresql://localhost:5432/Stock";
    private static final String LOGIN = "postgres";
    private static final String PASSWORD = "nikita89513523212";

    private Connection connection;

    public DBWorker(){

        try {
            connection = DriverManager.getConnection(URL, LOGIN, PASSWORD);
            System.out.println("Соединение установлено");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
