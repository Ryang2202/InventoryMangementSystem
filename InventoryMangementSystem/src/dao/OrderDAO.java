package dao;

import database.MyJDBC;
import model.Order;
import javax.swing.*;
import java.sql.*;

public class OrderDAO {
    Connection connection;
    Statement statement;
    PreparedStatement prepStatement1;
    ResultSet resultSet;
    ProductDAO productDao;
    InventoryDAO inventoryDao;

    public OrderDAO() {
        try {
            connection = new MyJDBC().getConn();
            productDao = new ProductDAO();
            inventoryDao = new InventoryDAO();
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Methods to add new order

    public boolean addOrderDAO(Order order) {
        try {
            String query = "SELECT `order`.`order_id`FROM `order` WHERE `order_id` = '"
                    + order.getOrderId()
                    + "'";
            resultSet = statement.executeQuery(query);
            if (resultSet.next())
                JOptionPane.showMessageDialog(null, "Order already exists.");
            else {
                addFunction(order);
                return(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return(false);
    }

    public void addFunction(Order order) {
        try {
            // Calculate the order total
            double sellPrice = productDao.getProSellPrice(order.getProId());
            double orderTotal = order.getOrderQuantity() * sellPrice;

            //check order quantity valid
            if (order.getOrderQuantity() <=0){
                JOptionPane.showMessageDialog(null, "Please enter a valid quantity");
            }

            // Insert the new order
            String query1 = "INSERT INTO `order`(`order_id`,`order_date`,`cus_id`,`pro_id`,`order_quantity`,`sell_price`, `order_total`) VALUES(?,?,?,?,?,?,?)";
            prepStatement1 = connection.prepareStatement(query1);
            prepStatement1.setInt(1, order.getOrderId());
            prepStatement1.setDate(2, order.getOrderDate());
            prepStatement1.setInt(3, order.getCusId());
            prepStatement1.setInt(4, order.getProId());
            prepStatement1.setInt(5, order.getOrderQuantity());
            prepStatement1.setDouble(6, sellPrice);
            prepStatement1.setDouble(7, orderTotal);
            prepStatement1.executeUpdate();
            JOptionPane.showMessageDialog(null, "New order has been added.");

            // Update the inventory quantity for the product
            String query2 = "UPDATE inventory SET inv_quantity = inv_quantity - " + order.getOrderQuantity() + " WHERE pro_id = " + order.getProId();
            statement.executeUpdate(query2);
            JOptionPane.showMessageDialog(null, "Inventory quantity has been updated by order.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    // Method to delete existing order
    public void deleteOrderDAO(int orderID) {
        try {
            // Get the product ID and quantity of the deleted order

            String query = "SELECT `pro_id`,`order_quantity` FROM `order` WHERE `order_id`='" + orderID + "'";
            resultSet = statement.executeQuery(query);
            int productId = 0;
            int orderQuantity = 0;
            if (resultSet.next()) {
                productId = resultSet.getInt("pro_id");
                orderQuantity = resultSet.getInt("order_quantity");
            }

            // Delete the order from the database
            query = "DELETE FROM `order` WHERE `order_id`='" + orderID + "'";
            statement.executeUpdate(query);
            JOptionPane.showMessageDialog(null, "Order has been removed.");

            // Update the inventory quantity for the product
            query = "UPDATE inventory SET inv_quantity = inv_quantity + " + orderQuantity + " WHERE pro_id = " + productId;
            statement.executeUpdate(query);
            JOptionPane.showMessageDialog(null, "Inventory quantity has been updated.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    // Method to retrieve data set to be displayed
    public ResultSet getOrderQueryResultForDisplay() {
        try {
            String query = "SELECT `order_id`,`order_date`,`cus_id`,`pro_id`,`order_quantity`,`sell_price`, `order_total` FROM `order`";
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    // Method to retrieve search data
    public ResultSet getOrderByOrderIdSearch(int orderId) {
        try {
            String query = "SELECT * FROM `order` WHERE `order_id` = '"
                    + orderId
                    + "'";
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public ResultSet getOrderByCustomerIdSearch(int cusId) {
        try {
            String query = "SELECT * FROM `order` WHERE `cus_id` = '"
                    + cusId
                    + "'";
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }
}
