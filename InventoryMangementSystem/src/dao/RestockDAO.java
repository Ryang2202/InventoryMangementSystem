package dao;

import database.MyJDBC;
import model.Restock;
import javax.swing.*;
import java.sql.*;

public class RestockDAO {
    Connection connection;
    Statement statement;
    PreparedStatement prepStatement;
    ResultSet resultSet;

    public RestockDAO() {
        try {
            connection = new MyJDBC().getConn();
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Methods to add new restock

    public boolean addRestockDAO(Restock restock) {
        try {

            String query = "SELECT `restock`.`pro_id`,`restock`.`restock_quantity`,`restock`.`restock_date`,`restock`.`restock_id` FROM `restock` WHERE `restock`.`restock_id`='"
                    + restock.getRestockId()
                    + "'";
            resultSet = statement.executeQuery(query);
            if (resultSet.next())
                JOptionPane.showMessageDialog(null, "restock already exists.");
            else{
                addFunction(restock);
                return(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return(false);
    }
    public void addFunction(Restock restock) {
        try {
            String query = "INSERT INTO `restock`(`restock_id`,`restock_date`,`pro_id`,`restock_quantity`) VALUES(?,?,?,?)";
//            String query1 = "INSERT INTO restock (`restock_id`,`restock_date`,`pro_id``restock_quantity`) VALUES(?,?,?,?)";
            prepStatement = connection.prepareStatement(query);
            prepStatement.setInt(1, restock.getRestockId());
            prepStatement.setDate(2, restock.getRestockDate());
            prepStatement.setInt(3, restock.getProId());
            prepStatement.setInt(4, restock.getRestockQuantity());
            prepStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "New restock has been created.");

            // Update the inventory quantity for the product
            String query2 = "UPDATE inventory SET inv_quantity = inv_quantity + " + restock.getRestockQuantity() + " WHERE pro_id = " + restock.getProId();
//            String query3 = "UPDATE inventory SET `inv_quantity` = <{inv_quantity: }>,`pro_id` = <{pro_id: }>WHERE `pro_id` = <{expr}>";

            statement.executeUpdate(query2);
            JOptionPane.showMessageDialog(null, "Inventory quantity has been updated by restock.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // Method to delete existing restock
    public void deleteRestockDAO(int restockID) {
        try {
            // Get the product ID and quantity of the deleted restock

            String query = "SELECT `pro_id`,`restock_quantity` FROM `restock` WHERE `restock_id`='" + restockID + "'";
            resultSet = statement.executeQuery(query);
            int productId = 0;
            int restockQuantity = 0;
            if (resultSet.next()) {
                productId = resultSet.getInt("pro_id");
                restockQuantity = resultSet.getInt("restock_quantity");
            }

            // Delete the restock from the database
            query = "DELETE FROM `restock` WHERE `restock_id`='" + restockID + "'";
            statement.executeUpdate(query);
            JOptionPane.showMessageDialog(null, "Restock Record has been removed.");

            // Update the inventory quantity for the product
            query = "UPDATE inventory SET inv_quantity = inv_quantity - " + restockQuantity + " WHERE pro_id = " + productId;
            statement.executeUpdate(query);
            JOptionPane.showMessageDialog(null, "Inventory quantity has been updated.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to retrieve data set to be displayed
    public ResultSet getRestockQueryResultForDisplay() {
        try {
            String query = "SELECT * FROM restock ORDER BY restock_id";
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    // Method to retrieve search data
    public ResultSet getRestockByRestockIdSearch(int restockId) {
        try {
            String query = "SELECT * FROM restock WHERE restock_id='" +restockId+ "'";
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public ResultSet getRestockByProIdSearch(int productId) {
        try {
            String query = "SELECT * FROM restock WHERE pro_id='" +productId+ "'";
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

}
