package dao;

import database.MyJDBC;
import java.sql.*;

public class InventoryDAO {
    Connection connection;
    Statement statement;
    ResultSet resultSet;
    public InventoryDAO() {
        try {
            connection = new MyJDBC().getConn();
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to retrieve data set to be displayed
    public ResultSet getInventoryQueryResultForDisplay() {
        try {
            String query = "SELECT * FROM inventory ORDER BY pro_id";
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    // Method to retrieve search data
    public ResultSet getInvByIdSearch(int proId) {
        try {
            String query = "SELECT * FROM `inventory` WHERE `pro_id` = '"
                    + proId
                    + "'";
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }
}









