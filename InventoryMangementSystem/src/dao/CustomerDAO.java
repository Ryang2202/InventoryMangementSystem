package dao;

import java.sql.*;
import model.Customer;
import database.MyJDBC;
import javax.swing.*;

public class CustomerDAO {
    Connection connection;
    Statement statement;
    PreparedStatement prepStatement;
    ResultSet resultSet;

    public CustomerDAO() {
        try {
            connection = new MyJDBC().getConn();
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Methods to add new customer
    public boolean addCustomerDAO(Customer customer) {
        try {
            String query = "SELECT * FROM customer WHERE cus_id='"
                    + customer.getCusId()
                    + "'";
            resultSet = statement.executeQuery(query);
            if (resultSet.next())
                JOptionPane.showMessageDialog(null, "Customer already exists.");
            else{
                addFunction(customer);
                return(true);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return(false);
    }

    public void addFunction(Customer customer) {
        try {
            String query = "INSERT INTO customer (`cus_name`,`cus_id`) VALUES(?,?)";
            prepStatement = connection.prepareStatement(query);
            prepStatement.setString(1, customer.getCusName());
            prepStatement.setInt(2, customer.getCusId());
            prepStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "New customer has been added.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to edit existing customer details
    public void editCustomerDAO(Customer customer) {
        try {
            String query = "UPDATE customer SET cus_name=? WHERE cus_id=?";
            prepStatement = connection.prepareStatement(query);
            prepStatement.setString(1, customer.getCusName());
            prepStatement.setInt(2, customer.getCusId());

            prepStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Customer details updated.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to delete existing customer
    public void deleteCustomerDAO(int cusId) {
        try {
            String query = "DELETE FROM customer WHERE cus_id='" + cusId + "'";
            statement.executeUpdate(query);
            JOptionPane.showMessageDialog(null, "Customer removed.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to retrieve data set to be displayed
    public ResultSet getCustomerQueryResultForDisplay() {
        try {
            String query = "SELECT cus_name,cus_id FROM customer ORDER BY cus_id";
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    // Method to retrieve search data
    public ResultSet getCustomerByIdSearch(int cusId) {
        try {
            String query = "SELECT * FROM customer WHERE cus_id='" +cusId+ "'";
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public ResultSet getCustomerByNameSearch(String cusName) {
        try {
            String query = "SELECT * FROM customer WHERE cus_name='" +cusName+ "'";
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

}

