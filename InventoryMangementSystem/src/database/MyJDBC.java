package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;


public class MyJDBC {
    Connection connection;
    Statement statement;

    public Connection getConn() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/inventory_management_system", "root", "root");
            statement = connection.createStatement();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }
}
